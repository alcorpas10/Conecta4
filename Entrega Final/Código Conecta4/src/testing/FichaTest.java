package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import model.Ficha;
import utils.Coordinate;

class FichaTest {

	@Test
	void testCrearFicha() {
		
		Ficha f=new Ficha(Color.BLACK, new Coordinate(0, 1));
		assertEquals(f.getColor(),Color.BLACK,"no iguales");
		assertEquals(f.getCoor().getCol(),1,"no iguales");
		assertEquals(f.getCoor().getRow(),0,"no iguales");

		
		Ficha f2=new Ficha( new Coordinate(10, 31));
		assertEquals(f2.getCoor().getCol(),31,"no iguales");
		assertEquals(f2.getCoor().getRow(),10,"no iguales");
		
		Ficha f3=new Ficha(Color.BLUE, new Coordinate(2, 5));
		assertEquals(f3.getColor(),Color.BLUE,"no iguales");
		assertEquals(f3.getCoor().getRow(),2,"no iguales");
		assertEquals(f3.getCoor().getCol(),5,"no iguales");
		
		Ficha f4=new Ficha(Color.RED, new Coordinate(2, 5));
		assertEquals(f4.getColor(),Color.RED,"no iguales");
		assertEquals(f4.getCoor().getRow(),2,"no iguales");
		assertEquals(f4.getCoor().getCol(),5,"no iguales");
		
		assertEquals(f3.equals(f4),true, "no iguales");
		assertEquals(f3.equals(f),false, " iguales");
		assertEquals(f3.equals(f4),true, "no iguales");
		assertEquals(f2.equals(f4),false, " iguales");
		assertEquals(f.equals(f4),false, " iguales");
		assertEquals(f2.equals(f3),false, " iguales");
		assertEquals(f2.equals(f),false, " iguales");



	}

}
