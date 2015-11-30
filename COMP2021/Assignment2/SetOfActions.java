import java.util.ArrayList;
import java.lang.Object;

public class SetOfActions {

    /**
     * When users click the Biographies button, the GUI will invoke this method to add a Biographies object into a list of Books.
     * @param st the list which contains all the added books
     * @param name the name of the Books the GUI gets from the name field
     * @param price the basic price of the Books the GUI gets from the price field
	 * @param quantity the quantity of the Books needed to be added in the list
     */
    public static void addBiographies(ArrayList<Books> st, String name, double price, int quantity) {
    	Biographies[] newBook = new Biographies[quantity];
    	for(int i = 0; i < quantity; i++){
    		newBook[i] = new Biographies(name, price);
    	}
    	for(int i = 0; i < quantity; i++){
        	st.add(newBook[i]);
    	}
    }

    /**
     * When users click the History button, the GUI will invoke this method to add a History object into a list of Books.
     * @param st
     * @param name
     * @param price
	 * @param quantity
     */
    public static void addHistory(ArrayList<Books> st, String name, double price, int quantity) {
    	History[] newBook = new History[quantity];
    	for(int i = 0; i < quantity; i++){
    		newBook[i] = new History(name, price);
    	}
    	for(int i = 0; i < quantity; i++){
        	st.add(newBook[i]);
    	}
    }

    /**
     * When users click the Travel button, the GUI will invoke this method to add a Travel object into a list of Books.
     * @param st
     * @param name
     * @param price
	 * @param quantity
     */
    public static void addTravel(ArrayList<Books> st, String name, double price, int quantity) {
    	Travel[] newBook = new Travel[quantity];
    	for(int i = 0; i < quantity; i++){
    		newBook[i] = new Travel(name, price);
    	}
    	for(int i = 0; i < quantity; i++){
        	st.add(newBook[i]);
    	}
    }
    

    /**
     * When users click the Journals button, the GUI will invoke this method to add an Journals object into a list of Books.
     * @param st
     * @param name
     * @param price
	 * @param quantity
     */
    public static void addJournals(ArrayList<Books> st, String name, double price, int quantity) {
    	Journals[] newBook = new Journals[quantity];
    	for(int i = 0; i < quantity; i++){
    		newBook[i] = new Journals(name, price);
    	}
    	for(int i = 0; i < quantity; i++){
        	st.add(newBook[i]);
    	}
    }

    /**
     * When users click the ScienceFiction button, the GUI will invoke this method to add an ScienceFiction object into a list of Books.
     * @param st
     * @param name
     * @param price
	 * @param quantity
     */
    public static void addScienceFiction(ArrayList<Books> st, String name, double price, int quantity) {
    	ScienceFiction[] newBook = new ScienceFiction[quantity];
    	for(int i = 0; i < quantity; i++){
    		newBook[i] = new ScienceFiction(name, price);
    	}
    	for(int i = 0; i < quantity; i++){
        	st.add(newBook[i]);
    	}
    }

    /**
     * When users click the Mystery button, the GUI will invoke this method to add a Mystery object into a list of Books.
     * @param st
     * @param name
     * @param price
	 * @param quantity
     */
    public static void addMystery(ArrayList<Books> st, String name, double price, int quantity) {
    	Mystery[] newBook = new Mystery[quantity];
    	for(int i = 0; i < quantity; i++){
    		newBook[i] = new Mystery(name, price);
    	}
    	for(int i = 0; i < quantity; i++){
        	st.add(newBook[i]);
    	}
    }

    /**
     * When users click the Horror button, the GUI will invoke this method to add an Horror object into a list of Books.
     * @param st
     * @param name
     * @param price
	 * @param quantity
     */
    public static void addHorror(ArrayList<Books> st, String name, double price, int quantity) {
    	Horror[] newBook = new Horror[quantity];
    	for(int i = 0; i < quantity; i++){
    		newBook[i] = new Horror(name, price);
    	}
    	for(int i = 0; i < quantity; i++){
        	st.add(newBook[i]);
    	}
    }

	 /**
     * When users click the Romance button, the GUI will invoke this method to add an Romance object into a list of Books.
     * @param st
     * @param name
     * @param price
	 * @param quantity
     */
    public static void addRomance(ArrayList<Books> st, String name, double price, int quantity) {
    	Romance[] newBook = new Romance[quantity];
    	for(int i = 0; i < quantity; i++){
    		newBook[i] = new Romance(name, price);
    	}
    	for(int i = 0; i < quantity; i++){
        	st.add(newBook[i]);
    	}
    }
	
    /**
     * The GUI frequently invokes this method to display the total price.
     * @param st the list of all added Books
     * @return the total value of all the Books in the list
     */
    public static double getTotalPrice(ArrayList<Books> st) {
    	if(st.isEmpty())
    		return -1;
    	
    	double totalPrice = 0.0;
    	int N = st.size();
    	for(int i = 0; i < N; i++){
    		totalPrice += st.get(i).getPrice();
    	}
    	
    	return totalPrice;
    }

    /**
     * When users click the Increase price button, the GUI will invoke this method to increase the prices of selected Book categories,
     * and the new total price will be displayed on the GUI
     * @param st the list of all added Books
	 * @param nfict the state of the checkbox of NonFiction, if the checkbox of NonFiction is checked, then nfict is true
	 * @param fict the state of the checkbox of Fiction, if the checkbox of Fiction is checked, then fict is true
     * @return the new total prices of all the Books in the list after increased
     */
    public static double increaseTotalPrice(ArrayList<Books> st, boolean nfict, boolean fict) {
    	if(st.isEmpty())
    		return -1;
    	int N = st.size();
    	    	
    	if(nfict == true){
    		for(int i = 0; i < N; i++){
    			if(st.get(i).getClass().getSuperclass().getName() == "NonFiction" )
    				st.get(i).increasePrice();
    		}
    				
    	}
   
    	if(fict == true){
    		for(int i = 0; i < N; i++){
    			if(st.get(i).getClass().getSuperclass().getName() == "Fiction" )
    				st.get(i).increasePrice();
    		}
    	}
    	double totalPrice = getTotalPrice(st);
    	return totalPrice;
    
    }

    /**
     * When users click the Decrease price button, the GUI will invoke this method to decrease the prices of selected Book categories,
     * and the new total price will be displayed on the GUI
     * @param st the list of all added Books
	 * @param nfict the state of the checkbox of NonFiction, if the checkbox of NonFiction is checked, then nfict is true
	 * @param fict the state of the checkbox of Fiction, if the checkbox of Fiction is checked, then fict is true
     * @return the new total prices of all the Books in the list after decreased
     */	
	public static double decreaseTotalPrice(ArrayList<Books> st, boolean nfict, boolean fict) {
    	if(st.isEmpty())
    		return -1;
    	
    	int N = st.size();
    	
		if(nfict == true){
			for(int i = 0; i < N; i++){
    			if(st.get(i).getClass().getSuperclass().getName() == "NonFiction" )
    				if(!st.get(i).testDecreasePrice())
						return -1;
    		}  
		}
		
		if(fict == true){
			for(int i = 0; i < N; i++){
    			if(st.get(i).getClass().getSuperclass().getName() == "Fiction" )
    				if(!st.get(i).testDecreasePrice())
						return -1;
    		}  
		}
    	    	
    	if(nfict == true){
    		for(int i = 0; i < N; i++){
    			if(st.get(i).getClass().getSuperclass().getName() == "NonFiction" )
    				st.get(i).decreasePrice();
    		}  
    	}
    	if(fict == true){
    		for(int i = 0; i < N; i++){
    			if(st.get(i).getClass().getSuperclass().getName() == "Fiction" )
    				st.get(i).decreasePrice();
    		}
    	}
   		
    	double totalPrice = getTotalPrice(st);
    	return totalPrice;
	}
}
