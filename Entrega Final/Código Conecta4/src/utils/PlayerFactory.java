package utils;

import java.awt.Color;

import model.Jugador;

public class PlayerFactory {

	public static Jugador createHJugador(String id, Color c, String nomColor) {
		return new Jugador(id, c, nomColor, PlayerType.jugador);
	}
	
	public static Jugador createAIJugador(AILevel lev) {
		Jugador ai = new Jugador(PlayerType.CPU);
		ai.setBehavior(lev);
		return ai;
	}
}
