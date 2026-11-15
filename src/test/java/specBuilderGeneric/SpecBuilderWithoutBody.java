package specBuilderGeneric;

import org.hamcrest.Matchers;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderWithoutBody {
	
	public static RequestSpecification request(String url,String content)
	{
		RequestSpecBuilder request=new RequestSpecBuilder();
		RequestSpecification req = request.setBaseUri(url).setContentType(content).build();
		return req;
		
	}
	public static ResponseSpecification response(int code,String line,String content,String key, String value,long time)
	{
		ResponseSpecBuilder
		response= new ResponseSpecBuilder();
		ResponseSpecification res = response.expectStatusCode(code)
				.expectStatusLine(line)
				.expectContentType(content)
				.expectBody(key,Matchers.equalTo(value))
				.expectResponseTime(Matchers.lessThanOrEqualTo(time))
				.build();
		return res;
	}
}
