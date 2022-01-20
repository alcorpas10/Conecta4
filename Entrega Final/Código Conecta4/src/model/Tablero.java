package model;

import java.awt.Color;
import utils.Coordinate;

public class Tablero 
{
	public static final int defNumRows = 6;
	public static final int defNumCols = 7;
	private boolean winner;
	
	private Ficha[][] tab;
	
	/*	DISPOSICION DE TABLERO ESTANDAR
	 		
	 		[0][0] 	[0][1] 	[0][2] 	[0][3] 	[0][4] 	[0][5] 	[0][6]
	 		
	 		[1][0] 	[1][1] 	[1][2] 	[1][3] 	[1][4] 	[1][5] 	[1][6] 	

 		 	[2][0] 	[2][1] 	[2][2] 	[2][3] 	[2][4] 	[2][5] 	[2][6] 	

	 		[3][0] 	[3][1] 	[3][2] 	[3][3] 	[3][4] 	[3][5] 	[3][6] 	

	 		[4][0] 	[4][1] 	[4][2] 	[4][3] 	[4][4] 	[4][5] 	[4][6] 	
	 		
	 		[5][0] 	[5][1] 	[5][2] 	[5][3] 	[5][4] 	[5][5] 	[5][6]	
	 */
	
	public Tablero(Tablero t) //CONSTRUCTOR POR COPIA
	{
		this.tab = new Ficha[defNumRows][defNumCols];
		for(int i = 0; i < defNumRows; i++) {
			for(int j = 0; j < defNumCols; j++) {
				if(t.getFicha(i, j) != null)
					tab[i][j] = t.getFicha(i, j);
				else
					tab[i][j] = null;
			}
		}
		winner = t.getWinner();
	}
	
	public Tablero() {
		super();
		this.tab = new Ficha[defNumRows][defNumCols];
		winner=false;
	}
	
	public void disable() {
		winner = false;
	}
	
	public Ficha getFicha(int i, int j) {
		return tab[i][j];
	}
	
	public void setFicha(Coordinate coor, Color c) {
		if(c.equals(Color.WHITE))
			tab[coor.getRow()][coor.getCol()] = null;
		else
			tab[coor.getRow()][coor.getCol()] = new Ficha(c, coor);
	}
	
	public boolean getWinner() {
		return winner;
	}
	
	public int colocarFicha(Color color, int colB) { //devuelve la fila que toca
		int rowB = lastRow(colB);
		
		if(rowB <= -1)
			throw new IllegalArgumentException("Invalid column to place the piece! Full column");
		tab[rowB][colB] = new Ficha(color, new Coordinate(rowB, colB));
		
		return rowB;
	}
	
	public void descolocar(int fila, int col) {
		tab[fila][col] = null;
	}
	
	public int lastRow(int colB) {
		int rowB = 0;
		if(colB >= 7 || colB < 0) {
			throw new IllegalArgumentException("Invalid column to place the piece! Expected: 0 to 6. Found: " + colB);
		}
		
		if(tab[rowB][colB] != null) rowB = -1;
		else {
			while(rowB < defNumRows && tab[rowB][colB] == null) {
				rowB++;
			}
		}
		
		return rowB - 1;
	}
	
	public boolean fullBoard() {
		//comprueba si la fila 0 está llena en vez de toda la matriz
		int colB = 0;
		
		while(colB < defNumCols && tab[0][colB] != null) {
			colB++;
		}
		
		return colB == defNumCols;
	}
	
	public boolean fullColumn(int col) {
		return tab[0][col] != null;
	}
	
	void reset() {
		this.tab = new Ficha[defNumRows][defNumCols];
		winner=false;
	}
	
	public boolean connectFour() {
		return winner;
	}
	
	public void chequearFinPartida(Color c) {
		for(int row = 0; (row < defNumRows) && !winner; row++)
			for (int col = 0; (col < defNumCols - 3) && !winner; col++)
				if(tab[row][col] != null && tab[row][col+1] != null && 
						tab[row][col+2] != null && tab[row][col+3] != null)
					if (tab[row][col].getColor().equals(c)  && tab[row][col+1].getColor().equals(c) &&
							tab[row][col+2].getColor().equals(c) &&	tab[row][col+3].getColor().equals(c)) 
						winner = true;

		for(int row = 0; (row < defNumRows - 3) && !winner; row++)
			for(int col = 0; (col < defNumCols) && !winner; col++)
				if(tab[row][col] != null && tab[row+1][col] != null &&
					tab[row+2][col] != null && tab[row+3][col] != null)
					if (tab[row][col].getColor().equals(c) && tab[row+1][col].getColor().equals(c) &&
							tab[row+2][col].getColor().equals(c) && tab[row+3][col].getColor().equals(c))
						winner=true;
		
		for(int row = 3; (row < defNumRows) && !winner; row++)
			for(int col = 0; (col < defNumCols - 3) && !winner; col++)
				if(tab[row][col] != null && tab[row-1][col+1] != null && 
						tab[row-2][col+2] != null && tab[row-3][col+3] != null)
					if (tab[row][col].getColor().equals(c) && tab[row-1][col+1].getColor().equals(c) &&
							tab[row-2][col+2].getColor().equals(c) && tab[row-3][col+3].getColor().equals(c))
						winner=true;
				
		for(int row = 0; (row < defNumRows - 3) && !winner; row++)
			for(int col = 0; (col < defNumCols - 3) && !winner; col++)
				if(tab[row][col] != null && tab[row+1][col+1] != null && 
						tab[row+2][col+2] != null && tab[row+3][col+3] != null)
					if (tab[row][col].getColor().equals(c) && tab[row+1][col+1].getColor().equals(c) &&
							tab[row+2][col+2].getColor().equals(c) && tab[row+3][col+3].getColor().equals(c))
						winner=true;
	}
	
	public Jugador chequearGanador(Jugador j) { //devuelve el ganador o null
		Color c = j.getColor();
		
		//chequear 4 en linea
		for(int row = 0; row<defNumRows; row++){
			for (int col = 0;col < defNumCols - 3;col++){
				if(tab[row][col]!=null && 
						tab[row][col+1]!=null  &&
						tab[row][col+2]!=null &&
						tab[row][col+3]!=null)
				if (tab[row][col].getColor().equals(c)  && 
					tab[row][col+1].getColor().equals(c) &&
					tab[row][col+2].getColor().equals(c) &&
					tab[row][col+3].getColor().equals(c)){
					winner=true;
					return j;
				}
			}			
		}
		//chequear 4 en raya
		for(int row = 0; row < defNumRows - 3; row++){
			for(int col = 0; col < defNumCols; col++){
				if(tab[row][col]!=null && 
					tab[row+1][col]!=null  &&
					tab[row+2][col]!=null &&
					tab[row+3][col]!=null)
				if (tab[row][col].getColor().equals(c) && 
					tab[row+1][col].getColor().equals(c) &&
					tab[row+2][col].getColor().equals(c) &&
					tab[row+3][col].getColor().equals(c)){
					winner=true;
					return j;
				}
			}
		}
		//chequear diagonal
		for(int row = 3; row < defNumRows; row++){
			for(int col = 0; col < defNumCols - 3; col++){
				if(tab[row][col]!=null && 
						tab[row-1][col+1]!=null  &&
						tab[row-2][col+2]!=null &&
						tab[row-3][col+3]!=null)
				if (tab[row][col].getColor().equals(c)   && 
					tab[row-1][col+1].getColor().equals(c) &&
					tab[row-2][col+2].getColor().equals(c) &&
					tab[row-3][col+3].getColor().equals(c)){
					winner=true;
					return j;
				}
			}
		}
		//check downward diagonal
		for(int row = 0; row < defNumRows - 3; row++){
			for(int col = 0; col < defNumCols - 3; col++){
				if(tab[row][col]!=null && 
						tab[row+1][col+1]!=null  &&
						tab[row+2][col+2]!=null &&
						tab[row+3][col+3]!=null)
				if (tab[row][col].getColor().equals(c)  && 
					tab[row+1][col+1].getColor().equals(c) &&
					tab[row+2][col+2].getColor().equals(c) &&
					tab[row+3][col+3].getColor().equals(c)){
					winner=true;
					return j;
				}
			}
		}
		return null;
	}
}
