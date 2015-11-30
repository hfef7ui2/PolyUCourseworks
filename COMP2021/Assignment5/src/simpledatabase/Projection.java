package simpledatabase;
import java.util.ArrayList;

public class Projection extends Operator{
	
	ArrayList<Attribute> newAttributeList;
	private String attributePredicate;


	public Projection(Operator child, String attributePredicate){
		
		this.attributePredicate = attributePredicate;
		this.child = child;
		newAttributeList = new ArrayList<Attribute>();
		
	}
	
	
	/**
     * Return the data of the selected attribute as tuple format
     * @return tuple
     */
	@Override
	public Tuple next(){
		Tuple curTuple = child.next();
		if(curTuple == null)
			return null;
		ArrayList<Attribute> curArributeList = curTuple.getAttributeList();
		int i;
		int index = 0;
		int size = curArributeList.size();
		for(i = 0; i < size; i++){
			if(curArributeList.get(index).getAttributeName().equals(attributePredicate)){
				index++;
				continue;
			}else{
				curArributeList.remove(index);
			}
		}
		Tuple newTuple = new Tuple(curArributeList);
		return newTuple;
	}
		

	
	/**
     * The function is used to get the attribute list of the tuple
     * @return attribute list
     */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}