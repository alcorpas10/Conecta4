package multiplayer;

import java.awt.BorderLayout;
import java.awt.ScrollPane;
import java.io.IOException;
import java.net.ServerSocket;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import model.MatchMaker;
import model.Tablero;

public class Server extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int MAX_PLAYERS = 2;
	
	private Tablero tab;
	private OnlineConnector players[];
	private ServerSocket serverSocket;
	private int numberOfPlayers, currentPlayer;
	private JTextArea areaTexto;
	private int portNumber;
	
	public Server(int portNumber) {
		super("Servidor Conecta 4");
		tab = new Tablero();
		players = new OnlineConnector[MAX_PLAYERS];
		currentPlayer = 0;
		this.portNumber = portNumber;
		initGUI();
		initServer();
	}
	
	private void initGUI() {
		this.setLayout(new BorderLayout());
		this.setSize(450, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		
		areaTexto = new JTextArea();
		areaTexto.setEditable(false);
		
		ScrollPane scroll = new ScrollPane();
		scroll.add(areaTexto);
		this.add(scroll, BorderLayout.CENTER);
		
		this.setVisible(true);
	}
	
	private void initServer() {
		try {
			serverSocket = new ServerSocket(portNumber, MAX_PLAYERS);
			areaTexto.append("Servidor iniciado en puerto " + portNumber + "\n");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void jugar() {
		while(true) {
			for (int i = 0; i < players.length; i++) {
				try {
					players[i] = new OnlineConnector(serverSocket.accept(), this, i);
					players[i].start();
					numberOfPlayers++;
				} catch(Exception e) {}
			}
			while(getNumberOfPlayers() == MAX_PLAYERS) {
				try {
					Thread.sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public synchronized int getNumberOfPlayers() {
		return numberOfPlayers;
	}
	
	public void setText(String texto) {
		areaTexto.append(texto + "\n");
	}
	
	public synchronized boolean validMove(int loc, int player) {
		try {
			int row = tab.colocarFicha((currentPlayer == 0 ? MatchMaker.defHostColor : MatchMaker.defEnemyColor), loc);
			if (row != -1) {
				tab.chequearFinPartida(currentPlayer == 0 ? MatchMaker.defHostColor : MatchMaker.defEnemyColor);
				currentPlayer++;
				currentPlayer %= MAX_PLAYERS;
				if (tab.connectFour())
					finishGame(loc);
				else
					changeTurn(loc);
				return true;
			}
			else return false;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	private synchronized void changeTurn(int loc) {
		players[currentPlayer].otherPlayerMoved(loc);
		notify();
	}
	
	public synchronized void finishGame(int loc){
		players[currentPlayer].otherPlayerWon(loc);
		players[(currentPlayer + 1) % MAX_PLAYERS].youWon(loc);
		notify();
	}
	
	public boolean finished() {
		if (tab.fullBoard() || tab.connectFour()) {
			endGame();
			return true;
		}
		else return false;
	}
	
	public void endGame() {
		tab = new Tablero();
		players = new OnlineConnector[MAX_PLAYERS];
		currentPlayer = 0;
		numberOfPlayers = 0;
	}
}
