package testRunner;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import genericUtility.FrameworkConstant;
import genericUtility.GenericProperty;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import specBuilderGeneric.SpecBuilderWithoutBody;

public class FetchShopperDetails  implements FrameworkConstant
{
	@Test(groups = "smoke")
	public void getShopper() {
		try {
			String token=GenericProperty.getData("jwtToken", setPropertiesPath);
			String userId=GenericProperty.getData("userID", setPropertiesPath);
			
			String baseUrl = GenericProperty.getData("BaseUrl", getPropertiesPath);
			String content=GenericProperty.getData("content", getPropertiesPath);

			RequestSpecification req = SpecBuilderWithoutBody.request(baseUrl, content);
			ResponseSpecification res = SpecBuilderWithoutBody.response(200, "HTTP/1.1 200 ",
					content, "message", "Success", responseTime);

			Response response = RestAssured.given().relaxedHTTPSValidation().spec(req).
					when().auth().oauth2(token).get("/shoppers/"+userId+"").
					then().spec(res).
					and().header("Content-Type", Matchers.equalTo(content)).
					and().header("Vary", Matchers.equalTo("Access-Control-Request-Headers")).
					and().header("Server", Matchers.equalTo("nginx/1.18.0 (Ubuntu)")).
					and().time(Matchers.lessThanOrEqualTo(4000l)).
					and().body("data.role", Matchers.equalTo("SHOPPER")).
					extract().response();
			System.out.println(response.asPrettyString());
		}
		catch (Exception e) {
			System.out.println("Shopper fetch details failed: " + e.getMessage());
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
