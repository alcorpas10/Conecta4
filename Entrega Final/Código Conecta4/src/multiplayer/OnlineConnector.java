package multiplayer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class OnlineConnector extends Thread {
	private DataOutputStream out;
	private DataInputStream in;
	private Socket socket;
	private Server server;
	private int numPlayer;
	private boolean done;
	
	public OnlineConnector(Socket s, Server servidor, int number) {
		socket = s;
		done = false;
		try {
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		server = servidor;
		numPlayer = number;
	}
	
	public void otherPlayerMoved(int loc) {
		try {
			out.writeUTF("El oponente jugo");
			out.writeInt(loc);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void youWon(int loc) {
		try {
			out.writeUTF("Ganaste");
			out.writeInt(loc);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void otherPlayerWon(int loc) {
		try {
			out.writeUTF("El oponente gano");
			out.writeInt(loc);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public void run() { //Codigo que ejecutara el hilo creado para administrar la partida online
		try {
			server.setText("Jugador " + numPlayer + " conectado");
			out.writeInt(numPlayer);
			out.writeUTF("Jugador " + (numPlayer == 0 ? "1 conectado" : "2 conectado, espera por favor"));
			if (server.getNumberOfPlayers() < 2) {
				out.writeUTF("Esperando otro jugador");
				while(server.getNumberOfPlayers() < 2);
				out.writeUTF("Oponente conectado. Te toca jugar");
			}
			while(!done) {
				int location = 0;
				location = in.readInt();
				if (server.validMove(location, numPlayer)) {
					server.setText("Columna: " + location);
					if (server.finished()) {
						server.setText("Fin de la partida\n");
						done = true;
					}
					else
						out.writeUTF("Jugada valida");
				}
				else 
					out.writeUTF("Jugada no valida");	
			}
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			server.endGame();
		}
	}
}
