package tp.pr2.logic.test;

import tp.pr2.logic.Counter;
import tp.pr2.logic.Move;
import tp.pr2.logic.ComplicaMove;
import tp.pr2.logic.ComplicaRules;
import tp.pr2.logic.GameRules;

public class ComplicaUndoTest extends Connect4UndoTest {
	
	@Override
	protected Move newMove(int column, Counter colour) {
		return new ComplicaMove(column, colour);
	}
	
	@Override
	protected GameRules newRules() {
		return new ComplicaRules();
	}
}
