package tp.pr2.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tp.pr2.logic.test.BoardTest;
import tp.pr2.logic.test.Connect4FourInARowTest;
import tp.pr2.logic.test.Connect4MoveTest;
import tp.pr2.logic.test.Connect4GameTest;
import tp.pr2.logic.test.Connect4RulesTest;
import tp.pr2.logic.test.Connect4UndoTest;


@RunWith(Suite.class) 
@Suite.SuiteClasses( { 
	BoardTest.class,
	Connect4MoveTest.class,
	Connect4RulesTest.class,
	Connect4GameTest.class,
	Connect4UndoTest.class,
	Connect4FourInARowTest.class,
	})
public class Connect4Tests {

}
