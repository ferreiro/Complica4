package tp.pr2.logic;
  
public interface GameRules {
 
	//iniciaTablero() 
	public void initialBoard();
	
	//hayGanador(Movimiento ultimoMovimiento, Tablero t)
	public boolean isWinner(Move lastMovement, Board board);
	
	//jugadorInicial()
	public Counter initialPLayer();
	
	//siguienteTurno(Ficha ultimoEnPoner, Tablero t) devuelve el color del siguiente turno
	public Counter nextTurn(Counter lastPLayerMoving, Board board);
	
	//tablas(Ficha ultimoEnPoner, Tablero t) indica si ha acabado en tablas
	public boolean tie(Counter lastPLayerMoving, Board board);
	
}
