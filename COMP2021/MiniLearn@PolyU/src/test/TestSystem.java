package test;

import domain.System;

import java.util.ArrayList;


import junit.framework.TestCase;

public class TestSystem extends TestCase {
	private System a1;
	/**
	 * This method does the initial setting up for the testing
	 * @throws Exception throws an exception
	 */
	public void setUp() throws Exception{
		super.setUp();
        a1 = new System();	
    }
	/**
	 * This method would run the test of the method logInTeacher
	 * @throws Exception throws an exception
	 */
    public void testLogInTeacher() throws Exception{
        a1.logInTeacher();
    	assertTrue(a1.getTeacher() != null);
    }
	/**
	 * This method would run the test of the method logInStudent
	 * @throws Exception throws an exception
	 */
    public void testLogInStudent() throws Exception{
        a1.logInStudent();
    	assertTrue(a1.getStudent() != null);
    }
	/**
	 * This method would run the test of the method addAssignment
	 * @throws Exception throws an exception
	 */
    public void testAddAssignment() throws Exception{
    	a1.addAssignment("TestAssignment1");
    	a1.addAssignment("TestAssignment2");
    	assertTrue(a1.getNumberOfAssignment() == 2);
    }
	/**
	 * This method would run the test of the method addQuiz
	 * @throws Exception throws an exception
	 */
    public void testAddQuiz() throws Exception{
    	a1.addQuiz("TestQuiz1");
    	a1.addQuiz("TestQuiz2");
    	assertTrue(a1.getNumberOfQuiz() == 2);
    }
	/**
	 * This method would run the test of the method addAssignmentQuestion
	 * @throws Exception throws an exception
	 */
    public void testAddAssignmentQuestion() throws Exception{
    	a1.addAssignment("TestAssignment1");
    	a1.selectOnlineWork(0);
    	a1.addAssignQuestion("Question1", 25);
    	a1.addAssignQuestion("Question2", 25);
    	assertTrue(a1.getAssignmentTotalMark() == 50);
    }
	/**
	 * This method would run the test of the method addQuizQuestion
	 * @throws Exception throws an exception
	 */
    public void testAddQuizQuestion() throws Exception{
    	a1.addQuiz("TestQuiz1");
    	a1.selectOnlineWork(0);
    	a1.addQuizQuestion("Question1", 25, "A", "B", "C", "D", 1);
    	a1.addQuizQuestion("Question1", 25, "A", "B", "C", "D", 1);
    	assertTrue(a1.getQuizTotalMark() == 50);
    }
	/**
	 * This method would run the test of the method selectOlineWork
	 * @throws Exception throws an exception
	 */
    public void testSelectOnlineWork() throws Exception{
    	a1.addAssignment("TestAssignment1");
    	a1.addAssignment("TestAssignment2");
    	a1.selectOnlineWork(1);
    	a1.addAssignQuestion("Question1", 50);
    	a1.addAssignQuestion("Question2", 50);
    	a1.selectOnlineWork(0);
    	assertTrue(a1.getAssignmentTotalMark() == 0);
    }
	/**
	 * This method would run the test of the method recordStudentAssignmentAnswer
	 * @throws Exception throws an exception
	 */
    public void testRecordStudentAssignmentAnswer() throws Exception{
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
	 * This method would run the test of the method recordStudentQuizResult
	 * @throws Exception throws an exception
	 */
    public void testRecordStudentQuizResult() throws Exception{
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
	/**
	 * This method would run the test of the method getNumberOfAssignment
	 * @throws Exception throws an exception
	 */
    public  void testGetNumberOfAssignment() throws Exception{
    	a1.addAssignment("TestAssignment1");
    	a1.addAssignment("TestAssignment2");
    	assertTrue(a1.getNumberOfAssignment() == 2);
	}
	/**
	 * This method would run the test of the method getNumberOfQuiz
	 * @throws Exception throws an exception
	 */
    public void testGetNumberOfQuiz() throws Exception{
    	a1.addQuiz("TestQuiz1");
    	a1.addQuiz("TestQuiz2");
    	assertTrue(a1.getNumberOfQuiz() == 2);
    }
	/**
	 * This method would run the test of the method getTeacher
	 * @throws Exception throws an exception
	 */
    public void testGetTeacher() throws Exception{
    	a1.logInTeacher();
    	assertTrue(a1.getTeacher() != null);
    }
	/**
	 * This method would run the test of the method getStudent
	 * @throws Exception throws an exception
	 */
    public void testGetStudent() throws Exception{
    	a1.logInStudent();
    	assertTrue(a1.getStudent() != null);
    }
	/**
	 * This method would run the test of the method getAssignmentTotalMark
	 * @throws Exception throws an exception
	 */
    public void testGetAssignmentTotalMark() throws Exception{
    	a1.addAssignment("TestAssignment1");
    	a1.selectOnlineWork(0);
    	a1.addAssignQuestion("Question1", 25);
    	a1.addAssignQuestion("Question2", 25);
    	assertTrue(a1.getAssignmentTotalMark() == 50);
    }
	/**
	 * This method would run the test of the method getQuizTotalMark
	 * @throws Exception throws an exception
	 */
    public void testGetQuizTotalMark() throws Exception{
    	a1.addQuiz("TestQuiz1");
    	a1.selectOnlineWork(0);
    	a1.addQuizQuestion("Question1", 25, "A", "B", "C", "D", 1);
    	a1.addQuizQuestion("Question1", 25, "A", "B", "C", "D", 1);
    	assertTrue(a1.getQuizTotalMark() == 50);
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
	/**
	 * This method would run the test of the method getAssignmentNames
	 * @throws Exception throws an exception
	 */
    public void testGetAssignmentNames() throws Exception{
    	a1.addAssignment("TestAssignment1");
    	a1.addAssignment("TestAssignment2");
    	ArrayList<String> temp = a1.getAssignmentNames(); 
    	assertTrue(temp.size() == 2);
    }
	/**
	 * This method would run the test of the method getQuizNames
	 * @throws Exception throws an exception
	 */
    public void testGetQuizNames() throws Exception{
    	a1.addQuiz("TestQuiz1");
    	a1.addQuiz("TestQuiz2");
    	ArrayList<String> temp = a1.getQuizNames();
    	assertTrue(temp.size() == 2);
    }
	/**
	 * This method would run the test of the method GetAssignmentContent
	 * @throws Exception throws an exception
	 */
    public void testGetAssignmentContent() throws Exception{
    	a1.addAssignment("TestAssignment1");
    	a1.selectOnlineWork(0);
    	a1.addAssignQuestion("Question1", 25);
    	a1.addAssignQuestion("Question2", 25);
    	assertTrue(a1.getAssignmentContent(0).size() == 2);
    }
	/**
	 * This method would run the test of the method getQuizContent
	 * @throws Exception throws an exception
	 */
    public void testGetQuizContent() throws Exception{
    	a1.addQuiz("TestQuiz1");
    	a1.selectOnlineWork(0);
    	a1.addQuizQuestion("Question1", 25, "A", "B", "C", "D", 1);
    	a1.addQuizQuestion("Question1", 25, "A", "B", "C", "D", 1);
    	assertTrue(a1.getQuizContent(0).size() == 2);
    }
}
