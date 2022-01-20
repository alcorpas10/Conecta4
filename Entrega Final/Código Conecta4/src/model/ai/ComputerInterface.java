package model.ai;

import model.Tablero;

public interface ComputerInterface {

	int playTurnAI(Tablero tab);
	int selectedColumnbyAI();
	String computerName();
}
