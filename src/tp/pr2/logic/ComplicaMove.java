package tp.pr2.logic;

import tp.pr2.Resources.Resources;


public class ComplicaMove extends Move {
//	Elultimo movimiento perdido
	protected Counter lostMove; 
	
	//constructor MovimientoComplica(int donde, Ficha color)
	public ComplicaMove(int column, Counter counter)
	{
		super(counter, column); 
	}

	public boolean executeMove(Board board) {
		boolean validMove = false;
		int firstFreeRow = 1;
		lostMove = Counter.EMPTY;

			if ((column >= 1) && (column <= Resources.DIMX_COMPLICA)) {				
				firstFreeRow = Resources.freeRowPosition(column, board); // hay que cambiar esto

				if (firstFreeRow > - 1) {
					validMove = true;
					board.setPosition(column, firstFreeRow, currentPlayer);
				}
				else if (firstFreeRow == - 1) {
					validMove = true;
					lostMove = board.getPosition(column, board.getHeight()); 
					Resources.moveColumnDown(board, column);
					board.setPosition(column, 1, currentPlayer);
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
			board.setPosition(column, board.getHeight(), lostMove);
		}	
	}
	
}
