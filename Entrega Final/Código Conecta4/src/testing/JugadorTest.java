package testing;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

import model.Jugador;
import utils.PlayerType;

class JugadorTest {

	
	@Test
	void testSetNombreAndGetNombre() {
		Jugador j=new Jugador("paco",Color.BLACK,"R", PlayerType.jugador);
		Jugador ex=new Jugador("paco",Color.BLACK,"R", PlayerType.jugador);
		assertEquals(j.getId(),ex.getId(),"iguales");
		
		ex.setId("pepe");
		assertEquals("pepe",ex.getId(),"Error de asignacion de nombres");
		
		assertEquals("paco",j.getId(),"Error de asignacion de nombres");

	}
	@Test
	void testPuntos() {
		Jugador j=new Jugador("paco",Color.BLACK,"R",PlayerType.jugador);
		
		
		assertEquals(j.getPoints(),0, "Error en asignacion de puntos");
		j.setPoints(25);
		assertEquals(j.getPoints(),25, "Error en asignacion de puntos");
		j.addPoints();
		assertEquals(j.getPoints(),26, "Error en asignacion de puntos");
		j.setPoints(0);
		assertEquals(j.getPoints(),0, "Error en asignacion de puntos");

		
	}
	@Test
	void testColor() {
		Jugador j=new Jugador("paco",Color.BLACK,"R", PlayerType.jugador);
		
		assertEquals(Color.BLACK,j.getColor(),"Error en asignacion de color");
		j.setColor(Color.RED);
		assertEquals(Color.RED,j.getColor(),"Error en asignacion de color");
		j.setColor(Color.GREEN);
		assertEquals(Color.GREEN,j.getColor(),"Error en asignacion de color");
		j.setColor(Color.CYAN);
		assertEquals(Color.CYAN,j.getColor(),"Error en asignacion de color");

	}
	@Test
	void testWinner() {
		Jugador j=new Jugador("paco",Color.BLACK,"R", PlayerType.jugador);
		Jugador ex=new Jugador("tom",Color.RED,"Y", PlayerType.jugador);
		
		assertEquals(false,j.isWinner(),"Must be loser");
		assertEquals(false,ex.isWinner(),"Must be loser");
		
		j.setWinner(true);
		assertEquals(true,j.isWinner()," Must be winner");
		assertEquals(false,ex.isWinner(),"Must be loser");

		ex.setWinner(true);
		assertEquals(true,j.isWinner(),"Must be winner");
		assertEquals(true,ex.isWinner(),"Must be winner");
		
		j.setWinner(false);
		assertEquals(false,j.isWinner(),"Must be loser");
		assertEquals(true,ex.isWinner(),"Must be winner");
		
		ex.setWinner(false);
		assertEquals(false,j.isWinner(),"Must be loser");
		assertEquals(false,ex.isWinner(),"Must be loser");

	}
	@Test
	void testToString() {
		Jugador j=new Jugador("paco",Color.BLACK,"R", PlayerType.jugador);
		
		assertEquals("paco: R",j.toString(),"String output failed");
		
		Jugador ex=new Jugador("tom",Color.RED,"Y",PlayerType.jugador);

		assertEquals("tom: Y",ex.toString(),"String output failed");

	}
	

}
