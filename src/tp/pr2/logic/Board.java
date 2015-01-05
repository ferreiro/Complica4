package tp.pr2.logic;

import tp.pr2.logic.Counter;

public class Board {

	private int height;
	private int width;
	private Counter [][] board;
	private boolean full;
	
	public Board(int tx, int ty){
		width = tx; // Row
		height = ty;  // column
		if ((tx < 1) || (ty < 1))
		{
			width = 1;
			height = 1;
		} 
		
		board = new Counter[height][width];
		emptyCells();
	}
	
	public int getHeight(){
		return height;
	}

	public int getLength(){
		return width;
	}
	
	// Añado getWidth para no tener que tocar los algoritmos que comprueban las diagonales.
	// Como no sé si has usado getLength, pues he dejado los dos.
	
	public int getWidth(){
		return width;
	}

	public Counter getPosition(int tx, int ty){
		return Counter.EMPTY;
	}
	
	public void setPosition(int tx, int ty, Counter counter){
		board[ty - 1][tx - 1] = counter;
	}
	
	public boolean isFull () {
		return full;
	}
	
	public void setFull() {
		int y = 1, x = 1;
		boolean fullB = true;
		while((y <= height) && (fullB)) {
			while(x <= width && (fullB)) {
				if (getPosition(x, y) == Counter.EMPTY) {
					fullB = false;
				}
				x++;
			}
			y++;
		}
		this.full = fullB;
	}
	
	public void emptyCells() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				board[y][x] = Counter.EMPTY;
			}
		}
	}
	
	public void printBoard() {
		String line = "";
		
		for (int y = 1; y <= height; y++) {
			line = tabLines(y);	
			System.out.println(line);
		}	

		line = "";
		for (int i = 0; i <= width; i++) {
			if (i == 0) {
				line += "+";
			}
			else if ((i > 0) && ( i <= width))
			line += "-"; 
			
			if (i == width) {
				line += "+";
			}
		}
		System.out.println(line);
		line = "";
		
		for (int i = 0; i <= width + 1; i++) {
			if (i == 0) {
				line += " ";
			}
			else if ((i > 0) && ( i <= width))
			line += i; 
			
		}
		System.out.println(line);
		System.out.println("");
	}
		


	public String tabLines(int y)
	{
		String line = "";
		
		line += "|"; 
		
		for (int x = 1; x <= width; x++) 
		{	
			if (getPosition(x, y) == Counter.EMPTY) 
			{
				line += " ";
			}
			else if (getPosition(x, y) == Counter.BLACK)
			{
				line +=  "X";
			}
			else if (getPosition(x, y) == Counter.WHITE)
			{
				line += "O";
			}			 
		}
		line += "|";
		
		return line;
	}
	
	
	
}
