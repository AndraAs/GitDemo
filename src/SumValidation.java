import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {
	
	@Test
	public void sumOfCourses() {
		JsonPath jsComplex = new JsonPath(Payload.CoursePrice());
		int count=jsComplex.getInt("courses.size()");
	int totalPricesAmounts=0;
	for (int j =0; j<count; j++ ) {
		int coursePrices= jsComplex.getInt("courses["+j+"].price");
		int courseAmounts=jsComplex.getInt("courses["+j+"].copies");
		int totalPerCourse=coursePrices*courseAmounts;
		totalPricesAmounts=totalPricesAmounts+totalPerCourse;
		}
		System.out.println("TOTAL:"+totalPricesAmounts);
		System.out.println("TOTAL1");
		System.out.println("TOTAL2");
		System.out.println("TOTAL3");
		System.out.println("TOTAL4");
		System.out.println("change to develop branch");
		System.out.println("change from x user");
	
		int purchaseAmount=jsComplex.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(totalPricesAmounts, purchaseAmount);

}
}