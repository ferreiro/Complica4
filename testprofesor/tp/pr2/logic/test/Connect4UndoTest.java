package tp.pr2.logic.test;

import java.util.Stack;

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

public class Connect4UndoTest {
	
	protected GameRules r;
	
	@Before
	public void init() {
		r = newRules();
	}
	
	protected Move newMove(int column, Counter colour) {
		return new Connect4Move(column, colour);
	}
	
	protected GameRules newRules() {
		return new Connect4Rules();
	}
	
	@Test
	public void testUndoTrasMovimiento() {
		Game g = new Game(r);
		
		g.executeMove(newMove(1, Counter.WHITE));
		assertTrue("After one move, it should be possible to perform undo (undo should return true).", g.undo());
		assertTrue("After undoing the first move of a game, the board should be empty.", Utils.tableroVacio(g.getBoard()));
		assertEquals("After undoing the first move of a game, it should be white's turn.", Counter.WHITE, g.getTurn());
		assertFalse("After undoing the first move of a game, the game should not be finished.", g.isFinished());
	}
	
	@Test
	public void testUndo() {
		Game g = new Game(r);
		Board b = g.getBoard();
		
		Stack<Board> pila = new Stack<Board>();
		for (int x=1; x<=b.getWidth(); x++) {
			pila.push(Utils.copiaTablero(b));

			g.executeMove(newMove(x, g.getTurn()));
		}
		
		for (int i=0; i<Math.min(b.getWidth(), 10); i++) {
			assertTrue("It should be possible to undo the placing of a counter in the first position of a column (undo should return true).", g.undo());
			assertTrue("Undoing a move should leave the board in the same state as before that move was executed.", Utils.TablerosIguales(b, pila.pop()));
		}
	}
	

	@Test
	public void testUndoMuchasVeces() {
		Game g = new Game(r);
		Board b = g.getBoard();
		
		Stack<Board> pila = new Stack<Board>();
		for (int x=1; x<=b.getWidth(); x++) {
			for (int y=1; y<=3; y++) {
				pila.push(Utils.copiaTablero(b));
				
				g.executeMove(newMove(x, g.getTurn()));
			}
		}
		
		for (int i=0; i<10; i++) {
			assertTrue("It should always be possible to undo (at least) the last 10 moves.", g.undo());
			assertTrue("Undoing the nth move should leave the board in the same state as before that move was executed, including when n>10.", Utils.TablerosIguales(b, pila.pop()));
		}
	}
	
	@Test
	public void testNoUndoTrasReset() {
		Game g = new Game(r);
		
		assertTrue(g.executeMove(newMove(3, g.getTurn())));
		g.reset(newRules());
		assertFalse("It should not be possible to perform Undo after a reset (undo should return false).", g.undo());
	}
}
