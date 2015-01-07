package tp.pr2.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tp.pr2.logic.test.BoardTest;
import tp.pr2.logic.test.ComplicaFourInARowTest;
import tp.pr2.logic.test.ComplicaMoveTest;
import tp.pr2.logic.test.ComplicaGameTest;
import tp.pr2.logic.test.ComplicaRulesTest;
import tp.pr2.logic.test.ComplicaUndoTest;

@RunWith(Suite.class) 
@Suite.SuiteClasses( { 
	BoardTest.class,
	ComplicaMoveTest.class,
	ComplicaRulesTest.class,
	ComplicaGameTest.class,
	ComplicaUndoTest.class,
	ComplicaFourInARowTest.class,
	})
public class ComplicaTests {

}
