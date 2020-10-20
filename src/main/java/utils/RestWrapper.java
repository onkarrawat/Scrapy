package utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import org.apache.log4j.Logger;

public class RestWrapper {
	
 Logger logger = LoggerFile.logConfig(RestWrapper.class.getName());
 
	private ValidatableResponse response;
	private int responseCode;
	PropertyHandler propertyHandler;

	public RestWrapper(PropertyHandler propertyHandler) {
		this.propertyHandler = propertyHandler;
	}

	public String postCall(String host, String endPointUrl, String postBody, Map<String, String> headers)
			throws Throwable {
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(host);
		builder.setBody(postBody);
		builder.setBasePath(endPointUrl);
		builder.setConfig(RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
		builder.setUrlEncodingEnabled(false);
		builder.setContentType(ContentType.JSON);
		RequestSpecification reqSpec = builder.build();
		logger.info("Payload is " + postBody);
		logger.info("header are  is " + headers);
		logger.info("Service URL is " + host + endPointUrl);
		response = given().spec(reqSpec).headers(headers).post().then().log().status();
		responseCode = response.extract().statusCode();
		logger.info("responsecode "+ response.extract().statusCode());
		String responseValue = response.extract().body().asString();
		logger.info("responseValue "+responseValue);
		return responseValue;
	}

	public String simpleGetCallWithParams(String host, String endPointUrl, Map<String,String> params, Map<String, String> headers)
			throws Throwable {
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(host);		
		builder.setBasePath(endPointUrl);
		builder.addQueryParams(params);
		builder.setConfig(RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
		builder.setUrlEncodingEnabled(false);
		builder.setContentType(ContentType.JSON);
		RequestSpecification reqSpec = builder.build();
		
		logger.info("Payload is" + params);
		logger.info("header are  is" + headers);
		logger.info("Service URL is " + host + endPointUrl);
		System.out.println("Service URL is: " + host + endPointUrl);
		response = given().spec(reqSpec).headers(headers).get().then().log().status();
		System.out.println("response are "+ response.extract().body().asString());
		logger.info("response "+response);
		responseCode = response.extract().statusCode();
		String responseValue = response.extract().body().asString();
		return responseValue;
	}
	public String putCall(String host, String endPointUrl, String postBody, Map<String, String> headers)
			throws Throwable {
		RequestSpecBuilder builder = new RequestSpecBuilder();
		builder.setBaseUri(host);
		builder.setBody(postBody);
		builder.setBasePath(endPointUrl);
		builder.setConfig(RestAssuredConfig.config().sslConfig(new SSLConfig().relaxedHTTPSValidation()));
		builder.setUrlEncodingEnabled(false);
		builder.setContentType(ContentType.JSON);
		RequestSpecification reqSpec = builder.build();
		logger.info("Payload is" + postBody);
		logger.info("header are  is" + headers);
		logger.info("Service URL is" + host + endPointUrl);
		response = given().spec(reqSpec).headers(headers).put().then().log().status();
		responseCode = response.extract().statusCode();
		String responseValue = response.extract().body().asString();
		return responseValue;
	}
	
	public Map<String,String> setHeaders() throws Throwable {
		Map<String,String>headers = new HashMap<String,String>();
		headers.put("Accept", "application/json");
		headers.put("ContentType", "application/json");
		headers.put("api_key", "authkeyfortest");
		return headers;
		
	}
	
	public PropertyHandler getpropertyHandler() {
		return propertyHandler;
	}
	public ValidatableResponse getResponse() {
		return response;
	}
}
