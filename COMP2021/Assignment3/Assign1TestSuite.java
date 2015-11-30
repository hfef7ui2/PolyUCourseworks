import junit.framework.Test;
import junit.framework.TestSuite;

public class Assign1TestSuite extends TestSuite{
	public static Test suite(){
		TestSuite suite = new TestSuite("Tests");
		suite.addTestSuite(Assign1TestLevel1.class);
		suite.addTestSuite(Assign1TestLevel2.class);
		suite.addTestSuite(Assign1TestLevel3.class);
		suite.addTestSuite(Assign1TestLevel4.class);
		return suite;
	}
}