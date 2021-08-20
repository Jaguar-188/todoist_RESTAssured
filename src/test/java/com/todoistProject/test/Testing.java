package com.todoistProject.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class Testing {
	
	
	@Test
	public void testToFetchData() {
		
		String token = "a8c484f6ab78bb5d8943ed56791c4ce57becff5e";
		
		Response resp = RestAssured.given().header("Authorization","Bearer "+token).get("https://api.todoist.com/rest/v1/projects");
		
		System.out.println(resp.getStatusCode());
		System.out.println(resp.getTime());
		System.out.println(resp.getBody().asString());

		Assert.assertEquals(resp.getStatusCode(), 200);
	}

}
