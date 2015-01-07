package tp.pr2.logic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import tp.pr2.logic.ComplicaRules;
import tp.pr2.logic.GameRules;
import tp.pr2.logic.Board;

public class ComplicaRulesTest extends Connect4RulesTest {

	@Override
	protected GameRules newRules() {
		return new ComplicaRules();
	}

	@Test
	@Override
	public void testIniciaTablero() {
		Board b = r.newBoard();
		
		assertEquals("The width of the board is incorrect.", 4, b.getWidth());
		assertEquals("The height of the board is incorrect.", 7, b.getHeight());
		assertTrue("At the start of the game, the board should be empty.", Utils.tableroVacio(b));
	}
}
