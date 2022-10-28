package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI="http://216.10.245.166";
		String response=given().log().all().header("Content-Type","application/json").
	    body(Payload.AddBook(isbn,aisle)).
		when().
		post("/Library/Addbook.php").
        then().log().all().assertThat().statusCode(200).extract()
        .response().asString();
		JsonPath js=ReusableMethods.rawToJson(response);
		String id= js.getString("ID");
		System.out.println(id);
	}
	
	@DataProvider(name="BooksData")
	//array=collection of elements
	//multidimensional array= collection of arrays
	public Object[][] getData() {
		return new Object[][] {{"baabd","2416"}, {"baaabd","24226"},{"baaaarbd","236"}}; 
	}
}
