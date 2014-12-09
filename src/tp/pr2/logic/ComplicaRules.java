package tp.pr2.logic;

import tp.pr2.logic.Board;
import tp.pr2.Resources.Resources;


public class ComplicaRules implements GameRules {
	private Board board;
	
	public void initialBoard() {
 		int dimx, dimy;
		dimx = Resources.DIMX_COMPLICA;
		dimy = Resources.DIMY_COMPLICA; 
		this.board = new Board(dimx, dimy);
	}

	@Override
	public boolean isWinner(Move lastMovement, Board board) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Counter initialPLayer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Counter nextTurn(Counter lastPLayerMoving, Board board) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean tie(Counter lastPLayerMoving, Board board) {
		// TODO Auto-generated method stub
		return false;
	}

	
	
}
