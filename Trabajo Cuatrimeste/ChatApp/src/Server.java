
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Server {
	
	private final static int MAX_PARTIDAS = 3;
	
    public static void main(String[] args) throws IOException {
	    if (args.length != 1) {
	        System.err.println("Usage: java Server <port number>");
	        System.exit(1);
	    }
        int portNumber = Integer.parseInt(args[0]);
        int numPartidas = 0;
        Socket j1 = null, j2 = null;
        
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
        	System.out.println("Servidor iniciado en puerto " + portNumber);
        	Scanner sc = new Scanner(System.in);
            while (numPartidas != MAX_PARTIDAS) {
            	j1 = serverSocket.accept();
	        	System.out.println("Nueva partida creada");
                System.out.println("El jugador1 se ha unido");
            	System.out.println("Esperando jugador 2...");
            	j2 = serverSocket.accept();
	            new ServerThread(j1, j2).start();
	        	numPartidas++;
	        }
            sc.close();
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}