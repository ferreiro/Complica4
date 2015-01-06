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

	public boolean executeMove(Move mov){  
		Counter wonColor;
		boolean valid, isDraw; 
		
		valid = mov.executeMove(board);
	
		if (valid) { // Aquí hay que llamar a varios métodos de las rules para que hagan su mierda.
			
			increaseStack(mov);
			this.turn = rules.nextTurn(mov.currentPlayer, board);
			
			wonColor = rules.winningMove(mov, board); // Ver si ha ganado o n
			if (wonColor != Counter.EMPTY) {
				this.winner = wonColor;
				finished = true;
			}	
			
// 			No creo que esto vaya aquí
//			if (rules.isDraw(mov.currentPlayer, board)) {
//				// Hay tablas!
//			}
			
		}
		
		return valid;
	}
	
	public void reset(GameRules rules) { 
		// Reinicia las reglas del juego (los atributos de Game)
		
		board = rules.newBoard();
		turn = rules.initialPlayer();
		winner = Counter.EMPTY;
		finished = false;
		stack = new Move[Resources.MAX_STACK]; // NO sé si está bien: Crear un array de 10 movimientos?
		numUndo = 0;	
	}
	
	//  Undo and stack 
	
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
