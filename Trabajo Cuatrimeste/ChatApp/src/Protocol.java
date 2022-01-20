
public class Protocol {
    private static final int PLAYING = 0;
    private static final int FINISHED = 1;
    private static final int STARTING = 2;
    
    private int state = STARTING;

    public String processInput(String theInput, int jugada) {
        String theOutput = null;
        if (state == STARTING) {
        	theOutput = "Comenzando partida: Eres el jugador numero ";
        	state = PLAYING;
        }
        else if (state == PLAYING) {
        	/*if (theInput.equalsIgnoreCase("exit")) {
        		state = FINISHED;
        		theOutput = "Juego finalizado";
        	}*/
        	/*else*/ theOutput = "Turno del jugador: " + ((jugada % 2) + 1);
        }
        else if (state == FINISHED) 
        	theOutput = "Juego finalizado";
        else
        	theOutput = "Error";
        return theOutput;
    }
    
    public int getStatus() {
    	return state;
    }
}