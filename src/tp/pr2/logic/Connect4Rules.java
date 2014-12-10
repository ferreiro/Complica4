package tp.pr2.logic;

import tp.pr2.Resources.Resources;

public class Connect4Rules implements GameRules {
	private int dimx = Resources.DIMX_COMPLICA;
	private int dimy = Resources.DIMY_COMPLICA;
	
	@Override
	public Board newBoard() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isDraw(Counter lastMove, Board b) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Counter winningMove(Move lastMove, Board b) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Counter initialPLayer() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Counter nextTurn(Counter lastMove, Board b) {
		// TODO Auto-generated method stub
		return null;
	}
 
	

}


