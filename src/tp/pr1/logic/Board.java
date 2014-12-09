package tp.pr1.logic;

public class Board {
	private Counter [][] board;
	private int width;
	private int height;
	
	public Board(int tx, int ty) {
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

	
	public Counter [][] getBoard() {
		return board;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height; 
	}
	
	public Counter getPosition(int x, int y) {
		Counter color = Counter.EMPTY;
		if ((x >= 1 && x <= width) || (y >= 1 && y <= height)) {
			color = board[y - 1][x - 1];
		}
		return color;		
	}
	
	public void setPosition(int x, int y, Counter colour) { 
		// if ((x >= 1 && x <= width) || (y >= 1 && y <= width)) {
			board[y - 1][x - 1] = colour;
		//}		
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
		
		for (int y = 1; y <= height; y++) 
		{
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


