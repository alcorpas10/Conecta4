package launcher;

import multiplayer.Server;

public class ServerMain {

	public static final int PORTNUMBER = 55555;
	
	public static void main(String[] args) { //Main que lanza el servidor para el multijugador online
        Server server = new Server(PORTNUMBER);
        server.jugar();
    }

}
