package test;

import java.util.ArrayList;

import domain.System;
import junit.framework.TestCase;

public class TestStudentGrade extends TestCase {
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
	 * This method would run the test of the method getStudentMark
	 * @throws Exception throws an exception
	 */
    public void testGetStudentMark() throws Exception{
    	a1.addQuiz("TestQuiz1");
    	a1.selectOnlineWork(0);
    	a1.addQuizQuestion("Question1", 25, "A", "B", "C", "D", 1);
    	a1.addQuizQuestion("Question1", 25, "A", "B", "C", "D", 1);
    	a1.logInStudent();
		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(1);
		answer.add(0);
    	a1.recordStudentQuizResult(answer);
    	assertTrue(a1.getStudentMark(0) == 25);
    }

}
