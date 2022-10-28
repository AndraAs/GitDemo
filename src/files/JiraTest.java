package files;
import static io.restassured.RestAssured.*;
import static org.testng.Assert.assertEquals;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.config.MultiPartConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraTest {

	public static void main(String[] args) {
		RestAssured.baseURI="http://localhost:8080";
//Login Scenario
		SessionFilter session = new SessionFilter();
		String response=given().relaxedHTTPSValidation().header("Content-Type","application/json").body("{ \"username\": \"andra.astalus\", \"password\": \"admin\" }").
		log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		String expectedMsg="testing123";
	//Add comment	
		String commentResponse=given().pathParam("id", "10003").log().all().header("Content-Type","application/json").body("{\r\n"
				+ "    \"body\": \""+expectedMsg+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{id}/comment").then().log().all().assertThat()
		.statusCode(201).extract().response().asString();
		JsonPath js= new JsonPath(commentResponse);
		String commentId=js.getString("id");

		//Add Attachment
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("id", "10003")
		.header("Content-Type","multipart/form-data")
		.multiPart("file", new File("jira.txt")).post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);
		
		//Get issue
		String getResponse=given().pathParam("id", "10003")
				.queryParam("fields", "comment")
				
				.log().all().filter(session).when().get("/rest/api/2/issue/{id}").then().log().all()
				.extract().response().asString();
		System.out.println(getResponse);
		
		JsonPath js2= new JsonPath(getResponse);
		int commentCount=js2.getInt("fields.comment.comments.size()");
	
		for(int i=0;i<commentCount;i++) {
		
			String commentIdIssue=js2.get("fields.comment.comments["+i+"].id").toString();
			if(commentIdIssue.equalsIgnoreCase(commentId)){
				String bodyParsedId= js2.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(bodyParsedId);
				Assert.assertEquals(bodyParsedId, expectedMsg);
			}else {
				System.out.println("something went wrong");
			}
			
		
		}
	}

}
