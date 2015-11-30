package test;
import java.util.ArrayList;

import domain.Assignment;
import domain.System;
import junit.framework.TestCase;

public class TestOnlineWork extends TestCase {
	private Assignment a1;
	private System a2;
	/**
	 * This method does the initial setting up for the testing
	 * @throws Exception throws an exception
	 */
	public void setUp() throws Exception {
		super.setUp();
		a1 = new Assignment("1234");
		a2 = new System();
	}
	/**
	 * This method would run the test of the method getName
	 * @throws Exception throws an exception
	 */
    public void testGetName() throws Exception{
    	a2.addAssignment("TestAssignment1");
    	a2.selectOnlineWork(0);
    	a2.addAssignQuestion("Question1", 25);
    	a2.addAssignQuestion("Question2", 25);
    	assertTrue(a2.getAssignmentTotalMark() == 50);
    }
	/**
	 * This method would run the test of the method increaseTotalMark
	 * @throws Exception throws an exception
	 */
    public void testIncreaseTotalMark() throws Exception{
    	a2.addAssignment("TestAssignment1");
    	a2.selectOnlineWork(0);
    	a2.addAssignQuestion("Question1", 50);
    	a2.logInStudent();
    	ArrayList<String> answer = new ArrayList<String>();
    	answer.add("asd");
    	a2.recordStudentAssignmentAnswer(answer);
    	assertTrue(a2.getNumberOfAnswer(0) == 1);
    }
	/**
	 * This method would run the test of the method getTotalMark
	 * @throws Exception throws an exception
	 */
	public void testGetTotalMark() throws Exception {
		a1.addQuestion("Question1", 25);
		a1.addQuestion("Question2", 25);
		a1.addQuestion("Question3", 50);
		assertTrue(a1.getTotalMark() == 100);
    }

}
