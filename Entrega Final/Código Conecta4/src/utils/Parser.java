package utils;

import java.awt.Color;

import org.json.JSONArray;
import org.json.JSONObject;

import model.Jugador;
import model.Tablero;

public class Parser {

	public static Jugador parsePlayer(JSONObject o) {
		if(o == null) return null;
		else {
			Jugador j = o.getBoolean("ai") ? new Jugador("", Color.YELLOW, PlayerType.CPU) : new Jugador("", Color.RED, PlayerType.jugador);
			String color = o.getString("color_piece");
			Color c = new Color(Integer.parseInt(color));
			j.setColor(c);
			
			String id = o.getString("id");
			j.setId(id);
			
			int points = o.getInt("points");
			j.setPoints(points);
			
			return j;
		}
	}
	
	public static Tablero parseBoard(JSONArray arr) {
		Tablero t = new Tablero();
		for(int i = 0; i < Tablero.defNumRows; i++) {
			JSONArray row = arr.getJSONArray(i);
			
			for(int j = 0; j < Tablero.defNumCols; j++) {
				String color = row.getString(j);
				Color c = new Color(Integer.parseInt(color));
				t.setFicha(new Coordinate(i, j), c);
			}
		}
		
		return t;
	}
	
	public static Jugador parsePlayerScoreBoard(JSONObject o) {
		if(o == null) return null;
		else {
			String id = o.getString("id");
			int time = o.getInt("time");
			int points = o.getInt("points");

			return o.getBoolean("ai") ? new Jugador(id, points, time, PlayerType.CPU) : new Jugador(id, points, time,PlayerType.jugador);
		}
	}
}
