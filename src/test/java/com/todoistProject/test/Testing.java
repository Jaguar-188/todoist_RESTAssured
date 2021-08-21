package com.todoistProject.test;


import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;

public class Testing {
	
	String token = "";
	String url = "https://api.todoist.com/rest/v1/";
	@Test
	public void testToFetchProjectsData() {
		
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
	public void testToGetAllActiveTasks() {
		
		//int id = 5081847734;
		//String project_id = "2270742863";
		
		RestAssured
		.given()
			.header("Authorization","Bearer "+token)
			.header("Content-Type","application/json")
			.get(url+"tasks")
		.then()
			.statusCode(200)
			.body("[0].content",equalTo("Buy Milk"))
//			.body("[0].project_id", equalTo(project_id))	
			.body("[1].content",equalTo("Buy Books"));
	}
	
	
	@Test
	public void testToCheckCreateTask() {
		
		JSONObject jsonObject = new JSONObject();
		
		String project_id = "2270742863";
		
		jsonObject.put("content","Go To Home");
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
		Assertions.assertEquals("Go To Home", resp.jsonPath().getString("content"));
		Assertions.assertEquals(project_id, resp.jsonPath().getString("project_id"));
	}
	
	@Test
	public void testToGetAnActiveTask() {
		
		String project_id = "2270742863";
		
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
		Assertions.assertEquals(project_id, resp.jsonPath().getString("project_id"));
		
	}
	
	@Test
	public void testToUpdateATask() {
		
		JSONObject jsonObject = new JSONObject();
		
		String project_id = "2270742863";
		
		jsonObject.put("content","Bring Back");
		jsonObject.put("due_string","Monday at 11:00");
		jsonObject.put("due_lang","en");
		jsonObject.put("priority",4);
		
		try
		{
			RestAssured
					.given()
						.header("Authorization","Bearer "+token)
						//.header("Content-Type","application/json")
						.contentType("application/json")
					.and()
						.contentType("application/json")
						.body(jsonObject.toString())
					.when()
						.post(url+"tasks/5081847734")
					.then()
						.assertThat()
						.statusCode(204)
						.body("[0].content",equalTo("Bring Back"));
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
				
		
		//Assertions.assertEquals(204, resp.statusCode());
		//Assertions.assertEquals("Bring Banana", resp.jsonPath().getString("content"));
		//Assertions.assertEquals(project_id, resp.jsonPath().getString("project_id"));
		//System.out.println(resp.jsonPath().getString("content"));
		
		
	}
	
	@Test
	public void testToCloseATask() {
		
		Response resp =  RestAssured
			.given()
				.header("Authorization","Bearer "+token)
				.header("Content-Type","application/json")
				.get(url+"tasks/5084623058")
			.then()
				.extract()
				.response();
		
		String status = resp.jsonPath().getString("completed");
		String id = resp.jsonPath().getString("id");
		
		if(status.equals("false"))
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
		
		Response resp =  RestAssured
			.given()
				.header("Authorization","Bearer "+token)
				.header("Content-Type","application/json")
			.when()
				.post(url+"tasks/5084623058/reopen")
			.then()
				.extract()
				.response();
		
		Assertions.assertEquals(204, resp.statusCode());
		//Assertions.assertEquals("Go To Library", resp.jsonPath().getString("content"));
		Assertions.assertEquals("false", resp.jsonPath().getString("completed"));
		
	}
	
	
	@Test
	public void testToDeleteATask() {
		
		RestAssured
			.given()
				.header("Authorization","Bearer "+token)
				.header("Content-Type","application/json")
				.delete(url+"tasks/5051347544")
			.then()
				.statusCode(204)
				.log()
				.all();
		
	}
	

}
