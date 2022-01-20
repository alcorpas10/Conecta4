package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.InfoObj;

class InfoObjTest {

	@Test
	void testCrear() {
		InfoObj in =new InfoObj("Paco","pepe");
		assertEquals(in.getHostName(),"Paco","no iguales");
		assertEquals(in.getEnemyName(),"pepe","no iguales");
		assertEquals(in.getActualRound(),-1," no iguales");
		assertEquals(in.getTotalRound(),-1," no iguales");
		assertEquals(in.getWonEnemyRounds(),-1," no iguales");
		assertEquals(in.getWonHostRounds(),-1," no iguales");
		
		InfoObj in2 =new InfoObj(5);
		assertEquals(in2.getHostName(),"","no iguales");
		assertEquals(in2.getEnemyName(),"","no iguales");
		assertEquals(in2.getActualRound(),0," no iguales");
		assertEquals(in2.getTotalRound(),5," no iguales");
		assertEquals(in2.getWonEnemyRounds(),0," no iguales");
		assertEquals(in2.getWonHostRounds(),0," no iguales");
		
		
		InfoObj in3 =new InfoObj("tom","pou",3,5,2,1,-1, "");
		assertEquals(in3.getHostName(),"tom","no iguales");
		assertEquals(in3.getEnemyName(),"pou","no iguales");
		assertEquals(in3.getActualRound(),3," no iguales");
		assertEquals(in3.getTotalRound(),5," no iguales");
		assertEquals(in3.getWonEnemyRounds(),1," no iguales");
		assertEquals(in3.getWonHostRounds(),2," no iguales");
		

		InfoObj in4 =new InfoObj(16);
		assertEquals(in4.getHostName(),"","no iguales");
		assertEquals(in4.getEnemyName(),"","no iguales");
		assertEquals(in4.getActualRound(),0," no iguales");
		assertEquals(in4.getTotalRound(),16," no iguales");
		assertEquals(in4.getWonEnemyRounds(),0," no iguales");
		assertEquals(in4.getWonHostRounds(),0," no iguales");
		
	}

}
