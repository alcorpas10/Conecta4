package launcher;

import javax.swing.SwingUtilities;

import controller.MainController;
import model.MatchMaker;
import view.MainView;

public class Main {

	public static void main(String[] args) { //Main que lanza el programa principal
		MatchMaker mt = new MatchMaker();
		MainController c = new MainController(mt);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainView(c); 
			}
		});
	}
}
