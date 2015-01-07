package tp.pr2.logic.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import tp.pr2.logic.Counter;
import tp.pr2.logic.Move;
import tp.pr2.logic.ComplicaMove;
import tp.pr2.logic.ComplicaRules;
import tp.pr2.logic.GameRules;
import tp.pr2.logic.Board;

public class ComplicaMoveTest extends Connect4MoveTest {
	
	@Override
	protected Move newMove(int column, Counter colour) {
		return new ComplicaMove(column, colour);
	}
	
	@Override
	protected GameRules newRules() {
		return new ComplicaRules();
	}
	
	// Movimientos cuando la columna está llena
	@Test
	@Override
	public void testEjecutaMovimientoColumnaLlena() {
		Counter counters[] = {
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE, 
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE,
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE,
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE
				};
		
		Board b = r.newBoard();
		
		for (int i=0; i<counters.length; i++) {
			Move move = newMove(1, counters[i]);
			move.executeMove(b);
			
			// Comprobar desplazamiento
			if (i >= b.getHeight()) {
				for (int j=0; j<b.getHeight(); j++) {
					Counter expected = counters[i - b.getHeight() + j + 1];
					Counter obtained = b.getPosition(1, b.getHeight() - j);
					assertEquals("Placing a counter in a full column does not produce the expected result.", obtained, expected);
				}
			}
			
		}
	}

	// Undo cuando la columna estaba llena y se ha desplazado
	@Test
	public void testUndoColumnaLlena() {
		Counter counters[] = {
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE, 
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE,
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE,
				Counter.BLACK, Counter.BLACK, Counter.BLACK, Counter.WHITE, Counter.WHITE, Counter.WHITE
				};
		
		Board b = r.newBoard();
		
		// Hacer los movimientos
		Move moves[] = new Move[counters.length];
		for (int i=0; i<counters.length; i++) {
			moves[i] = newMove(1, counters[i]);
			moves[i].executeMove(b);
		}

		// Deshacer los movimientos
		for (int i=1; i <= counters.length - b.getHeight(); i++) {
			moves[moves.length-i].undo(b);
			
			// Comprobar
			for (int j=1; j<=b.getHeight(); j++) {
				Counter expected = counters[counters.length - j - i];
				Counter obtained = b.getPosition(1, j);
				assertEquals("Undoing a move in which a counter is placed in a full column should leave that column as it was before the move was executed.", obtained, expected);
			}
		}
	}
}
