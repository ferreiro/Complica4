package tp.pr1.control;

import java.util.Scanner;

import tp.pr1.logic.Counter;
import tp.pr1.logic.Game;
import tp.pr1.resources.Resources;

public class Controller {
	private Game game;
	private Scanner in;
	
	public Controller(Game g, java.util.Scanner in) {
		game = g;
		this.in = in;
	}

	public void run() {
		int option = -1, col;
		boolean exit = false;
		boolean valid = false;
		boolean undo;
		String basura;
		
		do {
			
			option = Resources.menu(game, in);
			
			switch(option) {
			case 0: 
				// Make a move 
				
				System.out.print("Please provide the column number: ");
				col = this.in.nextInt();
				basura = this.in.nextLine();
				
				
				valid = game.executeMove(game.getTurn(), col);
				
				if (!valid) {
					System.out.println("Invalid move, please try again");
				}
				if (game.isFinished())
				{
					exit = true;
				}
					
				
				break;
			case 1:
				// Undo 
				undo = false;
				undo = game.undo();
				
				if (!undo) {
					System.out.println("Nothing to undo, please try again");
				}

				break;
			case 2:
				// Restart 
				game.reset();
				System.out.println("Game restarted");
				
				break;
			case 3:
				// Exist
				exit = true;
				break;
				
			}
			
			
			// If it's finished. Then exit the loop.
			
			if (game.isFinished()) 
			{
				game.getBoard().printBoard();
				in.close();
				Counter counterWinner = game.getWinner();
				exit = true;
				
				System.out.print("Game over."); 
				if (counterWinner != Counter.EMPTY) {
					if (counterWinner == Counter.WHITE)
					{
						System.out.println("White wins"); 
					}
					if (counterWinner == Counter.BLACK)
					{
						System.out.println("Black wins"); 
					}
				}
				else
				{
					System.out.println("Tie game, no winner");
				}					
			}  
		} while(!exit);	
		System.out.println("Closing the game... ");
		 
	}
}
