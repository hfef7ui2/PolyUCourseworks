package domain;

public abstract class OnlineWork {

	private String name;
	private int totalMark;
	/**
	 * This method is the constructor
	 * @param s a String
	 */
	public OnlineWork(String s) {
		name = s; 
	}
	
	/**
	 * This method gets the name of current object
	 * @return a String
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * This method returns the total mark of current OnlineWork
	 * @return a integer
	 */
	public int getTotalMark(){
		return totalMark;
	}
	
	/**
	 * This method increase the total mark of the current assignment
	 * @param num an integer
	 */
	public void increaseTotalMark(int num){
		totalMark += num;
	}
}
