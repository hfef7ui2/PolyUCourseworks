package test;

import java.util.ArrayList;

import domain.Quiz;
import domain.System;

import junit.framework.TestCase;


public class TestQuiz extends TestCase {

	private Quiz quiz;
	private System a1;
	/**
	 * This method does the initial setting up for the testing
	 * @throws Exception throws an exception 
	 */
	public void setUp() throws Exception {
		super.setUp();
		quiz = new Quiz("Quiz1");
		a1 = new System();
	}
	/**
	 * This method would run the test of the method addQuestion
	 * @throws Exception throws an exception
	 */
	public void testAddQuestion() throws Exception{
		quiz.addQuestion("asd", 25, "a", "b", "c", "d", 3);
		assertTrue(quiz.getTotalMark() == 25);
	}
	/**
	 * This method would run the test of the method examStudentAnswer
	 * @throws Exception throws an exception
	 */
	public void testExamStudentAnswer() throws Exception{
		quiz.addQuestion("asd", 25, "a", "b", "c", "d", 3);
		quiz.addQuestion("asd", 25, "a", "b", "c", "d", 2);
		ArrayList<Integer> answer = new ArrayList<Integer>();
		answer.add(3);
		answer.add(4);
		assertTrue(quiz.examStudentAnswer(answer) == 25);
	}
	/**
	 * This method would run the test of the method getContent
	 * @throws Exception throws an exception
	 */
    public void testGetContent() throws Exception{
    	a1.addQuiz("TestQuiz1");
    	a1.selectOnlineWork(0);
    	a1.addQuizQuestion("Question1", 25, "A", "B", "C", "D", 1);
    	a1.addQuizQuestion("Question1", 25, "A", "B", "C", "D", 1);
    	assertTrue(a1.getQuizContent(0).size() == 2);
    }

}
