import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Vector;
import java.util.TreeSet;

public class Assign1TestLevel2 extends TestCase {

    private Assign1Interface a1;
    ArrayList list;
    Vector vector;
    TreeSet set, set2;

    public void setUp() {
    	a1 = new Assign1();
        list = a1.createList();
        vector = a1.createVector();
        set = a1.createSet();
        set2 = a1.createSet();
    }

    public void testInsertToListHead() throws Exception {
    	a1.insertToListHead(list, "a");
		a1.insertToListHead(list, "b");
		a1.insertToListHead(list, "c");
		assertTrue(list.size() == 3 && list.get(0).equals("c")&& list.get(2).equals("a"));
    }

    public void testInsertToVector() throws Exception {
        a1.insertToVector(vector, "bb");
    	assertTrue(vector.size() == 1 && vector.get(0).equals("bb"));
    }

    public void testCreateSet() throws Exception {
        a1.insertToSet(set, 18);
		a1.insertToSet(set, 25);
		a1.insertToSet(set, 7);
		a1.insertToSet(set, 99);
    	assertTrue(set.size() == 4);
    }
    
    public void testRemoveLowestValue() throws Exception {
        a1.insertToSet(set2, 18);
		a1.insertToSet(set2, 25);
		a1.insertToSet(set2, 7);
		a1.insertToSet(set2, 99);
    	assertTrue(a1.removeLowestValue(set2) != null && a1.removeLowestValue(set2).equals(18));
    }
}