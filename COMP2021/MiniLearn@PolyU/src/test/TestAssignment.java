package test;

import domain.Assignment;
import domain.System;

import junit.framework.TestCase;

public class TestAssignment extends TestCase {
    
	private Assignment a1;
	private System a2;
	
	/**
	 * This method does the initial setting up for the testing
	 * @throws Exception throws an exception
	 */
    public void setUp() throws Exception{
		super.setUp();
        a1 = new Assignment("Test");
        a2 = new System();
    }
	/**
	 * This method would run the test of the method  addQuestion
	 * @throws Exception throws an exception
	 */
	public void testAddQuestion() throws Exception {
		a1.addQuestion("Question1", 25);
		a1.addQuestion("Question2", 25);
		a1.addQuestion("Question3", 50);
		assertTrue(a1.getTotalMark() == 100);
    }
	/**
	 * This method would run the test of the method getContent
	 * @throws Exception throws an exception
	 */
    public void testGetContent() throws Exception{
    	a2.addAssignment("TestAssignment1");
    	a2.selectOnlineWork(0);
    	a2.addAssignQuestion("Question1", 25);
    	a2.addAssignQuestion("Question2", 25);
    	assertTrue(a2.getAssignmentContent(0).size() == 2);
    }
}
