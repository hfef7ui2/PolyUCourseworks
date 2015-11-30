


import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;

public class Assign1TestLevel4 extends TestCase {

    private Assign1Interface a1;
    ArrayList list;
    List<Integer> l1;
    Collection countries;

    public void setUp() {
    	a1 = new Assign1();
        list = a1.createList();
        l1 = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
		String names[] = { "Japan", "Vietnam"};
		countries = new ArrayList();
		for (int i = 0, n = names.length; i < n; i++) {
			countries.add(names[i]);
		}
    }
    
    public void testAverageOfSubListOfIntegers() throws Exception {
       assertTrue(a1.averageOfSubListOfIntegers(1, 4, l1) != null && a1.averageOfSubListOfIntegers(1, 4, l1) == 3 && a1.averageOfSubListOfIntegers(3, 6, l1) == 5 && a1.averageOfSubListOfIntegers(3, 6, l1) != null);
    }

    public void testSortedSpecifiedList() throws Exception {
		ArrayList countryList1 = a1.createList();
		a1.insertToListHead(countryList1, "India");
        a1.insertToListHead(countryList1, "Japan");
        a1.insertToListHead(countryList1, "Cuba");
        a1.insertToListHead(countryList1, "Germany");
		a1.insertToListHead(countryList1, "America");

        assertTrue(a1.sortedSpecifiedList(countryList1, countries) != null && a1.sortedSpecifiedList(countryList1, countries).isEmpty());
    }

    public void testListComparing() throws Exception {
		ArrayList countryList2 = a1.createList();
		a1.insertToListHead(countryList2, "China");
        a1.insertToListHead(countryList2, "Thailand");
        a1.insertToListHead(countryList2, "Japan");
        a1.insertToListHead(countryList2, "Singapore");
		a1.insertToListHead(countryList2, "Vietnam");
		
		ArrayList countryList3 = a1.createList();
		a1.insertToListHead(countryList3, "Vietnam");
		a1.insertToListHead(countryList3, "Thailand");
		a1.insertToListHead(countryList3, "Singapore");
		a1.insertToListHead(countryList3, "Japan");
		a1.insertToListHead(countryList3, "China");
    	assertTrue(a1.sortedSpecifiedList(countryList2, countries) != null && a1.listComparing(a1.sortedSpecifiedList(countryList2, countries), countryList3));
    }
}