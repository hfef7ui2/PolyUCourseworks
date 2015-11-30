package test;

import java.util.ArrayList;
import domain.System;
import junit.framework.TestCase;

public class TestStudentPaperSubmitted extends TestCase {
	private System a1;
	
	/**
	 * This method does the initial setting up for the testing
	 * @throws Exception throws an exception
	 */
	public void setUp() throws Exception {
		super.setUp();
		a1 = new System();
	}

	/**
	 * This method would run the test of the method getNumberOfAnswer
	 * @throws Exception throws an exception
	 */
    public void testGetNumberOfAnswer() throws Exception{
    	a1.addAssignment("TestAssignment1");
    	a1.selectOnlineWork(0);
    	a1.addAssignQuestion("Question1", 50);
    	a1.logInStudent();
    	ArrayList<String> answer = new ArrayList<String>();
    	answer.add("asd");
    	a1.recordStudentAssignmentAnswer(answer);
    	assertTrue(a1.getNumberOfAnswer(0) == 1);
    }
}
