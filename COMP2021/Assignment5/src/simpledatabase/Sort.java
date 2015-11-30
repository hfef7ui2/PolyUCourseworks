package simpledatabase;
import java.util.ArrayList;
import java.util.Collections;

import simpledatabase.Type.DataTypes;

public class Sort extends Operator{

	private ArrayList<Attribute> newAttributeList;
	private String orderPredicate;
	ArrayList<Tuple> tuplesResult;


	public Sort(Operator child, String orderPredicate){
		this.child = child;
		this.orderPredicate = orderPredicate;
		newAttributeList = new ArrayList<Attribute>();
		tuplesResult = new ArrayList<Tuple>();

	}


	/**
	 * The function is used to return the sorted tuple
	 * @return tuple
	 */
	@Override
	public Tuple next(){
		if(tuplesResult.size() == 0){
			ArrayList<Tuple> tempList = new ArrayList<Tuple>(); 
			Tuple tempTuple;
			while(null != (tempTuple = child.next())){
				tempList.add(tempTuple);
			}
			if(tempList.size() == 0)
				return null;
			ArrayList<ArrayList<Attribute>> attributeMatrix = new ArrayList<ArrayList<Attribute>>();
			for(int i = 0; i < tempList.size(); i++)
				attributeMatrix.add(tempList.get(i).getAttributeList());
			int j;
			for(j = 0; j < attributeMatrix.get(0).size(); j++){
				if(attributeMatrix.get(0).get(j).getAttributeName().equals(orderPredicate))
					break;
			}
			int min;
			for(int k = 0; k < attributeMatrix.size(); k++){
				min = k;
				for(int l = k + 1; l < attributeMatrix.size(); l++){
					if(!orderPredicate.equals("id")){
						if(((String)attributeMatrix.get(l).get(j).getAttributeValue()).compareTo(
								(String)attributeMatrix.get(min).get(j).getAttributeValue()) < 0)
							min = l;
					}else{
						if((Integer.parseInt(attributeMatrix.get(l).get(j).getAttributeValue().toString())) < (
								Integer.parseInt(attributeMatrix.get(min).get(j).getAttributeValue().toString())))
							min = l;
					}
				}
				Collections.swap(attributeMatrix, min, k);
			}
			for(int m = 0; m < attributeMatrix.size(); m++){
				tuplesResult.add(new Tuple(attributeMatrix.get(m)));
			}
		}
		return tuplesResult.remove(0);

	}

	/**
	 * The function is used to get the attribute list of the tuple
	 * @return attribute list
	 */
	public ArrayList<Attribute> getAttributeList(){
		return child.getAttributeList();
	}

}