package apistepdefinitions;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.testng.Assert;

import apiobjects.PetStoreObject;
import cucumber.api.Scenario;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.RestAssuredConfig;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import utils.LoggerFile;
import utils.PropertyHandler;
import utils.RestWrapper;

public class PetStoreStepDef {
	RestWrapper restWrapper;
	Logger logger = LoggerFile.logConfig(PetStoreStepDef.class.getName());

	PropertyHandler propertyHandler;
	ValidatableResponse response;

	PetStoreObject petStoreObject = new PetStoreObject();

	@When("^user makes a get call for available pets$")
	public void user_makes_a_get_call_for_available_pets() throws Throwable {
		response = petStoreObject.getCallToAvailablePets();

		logger.info("response is " + response.extract().body().asString());
	}

	@Then("^assert the available status for each pet info received in json response$")
	public void assert_the_available_status_for_each_pet_info_received_in_json_response() throws Throwable {
		petStoreObject.ValidateResponseForAvailablePets();

	}

	@When("^user post a new pet to the store$")
	public void user_post_a_new_pet_to_the_store() throws Throwable {
		response = petStoreObject.postCallToAddNewPetToStore();

		logger.info("response is " + response.extract().body().asString());
	}

	@Then("^validate the response status code (\\d+)$")
	public void validate_response_status_code(int responseCode) throws Throwable {
		Assert.assertEquals(response.extract().statusCode(), responseCode);
	}

	@Then("^validate the id for the newly created pet in response body$")
	public void validate_the_id_for_the_newly_created_pet_in_response_body() throws Throwable {
		petStoreObject.ValidateNewIDCreatedInResponseJson();

	}
	
	@When("^user update status of an existing pet as \"(.*)\"$")
	public void user_update_status_of_an_existing_pet_as_sold(String statusToBeChangedTo) throws Throwable {
		response = petStoreObject.putCallToUpdateExistingPetInStore(statusToBeChangedTo);
		logger.info("response is " + response.extract().body().asString());
	}
	
	@Then("^validate status \"(.*)\" is changed in Json response body for the existing pet$")
	public void validate_status_is_changed_in_json_response_body_for_existing_pet(String expectedStatus) throws Throwable{
		petStoreObject.ValidateStatuschangeforUpdatedPetInResponseJson(expectedStatus);
	}
}
