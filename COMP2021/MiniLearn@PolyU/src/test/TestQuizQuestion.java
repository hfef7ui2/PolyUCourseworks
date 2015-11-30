package test;

import domain.System;

import junit.framework.TestCase;

import domain.QuizQuestion;

public class TestQuizQuestion extends TestCase {

	private QuizQuestion testQuestion;
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
	 * This method would run the test of the method getMark
	 * @throws Exception throws an exception
	 */
	public void testGetMark() throws Exception{
		testQuestion = new QuizQuestion("asd", 25, "a", "b", "c", "d", 1);
		assertTrue(testQuestion.getMark(1) == 25);
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
