package tp.pr2.logic.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import tp.pr2.logic.Counter;
import tp.pr2.logic.Move;
import tp.pr2.logic.Connect4Move;
import tp.pr2.logic.Game;
import tp.pr2.logic.Connect4Rules;
import tp.pr2.logic.GameRules;
import tp.pr2.logic.Board;

public class Connect4FourInARowTest {
	
	protected GameRules r;
	
	@Before
	public void init() {
		r = newRules();
	}
	
	protected GameRules newRules() {
		return new Connect4Rules();
	}
	
	protected Move newMove(int column, Counter colour) {
		return new Connect4Move(column, colour);
	}
	
	// Tableros sin 4 en raya
	@Test
	public void testNoCuatroEnRaya() {
		Game g = new Game(r);
		Board b = g.getBoard();

		for (int x=b.getWidth(); x>=1; x--) {
			for (int y=3; y>=1; y--) {
				Move move = newMove(x, g.getTurn());
				assertTrue(g.executeMove(move));
				
				assertEquals("The last move should not have led to a win.", Counter.EMPTY, r.winningMove(move, b));
				assertEquals("A winner should not have been assigned.", g.getWinner(), Counter.EMPTY);
				assertFalse("The last move should not have led to a draw.", r.isDraw(move.getPlayer(), b));		
				assertFalse("the last move should not have led to the game finishing.", g.isFinished());				
			}
		}
	}
	
	// Prepara la partida para que se pueda colocar, en el siguiente movimiento
	// la ficha del color dado en la posición indicada. Para eso utiliza
	// las reglas de la partida de C4. Para que pueda hacerlo, debe haber una columna
	// vacía en el tablero y que la columna donde se quiere colocar
	// cumpla las restricciones del conecta 4...
	private boolean preparaColocacionFicha(Game g, Counter colour, int x, int y) {

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

		if ((aPoner % 2 == 1) != (g.getTurn() == colour)) {
			// Hay que poner una en una columna dummy para ajustar
			// turno
			
			/*
			int aux = columnaAdecuada(t, g.getTurn(), x);
			if (aux == 0) return false;
			g.executeMove(newMove(aux, g.getTurn()));
			*/
			
			// HACK: poner y quitar la ficha para cambiar el turno
			g.executeMove(newMove(1, g.getTurn()));
			b.setPosition(1, b.getHeight(), Counter.EMPTY);
		}
		
		// Antes de poner, garantizamos que no hay huecos por
		// debajo...
		for (int i = ultimaConFicha + 1; i <= b.getHeight(); ++i) {
			if (b.getPosition(x,i) == Counter.EMPTY)
				b.setPosition(x, i, (colour == Counter.WHITE) ? Counter.BLACK : Counter.WHITE);
		}
		
		while (aPoner > 1) {
			Move move = newMove(x, g.getTurn());
			assertTrue(g.executeMove(move));
			
			assertTrue("The last move should not have led to a win.", r.winningMove(move, b) == Counter.EMPTY);
			assertFalse("The last move should not have led to a draw.", r.isDraw(move.getPlayer(), b));
			
			aPoner--;
		}
		
		return true;
	}	
	
	private void testCuatroEnRaya(int posX[], int posY[], int ultima, Counter colour) {
		Game g = new Game(newRules());
		Board b = g.getBoard();

		if (!preparaColocacionFicha(g, colour, posX[ultima], posY[ultima]))
			fail("Internal error of the tests :-?");
		
		for (int i = 0; i < posX.length; ++i) {
			if (i != ultima)
				b.setPosition(posX[i], posY[i], colour);
		}
		
		assertFalse("The game should not have finished since there are only three consecutive " + colour + " counters on the board.", g.isFinished());
		Move move = newMove(posX[ultima], colour);
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
	
	private void pruebaCuatroEnRaya(int posX[], int posY[]) {
		for (int i = 0; i < posX.length; ++i) {
			testCuatroEnRaya(posX, posY, i, Counter.WHITE);
			testCuatroEnRaya(posX, posY, i, Counter.BLACK);
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// horizontal
	@Test
	public void testCuatroEnRayaHorizontal() {
		Board b = r.newBoard();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int x = 1; x <= b.getWidth() - 3; ++x) {
			for (int y = 1; y <= b.getHeight(); ++y) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = x + l;
					posY[l] = y;
				}
				pruebaCuatroEnRaya(posX, posY);
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// vertical
	@Test
	public void testCuatroEnRayaVertical() {
		Board b = r.newBoard();
		
		int []posX = new int[4];
		int []posY = new int[4];
		for (int x = 1; x <= b.getWidth(); ++x) {
			for (int y = 1; y <= b.getHeight() - 3; ++y) {
				for (int l = 0; l < 4; ++l) {
					posX[l] = x;
					posY[l] = y + l;
				}
				testCuatroEnRaya(posX, posY, 0, Counter.WHITE);
				testCuatroEnRaya(posX, posY, 0, Counter.BLACK);
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal /
	@Test
	public void testCuatroEnRayaDiag1() {
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
				pruebaCuatroEnRaya(posX, posY);
				sy--; sx++;
			}
		}
	}
	
	// Partidas que terminan con todas las posibles 4 en raya
	// diagonal \
	@Test
	public void testCuatroEnRayaDiag2() {
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
				pruebaCuatroEnRaya(posX, posY);
				sy--; sx--;
			}
		}
	}
}
