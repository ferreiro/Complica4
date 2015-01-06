package tp.pr2.logic;

import tp.pr2.logic.Board;
import tp.pr2.Resources.Resources;

public class ComplicaRules implements GameRules {
	private int dimX = Resources.DIMX_COMPLICA;
	private int dimY = Resources.DIMY_COMPLICA;
	private Counter winner;

	public ComplicaRules() {  
		winner = Counter.EMPTY; 
	}
	
	public Board newBoard() {
		return new Board(dimX, dimY);
	}
 
	public boolean isDraw(Counter lastMove, Board b) {
		return false;
	}
 
	public Counter winningMove(Move lastMove, Board b) {
		boolean won = false;
		winner = Counter.EMPTY; // No ha ganado nadie
		 
		// LastMove?? Puede ser para actualizar el tablero con ese movimiento?
		//Ni idea tio
		
		won = checkHorizontal(b);
		
		if (!won) {
			won = checkVertical(b);
			if (!won) {
				won = checkDiagonal1(b);
				if (!won) {
					won = checkDiagonal2(b);
				}
			}
		} 
			
		return this.winner; // El color del ganador lo actualizan las funciones: checkhorizontal, etc... Actualizan el atributo winner
							// Devuelve Empty si no ha ganado nadie
	}
	
	public Counter nextTurn(Counter lastMove, Board b) {
		Counter nextTurn = Counter.EMPTY;
		
		if (lastMove == Counter.WHITE) {
			nextTurn = Counter.BLACK;
		}
		else if (lastMove == Counter.BLACK) {
			nextTurn = Counter.WHITE;
		}

		return nextTurn;
	}
	
	public Counter initialPlayer() {
		return Counter.WHITE;
	}

	/**************************************/
	/************ EXTRA METHODS ***********/
	/**************************************/
	
	public boolean checkHorizontal(Board board) {
		boolean isWinner = false;
		int tilesCounter, y, x;
		Counter counter, nextCounter;
		int whiteCounter= 0;
		int blackCounter = 0;
		
		y = dimY; // Starts at bottom
		
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
			winner = Counter.EMPTY;
		}
		else if ((blackCounter > 0) && (whiteCounter == 0)){
			isWinner = true;
			winner = Counter.BLACK;
		}
		else if ((blackCounter == 0) && (whiteCounter > 0)){
			isWinner = true;
			winner = Counter.WHITE;
		}
		
		return isWinner;
	}
	
	public boolean checkVertical(Board board) {
		boolean isWinner = false;
		int tilesCounter, y, x;
		Counter counter, nextCounter; 
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
			winner = Counter.EMPTY;
		}
		else if ((blackCounter > 0) && (whiteCounter == 0)){
			isWinner = true;
			winner = Counter.BLACK;
		}
		else if ((blackCounter == 0) && (whiteCounter > 0)){
			isWinner = true;
			winner = Counter.WHITE;
		}
		
		return isWinner;
	}
	
	public boolean checkDiagonal1(Board board) {
		boolean isWinner = false;
		int y, x, tilesCounter, aux_Y, aux_X, numIterations;
		Counter color, nextColor; 
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
			winner = Counter.EMPTY;
		}
		else if ((blackCounter > 0) && (whiteCounter == 0)){
			isWinner = true;
			winner = Counter.BLACK;
		}
		else if ((blackCounter == 0) && (whiteCounter > 0)){
			isWinner = true;
			winner = Counter.WHITE;
		}
		
		return isWinner;
	}
	
	public boolean checkDiagonal2(Board board) {
		boolean isWinner = false;
		int y, x, tilesCounter, aux_X, aux_Y, numIterations;
		Counter color, nextColor;
		int whiteCounter= 0;
		int blackCounter = 0;
		
		y = 1; // Always start in the firt row
		x = dimX; // Always start in the last column
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
			winner = Counter.EMPTY;
		}
		else if ((blackCounter > 0) && (whiteCounter == 0)){
			isWinner = true;
			winner = Counter.BLACK;
		}
		else if ((blackCounter == 0) && (whiteCounter > 0)){
			isWinner = true;
			winner = Counter.WHITE;
		}
		
		return isWinner;
	}

}
