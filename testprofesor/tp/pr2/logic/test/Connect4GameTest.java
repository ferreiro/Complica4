package tp.pr2.logic.test;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import tp.pr2.logic.Counter;
import tp.pr2.logic.Move;
import tp.pr2.logic.Connect4Move;
import tp.pr2.logic.Game;
import tp.pr2.logic.Connect4Rules;
import tp.pr2.logic.GameRules;
import tp.pr2.logic.Board;

public class Connect4GameTest {
	
	private Game g;
	
	@Before
	public void init() {
		g = new Game(newRules());
	}
	
	private GameRules newRules() {
		return new Connect4Rules();
	}
	
	private Move newMove(int column, Counter colour) {
		return new Connect4Move(column, colour);
	}
	
	@Test
	public void testCtor() {
		assertFalse("A game which has just started shouldn't have already finished.", g.isFinished());
		assertEquals("White always starts.", Counter.WHITE, g.getTurn());
		assertEquals("The board dimensions should be fixed as 7x6.", 7, g.getBoard().getWidth());
		assertEquals("The board dimensions should be fixed as 7x6.", 6, g.getBoard().getHeight());
		assertFalse("At the start of the game, there is nothing to undo.", g.undo());
	}
	
	@Test
	public void testEjecutaMovimientoSimple() {
		assertTrue(g.executeMove(newMove(1, Counter.WHITE)));
		assertEquals("After placing the first counter of the game in column 1, board position (1, 6) should contain a white counter.",
				Counter.WHITE,
				g.getBoard().getPosition(1,  6));
		assertFalse("After only one move the game should not have finished (on a non-trivial board).", g.isFinished());
		assertEquals("After white moves it should be black's turn.", Counter.BLACK, g.getTurn());
	}
	
	@Test
	public void testEjecutaMovimientoInvalido1() {
		assertFalse("executeMove should not allow a move to be made out of turn.",
				g.executeMove(newMove(1, Counter.BLACK)));
	}
	
	@Test
	public void testEjecutaMovimientoInvalido2() {
		assertTrue(g.executeMove(newMove(3, Counter.WHITE)));
		assertTrue(g.executeMove(newMove(3, Counter.BLACK)));
		assertTrue(g.executeMove(newMove(3, Counter.WHITE)));
		assertTrue(g.executeMove(newMove(3, Counter.BLACK)));
		assertTrue(g.executeMove(newMove(3, Counter.WHITE)));
		assertTrue(g.executeMove(newMove(3, Counter.BLACK)));
		assertFalse("It should not be possible to execute a move in a full column (executeMove should return false).", g.executeMove(newMove(3, Counter.WHITE)));
	}
	
	@Test
	public void testEjecutaMovimientoInvalido3() {
		for (int x = -10; x <= 10; ++x) {
			if ((1 <= x) && (x <= 7)) continue;
			assertFalse("It should not be possible to execute a move in a non-existent column (executeMove should return false)", g.executeMove(newMove(x, Counter.WHITE)));
		}
	}
	
	@Test
	public void persistenciaTablero() {
		// Comprobación que no está en la documentación pero de implementación
		// de sentido común (y, dicho sea de paso, que nos permite tomar atajos
		// en los test del cuatro en raya).
		Board b = g.getBoard();
		assertTrue(g.executeMove(newMove(3, Counter.WHITE)));
		assertTrue("The board object should not be changed in the middle of a game (except possibly on reset).",
				b == g.getBoard());
		assertEquals("After placing a white counter in an empty column 3, position (3, 6) should contain a white counter",
				Counter.WHITE,
				b.getPosition(3,  6));
	}
	
	
	@Test
	public void partidaEnTablas() {
		for (int x = 1; x <= 7; ++x) {
			if (x == 4) continue;
			for (int i = 0; i < 6; ++i) {
				if ((x == 7) && (i == 5)) continue;
				assertTrue(g.executeMove(newMove(x, g.getTurn())));
			}
		}
		
		for (int i = 0; i < 6; ++i) {
			assertTrue(g.executeMove(newMove(4, g.getTurn())));
		}
		
		assertTrue(g.executeMove(newMove(7, g.getTurn())));

		assertTrue("If the board is full, the game should have finished.", g.isFinished());
		assertEquals("If the board is full, the game should have finished in a draw, i.e. there should be no winner.", Counter.EMPTY, g.getWinner());
		
		for (int i = 1; i <= 7; ++i) {
			assertFalse("If the board is full, it should not be possible to make a move.", g.executeMove(newMove(i, g.getTurn())));
		}
	}
	
	@Test
	public void testReset1() {
		
		assertTrue(g.executeMove(newMove(3, Counter.WHITE)));
		g.reset(newRules());
		assertTrue("After a reset, the board should be empty.", Utils.tableroVacio(g.getBoard()));
		assertEquals("After a reset, it should be white's turn.", Counter.WHITE, g.getTurn());

	}
}
