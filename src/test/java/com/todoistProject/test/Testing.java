package com.todoistProject.test;


import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class Testing {
	
	String token = "a8c484f6ab78bb5d8943ed56791c4ce57becff5e";
	String url = "https://api.todoist.com/rest/v1/";
	@Test
	public void testToFetchData() {
		
		RestAssured
			.given()
				.header("Authorization","Bearer "+token)
				.header("Content-Type","application/json")
				.get(url+"projects")
			.then()
				.statusCode(200)
				.body("[0].name",equalTo("Inbox"))
				.body("[1].name",equalTo("Welcome 👋"))
				.body("[2].name",equalTo("Try Boards"))
				.body("[3].name",equalTo("task"))
				.body("[4].name",equalTo("test"))
				.body("[5].name",equalTo("test2"))
				.body("[6].name",equalTo("test3"))
				.log()
				.all();
				
	}
	
	@Test
	public void testToCheckCreateTask() {
		
		JSONObject jsonObject = new JSONObject();
		
		String id = "2270742863";
		
		jsonObject.put("content","Go To Temple");
		jsonObject.put("due_string","tuesday at 11:00");
		jsonObject.put("due_lang","en");
		jsonObject.put("priority",4);
		
		Response resp = RestAssured
			.given()
				.header("Authorization","Bearer "+token)
				.header("Content-Type","application/json")
			.and()
				.body(jsonObject.toString())
			.when()
				.post(url+"tasks")
			.then()
				.extract()
				.response();
			
		Assertions.assertEquals(200, resp.statusCode());
		Assertions.assertEquals("Go To Temple", resp.jsonPath().getString("content"));
		Assertions.assertEquals(id, resp.jsonPath().getString("project_id"));
	}
	
	@Test
	public void testToGetAnActiveTask() {
		
		String id = "2270742863";
		
		Response resp =  RestAssured
		.given()
			.header("Authorization","Bearer "+token)
			.header("Content-Type","application/json")
			.get(url+"tasks/5084126551")
		.then()
			.extract()
			.response();
		
		Assertions.assertEquals(200, resp.statusCode());
		Assertions.assertEquals("false", resp.jsonPath().getString("completed"));
		Assertions.assertEquals("Buy Water", resp.jsonPath().getString("content"));
		Assertions.assertEquals(id, resp.jsonPath().getString("project_id"));
		
	}
	
	@Test
	public void testToCloseATask() {
		
		Response resp =  RestAssured
			.given()
				.header("Authorization","Bearer "+token)
				.header("Content-Type","application/json")
				.get(url+"tasks/5082085132")
			.then()
				.extract()
				.response();
		
		String taskName = resp.jsonPath().getString("content");
		String status = resp.jsonPath().getString("completed");
		String id = resp.jsonPath().getString("id");
		
		if(taskName.equals("Buy Vegetables") && status.equals("false"));
		{
			 RestAssured
				.given()
					.header("Authorization","Bearer "+token)
					.header("Content-Type","application/json")
					.post(url+"tasks/"+id+"/close")
				.then()
					.statusCode(204)
			 		.log()
			 		.all();
		}
		
	}
	
	@Test
	public void testToReopenATask() {
		
	}
	

}
