package tp.pr2.logic;

import tp.pr2.logic.Board;
import tp.pr2.Resources.Resources;

public class ComplicaRules implements GameRules {
	private int dimx = Resources.DIMX_COMPLICA;
	private int dimy = Resources.DIMY_COMPLICA;

	// Override
	// Build a board that is to be used in the game, according to the rules of that game.
	
	public Board newBoard() {
		return new Board(dimx, dimy);
	}

	// Override
	// Consulta si hay empate. tablas(Ficha ultimoEnPoner, Tablero t) 
	
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
		return Counter.WHITE;
	}

	@Override
	public Counter nextTurn(Counter lastMove, Board b) {
		// TODO Auto-generated method stub
		return null;
	}


}
