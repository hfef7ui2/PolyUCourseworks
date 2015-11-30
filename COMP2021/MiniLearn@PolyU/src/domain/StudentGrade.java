package domain;

public class StudentGrade {

	private String name;
	private int studentMark;

	/**
	 * This method instantiates a new StudentGrade Object
	 * @param s an String
	 * @param num an integer
	 */
	public StudentGrade(String s, int num) {
		name = s;
		studentMark = num;
	}
	
	/**
	 * This method returns the student's grade
	 * @return an integer
	 */
	public int getStudentMark(){
		return studentMark;
	}

}
