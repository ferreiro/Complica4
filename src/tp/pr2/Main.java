package tp.pr2;

import java.util.Scanner;

import tp.pr2.control.Controller;
import tp.pr2.logic.Connect4Rules;
import tp.pr2.logic.Game;
import tp.pr2.logic.GameRules;
import tp.pr2.logic.Rules;

public class Main {

	public static void main(String[] args) {
		Game game;
		Scanner in;
		Controller controller;
		GameRules gameRules = new Connect4Rules();
		
		in = new Scanner(System.in); //	Read from the keyboard
		game = new Game(gameRules);
		
		controller = new Controller(game, in);
		controller.run();
		
		System.exit(0);
		
	}	
}
