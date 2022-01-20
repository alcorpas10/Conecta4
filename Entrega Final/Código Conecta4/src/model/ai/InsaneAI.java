package model.ai;

import java.awt.Color;
import model.MatchMaker;
import model.Tablero;

public class InsaneAI implements ComputerInterface {

	private static final Color defComputerColor = Color.BLUE;
	private int nextCol;
	
	//Distribucion normal gausiana alrededor del centro del tablero
	private int[][] lookUpTable = { { 3, 4, 5, 7, 5, 4, 3 }, 
									{ 4, 6, 8, 10, 8, 6, 4 },
									{ 5, 8, 11, 13, 11, 8, 5 }, 
									{ 5, 8, 11, 13, 11, 8, 5 }, 
									{ 4, 6, 8, 10, 8, 6, 4 }, 
									{ 3, 4, 5, 7, 5, 4, 3 } 
								   };


	@Override
	public int playTurnAI(Tablero tab) {
		int bestScore = Integer.MIN_VALUE, score = 0;
		int bestCol = 0;
		int bestRow = -1, fila;

		if (!tab.fullBoard()) {
			for (int i = 0; i < Tablero.defNumCols; i++) {
				if (!tab.fullColumn(i)) {
					
					fila = tab.colocarFicha(MatchMaker.enemy.getColor(), i);

					score = minimax(tab, 8, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
					tab.descolocar(fila, i);

					if (score > bestScore) {
						bestScore = score;
						bestCol = i;
					}
				}
			}
			nextCol = bestCol;
			bestRow = tab.colocarFicha(defComputerColor, nextCol);
		}
		tab.disable();

		return bestRow;
	}

	private int minimax(Tablero tab, int depth, boolean maximizing, int alpha, int beta) {
		int score;
		if (MatchMaker.hostWins()) {
			score = Integer.MIN_VALUE;
			return score;
		} 
		else if (MatchMaker.enemyWins()) {
			score = Integer.MAX_VALUE;
			return score;
		}
		else if(tab.fullBoard()) {
			return 0;
		}
		else if(depth == 0)	{
			return scoreEvaluation(tab);
		}

		if (maximizing) {
			int bestScore = Integer.MIN_VALUE, fila;
			for (int i = 0; i < Tablero.defNumCols; i++) {
				if (!tab.fullColumn(i)) 
				{
					fila = tab.colocarFicha(MatchMaker.enemy.getColor(), i);
					score = minimax(tab, depth - 1, false, alpha, beta);
					tab.descolocar(fila, i);

					bestScore = Math.max(bestScore, score);
					alpha = Math.max(score, alpha);
					if (beta <= alpha)
						break;
				}
			}
			return bestScore;
		} 
		else {
			int bestScore = Integer.MAX_VALUE, fila;
			for (int i = 0; i < Tablero.defNumCols; i++) {
				if (!tab.fullColumn(i)) {
					fila = tab.colocarFicha(MatchMaker.host.getColor(), i);
					score = minimax(tab, depth - 1, true, alpha, beta);
					tab.descolocar(fila, i);

					bestScore = Math.min(bestScore, score);
					beta = Math.min(score, beta);
					if (beta <= alpha)
						break;
				}
			}
		return bestScore;
		}
	}
	
	private int scoreEvaluation(Tablero tab) 
	{
		int factor = 138; //276 = 2 * 138
		int res = 0;
		for (int i = 0; i < Tablero.defNumRows; i++)
		{
			for (int j = 0; j < Tablero.defNumCols; j++)
			{
				if(tab.getFicha(i, j) != null)
				{
					if (tab.getFicha(i, j).getColor().equals(MatchMaker.enemy.getColor())) {
						res += lookUpTable[i][j];}
					else if (tab.getFicha(i, j).getColor().equals(MatchMaker.host.getColor())) {
						res -= lookUpTable[i][j];}
				}
			}
		}
		
		return factor + res;
	}

	@Override
	public String computerName() 
	{
		return "Terminator";
	}

	@Override
	public int selectedColumnbyAI() 
	{
		return nextCol;
	}

}
