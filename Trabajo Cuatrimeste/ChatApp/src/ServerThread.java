
import java.net.*;
import java.io.*;

public class ServerThread extends Thread {
    private Socket socket1;
    private Socket socket2;

    public ServerThread(Socket socket1, Socket socket2) {
        super("ServerThread");
        this.socket1 = socket1;
        this.socket2 = socket2;
    }
    
    public void run() {
        int jugada = 0;
    	System.out.println("Nueva partida iniciada");
        try (PrintWriter out1 = new PrintWriter(socket1.getOutputStream(), true);
        		BufferedReader in1 = new BufferedReader(new InputStreamReader(socket1.getInputStream()));
        		PrintWriter out2 = new PrintWriter(socket2.getOutputStream(), true);
        		BufferedReader in2 = new BufferedReader(new InputStreamReader(socket2.getInputStream()));) {
            String inputLine;
            out1.println("Comenzando partida: Eres el jugador numero " + 1);
            out2.println("Comenzando partida: Eres el jugador numero " + 2);
            while (true) {
            	//outputLine = protocol.processInput(inputLine, i);
            	//out1.println(outputLine);
                if ((jugada % 2) + 1 == 1) {
                	inputLine = in1.readLine();
                	if (inputLine.equalsIgnoreCase("salir")) {
                		out1.println("Juego finalizado");
                		out2.println("Juego finalizado");
                		break;
                	}
                    out2.println(inputLine);
            	}
                else {
                	inputLine = in2.readLine();
                	if (inputLine.equalsIgnoreCase("salir")) {
                		out1.println("Juego finalizado");
                		out2.println("Juego finalizado");
                		break;
                	}
                    out1.println(inputLine);
                }
                jugada++;
            }
            socket2.close();
            socket1.close();
            System.out.println("Conexion finalizada");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}