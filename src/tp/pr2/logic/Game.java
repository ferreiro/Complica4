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
	
	public boolean checkHorizontal(Connect4Rules rules) {
		boolean isWinner = false;
		int tilesCounter, y, x;
		Counter counter, nextCounter;
		int dimX = Resources.DIMX_CONNECT4;
		
		y = Resources.DIMY_CONNECT4; // Starts at bottom
		
		while((y >= 1) && (!isWinner)) 
		{	
			tilesCounter = 1; // Reset counter
			x = 1; // Starts at first position
			counter = board.getPosition(x, y); // Color of first cell on each iteration
			
			while ((x < dimX) && (!isWinner)) 
			{
				nextCounter = board.getPosition(x + 1, y);
				
				if ((counter == nextCounter) && (counter != Counter.EMPTY)) {
					tilesCounter++;
					if (tilesCounter == Resources.TILES_TO_WIN) {
						isWinner = true;
//						this.winner = counter; no se si esta linea se deja como antes o simplemente lo actualizamos desde fuera
					}
				}
				else {
					tilesCounter = 1;
					counter = board.getPosition(x + 1, y); // next Cell color
				}		
				
				x++; // Go to right
			}			
			y--; // Decrease the row (from bottom to top)
		}
		return isWinner;
	}
	
	public boolean checkHorizontal(ComplicaRules rules) {
		boolean isWinner = false;
		int tilesCounter, y, x;
		Counter counter, nextCounter;
		int whiteCounter= 0;
		int blackCounter = 0;
		int dimX = Resources.DIMX_COMPLICA;
		
		y = Resources.DIMY_COMPLICA; // Starts at bottom
		
		while(y >= 1) 
		{	
			tilesCounter = 1; // Reset counter
			x = 1; // Starts at first position
			counter = board.getPosition(x, y); // Color of first cell on each iteration
			
			while (x < dimX)
			{
				nextCounter = board.getPosition(x + 1, y);
				
				if ((counter == nextCounter) && (counter != Counter.EMPTY)) {
					tilesCounter++;
					if (tilesCounter == Resources.TILES_TO_WIN) {
						if (counter.equals(Counter.BLACK)){
							blackCounter++;
						}
						else if (counter.equals(Counter.WHITE)){
							whiteCounter++;
						}
						tilesCounter = 1;
					}
				}
				else {
					tilesCounter = 1;
					counter = board.getPosition(x + 1, y); // next Cell color
				}		
				
				x++; // Go to right
			}			
			y--; // Decrease the row (from bottom to top)
		}
		if ((blackCounter > 0) && (whiteCounter > 0)){
			isWinner = false;
		}
		else {
			isWinner = true;
		}
		
		return isWinner;
	}
		

	/***
	 * Check Vertical tiles 
	 * Comprueba si hay en alguna columna se ha formado una tile con  
	 * la dimension de la constante para ganar
	 */

	public boolean checkVertical(Connect4Rules rules) {
		boolean isWinner = false;
		int tilesCounter, y, x;
		Counter counter, nextCounter;
		int dimX = Resources.DIMX_CONNECT4;
		
		x = 1;
		
		while((x <= dimX) && (!isWinner)) 
		{
			tilesCounter = 1; // Reset counter
			y = board.getHeight(); // Start at bottom
			counter = board.getPosition(x, y); // Color of first cell on each iteration
			
			while((y > 1) && (!isWinner))  
			{
				nextCounter = board.getPosition(x, y - 1); // take the color of row before
				
				if ((counter == nextCounter) && (counter != Counter.EMPTY)) {
					tilesCounter++;
					if (tilesCounter == Resources.TILES_TO_WIN) {
						isWinner = true;
//						this.winner = counter; no se si esta linea se deja como antes o simplemente lo actualizamos desde fuera
					}
				}
				else {
					tilesCounter = 1;
					counter = board.getPosition(x, y - 1); // next Cell color
				}		
				
				y--;
			}			
			x++;
		}
		return isWinner;
	}
	
	public boolean checkVertical(ComplicaRules rules) {
		boolean isWinner = false;
		int tilesCounter, y, x;
		Counter counter, nextCounter;
		int dimX = Resources.DIMX_COMPLICA;
		int whiteCounter= 0;
		int blackCounter = 0;
		
		x = 1;
		
		while(x <= dimX) 
		{
			tilesCounter = 1; // Reset counter
			y = board.getHeight(); // Start at bottom
			counter = board.getPosition(x, y); // Color of first cell on each iteration
			
			while(y > 1)  
			{
				nextCounter = board.getPosition(x, y - 1); // take the color of row before
				
				if ((counter == nextCounter) && (counter != Counter.EMPTY)) {
					tilesCounter++;
					if (tilesCounter == Resources.TILES_TO_WIN) {
						if (counter.equals(Counter.BLACK)){
							blackCounter++;
						}
						else if (counter.equals(Counter.WHITE)){
							whiteCounter++;
						}
						tilesCounter = 1;
					}
				}
				else {
					tilesCounter = 1;
					counter = board.getPosition(x, y - 1); // next Cell color
				}		
				
				y--;
			}			
			x++;
		}
		if ((blackCounter > 0) && (whiteCounter > 0)){
			isWinner = false;
		}
		else {
			isWinner = true;
		}
		
		return isWinner;
	}
	
	
//	
//	/***
//	 * Check Diagonal 1
//	 * Sentido:  \
//	 * Primero empieza desde la esquina superior izquierda y 
//	 * comprueba cada diagonal (aumentando la row)
//	 * En el segundo bucle comprueba las diagonales
//	 * pero para abajo (desde la esquina superior derecha hasta abajo)
//	 * 
//	 */
//		
	public boolean checkDiagonal1(Connect4Rules rules) {
		boolean isWinner = false;
		int y, x, tilesCounter, aux_Y, aux_X, numIterations;
		Counter color, nextColor;
		int dimX = Resources.DIMX_CONNECT4;
		int dimY = Resources.DIMY_CONNECT4;
		
		// starting bottom left position
		// Checks diagonals until the first cell (1,1)

		x = 1; // Always start in the first column
		y = 1; // Always start in the last row 
		numIterations = y;
		
		while ((y <= dimY) && !(isWinner)) {
			x = 1;
			aux_Y = y;
			tilesCounter = 1;
			
			if (numIterations > dimX) //it was with get width so it has to be simX
			{
				numIterations = dimX;
			}
			
			while ((x < numIterations) && !(isWinner)) {
				color = board.getPosition(x, aux_Y);
				nextColor = board.getPosition(x + 1, aux_Y - 1);
				
				if ((color == nextColor) && (color != Counter.EMPTY)) {
					tilesCounter++;
					if (tilesCounter == Resources.TILES_TO_WIN) {
						isWinner = true;
//						finished = true;
//						winner = color;
					}
				}
				else
				{
					tilesCounter = 1;
				}	
				aux_Y--;
				x++;
			}			
			y++;
			numIterations++;
		}
		
		if (!isWinner) {
			// starting at (height, height) ex: (5,5)
			// Checks from bottom to top right
	 
			y = dimY; // Always start in the last row
			x = dimX; // Always start in the first column; pero aqui ponia getHeight no Width
			color = board.getPosition(x, y);
			int counter = 1;
			
			while ((x > 1) && !(isWinner)) {
				y = dimY;
				aux_X = x;
				tilesCounter = 1;
				numIterations = dimX - x + 1;
				if (numIterations > dimY)
				{
					numIterations = dimY;
				}
				counter = 1;
				while ((counter < numIterations) && !(isWinner)) {
					color = board.getPosition(aux_X, y);
					nextColor = board.getPosition(aux_X + 1, y - 1);
					
					if ((color == nextColor) && (color != Counter.EMPTY)) {
						tilesCounter++;
						if (tilesCounter == Resources.TILES_TO_WIN) {
							isWinner = true;
//							finished = true;
//							winner = color;
						}
					}
					else
					{
						tilesCounter = 1;
					}	
					y--;
					aux_X++;
					
					counter++;
				}			
				x--;
			}			
		}
		
		return isWinner;
	}
	
	public boolean checkDiagonal1(ComplicaRules rules) {
		boolean isWinner = false;
		int y, x, tilesCounter, aux_Y, aux_X, numIterations;
		Counter color, nextColor;
		int dimX = Resources.DIMX_COMPLICA;
		int dimY = Resources.DIMY_COMPLICA;
		int whiteCounter= 0;
		int blackCounter = 0;
		
		// starting bottom left position
		// Checks diagonals until the first cell (1,1)

		x = 1; // Always start in the first column
		y = 1; // Always start in the last row 
		numIterations = y;
		
		while (y <= dimY) {
			x = 1;
			aux_Y = y;
			tilesCounter = 1;
			
			if (numIterations > dimX) //it was with get width so it has to be simX
			{
				numIterations = dimX;
			}
			
			while (x < numIterations) {
				color = board.getPosition(x, aux_Y);
				nextColor = board.getPosition(x + 1, aux_Y - 1);
				
				if ((color == nextColor) && (color != Counter.EMPTY)) {
					tilesCounter++;
					if (tilesCounter == Resources.TILES_TO_WIN) {
						if (color.equals(Counter.BLACK)){
							blackCounter++;
						}
						else if (color.equals(Counter.WHITE)){
							whiteCounter++;
						}
						tilesCounter = 1;
					}
				}
				else
				{
					tilesCounter = 1;
				}	
				aux_Y--;
				x++;
			}			
			y++;
			numIterations++;
		}
		
		if (!isWinner) {
			// starting at (height, height) ex: (5,5)
			// Checks from bottom to top right
	 
			y = dimY; // Always start in the last row
			x = dimX; // Always start in the first column; pero aqui ponia getHeight no Width
			color = board.getPosition(x, y);
			int counter = 1;
			
			while (x > 1) {
				y = dimY;
				aux_X = x;
				tilesCounter = 1;
				numIterations = dimX - x + 1;
				if (numIterations > dimY)
				{
					numIterations = dimY;
				}
				counter = 1;
				while (counter < numIterations){
					color = board.getPosition(aux_X, y);
					nextColor = board.getPosition(aux_X + 1, y - 1);
					
					if ((color == nextColor) && (color != Counter.EMPTY)) {
						tilesCounter++;
						if (tilesCounter == Resources.TILES_TO_WIN) {
							if (color.equals(Counter.BLACK)){
								blackCounter++;
							}
							else if (color.equals(Counter.WHITE)){
								whiteCounter++;
							}
							tilesCounter = 1;
						}
					}
					else
					{
						tilesCounter = 1;
					}	
					y--;
					aux_X++;
					
					counter++;
				}			
				x--;
			}			
		}
		
		if ((blackCounter > 0) && (whiteCounter > 0)){
			isWinner = false;
		}
		else {
			isWinner = true;
		}
		
		return isWinner;
	}
	
	
	
	/***
	 * Check Diagonal 2
	 * Sentido /
	 * Primero empieza desde la esquina superior derecha y 
	 * comprueba cada diagonal hacia la izquierda
	 * En el segundo bucle comprueba las diagonales
	 * pero para abajo (desde la esquina superior derecha hasta abajo)
	 * 
	 */
	
	public boolean checkDiagonal2(Connect4Rules rules) {
		boolean isWinner = false;
		int y, x, tilesCounter, aux_X, aux_Y, numIterations;
		Counter color, nextColor;
		
		y = 1; // Always start in the firt row
		x = Resources.DIMX_CONNECT4; // Always start in the last column
		color = board.getPosition(x, y);
		numIterations = 1;
		// starting top right position
		// Checks until the first diagonal
		
		while ((x > 1) && !(isWinner)) {
			y = 1;
			aux_X = x;
			tilesCounter = 1;
			if (numIterations > Resources.DIMY_CONNECT4)
			{
				numIterations = Resources.DIMY_CONNECT4;
			}
			
			while ((y < numIterations) && !(isWinner)) { // o aqui
				color = board.getPosition(aux_X, y);
				nextColor = board.getPosition(aux_X + 1, y + 1);
				
				if ((color == nextColor) && (color != Counter.EMPTY)) {
					tilesCounter++;
					if (tilesCounter == Resources.TILES_TO_WIN) {
						isWinner = true;
//						finished = true;
//						winner = color;
					}
				}
				else
				{
					tilesCounter = 1;
				}	
				y++;
				aux_X++;
			}			
			x--;
			numIterations++;
		}
		
		if (!isWinner) {
			// starting at (height, 1)
			// Checks diagonals to bottom

			x = 1; // Always start in the first column
			y = Resources.DIMY_CONNECT4; // Always start in the firt row
			color = board.getPosition(x, y);
			
			while ((y >= 1) && !(isWinner)) {
				x = 1;
				aux_Y = y;
				tilesCounter = 1;
				numIterations = Resources.DIMY_CONNECT4 - y + 1;//antes era width y sin el +1
				if (numIterations > Resources.DIMX_CONNECT4)
				{
					numIterations = Resources.DIMX_CONNECT4;
				}
				
				while ((x < numIterations) && !(isWinner)) {//<=
					color = board.getPosition(x, aux_Y);
					nextColor = board.getPosition(x + 1, aux_Y + 1);
					
					if ((color == nextColor) && (color != Counter.EMPTY)) {
						tilesCounter++;
						if (tilesCounter == Resources.TILES_TO_WIN) {
							isWinner = true;
//							finished = true;
//							winner = color;
						}
					}
					else
					{
						tilesCounter = 1;
					}	
					x++;
					aux_Y++;
				}			
				y--;
			}			
		}
		
		return isWinner;
	}
	
	public boolean checkDiagonal2(ComplicaRules rules) {
		boolean isWinner = false;
		int y, x, tilesCounter, aux_X, aux_Y, numIterations;
		Counter color, nextColor;
		int whiteCounter= 0;
		int blackCounter = 0;
		
		y = 1; // Always start in the firt row
		x = Resources.DIMX_COMPLICA; // Always start in the last column
		color = board.getPosition(x, y);
		numIterations = 1;
		// starting top right position
		// Checks until the first diagonal
		
		while (x > 1){
			y = 1;
			aux_X = x;
			tilesCounter = 1;
			if (numIterations > Resources.DIMY_COMPLICA)
			{
				numIterations = Resources.DIMY_COMPLICA;
			}
			
			while (y < numIterations) { // o aqui
				color = board.getPosition(aux_X, y);
				nextColor = board.getPosition(aux_X + 1, y + 1);
				
				if ((color == nextColor) && (color != Counter.EMPTY)) {
					tilesCounter++;
					if (tilesCounter == Resources.TILES_TO_WIN) {
						if (color.equals(Counter.BLACK)){
							blackCounter++;
						}
						else if (color.equals(Counter.WHITE)){
							whiteCounter++;
						}
						tilesCounter = 1;
					}
				}
				else
				{
					tilesCounter = 1;
				}	
				y++;
				aux_X++;
			}			
			x--;
			numIterations++;
		}
		
		if (!isWinner) {
			// starting at (height, 1)
			// Checks diagonals to bottom

			x = 1; // Always start in the first column
			y = Resources.DIMY_COMPLICA; // Always start in the firt row
			color = board.getPosition(x, y);
			
			while (y >= 1) {
				x = 1;
				aux_Y = y;
				tilesCounter = 1;
				numIterations = Resources.DIMY_COMPLICA - y + 1;//antes era width y sin el +1
				if (numIterations > Resources.DIMX_COMPLICA)
				{
					numIterations = Resources.DIMX_COMPLICA;
				}
				
				while ((x < numIterations) && !(isWinner)) {//<=
					color = board.getPosition(x, aux_Y);
					nextColor = board.getPosition(x + 1, aux_Y + 1);
					
					if ((color == nextColor) && (color != Counter.EMPTY)) {
						tilesCounter++;
						if (tilesCounter == Resources.TILES_TO_WIN) {
							if (color.equals(Counter.BLACK)){
								blackCounter++;
							}
							else if (color.equals(Counter.WHITE)){
								whiteCounter++;
							}
							tilesCounter = 1;
						}
					}
					else
					{
						tilesCounter = 1;
					}	
					x++;
					aux_Y++;
				}			
				y--;
			}			
		}
		
		if ((blackCounter > 0) && (whiteCounter > 0)){
			isWinner = false;
		}
		else {
			isWinner = true;
		}
		
		return isWinner;
	}
}
