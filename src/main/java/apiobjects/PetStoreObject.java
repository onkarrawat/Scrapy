package apiobjects;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;

import io.restassured.response.ValidatableResponse;
import utils.LoggerFile;
import utils.PropertyHandler;
import utils.RestWrapper;

public class PetStoreObject {
	Integer rand_int_For_ID,id_For_NewlyCreated_Pet_Received_In_JsonResponse;
	Logger logger = LoggerFile.logConfig(this.getClass().getName());
	
    PropertyHandler propertyHandler = new PropertyHandler();;
    RestWrapper restWrapper = new RestWrapper(this.propertyHandler);
    
    public ValidatableResponse getCallToAvailablePets() throws Throwable{
    	String serviceHost = propertyHandler.readProperty("PetStore_Host");
    	String ServiceEndPoint = "/v2/pet/findByStatus";
    	Map<String,String>params = new HashMap<String,String>();
    	params.put("status", "available");
    	restWrapper.simpleGetCallWithParams(serviceHost, ServiceEndPoint, params, restWrapper.setHeaders());
    	
    	return restWrapper.getResponse();
    }
    
    public void ValidateResponseForAvailablePets() throws Throwable {
    	JSONArray jsArray = new JSONArray(restWrapper.getResponse().extract().body().asString());
    	logger.info("length of JSONArray is " +jsArray.length());
    	for(int i=0;i<jsArray.length();i++) {
    		logger.info(jsArray.get(i).toString());
    		JSONObject jsonOBJ = new JSONObject(jsArray.get(i).toString());
    		Assert.assertEquals(jsonOBJ.get("status").toString(),"available","status is not received as available in json for the pet info"+jsonOBJ.get("id").toString());
    		
    	}
    }
    
    public ValidatableResponse postCallToAddNewPetToStore() throws Throwable{
    	String serviceHost = propertyHandler.readProperty("PetStore_Host");
    	String ServiceEndPoint = "/v2/pet";
    	Random rand = new Random(); 
    	rand_int_For_ID = rand.nextInt(999999999);
    	logger.info("rand_int "+rand_int_For_ID); 
    	String Payload = "{\r\n" + 
    			"  \"id\": "+rand_int_For_ID+",\r\n" + 
    			"  \"category\": {\r\n" + 
    			"    \"id\": 0,\r\n" + 
    			"    \"name\": \"string\"\r\n" + 
    			"  },\r\n" + 
    			"  \"name\": \"doggie\",\r\n" + 
    			"  \"photoUrls\": [\r\n" + 
    			"    \"string\"\r\n" + 
    			"  ],\r\n" + 
    			"  \"tags\": [\r\n" + 
    			"    {\r\n" + 
    			"      \"id\": 0,\r\n" + 
    			"      \"name\": \"string\"\r\n" + 
    			"    }\r\n" + 
    			"  ],\r\n" + 
    			"  \"status\": \"available\"\r\n" + 
    			"}";
    	restWrapper.postCall(serviceHost, ServiceEndPoint, Payload, restWrapper.setHeaders());
    	
    	return restWrapper.getResponse();
    }

    
   public void  ValidateNewIDCreatedInResponseJson() throws Throwable {
	   JSONObject jsonOBJ = new JSONObject(restWrapper.getResponse().extract().body().asString());
	   id_For_NewlyCreated_Pet_Received_In_JsonResponse = Integer.valueOf(jsonOBJ.get("id").toString());
       logger.info("id_For_NewlyCreated_Pet_Received_In_JsonResponse "+id_For_NewlyCreated_Pet_Received_In_JsonResponse);
	   Assert.assertEquals(id_For_NewlyCreated_Pet_Received_In_JsonResponse.intValue(), rand_int_For_ID.intValue());
   }
   
   public ValidatableResponse putCallToUpdateExistingPetInStore(String status) throws Throwable{
   	String serviceHost = propertyHandler.readProperty("PetStore_Host");
   	String ServiceEndPoint = "/v2/pet"; 
   	String Payload = "{\r\n" + 
   			"  \"id\": 9222968140491028000,\r\n" + 
   			"  \"category\": {\r\n" + 
   			"    \"id\": 0,\r\n" + 
   			"    \"name\": \"string\"\r\n" + 
   			"  },\r\n" + 
   			"  \"name\": \"doggie\",\r\n" + 
   			"  \"photoUrls\": [\r\n" + 
   			"    \"string\"\r\n" + 
   			"  ],\r\n" + 
   			"  \"tags\": [\r\n" + 
   			"    {\r\n" + 
   			"      \"id\": 0,\r\n" + 
   			"      \"name\": \"string\"\r\n" + 
   			"    }\r\n" + 
   			"  ],\r\n" + 
   			"  \"status\": \""+status+"\"\r\n" + 
   			"}";
   	restWrapper.putCall(serviceHost, ServiceEndPoint, Payload, restWrapper.setHeaders());
   	
   	return restWrapper.getResponse();
   }
   
   public void  ValidateStatuschangeforUpdatedPetInResponseJson(String ExpectedStatus) throws Throwable {
	   JSONObject jsonOBJ = new JSONObject(restWrapper.getResponse().extract().body().asString());
	   String ActualStatus = jsonOBJ.get("status").toString();
       logger.info("ActualStatus "+ActualStatus);
	   Assert.assertEquals(ActualStatus, ActualStatus);
   }

}
