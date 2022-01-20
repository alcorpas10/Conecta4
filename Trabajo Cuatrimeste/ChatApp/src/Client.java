
import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        
        if (args.length != 2) {
            System.err.println(
                "Usage: java Client <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);

        try (Socket kkSocket = new Socket(hostName, portNumber);
	        PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
	        BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));) {
            BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
            String fromServer;
            String fromUser;
            int numeroJugador, jugada = 0;
            fromServer = in.readLine();
    		System.out.println(fromServer);
            numeroJugador = Integer.parseInt(fromServer.split("numero ")[1]);
            do {
            	if (jugada % 2 == numeroJugador - 1) {
            		System.out.println("Que deseas hacer?: ");
        			fromUser = stdIn.readLine();
                    out.println(fromUser);
                    if (fromUser.equalsIgnoreCase("salir")) {
                    	fromServer = in.readLine();
                    	System.out.println("Server: " + fromServer);
                    	break;
                    }
        		}
            	else {
            		System.out.println("Espere a que responda el otro jugador...");
                	fromServer = in.readLine();
                	if (fromServer.equalsIgnoreCase("Juego finalizado")) {
                    	System.out.println("Server: " + fromServer);
                    	break;
                	}
                	System.out.println("Jugador: " + fromServer);
            	}
            	jugada++;
            } while (fromServer != null);
            System.out.println("");
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }
}