package domain;

import java.util.ArrayList;

public class Student {
	
	private ArrayList<StudentPaperSubmitted> studentPaperList;
	private ArrayList<StudentGrade> studentGradeList;
	private static Student currentStudent;

	
	private Student() {
		studentPaperList = new ArrayList<StudentPaperSubmitted>();
		studentGradeList = new ArrayList<StudentGrade>();
	}
	
	/**
	 * This method uses singleton pattern to make sure there is up to one Student Object
	 * @return a Student
	 */
	public static Student logInStudent(){
		if(currentStudent == null){
			currentStudent = new Student();
		}
		return currentStudent;
	}
	
	/**
	 * This method records student's assignment answer in database
	 * @param s a String
	 * @param al a ArrayList
	 */
	public void submitNewAssignment(String s, ArrayList<String> al) {
		studentPaperList.add (new StudentPaperSubmitted(s, al));
	}

	/**
	 * This method records student's quiz mark in database
	 * @param s a String
	 * @param num an integer
	 */
	public void submitNewQuiz(String s, int num) {
		studentGradeList.add (new StudentGrade(s,num));
	}
	
	/**
	 * This method returns the questions a student finished in certain assignment
	 * @param index an integer
	 * @return an integer
	 */
	public int getNumberOfAnswer(int index){
		return studentPaperList.get(index).getNumberOfAnswer();
	}
	
	/**
	 * This method returns the student's mark of certain quiz
	 * @param index an integer
	 * @return an integer
	 */
	public int getStudentMark(int index){
		return studentGradeList.get(index).getStudentMark();
	}
	


}
