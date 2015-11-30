package domain;

public class Teacher {

	private static Teacher currentTeacher;
	private Teacher() {
	}
	
	/**
	 * This method instantiates a new Teacher object
	 * @return a Teacher
	 */
	public static Teacher logInTeacher(){
		if(currentTeacher == null){
			currentTeacher = new Teacher();
		}
		return currentTeacher;
	}
}
