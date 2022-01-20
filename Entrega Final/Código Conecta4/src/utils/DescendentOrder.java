package utils;

import java.util.Comparator;

import model.Jugador;

public class DescendentOrder implements Comparator<Jugador> {

	public int compare(Jugador j1, Jugador j2) {
		if(j1.getPoints()>j2.getPoints())return -1;
		else if (j1.getPoints()<j2.getPoints()) return 1;
		else {
			if(j1.getTime()>j2.getTime())return 1;
			else if (j1.getTime()<j2.getTime()) return -1;
			else return 0;
		}
	}

}