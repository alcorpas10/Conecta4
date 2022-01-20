package model;

import java.awt.Color;
import java.util.InputMismatchException;

import utils.AILevel;

public class HJugador implements PlayerInterface{

	protected String id;
	private Color colorPiece;
	private int nextCol;
	
	public HJugador(String id, Color c) {
		this.id=id;
		colorPiece =c;
	}

	@Override
	public int playTurn(Tablero tab, int col) {
		int fila;
		
		try {
			fila = tab.colocarFicha(colorPiece, col);
			nextCol = col;
			return fila;
		}
		catch(InputMismatchException ex) {
			throw new IllegalArgumentException("Input error, please insert a number from 0 to 6");
		}
	}

	@Override
	public void setBehavior(AILevel lev) {}

	@Override
	public int getNextCol() {
		return nextCol;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id=id;
	}

	@Override
	public void setColor(Color colorPiece) {
		this.colorPiece = colorPiece;
	}
}
