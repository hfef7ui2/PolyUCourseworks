package domain;

import java.util.ArrayList;

public class System {

	private int selectedIndex;
	
	
	private Teacher currentTeacher;

	private Student currentStudent;

	private ArrayList<Assignment> assignmentList;

	private ArrayList<Quiz> quizList;
	
	/**
	 * This method instantiates a new System object, which is the controlling object
	 */
	public System() {
		selectedIndex = 0;
		currentTeacher = null;
		currentStudent = null;
		assignmentList = new ArrayList<Assignment>();
		quizList = new ArrayList<Quiz>();
	}

	/**
	 * This method creates one and only one Teacher object by Singleton pattern
	 */
	public void logInTeacher() {
		currentTeacher = Teacher.logInTeacher();
	}
	
	/**
	 * This method creates one and only one Student object by Singleton pattern
	 */
	public void logInStudent() {
		currentStudent = Student.logInStudent();
	}
	
	/**
	 * This method adds a new assignment into database
	 * @param name a String
	 */
	public void addAssignment(String name) {
		assignmentList.add(new Assignment(name));

	}
	/**
	 * This method adds a new quiz into database
	 * @param name a String
	 */
	public void addQuiz(String name) {
		quizList.add(new Quiz(name));

	}
	/**
	 * This method adds a new assignment question into current assignment
	 * @param content a String
	 * @param mark an integer
	 */
	public void addAssignQuestion(String content, int mark) {
		assignmentList.get(assignmentList.size() - 1).addQuestion(content, mark);;
	}
	
	/**
	 * This method adds a new quiz question into current quiz
	 * @param content a String
	 * @param mark an integer
	 * @param c1 a String
	 * @param c2 a String
	 * @param c3 a String
	 * @param c4 a String
	 * @param ans an integer
	 */
	public void addQuizQuestion(String content, int mark, String c1, String c2, String c3, String c4, int ans) {
		quizList.get(quizList.size() - 1).addQuestion(content, mark, c1, c2, c3, c4, ans);
	}
 
	/**
	 * This method chooses certain online work as current work
	 * @param index an integer
	 */
	public void selectOnlineWork(int index) {
		selectedIndex = index;
	}

	/**
	 * This method records student's answer of current assignment
	 * @param answer an ArrayList
	 */
	public void recordStudentAssignmentAnswer(ArrayList<String> answer) {
		String assignmentName = assignmentList.get(selectedIndex).getName();
		currentStudent.submitNewAssignment(assignmentName, answer);
	}

	/**
	 * This method 
	 * @param answer records student's mark of current quiz
	 * @return an integer
	 */
	public int recordStudentQuizResult(ArrayList<Integer> answer) {
		String quizName = quizList.get(selectedIndex).getName();
		int studentMark = quizList.get(selectedIndex).examStudentAnswer(answer);
		currentStudent.submitNewQuiz(quizName, studentMark);
		return studentMark;
	}

	/**
	 * This method returns the number of assignment in databate
	 * @return an integer
	 */
	public int getNumberOfAssignment(){
		return assignmentList.size();
	}
	
	/**
	 * This method returns the number of quiz in databate
	 * @return an integer
	 */
	public int getNumberOfQuiz(){
		return quizList.size();
	}
	
	/**
	 * This method returns the reference of teacher
	 * @return a Teacher
	 */
	public Teacher getTeacher(){
		return currentTeacher;
	}
	
	/**
	 * This method returns the reference of student
	 * @return a Teacher
	 */
	public Student getStudent(){
		return currentStudent;
	}
	
	/**
	 * This method returns the total mark of current assignment
	 * @return an integer
	 */
	public int getAssignmentTotalMark(){
		return assignmentList.get(selectedIndex).getTotalMark();
	}
	
	/**
	 * This method returns the total mark of current quiz
	 * @return an integer
	 */
	public int getQuizTotalMark(){
		return quizList.get(selectedIndex).getTotalMark();
	}
	
	/**
	 * This method returns the number of questions a student finished in certain assignment
	 * @param index an integer
	 * @return an integer
	 */
	public int getNumberOfAnswer(int index){
		return currentStudent.getNumberOfAnswer(index);
	}
	
	/**
	 * This method returns the mark of certain quiz a student finished
	 * @param index an integer
	 * @return an integer
	 */
	public int getStudentMark(int index){
		return currentStudent.getStudentMark(index);
	}
	
	/*
	 * This method returns all the names of assignments stored in ArrayList
	 */
	public ArrayList<String> getAssignmentNames(){
		ArrayList<String> names = new ArrayList<String>();
		for(int i = 0; i < assignmentList.size(); i++){
			names.add(assignmentList.get(i).getName());
		}
		return names;
	}
	
	/**
	 * This method return all the names of quizzes stored in ArrayList
	 * @return a ArrayList
	 */
	public ArrayList<String> getQuizNames(){
		ArrayList<String> names = new ArrayList<String>();
		for(int i = 0; i < quizList.size(); i++){
			names.add(quizList.get(i).getName());
		}
		return names;
	}
	
	/**
	 * This method return the content of current assignment stored in ArrayList
	 * @param index an integer
	 * @return an ArrayList
	 */
	public ArrayList<ArrayList> getAssignmentContent(int index){
		return assignmentList.get(index).getContent();
	}
	
	/**
	 * This method return the content of current quiz stored in ArrayList
	 * @param index an integer
	 * @return an ArrayList
	 */
	public ArrayList<ArrayList> getQuizContent(int index){
		return quizList.get(index).getContent();
	}

	
}
