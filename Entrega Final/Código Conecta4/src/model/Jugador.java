package model;

import java.awt.Color;
import java.util.InputMismatchException;
import java.util.Scanner;

import utils.AILevel;
import utils.PlayerType;

public class Jugador {

	protected String id;
	private int points; //puede representar el nº de rondas ganadas o de partidas ganadas
	private int time;	//los guarda en segundos
	protected Color colorPiece;
	private boolean winner;
	private String nomColor;
	protected int nextCol;
	
	private PlayerType pt;
	private PlayerInterface pi;
	
	public Jugador(String id, Color c, String nomColor, PlayerType tp) {
		this.id = id;
		points = 0;
		winner = false;
		colorPiece = c;
		this.nomColor = nomColor;
		time =0;
		this.pt=tp;
		setType(tp);
	}
	
	public Jugador(String id, int p , int time, PlayerType tp) {
		this.id = id;
		points = p;
		winner = false;
		this.time=time;
		this.pt=tp;
		setType(tp);
	}
	
	public Jugador(String id, Color c, PlayerType tp) {
		this.id = id;
		points = 0;
		winner = false;
		colorPiece = c;
		time =0;
		this.pt=tp;
		setType(tp);
	}
	
	public Jugador(Jugador j, PlayerType tp) //CONSTRUCTOR POR COPIA
	{
		id = j.getId();
		colorPiece = j.getColor();
		points = j.getPoints();
		time = j.getTime();
		winner = j.isWinner();
		this.pt=tp;
		setType(tp);
	}
	
	public Jugador(PlayerType tp) {
		this.id = "";
		points = 0;
		winner = false;
		colorPiece = Color.BLUE;
		this.nomColor = "azul";
		time =0;
		this.pt=tp;
		setType(tp);
	}

	public Jugador(Jugador j) //CONSTRUCTOR POR COPIA
	{
		id = j.getId();
		colorPiece = j.getColor();
		points = j.getPoints();
		time = j.getTime();
		winner = j.isWinner();
		setType(j.pt);
	}
	
	private void setType(PlayerType tp) {
		if(tp==PlayerType.CPU)
			this.pi = new AIJugador();
		else
			this.pi = new HJugador(id, colorPiece);
		id=pi.getId();
	}
	
	public String getId() {
		id= pi.getId();
		return id;
	}

	public void setId(String id) {
		this.id=id;
		this.pi.setId(id);
	}
	
	public void setTime(int i) {
		this.time=i + time;
	}

	public void addPoints() {
		points++;
	}
	
	public void setPoints(int p) {
		points = p;
	}
	
	public int getPoints() {
		return points;
	}
	
	public int getTime() {
		return time;
	}
	
	public void setColor(Color c) {
		colorPiece = c;
		pi.setColor(colorPiece);
	}
	
	public Color getColor() {
		return colorPiece;
	}
	
	public void setWinner(boolean w) {
		winner = w;
	}
	
	public boolean isWinner() {
		return winner;
	}
	
	public int getNextCol() {
		return this.pi.getNextCol();
	}
	
	@Override
	public String toString() {
		return id + ": " + nomColor;
	}

	public boolean isAI() {
		return this.pt==PlayerType.CPU;
	}

	public void setBehavior(AILevel lev) {
		this.pi.setBehavior(lev);
	}

	public int playTurn(Tablero tab, int col) {
		return this.pi.playTurn(tab, col);
	}
}
