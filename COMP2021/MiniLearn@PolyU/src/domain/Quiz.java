package domain;

import java.util.ArrayList;

public class Quiz extends OnlineWork {
	private ArrayList<QuizQuestion> questionList;

	/**
	 * This method instantiates a new Quiz object
	 * @param s a String
	 */
	public Quiz(String s) {
		super(s);
		questionList = new ArrayList<QuizQuestion>();
	}

	/**
	 * This method adds a new QuizQuestion into current Quiz object
	 * @param content a String
	 * @param mark a int
	 * @param c1 a String
	 * @param c2 a String
	 * @param c3 a String
	 * @param c4 a String
	 * @param ans a in
	 */
	public void addQuestion(String content, int mark, String c1, String c2, String c3, String c4, int ans) {
		questionList.add(new QuizQuestion(content, mark, c1, c2, c3, c4, ans));
		increaseTotalMark(mark);
	}

	/**
	 * This method calculates the mark of student's answer of current quiz
	 * @param answer a ArrayList
	 * @return an integer
	 */
	public int examStudentAnswer(ArrayList<Integer> answer) {
		int studentMark = 0;
		for(int i = 0; i < questionList.size() && i < answer.size(); i++){
			studentMark += questionList.get(i).getMark(answer.get(i));
		}
		return studentMark;
	}
	
	/**
	 * This method returns the content of current quiz stored in ArrayList
	 * @return a ArrayList
	 */
	public ArrayList<ArrayList> getContent(){
		ArrayList<ArrayList> content = new ArrayList<ArrayList>();
		for(int i = 0; i < questionList.size(); i++){
			content.add(questionList.get(i).getContent());
		}
		return content;
	}


}
