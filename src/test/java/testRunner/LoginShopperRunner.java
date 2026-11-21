package testRunner;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.testng.annotations.Test;

import genericUtility.FrameworkConstant;
import genericUtility.GenericProperty;
import io.restassured.RestAssured;import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import plainOldObject.LoginShopperDese;
import plainOldObject.LoginShopperSere;
import specBuilderGeneric.SpecBuilderWithBody;
import tools.jackson.databind.ObjectMapper;

public class LoginShopperRunner implements FrameworkConstant
{
	@Test(groups = "smoke")
	public void loginShopper() throws IOException {
		try {
			String baseUrl = GenericProperty.getData("BaseUrl", getPropertiesPath);
			String content=GenericProperty.getData("content", getPropertiesPath);
			String email=GenericProperty.getData("email", setPropertiesPath);
			String role=GenericProperty.getData("role", setPropertiesPath);

			LoginShopperSere login = new LoginShopperSere();
			login.setEmail(email);
			login.setPassword(GenericProperty.getData("constantPassword", getPropertiesPath));
			login.setRole(role);

			ObjectMapper	obj = new ObjectMapper();
			String jsonBody = obj.writerWithDefaultPrettyPrinter().writeValueAsString(login);

			RequestSpecification reqSpec = SpecBuilderWithBody.request(baseUrl, jsonBody, content);
			ResponseSpecification resSpec = SpecBuilderWithBody.response(200, "HTTP/1.1 200 ", content, "message", "OK", responseTime);

			Response response = RestAssured.given().relaxedHTTPSValidation().spec(reqSpec).when()
					.post("/users/login").then().spec(resSpec).
					and().header("Content-Type", Matchers.equalTo(content)).
					and().header("Server", Matchers.equalTo("nginx/1.18.0 (Ubuntu)")).
					and().time(Matchers.lessThanOrEqualTo(6000l)).
					and().body("data.role", Matchers.equalTo("SHOPPER")).
					extract().response();
			System.out.println(response.asPrettyString());

			LoginShopperDese loginD=response.as(LoginShopperDese.class);
			String userID = loginD.getData().getUserId();
			String jwtToken = loginD.getData().getJwtToken();

			GenericProperty.store("jwtToken", jwtToken,setPropertiesPath);
			GenericProperty.store("userID", userID,setPropertiesPath);

		}catch (Exception e) {
			System.out.println("Shopper Login failed: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
