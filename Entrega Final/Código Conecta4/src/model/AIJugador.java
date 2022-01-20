package model;

import java.awt.Color;

import model.ai.ComputerInterface;
import model.ai.InsaneAI;
import model.ai.MediumAI;
import model.ai.WeakAI;
import utils.AILevel;

public class AIJugador implements PlayerInterface  {

	private ComputerInterface compBehaviour; 
	
	public AIJugador() {}
	
	public int getNextCol() {
		return this.compBehaviour.selectedColumnbyAI();
	}
	
	public void setBehavior(AILevel lev) {
		switch (lev) {
		case EASY:
			compBehaviour = new WeakAI();
			break;
		case MEDIUM:
			compBehaviour = new MediumAI();
			break;
		case HARD:
			compBehaviour = new InsaneAI();
			break;
		}
	}

	@Override
	public int playTurn(Tablero tab, int col) {
		return this.compBehaviour.playTurnAI(tab);
	}

	@Override
	public String getId() {
		return this.compBehaviour.computerName();
	}

	@Override
	public void setId(String id) {}

	@Override
	public void setColor(Color colorPiece) {}
}
