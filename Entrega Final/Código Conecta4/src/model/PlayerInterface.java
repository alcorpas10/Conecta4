package model;

import java.awt.Color;

import utils.AILevel;

public interface PlayerInterface {

	int playTurn(Tablero tab,  int col);
	void setBehavior(AILevel lev);
	int getNextCol();
	String getId();
	void setId(String id);
	void setColor(Color colorPiece);
}
