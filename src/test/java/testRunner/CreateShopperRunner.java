package testRunner;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import genericUtility.FrameworkConstant;
import genericUtility.GenericProperty;
import genericUtility.RandomDataUtility;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import plainOldObject.CreateShopperSers;
import specBuilderGeneric.SpecBuilderWithBody;
import tools.jackson.databind.ObjectMapper;

public class CreateShopperRunner implements FrameworkConstant
{
	@Test(groups = "smoke")
	public void createShopper() {
		
		String baseUrl = GenericProperty.getData("BaseUrl", getPropertiesPath);
		String content=GenericProperty.getData("content", getPropertiesPath);
		
		
		
		CreateShopperSers shopper = new CreateShopperSers();
		shopper.setCity("Banglore");
		shopper.setCountry("India");
		shopper.setEmail(RandomDataUtility.randomEmail());
		shopper.setFirstName("Dhiraj");
		shopper.setGender("MALE");
		shopper.setLastName("kumar");
		shopper.setPassword("Dhiraj@123");
		shopper.setPhone(RandomDataUtility.number());
		shopper.setState("Karnataka");
		shopper.setZoneId("ALPHA");
		
		
		ObjectMapper obj = new ObjectMapper();
		String jsonBody = obj.writerWithDefaultPrettyPrinter().writeValueAsString(shopper);
		
		RequestSpecification req = SpecBuilderWithBody.request(baseUrl, jsonBody,content);
		ResponseSpecification res = SpecBuilderWithBody.response(201, "HTTP/1.1 201 ", content, "message", "Created", responseTime);
		
		Response response = RestAssured.given().relaxedHTTPSValidation()
				.spec(req).when().post("/shoppers").then().spec(res).
				and().header("Content-Type", Matchers.equalTo(content)).
				and().header("Vary", Matchers.equalTo("Access-Control-Request-Headers")).
				and().header("Server", Matchers.equalTo("nginx/1.18.0 (Ubuntu)")).
				and().time(Matchers.lessThanOrEqualTo(4000l)).
				and().body("data.role", Matchers.equalTo("SHOPPER")).
				extract().response();
				System.out.println(response.asPrettyString());
		
		
	}

}
