package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import multiplayer.Server;

class MultiplayerTest {

	@Test
	void testServer() {
		Server s = new Server(55555);
		assertEquals(0, s.getNumberOfPlayers(), "Numero de jugadores incorrecto");
		assertEquals(false, s.finished(), "La partida no deberia estar finalizada");
		assertEquals(false, s.validMove(1, 0), "Movimiento valido pero faltan jugadores");
		//No se hacer mas tests sin tener que crear getters o hacer publicos atributos privados
	}

}
