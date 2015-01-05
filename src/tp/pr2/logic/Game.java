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
	
	public Game(GameRules rules) {  
		this.rules = rules;
		reset(rules); // Inicializa los atributos según el juego.
	}

	public boolean executeMove(Move mov){ 
		boolean valid = false;
		
		valid = mov.executeMove(board);
		// Aquí hay que llamar a varios métodos de las rules para que hagan su mierda.
		// Por un lado, public Counter rules.winningMove(Move lastMove, Board b)
		// => Si el counter que te devuelve, es distinto de EMPTY. Ha terminado el juego finished = true;
		// => Si el counter no es distinto empty, llamamos a rules.isDraw() para ver si hay tablas o no
		//    => Si hay tablas, finished = true;
		// 	  => Si NO hay tablas, finishd = false y se podrá seguir jugando...
		
		return valid;
	}
	
	public void reset(GameRules rules){ 
		// Reinicia las reglas del juego (los atributos de Game)
		
		board = rules.newBoard();
		turn = rules.initialPlayer();
		winner = Counter.EMPTY;
		finished = false;
		stack = new Move[10]; // NO sé si está bien: Crear un array de 10 movimientos?
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
		stack[numUndo] = movement;
		numUndo++;
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
