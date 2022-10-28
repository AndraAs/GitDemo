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
	
		int purchaseAmount=jsComplex.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(totalPricesAmounts, purchaseAmount);

}
}