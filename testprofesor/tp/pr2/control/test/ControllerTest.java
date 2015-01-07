package tp.pr2.control.test;

import java.util.Scanner;

import org.junit.Test;

import static org.junit.Assert.fail;
import tp.pr2.control.Controller;
import tp.pr2.logic.Game;
import tp.pr2.logic.Connect4Rules;

public class ControllerTest {
	
	// Comprobar que existe la clase Controlador
	@Test
	public void testCtor() {
		try {
			Game p = new Game(new Connect4Rules());
			Scanner sc = new Scanner(System.in);
			new Controller(p, sc);
		} catch (Exception e) {
			fail("The Controller constructor is defective.");
		}
	}
}
