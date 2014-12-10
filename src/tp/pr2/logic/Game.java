package tp.pr2.logic;

import tp.pr2.logic.Counter;
import tp.pr2.Resources.Resources;
import tp.pr2.logic.Board;
import tp.pr2.logic.Move;

public class Game {

	private Board board;
	private Counter winner;
	private Counter turn;
	private boolean finished;
	private Move[] stack;
	private int numUndo = 0; // si eso cambiamos la inicializacion
	private GameRules rules;
	
	 
	
	
	
	//Partida(ReglasJuego reglas)
	public Game(GameRules rules) {
		// reset function
		reset(rules);
	}
	
	//ejecutaMovimiento(Movimiento mov)
	public boolean executeMove(Move mov){
		boolean valid;

		valid = mov.executeMove(board);
		// Comprobar horizontal, vertical, diagonal...
		
		
		return valid;
	}
	
	public Counter getWinner(){
		return this.winner;
	}
	
	public Board getBoard(){
		return this.board;
	}
	
	public Counter getTurn(){
		return this.turn;
	}
	
	public boolean isFinished(){
		return this.finished;
	}
	
	//reset(ReglasJuego reglas) reinicia las reglas del juego
	public void reset(GameRules rules){
		rules.newBoard();	
	}
	
	public boolean undo(){
		boolean success = false;
		if (numUndo > 0) {
			success = true;
			stack[numUndo - 1].undo(board);
			numUndo--;
		}
		return success;
	}
	
	public void increaseStack(Move movement) {
		stack[numUndo] = movement;
		numUndo++;
	}
}
