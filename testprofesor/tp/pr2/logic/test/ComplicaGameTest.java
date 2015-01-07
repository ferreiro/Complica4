package tp.pr2.logic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tp.pr2.logic.Counter;
import tp.pr2.logic.Move;
import tp.pr2.logic.ComplicaMove;
import tp.pr2.logic.Game;
import tp.pr2.logic.ComplicaRules;
import tp.pr2.logic.GameRules;
import tp.pr2.logic.Board;


public class ComplicaGameTest {
	
	private Game g;
	
	@Before
	public void init() {
		g = new Game(newRules());
	}
	
	private GameRules newRules() {
		return new ComplicaRules();
	}
	
	private Move newMove(int column, Counter colour) {
		return new ComplicaMove(column, colour);
	}
	
	@Test
	public void testCtor() {
		assertFalse("A game which has just started shouldn't have already finished.", g.isFinished());
		assertEquals("White always starts.", Counter.WHITE, g.getTurn());
		assertEquals("The board dimensions should be fixed as 4x7.", 4, g.getBoard().getWidth());
		assertEquals("The board dimensions should be fixed as 4x7.", 7, g.getBoard().getHeight());
		assertFalse("At the start of the game, there is nothing to undo.", g.undo());
	}
	
	@Test
	public void testEjecutaMovimientoSimple() {
		assertTrue(g.executeMove(newMove(1, Counter.WHITE)));
		assertEquals("After placing the first counter of the game in column 1, board position (1, 7) should contain a white counter.",
				Counter.WHITE,
				g.getBoard().getPosition(1,  7));
		assertFalse("After only one move the game should not have finished (on a non-trivial board).", g.isFinished());
		assertEquals("After white moves it should be black's turn.", Counter.BLACK, g.getTurn());
	}
	
	@Test
	public void testEjecutaMovimientoInvalido1() {
		assertFalse("executeMove should not allow a move to be made out of turn.",
				g.executeMove(newMove(1, Counter.BLACK)));
	}
	
	@Test
	public void testEjecutaMovimientoInvalido3() {
		for (int x = -10; x <= 10; ++x) {
			if ((1 <= x) && (x <= 4)) continue;
			assertFalse("It should not be possible to execute a move in a non-existent column (executeMove should return false).", g.executeMove(newMove(x, Counter.WHITE)));
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
		assertEquals("After placing a white counter in an empty column 3, position (3, 7) should contain a white counter",
				Counter.WHITE,
				b.getPosition(3,  7));
	}
	
	@Test
	public void testReset1() {
		
		assertTrue(g.executeMove(newMove(3, Counter.WHITE)));
		g.reset(newRules());
		assertTrue("After a reset, the board should be empty.", Utils.tableroVacio(g.getBoard()));
		assertEquals("After a reset, it should be white's turn.", Counter.WHITE, g.getTurn());

	}
	
	private void fillColumn(int c) {
		for (int y=1; y<=7; y++) {
			assertTrue(g.executeMove(newMove(c, g.getTurn())));
			assertFalse("The game shouldn't have finished without a winning move.", g.isFinished());
			assertEquals("A winner shouldn't be assigned without there being a single sequence of four consecutive counters of the same colour on the board.", g.getWinner(), Counter.EMPTY);
		}
	}
	
	@Test
	public void partidaEnTablas() {
		
		g = new Game(newRules());
		
		// Tablero sin conecta 4
		fillColumn(1);
		fillColumn(3);
		fillColumn(2);
		fillColumn(4);
		
		// Tablero con varios conecta cuatro
		assertTrue(g.executeMove(newMove(3, g.getTurn())));
		assertFalse("The game shouldn't have finished without a winning move.", g.isFinished());
		assertEquals("A winner should not be assigned without there being a single sequence of four consecutive counters of the same colour on the board.", g.getWinner(), Counter.EMPTY);
		
		assertTrue(g.executeMove(newMove(4, g.getTurn())));
		assertFalse("The game shouldn't have finished without a winning move.", g.isFinished());
		assertEquals("A winner shouldn't be assigned if there is more than one sequence of four consecutive counters of the same colour on the board.", g.getWinner(), Counter.EMPTY);
	}
	
	@Test
	public void varios4EnRaya() {
		g = new Game(newRules());
		Board b = g.getBoard();
		
		// Tablero con un grupo de 4 en raya blanco y otro negro, con
		// columnas completas y otras no completas. Al meter una nueva
		// ficha blanca en una columna no completa se crea otro grupo 
		// de blancas. No debería haber ganador.
		
		// Rellenar dos columnas
		Counter counter;
		for (int x = 3; x <= 4; x++) {
			counter = Counter.WHITE;
			for (int y = 7; y >= 1; y--) {
				b.setPosition(x, y, counter);
				counter = Utils.contraria(counter);
			}
		}
		
		// Poner fichas en otra dos columnas
		b.setPosition(2, 7, Counter.WHITE);
		b.setPosition(2, 6, Counter.BLACK);
		b.setPosition(2, 5, Counter.WHITE);
		
		b.setPosition(1, 7, Counter.WHITE);
		b.setPosition(1, 6, Counter.BLACK);
		
		// Ejecutar movimiento
		Move move = newMove(1, Counter.WHITE);
		assertTrue("It should be possible to place a counter in an already-full column.", g.executeMove(move));
		assertEquals("White should not have won if black already had a sequence of four consecutive counters on the board.", g.getWinner(), Counter.EMPTY);
		assertFalse("The game shouldn't have finished if both players have one or more sequences of four consecutive counters on the board.", g.isFinished());
	}
}
