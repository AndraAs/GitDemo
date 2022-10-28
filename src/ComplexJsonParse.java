import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	
	public static void main(String[] args) {
	
	JsonPath jsComplex = new JsonPath(Payload.CoursePrice());
	
	//Print no of courses returned by API
	int count=jsComplex.getInt("courses.size()");
	System.out.println(count);
	
	//Print purchase amount
	int purchaseAmount=jsComplex.getInt("dashboard.purchaseAmount");
	System.out.println(purchaseAmount);
	//Print title of the first course
	String firstTitle=jsComplex.getString("courses[0].title");
	System.out.println(firstTitle);
	
	//Print all course titles and their respective prices
	
	for (int i =0; i<count; i++ ) {
		String courseTitles=jsComplex.get("courses["+i+"].title");
		System.out.println(courseTitles);
		int coursePrices= jsComplex.getInt("courses["+i+"].price");
		System.out.println(coursePrices);
		//Print no of copies sold by RPA course
		if(jsComplex.getString("courses["+i+"].title").equalsIgnoreCase("RPA")) {
			System.out.println(jsComplex.get("courses["+i+"].copies").toString());
		}
		
	
	}
	
	//Verify if sum of all courses matches purchase amount
	int totalPricesAmounts=0;
	for (int j =0; j<count; j++ ) {
		int coursePrices= jsComplex.getInt("courses["+j+"].price");
		int courseAmounts=jsComplex.getInt("courses["+j+"].copies");
		int totalPerCourse=coursePrices*courseAmounts;
		totalPricesAmounts=totalPricesAmounts+totalPerCourse;
		}
		System.out.println("TOTAL:"+totalPricesAmounts);
	
	}

	}

