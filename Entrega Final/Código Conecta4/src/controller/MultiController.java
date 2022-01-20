package controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import view.MainView;
import view.MultiView;

public class MultiController implements Runnable, Controller {
	
	private Socket socket;
	private DataOutputStream out;
	private DataInputStream in;
	private Thread hilo;
	private int numPlayer;
	private MultiView vista;
	private int columna;
	private boolean jugando;
	
	public MultiController() {
		socket = null;
		out = null;
		in = null;
	}
	
	public void setMultiView(MultiView vista) {
		this.vista = vista;
	}
	
	public void iniciarSocket(int puerto, String direccion) throws UnknownHostException, IOException {
		socket = new Socket(direccion, puerto);
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
		hilo = new Thread(this);
		hilo.start();
	}
	
	public void playTurnG(int col)
	{
		try {
			out.writeInt(col);
			columna = col;
		} catch (IOException e) {}
	}
	
	@Override
	public void run() {
		jugando = true;
		TextProcessor tp = new TextProcessor();
		try {
			numPlayer = in.readInt();
			vista.setLabelText("Eres el jugador " + (numPlayer + 1));
			if (numPlayer == 1)
				vista.setButtonsEnabled(false);
		} catch(IOException e) {
			e.printStackTrace();
		}
		while(jugando) {
			try {
				String s = in.readUTF();
				tp.processMessage(s);
			} catch(Exception e) {
				JOptionPane.showMessageDialog(vista, "Error en la conexion, desconectado del servidor","Error red", JOptionPane.ERROR_MESSAGE);
				tp.finPartida();
			}
		}
	}
	
	private class TextProcessor { //Clase encargada de realizar distintas acciones en funcion de la informacion recibida del servidor
		public synchronized void processMessage(String s) { 
			if (s.equalsIgnoreCase("Jugada valida"))
				jugadaCorrecta();
			else if (s.equalsIgnoreCase("Jugada no valida"))
				jugadaIncorrecta(s);
			else if (s.equalsIgnoreCase("El oponente jugo"))
				jugadaDelOponente();
			else if (s.equalsIgnoreCase("Fin de la partida!"))
				finPartida();
			else if (s.equalsIgnoreCase("Oponente conectado. Te toca jugar")) 
				oponenteConectado(s);
			else if (s.equalsIgnoreCase("Esperando otro jugador"))
				oponenteNoConectado(s);
			else if (s.equalsIgnoreCase("El oponente gano")) {
				ultimaJugadaEnemiga();
				vista.appendText(s + "\n");
				finPartida();
			}
			else if (s.equalsIgnoreCase("Ganaste")) {
				ultimaJugadaAmiga();
				vista.appendText(s + "\n");
				finPartida();
			}
			else
				vista.appendText(s + "\n");
		}
		
		private void jugadaCorrecta() {
			vista.appendText("Jugada valida, espera por favor\n");
			vista.setButtonsEnabled(false);
			vista.ponerFicha(numPlayer, columna);
			vista.redraw();
		}
		
		private void jugadaIncorrecta(String s) {
			vista.appendText(s + "\n");
		}
		
		private void ultimaJugadaEnemiga() {
			try {
				int loc = in.readInt();
				vista.ponerFicha((numPlayer + 1) % 2, loc);
				vista.redraw();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void ultimaJugadaAmiga() {
			try {
				int loc = in.readInt();
				vista.ponerFicha(numPlayer, loc);
				vista.redraw();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		private void jugadaDelOponente() {
			try {
				int loc = in.readInt();
				vista.ponerFicha((numPlayer + 1) % 2, loc);
				vista.appendText("El oponente ha jugado, te toca\n");
				vista.setButtonsEnabled(true);
				vista.redraw();
			} catch(IOException e) {}
		}
		
		private void finPartida() {
			jugando = false;
			try {
				in.close();
				out.close();
				socket.close();
				vista.newMessage("Fin de la partida volveras al menu de multijugador");
				vista.setText("");
				vista.resetTab();
			} catch (IOException e) {
				e.printStackTrace();
			}
			MainView.changeToPanel(4);
		}
		
		private void oponenteConectado(String s) {
			vista.appendText(s + "\n");
			vista.setButtonsEnabled(true);
		}
		
		private void oponenteNoConectado(String s) {
			vista.appendText(s + "\n");
			vista.setButtonsEnabled(false);
		}
	}
}