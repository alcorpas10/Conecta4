package testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.MatchMaker;
import utils.AILevel;

class MatchmakerTest {

	@Test
	void testAsignarNombres() {
		MatchMaker mt=new MatchMaker();
		
		
		assertEquals(MatchMaker.defEnemyId,mt.getEnemyName(),"Must be equal");
		assertEquals(MatchMaker.defHostId,mt.getHostName(),"Must be equal");

		mt.asignarNombres("Karen", "Tom");
		
		assertEquals("Tom",mt.getEnemyName(),"Must be equal");
		assertEquals("Karen",mt.getHostName(),"Must be equal");
		
		mt.asignarNombres("Paco", "Pepe");
		assertEquals("Pepe",mt.getEnemyName(),"Must be equal");
		assertEquals("Paco",mt.getHostName(),"Must be equal");
	}
	
	@Test
	void testReset() {
		MatchMaker mt=new MatchMaker();
		
		assertEquals(1,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		assertEquals(2,mt.getActualRound(),"Current round not correct");
		assertEquals(1,mt.getWonEnemyRounds(),"Enemy won rounds not equal");
		assertEquals(0,mt.getWonHostRounds(),"Host won rounds not equal");

		mt.reset();
		assertEquals(0,mt.getActualRound(),"Current round not correct");
		assertEquals(0,mt.getWonEnemyRounds(),"Enemy won rounds not equal");
		assertEquals(0,mt.getWonHostRounds(),"Host won rounds not equal");

		
	}
	@Test
	void testPlayTurnG() {
		MatchMaker mt=new MatchMaker();
		
		assertEquals(1,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		assertEquals(2,mt.getActualRound(),"Current round not correct");
		assertEquals(1,mt.getWonEnemyRounds(),"Enemy won rounds not equal");
		assertEquals(0,mt.getWonHostRounds(),"Host won rounds not equal");
		
		mt.resetGame();
		
		assertEquals(2,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		assertEquals(3,mt.getActualRound(),"Current round not correct");
		assertEquals(2,mt.getWonEnemyRounds(),"Enemy won rounds not equal");
		assertEquals(0,mt.getWonHostRounds(),"Host won rounds not equal");
		
		mt.resetGame();
		
		assertEquals(3,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(2);
		assertEquals(4,mt.getActualRound(),"Current round not correct");
		assertEquals(2,mt.getWonEnemyRounds(),"Enemy won rounds not equal");
		assertEquals(1,mt.getWonHostRounds(),"Host won rounds not equal");
		
		mt.resetGame();
		
		assertEquals(4,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		assertEquals(5,mt.getActualRound(),"Current round not correct");
		assertEquals(3,mt.getWonEnemyRounds(),"Enemy won rounds not equal");
		assertEquals(1,mt.getWonHostRounds(),"Host won rounds not equal");
		
		
		mt.resetGame();
		
		mt.playTurnG(1);
		assertEquals(0,mt.getActualRound(),"Current round not correct");
		assertEquals(0,mt.getWonEnemyRounds(),"Enemy won rounds not equal");
		assertEquals(0,mt.getWonHostRounds(),"Host won rounds not equal");
		
	}
	@Test
	void testGameFinished() {
		MatchMaker mt=new MatchMaker();
		
		assertEquals(1,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		assertEquals(2,mt.getActualRound(),"Current round not correct");
		mt.resetGame();
		assertEquals(2,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		assertEquals(3,mt.getActualRound(),"Current round not correct");
		mt.resetGame();
		assertEquals(3,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(2);
		assertEquals(4,mt.getActualRound(),"Current round not correct");
		mt.resetGame();
		assertEquals(4,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		assertEquals(5,mt.getActualRound(),"Current round not correct");
		mt.resetGame();
		assertEquals(true,mt.gameFinished(),"Game must be finished");

		mt.playTurnG(1);
		
		mt.reset();
		
		assertEquals(0,mt.getActualRound(),"Current round not correct");
		
		assertEquals(0,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		assertEquals(1,mt.getActualRound(),"Current round not correct");
		mt.resetGame();
		assertEquals(1,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(3);
		mt.playTurnG(1);
		assertEquals(2,mt.getActualRound(),"Current round not correct");
		mt.resetGame();
		assertEquals(2,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		assertEquals(3,mt.getActualRound(),"Current round not correct");
		mt.resetGame();
		assertEquals(3,mt.getActualRound(),"Current round not correct");
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		mt.playTurnG(2);
		mt.playTurnG(1);
		
		assertEquals(4,mt.getActualRound(),"Current round not correct");
		mt.resetGame();
		
		assertEquals(true,mt.gameFinished(),"Game must be finished");


		mt.playTurnG(1);
		
		
	}
	@Test
	void testRounds() {
		MatchMaker mt=new MatchMaker();
		
		assertEquals(5,mt.getTotalRounds(),"Total round count failed");
		mt.setRoundNumber(1);
		assertEquals(1,mt.getTotalRounds(),"Total round count failed");
		mt.setRoundNumber(2);
		assertEquals(2,mt.getTotalRounds(),"Total round count failed");
		mt.setRoundNumber(7);
		assertEquals(7,mt.getTotalRounds(),"Total round count failed");
		mt.setRoundNumber(100);
		assertEquals(100,mt.getTotalRounds(),"Total round count failed");
		mt.setRoundNumber(10000);
		assertEquals(10000,mt.getTotalRounds(),"Total round count failed");
		mt.setRoundNumber(-5);
		assertEquals(5,mt.getTotalRounds(),"Total round count failed");

	}
	
	@Test
	void testAI() {
		MatchMaker mt=new MatchMaker();
		
		assertEquals(false,mt.AIEnabled(),"AI must not be selected");
		
		mt.enableAI(AILevel.EASY);
		assertEquals(true,mt.AIEnabled(),"AI must be selected");
		mt.disableAI();
		assertEquals(false,mt.AIEnabled(),"AI must not be selected");
		mt.enableAI(AILevel.MEDIUM);
		assertEquals(true,mt.AIEnabled(),"AI must be selected");
		mt.disableAI();
		assertEquals(false,mt.AIEnabled(),"AI must not be selected");
		mt.enableAI(AILevel.HARD);
		assertEquals(true,mt.AIEnabled(),"AI must be selected");
		mt.disableAI();
		assertEquals(false,mt.AIEnabled(),"AI must not be selected");
		
	}
}
