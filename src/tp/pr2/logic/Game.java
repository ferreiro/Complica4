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
	protected GameRules rules;
	
	public Game(GameRules rules) { 
		this.rules = rules; 
		reset(rules); // Crea un primer juego del tipo conecta4. 
	}

	public boolean executeMove(Move mov) {  
		Counter wonColor;
		boolean valid = false, draw; 
		
		if ((mov.currentPlayer == turn) && (!finished)) { // No puede permitir hacer movimientos fuera de turno o se ha terminado el juego
			
			winner = Counter.EMPTY;
			finished = false;
			valid = mov.executeMove(board);
			
			if (valid) { 
				
				wonColor = rules.winningMove(mov, board); // Importante! Primero hay que llamar a esta, para
														  // que actualice el color del ganador!
				draw = rules.isDraw(mov.currentPlayer, board); // Hay empate?
				
				if (draw) {
					finished = true; // hay empate, terminar
					this.winner = Counter.EMPTY;
				}
				else {
					if (wonColor == Counter.EMPTY) {
						increaseStack(mov); // Si no gana nadie, guardar movimiento
						turn = rules.nextTurn(mov.currentPlayer, board); // Cambiar el turno.
					}
					else {
						this.winner = wonColor;
						finished = true;
					} 
				}
			}
		}
		
		return valid;
	}
	
	public void reset(GameRules rules) { // Reinicia las reglas del juego (los atributos de Game)
		this.rules = rules;
		board = rules.newBoard();
		turn = rules.initialPlayer();
		winner = Counter.EMPTY;
		finished = false;
		stack = new Move[Resources.MAX_STACK]; // NO sé si está bien: Crear un array de 10 movimientos?
		numUndo = 0;	
	}
	
	//  Undo and stack 
	
	public boolean undo() {
		boolean success = false;
		Move previousMove;
		
		if (numUndo > 0) {
			previousMove = stack[numUndo - 1]; // local variable con el movimiento anterior

			numUndo--;			
			success = true;
			previousMove.undo(board); 
			turn = previousMove.currentPlayer; // Bug fixed!!! Actualizar el color del jugador!
		}
		
		return success;
	}
	
	public void increaseStack(Move movement) {
		if (numUndo < Resources.MAX_STACK ) {
			stack[numUndo] = movement;
			numUndo++;
		}
	}
	
	// Getters and setters 
	
	public boolean isFinished() {
		return this.finished;
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
	
}
