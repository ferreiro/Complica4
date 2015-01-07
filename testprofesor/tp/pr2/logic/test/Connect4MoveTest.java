package tp.pr2.logic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import tp.pr2.logic.Counter;
import tp.pr2.logic.Move;
import tp.pr2.logic.Connect4Move;
import tp.pr2.logic.Connect4Rules;
import tp.pr2.logic.GameRules;
import tp.pr2.logic.Board;

public class Connect4MoveTest {
	
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
	public void testGetJugador() {
		Move move = newMove(1, Counter.WHITE);
		assertEquals("The colour of the counter of the move should be that which it was created with.", move.getPlayer(), Counter.WHITE);
		
		move = newMove(1, Counter.BLACK);
		assertEquals("The colour of the counter of the move should be that which it was created with.", move.getPlayer(), Counter.BLACK);
	}
	
	// Movimientos con columna dentro del tablero
	@Test
	public void testEjecutaMovimientoDentro() {
		Board b = r.newBoard(); 

		for (int x=1; x<=b.getWidth(); x++) {
			Move move = newMove(x, Counter.WHITE);
			assertTrue("Placing a counter in a valid empty column should be a valid move (executeMove should return true)", move.executeMove(b));
		}
	}
	
	// Movimientos con columna fuera del tablero
	@Test
	public void testEjecutaMovimientoFuera() {
		Board b = r.newBoard(); 

		for (int x=-20; x<=20; x++) {
			if ((x < 1) || (x > b.getWidth())) {
				Move move = newMove(x, Counter.WHITE);
				assertFalse("It should not be possible to place a counter in a non-existant column (executeMove should return false)", move.executeMove(b));
			}
		}
	}
	
	// Movimientos cuando la columna no está llena
	@Test
	public void testEjecutaMovimientoColumnaNoLlena() {
		Board b = r.newBoard();
		
		for (int x = b.getWidth(); x >= 1; x--) {
			for (int y = b.getHeight(); y >= 1; y--) {
				Counter counter = (x + y * b.getWidth()/2)  % 2 == 0 ? Counter.BLACK : Counter.WHITE;
				Move move = newMove(x, counter);
				move.executeMove(b);
				assertEquals("The counter in board position ("+x+","+y+") should be that which was placed there by executeMove", b.getPosition(x, y), counter);
			}
		}
	}
	
	// Movimientos cuando la columna está llena
	@Test
	public void testEjecutaMovimientoColumnaLlena() {

		Board b = r.newBoard();
		for (int i=0; i<b.getHeight(); i++) {
			Move move = newMove(1, Counter.WHITE);
			move.executeMove(b);
		}
		
		Move move = newMove(1, Counter.WHITE);
		assertFalse("It should not be possible to place a counter in a full column (executeMove should return false)", move.executeMove(b));
	}

	// Undo cuando la columna no se ha llenado
	@Test
	public void testUndo() {
		Board b = r.newBoard();
		
		// Ejecutar movimientos
		Move move[] = new Move[b.getHeight()];
		for (int i=0; i<b.getHeight(); i++) {
			move[i] = newMove(2, Counter.WHITE);
			move[i].executeMove(b);
		}
		
		// Deshacer
		for (int i=1; i<=b.getHeight(); i++) {
			assertTrue("Undoing a move in a column should leave that column as it was before the move was executed", b.getPosition(2, i) == Counter.WHITE);
			move[b.getHeight()-i].undo(b);
			assertTrue("Undoing a move should eliminate the counter placed in that move from the board", b.getPosition(2, i) == Counter.EMPTY);
		}
	}
}
