package tp.pr1.resources;

import java.util.Scanner;

import tp.pr1.logic.Board;
import tp.pr1.logic.Counter;
import tp.pr1.logic.Game;

public class Resources {
	
	public static final int TILES_TO_WIN = 4;
	public static final int BOARD_DIMX = 7, BOARD_DIMY = 6;

	public static int freeRowPosition(int col, Board board) {
		int row = -1;
		int y =  board.getHeight();
		boolean empty = false;
	
		while ((!empty) && (y >= 1)) {
			if (board.getPosition(col,y) == Counter.EMPTY) {
				empty = true;
				row = y;
			}
			else
			{
				y--;
			}			
		} 
		return row;
	}

	// This functions is used for the Undo Function
	// Which returns the first row occupied (means that is the last movement of the user in that colum)
	
	public static int occupiedRowPosition(Board board, int col) {
		int height = board.getHeight();
		int row = height, y = 1;
		boolean occupied = false;
		
		while (!occupied && y <= height){
			if (board.getPosition(col, y) != Counter.EMPTY) 
			{
				occupied = true;
				row = y;
			}
			y++;
		}
		return row;
	}
	
	public static int menu(Game game, Scanner input) {
		int option = - 1;
		String optionString = "";
		String lowerCaseStr;
		boolean valid = false;
		
		while (!valid) {

			game.getBoard().printBoard();
			whoMoves(game);
			System.out.print ("Please enter a command: ");

			optionString = input.nextLine().toUpperCase();
			 
			if (optionString.equals("MAKE A MOVE"))
			{
				option = 0;
			}
			else if (optionString.equals("UNDO"))
			{
				option = 1;
			}
			else if (optionString.equals("RESTART"))
			{
				option = 2;
			}
			else if (optionString.equals("EXIT"))
			{
				option = 3;
			}
			else
			{
				lowerCaseStr = optionString.toLowerCase();
				System.err.print(lowerCaseStr + ": command not understood, please try again");
			}
			
			if ((option >= 0) && (option <= 3)) {
				valid = true;
			}
		}
		
		return option;
	}
	
	public static void whoMoves(Game game) {
		if (game.getTurn() == Counter.WHITE)
		{
			System.out.println("White to move");
		}
		else if (game.getTurn() == Counter.BLACK)
		{
			System.out.println("Black to move");
		}
	}
	
}
