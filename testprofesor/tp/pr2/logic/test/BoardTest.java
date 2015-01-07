package tp.pr2.logic.test;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import tp.pr2.logic.Counter;
import tp.pr2.logic.Board;

public class BoardTest {
	
	private static final int TX = 10;
	private static final int TY = 8;
	
	@Test
	public void testCtor() {

		for (int x = 1; x <= 10; ++x) {
			for (int y = 1; y <= 10; ++y) {
				Board b = new Board(x, y);
				assertEquals("The width of the board should agree with that provided as argument to the constructor.", x, b.getWidth());
				assertEquals("The height of the board should agree with that provided as argument to the constructor.", y, b.getHeight());
			}
		}
	}
	
	@Test
	public void testCtorParamsIncorrectos() {
		try {
			for (int x = -10; x <= 0; ++x) {
				for (int y = -10; y <= 0; ++y) {
					Board b = new Board(x, y);
					assertEquals("The Board constructor should build a 1x1 board if an invalid width (less than 1) is passed as an argument.", 1, b.getHeight());
					assertEquals("The Board constructor should build a 1x1 board if an invalid height (less than 1) is passed as an argument.", 1, b.getWidth());
				}
			}
		} catch (Exception ex) {
			fail("The Board constructor should build a 1x1 board if an invalid size (less than 1) is passed as an argument.");
		}
	}
	
	@Test
	public void testCtorVaciaTablero() {
		Board b = new Board(TX, TY);
		assertTrue("The board should initially be empty.", Utils.tableroVacio(b));
	}
	
	@Test
	public void testGetCasilla() {
		Board b = new Board(TX, TY);
		for (int x = 1; x <= TX; ++x) {
			for (int y = 1; y <= TY; ++y) {
				b.setPosition(x, y, Counter.WHITE);
				assertEquals("getPostion(x,y) should return the value of the counter previously inserted via setPosition(x,y, value).", Counter.WHITE, b.getPosition(x, y));
				b.setPosition(x, y, Counter.BLACK);
				assertEquals("getPostion(x,y) should return the value of the counter previously inserted via setPosition(x,y, value).", Counter.BLACK, b.getPosition(x, y));
				b.setPosition(x, y, Counter.EMPTY);
				assertEquals("getPosition does not appear to allow the counter at a given position to be deleted (i.e. replaced by Counter.Empty).", Counter.EMPTY, b.getPosition(x, y));
			}
		}
	}
	
	@Test
	public void testGetCasillaIncorrecta() {
		try {
			Board t = new Board(TX, TY);
			for (int x = -TX; x <= 2*TX; ++x) {
				if ((1 <= x) && (x <= TX)) continue;
				for (int y = -TY; y <= 2*TY; ++y) {
					if ((1 <= y) && (y <= TY)) continue;
					assertEquals("getPosition should return Counter.EMPTY if an invalid position is passed as argument.", Counter.EMPTY, t.getPosition(x,  y));
				}
			}
		} catch (Exception ex) {
			fail("getPosition should not fail if an invalid position is passed as argument.");
		}
	}
	
	@Test
	public void testSetCasillaIncorrecta() {
		try {
			Board t = new Board(TX, TY);
			for (int x = -TX; x <= 2*TX; ++x) {
				if ((1 <= x) && (x <= TX)) continue;
				for (int y = -TY; y <= 2*TY; ++y) {
					if ((1 <= y) && (y <= TY)) continue;
					t.setPosition(x, y, Counter.BLACK);
				}
			}
		} catch (Exception ex) {
			fail("SetPosition should not fail if an invalid position is passed as argument.");
		}
	}
}
