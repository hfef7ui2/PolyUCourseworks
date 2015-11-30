package simpledatabase;
import java.util.ArrayList;

public class Selection extends Operator{
	
	ArrayList<Attribute> attributeList;
	String whereTablePredicate;
	String whereAttributePredicate;
	String whereValuePredicate;

	
	public Selection(Operator child, String whereTablePredicate, String whereAttributePredicate, String whereValuePredicate) {
		this.child = child;
		this.whereTablePredicate = whereTablePredicate;
		this.whereAttributePredicate = whereAttributePredicate;
		this.whereValuePredicate = whereValuePredicate;
		attributeList = new ArrayList<Attribute>();

	}
	
	
	/**
     * Get the tuple which match to the where condition
     * @return the tuple
     */
	@Override
	public Tuple next(){
		Tuple curTuple = child.next();
		if(curTuple == null)
			return null;
		if(!whereTablePredicate.equals(child.from)){
			return curTuple;
		}
		ArrayList<Attribute> curAttributeList = curTuple.getAttributeList();
		int i;
		for(i = 0; i < curAttributeList.size(); i++){
			if(curAttributeList.get(i).getAttributeName().equals(whereAttributePredicate))
				break;
		}
		if(whereAttributePredicate.equals("id")){
			String temp = new String(curAttributeList.get(i).getAttributeValue().toString());
			if(temp.equals(whereValuePredicate)){
				return curTuple;
			}
			else 
				return this.next();
		}else{
			if(curAttributeList.get(i).getAttributeValue().equals(whereValuePredicate))
				return curTuple;
			else
				return this.next();
		}
	}
	
	/**
     * The function is used to get the attribute list of the tuple
     * @return the attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		
		return(child.getAttributeList());
	}

	
}