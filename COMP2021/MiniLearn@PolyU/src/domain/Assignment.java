package domain;

import java.util.ArrayList;

public class Assignment extends OnlineWork {
	
	private ArrayList<AssignmentQuestion> questionList;
	
	/**
	 * This method instantiates a Assignment object
	 * @param s a String
	 */
	public Assignment(String s) {
		super(s);
		questionList = new ArrayList<AssignmentQuestion>();
	}
	/**
	 * This method adds a new assignment question object into certain assignment object
	 * @param content a String
	 * @param mark an integer
	 */
	public void addQuestion(String content, int mark) {
		questionList.add(new AssignmentQuestion(content, mark));
		increaseTotalMark(mark);
	}
	/**
	 * This method returns the content of a assignment stored in ArrayList
	 * @return an ArrayList
	 */
	public ArrayList<ArrayList> getContent(){
		ArrayList<ArrayList> content = new ArrayList<ArrayList>();
		for(int i = 0; i < questionList.size(); i++){
			content.add(questionList.get(i).getContent());
		}
		return content;
	}
}
