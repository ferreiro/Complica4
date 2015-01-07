package tp.pr2.logic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tp.pr2.logic.Counter;
import tp.pr2.logic.Connect4Rules;
import tp.pr2.logic.GameRules;
import tp.pr2.logic.Board;

public class Connect4RulesTest {
	
	protected GameRules r;
	
	@Before
	public void init() {
		r = newRules();
	}
	
	protected GameRules newRules() {
		return new Connect4Rules();
	}

	@Test
	public void testIniciaTablero() {
		Board b = r.newBoard();
		assertEquals("The width of the board is incorrect.", 7, b.getWidth());
		assertEquals("The height of the board is incorrect.", 6, b.getHeight());
		assertTrue("At the start of the game, the board should be empty.", Utils.tableroVacio(b));
	}
	
	@Test
	public void testJugadorInicial() {
		assertEquals("White always starts.", Counter.WHITE, r.initialPlayer());
	}
	
	@Test
	public void testSiguienteTurno() {
		Board b = r.newBoard();
		
		// Independiente del estado
		assertEquals("After white has moved, it should be black's turn", Counter.BLACK, r.nextTurn(Counter.WHITE, b));
		assertEquals("After black has moved, it should be white's turn", Counter.WHITE, r.nextTurn(Counter.BLACK, b));
		
		// Independiente del tablero
		for (int x=b.getWidth(); x>=1; x--) {
			for (int y=b.getHeight(); y>=1; y--) {
				Counter counter = (x + y + y/3) % 2 == 0 ? Counter.BLACK : Counter.WHITE;
				b.setPosition(x, y, counter);
				assertEquals("After white has moved, it should be black's turn", Counter.BLACK, r.nextTurn(Counter.WHITE, b));
				assertEquals("After black has moved, it should be white's turn", Counter.WHITE, r.nextTurn(Counter.BLACK, b));
			}
		}
	}
}
