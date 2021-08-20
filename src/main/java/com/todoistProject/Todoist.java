package com.todoistProject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.net.HttpURLConnection;
import org.json.*;

public class Todoist {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//Scanner scan = new Scanner(System.in);
		String token = "a8c484f6ab78bb5d8943ed56791c4ce57becff5e";
		String address = "https://api.todoist.com/rest/v1/";
		try
		{
			JSONObject data = new JSONObject();
//			System.out.println("Enter a task : ");
//			String content = scan.nextLine();
//			System.out.println("Enter the task timing: ");
//			String taskTime = scan.nextLine();
			data.put("content","Buy Vegetables");
			data.put("due_string","tomorrow at 11:00");
			data.put("due_lang","en");
			data.put("priority",3);
			URL url = new URL(address+"tasks");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Authorization","Bearer "+token);
			con.setRequestProperty("Content-Type","application/json;charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(data.toString());
			wr.flush();
			//System.out.println(con.getResponseCode());
			if(con.getResponseCode() != 200)
			{
				throw new RuntimeException("FAILED : HTTP RESPONSE CODE :" +con.getResponseCode());
			}
			
			
			
			
			InputStreamReader input = new InputStreamReader(con.getInputStream());
			BufferedReader br = new BufferedReader(input);
			
			String output = "";
			
			while((output = br.readLine()) != null)
			{
				System.out.println(output);
				//System.out.println(output.getClass().getSimpleName());
			}
			
			br.close();
			con.disconnect();
			
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
		// to fetch tasks data from api
//		try
//		{
//			URL url = new URL(address+"tasks");
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setRequestProperty("Authorization","Bearer "+token);
//			con.setRequestProperty("Content-Type","application/json");
//			con.setRequestMethod("GET");
//			if(con.getResponseCode() != 200)
//			{
//				throw new RuntimeException("FAILED : HTTP RESPONSE CODE :" +con.getResponseCode());
//			}
//			
//			InputStreamReader input = new InputStreamReader(con.getInputStream());
//			BufferedReader br = new BufferedReader(input);
//			
//			String output = "";
//			
//			while((output = br.readLine()) != null)
//			{
//				System.out.println(output);
//				//System.out.println(output.getClass().getSimpleName());
//			}
//			
//			br.close();
//			con.disconnect();
//			
//		}
//		catch(MalformedURLException e)
//		{
//			e.printStackTrace();
//		}
//		catch(IOException e)
//		{
//			e.printStackTrace();
//		}
		
		
		// to fetch project data from api
//		try
//		{
//			URL url = new URL(address+"projects");
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			con.setRequestProperty("Authorization","Bearer "+token);
//			con.setRequestProperty("Content-Type","application/json");
//			con.setRequestMethod("GET");
//			if(con.getResponseCode() != 200)
//			{
//				throw new RuntimeException("FAILED : HTTP RESPONSE CODE :" +con.getResponseCode());
//			}
//			
//			InputStreamReader input = new InputStreamReader(con.getInputStream());
//			BufferedReader br = new BufferedReader(input);
//			
//			String output = "";
//			
//			while((output = br.readLine()) != null)
//			{
//				System.out.println(output);
//				//System.out.println(output.getClass().getSimpleName());
//			}
//			
//			br.close();
//			con.disconnect();
//			
//		}
//		catch(MalformedURLException e)
//		{
//			e.printStackTrace();
//		}
//		catch(IOException e)
//		{
//			e.printStackTrace();
//		}

	}

}
