package simpledatabase;
import java.util.ArrayList;

import javax.print.attribute.standard.RequestingUserName;

public class Join extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String joinPredicate;
	ArrayList<Tuple> tuples1;


	//Join Constructor, join fill
	public Join(Operator leftChild, Operator rightChild, String joinPredicate){
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.joinPredicate = joinPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuples1 = new ArrayList<Tuple>();

	}


	/**
	 * It is used to return a new tuple which is already joined by the common attribute
	 * @return the new joined tuple
	 */
	//The record after join with two tables
	@Override
	public Tuple next(){

		Operator test = this.leftChild;

		if(test.from == null)
			test = test.child;

		if(tuples1.size() == 0){
			Tuple tempTuple;
			while(null != (tempTuple = leftChild.next())){
				tuples1.add(tempTuple);
			}
		}

		Tuple baseTuple = rightChild.next();
		if(baseTuple == null){
			return null;
		}
		ArrayList<Attribute> baseAttributList = baseTuple.getAttributeList();
		Object baseID = null;
		for(int k = 0; k < baseAttributList.size(); k++){
			if(baseAttributList.get(k).getAttributeName().equals("id")){
				baseID = baseAttributList.get(k).getAttributeValue();
				break;
			}
		}

		Object tempID = null;
		ArrayList<Attribute> tempAttributeList;
		for(int i = 0; i < tuples1.size(); i++){
			tempAttributeList = tuples1.get(i).getAttributeList();
			for(int k = 0; k < tempAttributeList.size(); k++){
				if(tempAttributeList.get(k).getAttributeName().equals("id")){
					tempID = tempAttributeList.get(k).getAttributeValue();

					if(baseID.equals(tempID)){
						tempAttributeList.addAll(baseAttributList);
						return new Tuple(tempAttributeList);
					}
				}
			}
		}
		return this.next();
	}
	
}