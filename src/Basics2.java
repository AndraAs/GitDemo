import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;

public class Basics2 {

	public static void main(String[] args) throws IOException {
		// Validate AddPlace API
		// Rest Assured works with:
//		given-> All input details
//		when-> Hit the API
//		then -> Validate the response
		//content of the file to string->content of file can convert into byte->byte data to string
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\Asus\\Documents\\RestAssuredCourse\\addPlace.json")))).when().post("maps/api/place/add/json")
		.then().statusCode(200).body("scope", equalTo("APP"))
		.header("Server","Apache/2.4.41 (Ubuntu)").extract().response().asString();
//		System.out.println("****"+response+"****");
		//Add place; update place with new address; get place to validate update
		JsonPath js=ReusableMethods.rawToJson(response);//for parsing json
		String placeId=js.getString("place_id");
		System.out.println(placeId);
		
		//PUT Method
		String newAddress="rua da tapada, pt";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//GET Method
		
		String getPlaceResponse=given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", placeId)
		.when().get("/maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		JsonPath jsonParse=ReusableMethods.rawToJson(getPlaceResponse);
		String actualAddress=jsonParse.getString("address");
		
		//for Java assertions we need either Junit or TestNg
		Assert.assertEquals(actualAddress, newAddress);
		
		
		
	}

}
