package domain;

import java.util.ArrayList;

public class AssignmentQuestion {

	private String content;
	
	private int mark;
	/**
	 * This method instantiates a new AssignmentQuesion object
	 * @param s a String
	 * @param num an integer
	 */
	public AssignmentQuestion(String s, int num) {
		content = s;
		mark = num;
	}
	/**
	 * This method returns the content of the AssignmentQuestion stored in ArrayList
	 * @return a ArrayList
	 */
	public ArrayList getContent(){
		ArrayList result = new ArrayList();
		result.add(content);
		result.add(mark);
		return result;
	}
}
