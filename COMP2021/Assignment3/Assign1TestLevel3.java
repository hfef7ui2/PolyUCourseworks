import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Vector;
import java.util.Arrays;
import java.util.List;

public class Assign1TestLevel3 extends TestCase {

    private Assign1Interface a1;
    ArrayList list, list2;
    Vector vector;

    public void setUp() {
    	a1 = new Assign1();
        list = a1.createList();
        list2 = a1.createList();
        vector = a1.createVector();
    }

    public void testInsertToListNTimes() throws Exception {
        a1.insertToListNTimes(list, "test", 3);
		assertTrue(list.size() == 3);
    }

    public void testRemoveFromListHead() throws Exception {
        a1.insertToListNTimes(list2, "test", 3);
    	boolean test = true;
    	if (a1.removeFromListHead(list2) != null && a1.removeFromListHead(list2).equals("test")) {
            try {
                for (int i = 0; i < 10; i++)
                    a1.removeFromListHead(list2);
            } catch (Exception e) {
                test = false;
            }

        } else 
        	test = false;  
    	assertTrue(test);
    }

    public void getOddIndexElement() throws Exception {
        a1.insertToVector(vector, 1);
        a1.insertToVector(vector, list);
        a1.insertToVector(vector, "test");
        a1.insertToVector(vector, 8);
        a1.insertToVector(vector, null);
    	assertTrue(a1.getOddIndexElement(vector) != null && a1.getOddIndexElement(vector).size() == 2 &&  a1.getOddIndexElement(vector).get(0) == list && a1.getOddIndexElement(vector).get(1).equals(8) && vector.size() == 5);
    }
    
    public void testFindRangeMax() throws Exception {
        List<Double> l1 = Arrays.asList(39.2, 12.1, 22.2, 55.4, 42.2, 59.5, 67.6);
    	assertTrue(a1.findRangeMax(0, 2, l1)!=null && a1.findRangeMax(0, 2, l1) == 39.2 && a1.findRangeMax(4, 6, l1) == 67.6 && a1.findRangeMax(4, 6, l1)!=null);
    }
}