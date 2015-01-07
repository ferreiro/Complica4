package tp.pr2.logic.test;

import tp.pr2.logic.Counter;
import tp.pr2.logic.Board;

public class Utils {

	/**
	 * Comprueba si el tablero está vacío.
	 */
	public static boolean tableroVacio(Board b) {
		for (int x = 1; x <= b.getWidth(); ++x)
			if (!columnaVacia(b, x))
				return false;
		return true;
	}
	
	/**
	 * Comprueba si la columna x del tablero está vacía. 
	 */
	public static boolean columnaVacia(Board b, int x) {
		for (int y = 1; y <= b.getHeight(); ++y)
			if (b.getPosition(x, y) != Counter.EMPTY)
				return false;
		return true;
	}
	
	/**
	 * Devuelve una copia del tablero.
	 */
	public static Board copiaTablero(Board b) {
		Board copia = new Board(b.getWidth(), b.getHeight());
		
		for (int x=1; x<=b.getWidth(); x++) {
			for (int y=1; y<=b.getHeight(); y++) {
				copia.setPosition(x, y, b.getPosition(x, y));
			}
		}
		
		return copia;
	}
	
	/**
	 * Comprueba si dos tablero son iguales.
	 */
	public static boolean TablerosIguales(Board b1, Board b2) {
		if (b1 == b2)
			return true;
		
		if ((b1 == null && b2 != null) || (b1 != null && b2 == null))
			return false;
		
		if (b1.getWidth() != b2.getWidth())
			return false;
		if (b1.getHeight() != b2.getHeight())
			return false;
		
		for (int x=1; x<=b1.getWidth(); x++) {
			for (int y=1; y<=b1.getHeight(); y++) {
				if (b1.getPosition(x, y) != b2.getPosition(x, y))
					return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Devuelve la ficha contraria
	 */
	public static Counter contraria(Counter c) {
		if (c == Counter.WHITE)
			return Counter.BLACK;
		else if (c == Counter.BLACK)
			return Counter.WHITE;
		else
			return Counter.EMPTY;
	}
}
