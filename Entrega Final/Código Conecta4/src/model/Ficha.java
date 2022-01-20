package model;

import java.awt.Color;
import utils.Coordinate;

public class Ficha 
{
	private Color color = Color.WHITE;
	private Coordinate coor;
	
	public Ficha(Color color, Coordinate coor) {
		this.color = color;
		this.coor = coor;
	}
	
	public Ficha(Coordinate coor) //Constructor exclusivo para tests
	{
		this.color = null;
		this.coor = coor;
	}

	public Color getColor() {
		return color;
	}

	public Coordinate getCoor() { //Metodo exclusivo para tests
		return coor;
	}

	@Override
	public boolean equals(Object obj) {
		Ficha p= (Ficha)obj;
		return p.coor.getCol()==this.coor.getCol()&&p.coor.getRow()==this.coor.getRow();
	}
}
