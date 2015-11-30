package test;
import java.util.ArrayList;

import domain.Student;
import domain.System;
import junit.framework.TestCase;

public class TestStudent extends TestCase {

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
	 * This method would run the test of the method logInStudent
	 * @throws Exception throws an exception
	 */
	public void testLogInStudent() throws Exception{
		assertTrue(Student.logInStudent() != null);
	}
	/**
	 * This method would run the test of the method submitNewAssignment
	 * @throws Exception throws an exception
	 */
    public void testSubmitNewAssignment() throws Exception{
    	a1.logInStudent();
    	a1.addAssignment("TestAssignment1");
    	a1.selectOnlineWork(0);
    	a1.addAssignQuestion("Question1", 50);
    	ArrayList<String> answer = new ArrayList<String>();
    	answer.add("asd");
    	a1.recordStudentAssignmentAnswer(answer);
    	assertTrue(a1.getNumberOfAnswer(0) == 1);
    }
	/**
	 * This method would run the test of the method submitNewQuiz
	 * @throws Exception throws an exception
	 */
    public void testSubmitNewQuiz() throws Exception{
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
	/**
	 * This method would run the test of the method getNumberOfAnswer
	 * @throws Exception throws an exception
	 */
    public void testGetNumberOfAnswer() throws Exception{
    	a1.logInStudent();
    	a1.addAssignment("TestAssignment1");
    	a1.selectOnlineWork(0);
    	a1.addAssignQuestion("Question1", 50);
    	ArrayList<String> answer = new ArrayList<String>();
    	answer.add("asd");
    	a1.recordStudentAssignmentAnswer(answer);
    	assertTrue(a1.getNumberOfAnswer(0) == 1);
    }
	/**
	 * This method would run the test of the method getStudentMark
	 * @throws Exception throws an exception
	 */
    public void testGetStudentMark() throws Exception{
    	a1.logInStudent();
    	a1.addQuiz("TestQuiz1");
    	a1.selectOnlineWork(0);
    	a1.addQuizQuestion("Question1", 25, "A", "B", "C", "D", 1);
    	a1.addQuizQuestion("Question1", 25, "A", "B", "C", "D", 1);
		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(1);
		answer.add(0);
		a1.recordStudentQuizResult(answer);
    	assertTrue(a1.getStudentMark(0) == 25);
    }

}
