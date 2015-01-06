package tp.pr2.logic;
 
import tp.pr2.Resources.Resources;
 

public class Connect4Rules implements GameRules {
	private int dimX = Resources.DIMX_CONNECT4;
	private int dimY = Resources.DIMY_CONNECT4;
	private Counter winner;
	
	public Connect4Rules() {  
		winner = Counter.EMPTY; 
	}
	
	public Board newBoard() {
		return new Board(dimX, dimY);	// Return a new board with connect4 dimensions
	}
	
	public boolean isDraw(Counter lastMove, Board b) {
		boolean isDraw = true, won;
		
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
		
		// Si ha ganado alguien, en conecta no hay empate.
		// Si no ha ganado nadie, mira si el tablero está lleno. Si lo está, hay empate.
		
		if (won) {
			isDraw = false; // Un jugador ha ganado, por lo que no hay tablas
		}
		else {
			won = b.isFull(); // comprueba si el tablero está lleno (imagino que en complica habrá que cambiarlo.)
			if (won) {
				isDraw = true; // empate
			}
		}
		
		return isDraw;
	}
	 	
	public Counter winningMove(Move lastMove, Board b) {	// Checks whether or not, with the current board, one of the players has won and, if so, returns the colour of the winner
		boolean won = false;
		winner = Counter.EMPTY; // No ha ganado nadie
		 
		// LastMove?? Puede ser para actualizar el tablero con ese movimiento?
		
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
	
	public Counter initialPlayer() {
		return Counter.WHITE;
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
	
	/**************************************/
	/************ EXTRA METHODS ***********/
	/**************************************/

	public boolean checkHorizontal(Board board) {
		boolean isWinner = false;
		int tilesCounter, y, x;
		Counter counter, nextCounter;
		
		y = dimY; // Starts at bottom
		
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
						this.winner = counter; // no se si esta linea se deja como antes o simplemente lo actualizamos desde fuera
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

	public boolean checkVertical(Board board) {
		boolean isWinner = false;
		int tilesCounter, y, x;
		Counter counter, nextCounter;
 		
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
						this.winner = counter; // no se si esta linea se deja como antes o simplemente lo actualizamos desde fuera
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
	
	public boolean checkDiagonal1(Board board) {
		boolean isWinner = false;
		int y, x, tilesCounter, aux_Y, aux_X, numIterations;
		Counter color, nextColor;
		
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
						this.winner = color;
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
							this.winner = color;
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
	
	public boolean checkDiagonal2(Board board) {
		boolean isWinner = false;
		int y, x, tilesCounter, aux_X, aux_Y, numIterations;
		Counter color, nextColor;
		
		y = 1; // Always start in the firt row
		x = dimX; // Always start in the last column
		color = board.getPosition(x, y);
		numIterations = 1;
		// starting top right position
		// Checks until the first diagonal
		
		while ((x > 1) && !(isWinner)) {
			y = 1;
			aux_X = x;
			tilesCounter = 1;
			if (numIterations > dimX)
			{
				numIterations = dimX;
			}
			
			while ((y < numIterations) && !(isWinner)) { // o aqui
				color = board.getPosition(aux_X, y);
				nextColor = board.getPosition(aux_X + 1, y + 1);
				
				if ((color == nextColor) && (color != Counter.EMPTY)) {
					tilesCounter++;
					if (tilesCounter == Resources.TILES_TO_WIN) {
						isWinner = true;
//						finished = true;
						this.winner = color;
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
			y = dimY; // Always start in the firt row
			color = board.getPosition(x, y);
			
			while ((y >= 1) && !(isWinner)) {
				x = 1;
				aux_Y = y;
				tilesCounter = 1;
				numIterations = dimY - y + 1;//antes era width y sin el +1
				if (numIterations > dimY)
				{
					numIterations = dimY;
				}
				
				while ((x < numIterations) && !(isWinner)) {//<=
					color = board.getPosition(x, aux_Y);
					nextColor = board.getPosition(x + 1, aux_Y + 1);
					
					if ((color == nextColor) && (color != Counter.EMPTY)) {
						tilesCounter++;
						if (tilesCounter == Resources.TILES_TO_WIN) {
							isWinner = true;
//							finished = true;
							this.winner = color;
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
	
}


