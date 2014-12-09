package tp.pr2.logic;

import tp.pr2.Resources.Resources;
import tp.pr2.logic.Counter;
import tp.pr2.logic.Board;

public class MoveConnect4 extends Move {
	 
	public MoveConnect4(int column, Counter counter)
	{
		super(counter, column); 
	}

	public boolean executeMove(Board board) {
		
		boolean validMove = false;
		int firstFreeRow = 1;

			if ((column >= 1) && (column <= Resources.DIMX_CONNECT4)) {				
				firstFreeRow = Resources.freeRowPosition(column, board);

				if (firstFreeRow > - 1) {		
					validMove = true;

					board.setPosition(column, firstFreeRow, currentPlayer); 
				}
			}
		
		return validMove;
	}

	public void undo(Board board) {
		int columnToUndo, rowToUndo;
		
		columnToUndo = column;
		rowToUndo = Resources.occupiedRowPosition(board, columnToUndo); 
		
		board.setPosition(columnToUndo, rowToUndo, Counter.EMPTY);
	}
}

