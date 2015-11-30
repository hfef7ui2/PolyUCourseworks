package test;

import domain.System;

import junit.framework.TestCase;

public class TestAssignmentQuestion extends TestCase {
	private System a1;
	/**
	 * This method do the initial setting up for the testing
	 * @throws Exception throws an exception
	 */
	protected void setUp() throws Exception {
		super.setUp();
		a1 = new System();
	}
	/**
	 * This method would run the test of the method getContent
	 * @throws Exception throws an exception
	 */
    public void testGetContent() throws Exception{
    	a1.addAssignment("TestAssignment1");
    	a1.selectOnlineWork(0);
    	a1.addAssignQuestion("Question1", 25);
    	a1.addAssignQuestion("Question2", 25);
    	assertTrue(a1.getAssignmentContent(0).size() == 2);
    }
    }
