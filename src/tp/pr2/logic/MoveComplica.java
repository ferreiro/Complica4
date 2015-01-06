package tp.pr2.logic;

import tp.pr2.Resources.Resources;

public class MoveComplica extends Move{
//	Elultimo movimiento perdido
	protected Counter lostMove; 
	
	//constructor MovimientoComplica(int donde, Ficha color)
	public MoveComplica(int column, Counter counter)
	{
		super(counter, column); 
	}

	public boolean executeMove(Board board) {
		boolean validMove = false;
		int firstFreeRow = 1;
		lostMove = Counter.EMPTY;

			if ((column >= 1) && (column <= Resources.DIMX_CONNECT4)) {				
				firstFreeRow = Resources.freeRowPosition(column, board); // hay que cambiar esto

					if (!board.isFull()) {
						if (firstFreeRow > - 1) {
							validMove = true;
							board.setPosition(column, firstFreeRow, currentPlayer);
						}
					}
					else {
						lostMove = board.getPosition(column, board.getHeight() - 1); 
						Resources.moveColumnDown(board, column);
						board.setPosition(column, 0, currentPlayer);
					}
			}
		return validMove;
	}

	public void undo(Board board) {
		int columnToUndo, rowToUndo;
		
		columnToUndo = column;
//		rowToUndo = Resources.occupiedRowPosition(board, columnToUndo); 
//		board.setPosition(columnToUndo, rowToUndo, Counter.EMPTY);
		if (lostMove == Counter.EMPTY) {
			rowToUndo = Resources.occupiedRowPosition(board, columnToUndo); 
			board.setPosition(columnToUndo, rowToUndo, Counter.EMPTY);
		}
		else {
			Resources.moveColumnUp(board, column);
			board.setPosition(column, board.getHeight() - 1, lostMove);
		}	
	}
	
}
