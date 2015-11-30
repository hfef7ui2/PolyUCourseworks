package domain;

import java.util.ArrayList;

public class StudentPaperSubmitted {

	private String name;
	private ArrayList<String> studentAnswer;

	/**This method instantiates a new StudentPaperSumitted object
	 * @param s a String
	 * @param al an ArrayList
	 */
	public StudentPaperSubmitted(String s, ArrayList<String> al) {
		name = s;
		studentAnswer = al;
	}
	/**
	 * This method returns the number of questions a student finished in current assignment
	 * @return an integer
	 */
	public int getNumberOfAnswer(){
		return studentAnswer.size();
	}

}
