package observers;

import java.awt.Color;
import java.util.List;

import model.InfoObj;
import model.Jugador;
import utils.Coordinate;

public interface Observer {

	public void update(Color c, Coordinate coor);
	public void reload(InfoObj infoObj);
	public void onWinner(InfoObj infoObj);
	public void onDraw(InfoObj infoObj);
	public void onAITurn();
	public void onAIFinish();
	public void onNameUpdate(InfoObj infoObj);
	public void updateScoreboard(List<Jugador> list);
}
