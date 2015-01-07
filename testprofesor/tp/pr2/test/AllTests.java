package tp.pr2.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import tp.pr2.control.test.ControllerTest;

@RunWith(Suite.class) 
@Suite.SuiteClasses( { 
	Connect4Tests.class,
	ComplicaTests.class,
	ControllerTest.class	
	})
public class AllTests {

}
