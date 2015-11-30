package domain;

import java.util.ArrayList;

public class QuizQuestion {

	private String content;
	private String[] choices;
	private int correctAnswer;
	private int mark;
	
	/**
	 * This method instantiates a new QuizQuestion object
	 * @param s a String
	 * @param ma an integer
	 * @param c1 a String
	 * @param c2 a String
	 * @param c3 a String
	 * @param c4 a String
	 * @param ans an integer
	 */
	public QuizQuestion(String s, int ma, String c1, String c2, String c3, String c4, int ans) {
		content = s;
		mark = ma;
		choices = new String[4];
		choices[0] = c1;
		choices[1] = c2;
		choices[2] = c3;
		choices[3] = c4;
		correctAnswer = ans;
	}

	/**
	 * This method returns the student's mark of current question
	 * @param num an integer
	 * @return an integer
	 */
	public int getMark(int num) {
		return correctAnswer == num? mark :0;
	}
	
	/**
	 * This method returns the content of current QuizQuestion stored in ArrayList
	 * @return an ArrayList
	 */
	public ArrayList getContent(){
		ArrayList result = new ArrayList();
		result.add(content);
		result.add(mark);
		result.add(choices[0]);
		result.add(choices[1]);
		result.add(choices[2]);
		result.add(choices[3]);
		result.add(correctAnswer);
		return result;
	}

}
