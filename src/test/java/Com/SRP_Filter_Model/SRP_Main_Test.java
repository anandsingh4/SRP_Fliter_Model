package Com.SRP_Filter_Model;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SRP_Main_Test {

	@BeforeClass
	public void setup() throws Exception {
		Config_Utile.loadConfig();
	}

	@Test
	public void runSRPTest() {

		for (String url : Base_EndPoint.Final_Url) {
//	            System.out.println("\n===== Running validation for: " + url + " =====");

			SRP_Validation validation = new SRP_Validation();

			validation.SRP_Validation_check(url);

			SRP_Check_Points reporter = new SRP_Check_Points();
			reporter.SRP_Validation_check(validation);

		}
	}
}
