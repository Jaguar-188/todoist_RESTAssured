package com.todoistProject.test;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Testing {
	
	String token = "";
	String url = "https://api.todoist.com/rest/v1/";
	@Test
	public void testToFetchData() {
		
		Response resp = RestAssured.given().header("Authorization","Bearer "+token).get(url+"projects");
		
		System.out.println(resp.getStatusCode());
		System.out.println(resp.getTime());
		System.out.println(resp.getBody().asString());

		Assert.assertEquals(resp.getStatusCode(), 200);
	}
	
	@Test
	public void testToCheckCreateTask() {
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("content","Buy Water");
		jsonObject.put("due_string","tomorrow at 11:00");
		jsonObject.put("due_lang","en");
		jsonObject.put("priority",3);
		
		RequestSpecification request =  (RequestSpecification) RestAssured.given()
						.header("Authorization","Bearer "+token)
						.header("Content-Type","application/json")
						.body(jsonObject.toString())
						.when();
						
		
		Response resp = request.post(url+"tasks");
		System.out.println(resp.getStatusCode());
		System.out.println(resp.getTime());
		System.out.println(resp.getBody().asString());

		Assert.assertEquals(resp.getStatusCode(), 200);
		
	}
	

}
