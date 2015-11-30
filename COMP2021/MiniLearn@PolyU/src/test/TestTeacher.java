package test;

import domain.Teacher;
import junit.framework.TestCase;

public class TestTeacher extends TestCase {
	/**
	 * This method does the initial setting up for the testing
	 * @throws Exception throws an exception
	 */
	public void setUp() throws Exception {
		super.setUp();
	}
	/**
	 * This method would run the test of the method logInTeacher
	 * @throws Exception throws an exception
	 */
	public void testLogInTeacher() throws Exception{
		assertTrue(Teacher.logInTeacher() != null);
	}
}
