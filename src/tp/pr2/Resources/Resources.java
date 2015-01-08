package tp.pr2.Resources;

import java.util.Scanner;

import tp.pr2.logic.Board;
import tp.pr2.logic.Counter;
import tp.pr2.logic.Game; 

public class Resources {
	public static final int MAX_STACK = 100;
	public static final int TILES_TO_WIN = 4;
	// public static final int DIMX_CONNECT4 = 2, DIMY_CONNECT4 = 2;
	public static final int DIMX_CONNECT4 = 7, DIMY_CONNECT4 = 6;
//	public static final int DIMX_COMPLICA = 2, DIMY_COMPLICA = 2;
	public static final int DIMX_COMPLICA = 4, DIMY_COMPLICA = 7;

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
			lowerCaseStr = optionString.toLowerCase();
			String[] words = optionString.split(" ");

			if (words.length == 1)
			{
				if (words[0].equals("UNDO"))
				{
					option = 1;
				}
				else if (words[0].equals("RESTART"))
				{
					option = 2;
				}
				else if (words[0].equals("EXIT"))
				{
					option = 3;
				}
				else
				{
					System.err.println(lowerCaseStr + ": command not understood, please try again");
				}
			}
			else if (words.length == 2)
			{
				if (words[0].equals("PLAY"))
				{
					if (words[1].equals("C4"))
					{
						option = 4;
					}
					else if (words[1].equals("CO"))
					{
						option = 5;
					}
					else
					{
						System.err.println(lowerCaseStr + ": command not understood, please try again");
					}
				}
				else
				{
					System.err.println(lowerCaseStr + ": command not understood, please try again");
				}
			}
			else if (words.length == 3)
			{
				if (words[0].equals("MAKE"))
				{
					if (words[1].equals("A"))
					{
						if (words[2].equals("MOVE"))
						{
							option = 0;
						}
						else
						{
							System.err.println(lowerCaseStr + ": command not understood, please try again");
						}
							
					}
					else
					{
						System.err.println(lowerCaseStr + ": command not understood, please try again");
					}
					
				}
				else
				{
					System.err.println(lowerCaseStr + ": command not understood, please try again");
				}
			}
			else
			{
				System.err.println(lowerCaseStr + ": command not understood, please try again");
			}
			
			if ((option >= 0) && (option <= 5)) {
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
		for (int i = board.getHeight(); i > 1; i--) {
			board.setPosition(column, i, board.getPosition(column , i - 1));
		}
	}
	public static void moveColumnUp(Board board, int column)
	{
		for (int i = 1 ; i < board.getHeight(); i++)
		{
			board.setPosition(column, i, board.getPosition(column, i + 1));
		}
	}
	
}
