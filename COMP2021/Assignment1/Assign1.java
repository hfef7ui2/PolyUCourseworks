

import java.util.*;

public class Assign1 implements Assign1Interface {

//Level 1
    public ArrayList createList() {
        return new ArrayList();
    }

    public Vector createVector() {
        return new Vector();
    }

    public TreeSet createSet() {
        return new TreeSet();
    }

// Level2
    public void insertToListHead(ArrayList list, Object o) {
    	list.add(0,o);
    }

    public void insertToVector(Vector vector, Object o) {
    	vector.add(o);
    }

    public void insertToSet(TreeSet set, Object o) {
        set.add(o);
    }

    public Object removeLowestValue(TreeSet set) {
    	if(set.isEmpty())
    		return null;
    	Object o = set.first();
        set.remove(set.first());
        return o;
    }

// Level 3
    public void insertToListNTimes(ArrayList list, Object o, int n) {
        int i;
    	for (i=0;i<n;i++)
    		list.add(o);
    }

    public Object removeFromListHead(ArrayList list) {
        if(list.isEmpty())
        	return null;
        Object o = list.get(0);
        list.remove(0);
        return o;
    }

    public Vector getOddIndexElement(Vector vector) {
		if(vector.size()<2)
			return null;
		Vector odd = new Vector();
		int i=0;
		int N=vector.size();
		while(i<N){
			if((i%2)==1){
				odd.add(vector.get(i));
			}
			i++;
		}
		return odd;
    }

    public Double findRangeMax(int fromIndex, int endIndex, List<Double> list) {
        if(endIndex<fromIndex||list.get(endIndex)==null)
        	return null;
        Double max=0.0;
        for(;fromIndex<=endIndex;fromIndex++){
        	if(list.get(fromIndex)>max)
        		max=list.get(fromIndex);
        }
        return max;
    }

// Level 4
    public Integer averageOfSubListOfIntegers(int fromIndex, int endIndex, List<Integer> list) {
        int sum=0,cnt=0,temp=0;
        for(;fromIndex<=endIndex;fromIndex++){
        	temp=list.get(fromIndex);
        	sum=sum+temp;
        	cnt++;
        }
        if(cnt==0)
        	return null;
        Integer r=sum/cnt;
        return r;
    }

    public int getObjectPositionOfList(ArrayList list, Object o) {
    	if(!list.contains(o))
    		return -1;
    	int r=list.indexOf(o);
    	return ++r;
    }
	
	public ArrayList sortedSpecifiedList(ArrayList list, Collection<String> collection) {
		if(!list.containsAll(collection)){
			list.clear();
			return list;
		}
		Collections.sort(list);
		return list;
	}
	
	public boolean listComparing (ArrayList list1, ArrayList list2) {
		int i=0,j=0;
		while(i<list1.size()&&j<list2.size()){
			if(list1.get(i)!=list2.get(j))
				break;
			i++;
			j++;
		}
		if(i!=list1.size()||j!=list2.size())
			return false;
		return true;
	}
}
