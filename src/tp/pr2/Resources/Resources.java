package tp.pr2.Resources;

import java.util.Scanner;

import tp.pr2.logic.Board;
import tp.pr2.logic.Counter;
import tp.pr2.logic.Game; 

public class Resources {
	
	public static final int TILES_TO_WIN = 4;
	public static final int DIMX_CONNECT4 = 7, DIMY_CONNECT4 = 6;
	public static final int DIMX_COMPLICA = 7, DIMY_COMPLICA = 4;

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
	

//	
//	Para cuando cojamos del teclado.
//	
//	String[] words = line.split(" ");
//	Example: play co
//			 play c4
//			 
//			 make a move 
//			 words[0] (make)
//			 words[1] (a)
//			 words[2] (move)
//	
	
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
	
	public static void moveColumnDown(Board board, int column)
	{
		for (int i = 0; i < board.getHeight() - 1; i++)
		{
			board.setPosition(column, i+1, board.getPosition(column, i));
		}
	}
	public static void moveColumnUp(Board board, int column)
	{
		for (int i = board.getHeight() - 1; i < 0; i--)
		{
			board.setPosition(column, i-1, board.getPosition(column, i));
		}
	}
	
}
