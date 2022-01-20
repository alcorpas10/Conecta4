package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import model.Ficha;
import model.Tablero;
import utils.Coordinate;

class TableroTest {

	@Test
	void testColocarFicha() {
		
		Tablero tester1 = new Tablero();
	
		//rellenar una fila
	assertEquals(5, tester1.colocarFicha(Color.BLACK,0), "must be 5");
	assertEquals(4, tester1.colocarFicha(Color.BLACK,0), "must be 4");
	assertEquals(3, tester1.colocarFicha(Color.BLACK,0), "must be 3");
	assertEquals(2, tester1.colocarFicha(Color.BLACK,0), "must be 2");
	assertEquals(1, tester1.colocarFicha(Color.BLACK,0), "must be 1");
	assertEquals(0, tester1.colocarFicha(Color.BLACK,0), "must be 0");

	}

	@Test
	void testSetFichaAndGetFicha() {
		
		Tablero tester1 = new Tablero();
	
		Ficha expected = new Ficha(Color.YELLOW,new Coordinate(5,5));
		tester1.setFicha(new Coordinate(5,5), Color.YELLOW);
		assertEquals(expected,tester1.getFicha(5,5), "Ficha en (5,5)");	//requiri� sobreescribir equals en Ficha
	}
	
	@Test
	void testBorrarFicha() {
		
		Tablero tester1 = new Tablero();
		Ficha expected = new Ficha(Color.YELLOW,new Coordinate(5,5));
		tester1.setFicha(new Coordinate(5,5), Color.YELLOW);
		tester1.descolocar(5, 5);
	
		assertEquals(null,tester1.getFicha(5,5), "No debe existir");	
	
		tester1.setFicha(new Coordinate(5,5), Color.YELLOW);
		assertEquals(expected,tester1.getFicha(5,5), "No debe existir");	
		
	}
	
	@Test
	void testLastRow() {
		
		Tablero tester1 = new Tablero();
	
		//rellenamos un tablero
		assertEquals(5,tester1.lastRow(5), "must be 5");
		tester1.setFicha(new Coordinate(5,5), Color.YELLOW);
		assertEquals(4,tester1.lastRow(5), "must be 4");
		tester1.setFicha(new Coordinate(4,5), Color.YELLOW);
		assertEquals(3,tester1.lastRow(5), "must be 3");
		tester1.setFicha(new Coordinate(3,5), Color.YELLOW);
		assertEquals(2,tester1.lastRow(5), "must be 2");
		tester1.setFicha(new Coordinate(2,5), Color.YELLOW);
		assertEquals(1,tester1.lastRow(5), "must be 1");
		tester1.setFicha(new Coordinate(1,5), Color.YELLOW);
		assertEquals(0,tester1.lastRow(5), "must be 4");
	}
	
	@Test
	void testFullBoard() {
	
		Tablero tester1 = new Tablero();
		
		for (int i = 0; i < Tablero.defNumRows; i++)
		{
			for (int j = 0; j < Tablero.defNumCols; j++) {
				tester1.setFicha(new Coordinate(i,j), Color.YELLOW);
			}
		}
		
		assertEquals(true, tester1.fullBoard(), "must be true");
		
		tester1.descolocar(0, 0);
		
		assertEquals(false, tester1.fullBoard(), "must be false");
	}
	
	@Test
	void testFullColumn() {
	
		Tablero tester1 = new Tablero();
		
		for (int i = 0; i < Tablero.defNumRows; i++)
		{
			tester1.setFicha(new Coordinate(i,0), Color.YELLOW);
			
		}
		
		assertEquals(true, tester1.fullColumn(0), "must be true");
		
	}
	
	
}
