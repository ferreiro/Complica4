package tp.pr2.logic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import tp.pr2.logic.Counter;
import tp.pr2.logic.Move;
import tp.pr2.logic.ComplicaMove;
import tp.pr2.logic.Game;
import tp.pr2.logic.ComplicaRules;
import tp.pr2.logic.GameRules;
import tp.pr2.logic.Board;

public class ComplicaFourInARowTest extends Connect4FourInARowTest {

	@Override
	protected GameRules newRules() {
		return new ComplicaRules();
	}
	
	@Override
	protected Move newMove(int column, Counter colour) {
		return new ComplicaMove(column, colour);
	}
	
	private boolean preparaColocacionFichaDesplaza(Game g, Counter colour, int x, int y) {

		if (g.isFinished()) return false;
		
		Board b = g.getBoard();

		// Sanity-check: encima de y no hay nada
		for (int i = y; i >= 1; --i)
			if (b.getPosition(x, i) != Counter.EMPTY)
				return false;
		
		// Sacamos la fila sobre la que nos apoyaríamos
		int ultimaConFicha = y + 1;
		while ((ultimaConFicha <= b.getHeight()) && (b.getPosition(x, ultimaConFicha) == Counter.EMPTY))
			ultimaConFicha++;
		
		int aPoner = ultimaConFicha - y; // Con la ficha final que no pondremos
		
		// Cambiar turno?
		if ((aPoner % 2 == 1) != (g.getTurn() == colour)) {
			
			// HACK: poner en una columna vacía y quitar la ficha
			g.executeMove(newMove(1, g.getTurn()));
			b.setPosition(1, b.getHeight(), Counter.EMPTY);
		}
		
		// Rellenar la columna
		aPoner = ultimaConFicha;
		while (aPoner > 1) {
			Move move = newMove(x, g.getTurn());
			assertTrue(g.executeMove(move));
			
			assertTrue("The last move should not have led to a win.", r.winningMove(move, b) == Counter.EMPTY);
			assertFalse("The last move should not have led to a draw.", r.isDraw(move.getPlayer(), b));
			
			aPoner--;
		}
		
		// Cambiar turno?
		if (colour != g.getTurn()) {
			// HACK: poner en una columna vacía y quitar la ficha
			int emptyColumn = x % b.getWidth() + 1;
			g.executeMove(newMove(emptyColumn, g.getTurn()));
			b.setPosition(emptyColumn, b.getHeight(), Counter.EMPTY);
		}
		
		return true;
	}	
	
	private void testCuatroEnRayaDesplaza(int posX[], int posY[], int ultima, Counter colour) {
		Game g = new Game(newRules());
		Board b = g.getBoard();

		if (!preparaColocacionFichaDesplaza(g, colour, posX[ultima], posY[ultima]))
			fail("Internal error of the tests :-?");
		
		for (int i = 0; i < posX.length; ++i) {
			if (i != ultima)
				b.setPosition(posX[i], posY[i], colour);
		}
		
		assertFalse("The game should not have finished since there are only three consecutive " + colour + " counters on the board.", g.isFinished());
		Move move = newMove(posX[ultima], g.getTurn());
		assertTrue(g.executeMove(move));
		
		assertTrue("The game should have finished since there are four consecutive " + colour + " counters on the board.", g.isFinished());
		assertTrue("The winner has been incorrectly assigned after a win by " + colour, r.winningMove(move, b) == colour);
		assertFalse("The game should not have been classified as a draw after a win by " + colour, r.isDraw(colour, b));
		assertEquals("The winner should have been " + colour, colour, g.getWinner());
		
		for (int x = 1; x <= 7; ++x) {
			assertFalse("It should not be possible to make a move after the game has finished.", g.executeMove(newMove(x, Counter.WHITE)));
			assertFalse("It should not be possible to make a move after the game has finished.", g.executeMove(newMove(x, Counter.BLACK)));
		}
	}

	
	private void pruebaCuatroEnRayaDesplaza(int posX[], int posY[]) {
		for (int i = 0; i < posX.length; ++i) {
			posY[i]--;
			testCuatroEnRayaDesplaza(posX, posY, i, Counter.WHITE);
			testCuatroEnRayaDesplaza(posX, posY, i, Counter.BLACK);
			posY[i]++;
		}
	}
	
	
	// Partidas que terminan con todas las posibles 4 en raya
	// horizontal
	@Test
	public void testCuatroEnRayaHorizontalDesplaza() {
		Board b = r.newBoard();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int x = 1; x <= b.getWidth() - 3; ++x) {
			for (int y = 1; y <= b.getHeight(); ++y) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = x + l;
					posY[l] = y;
				}
				pruebaCuatroEnRayaDesplaza(posX, posY);
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// vertical
	// Nota: en vertical no tiene sentido

	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal /
	@Test
	public void testCuatroEnRayaDiag1Desplaza() {
		Board b = r.newBoard();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int i = 1; i <= b.getHeight() + b.getWidth() - 1; ++i) {
			int sx = Math.max(1, i-b.getHeight()-1);
			int sy = Math.min(i, b.getHeight());
			while ((sy - 4 >= 0) && (sx + 3 <= b.getWidth())) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = sx + l;
					posY[l] = sy - l;
				}
				
				pruebaCuatroEnRayaDesplaza(posX, posY);
				sy--; sx++;
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal \
	@Test
	public void testCuatroEnRayaDiag2Desplaza() {
		Board b = r.newBoard();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int i = 1; i <= b.getHeight() + b.getWidth() - 1; ++i) {
			int sx = Math.min(i,  b.getWidth());
			int sy = Math.min(b.getWidth() + b.getHeight() - i, b.getHeight());
			while ((sy - 4 >= 0) && (sx - 4 >= 0)) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = sx - l;
					posY[l] = sy - l;
				}
				pruebaCuatroEnRayaDesplaza(posX, posY);
				sy--; sx--;
			}
		}
	}
}
