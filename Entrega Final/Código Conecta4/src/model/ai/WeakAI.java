package model.ai;

import java.awt.Color;
import java.util.Random;

import model.Tablero;

public class WeakAI implements ComputerInterface {

	private static final Color defComputerColor = Color.BLUE;
	private int nextCol;
	
	@Override
	public int playTurnAI(Tablero tab) {
		int fila = -1;
		if (!tab.fullBoard()) {
			Random randomizer = new Random();
			nextCol = randomizer.nextInt(Tablero.defNumCols);
			while (tab.fullColumn(nextCol)) {
				nextCol = randomizer.nextInt(Tablero.defNumCols);
			}
			
			fila = tab.colocarFicha(defComputerColor, nextCol);
		}
		return fila;
	}

	@Override
	public String computerName() {
		return "Wall-E";
	}

	@Override
	public int selectedColumnbyAI() {
		return nextCol;
	}

}
