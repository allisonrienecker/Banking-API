package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.templates.LoginTemplate;

public class LoginServlet extends HttpServlet {
	private static final ObjectMapper om = new ObjectMapper();
	private static final UserService service = new UserService();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
			
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws ServletException, IOException {
		
		HttpSession session = req.getSession();	
		
		User current = (User) session.getAttribute("currentUser");
		
		//already logged in
		if(current != null) {
			res.setStatus(400);
			res.getWriter().println("You are already logged in as user " + current.getUsername());
			return;
		}
		
		BufferedReader reader = req.getReader();
		
		StringBuilder sb = new StringBuilder();
		
		String line;
		
		while( (line = reader.readLine()) != null ) {
			sb.append(line);
		}
		//(line = reader.readLine()) obtains the value for a single line
		//from the body of the request and stores it into the line variable
		// != null compares the value of the string to null
		//if the readLine() method is null, that means we are at the end
		
		String body = sb.toString();
		
		LoginTemplate lt = om.readValue(body, LoginTemplate.class);
		
		//LoginTemplate lt = om.readValue(req.getReader(), LoginTemplate.class);
		//instead of doing the while loop above
		
		User u = service.login(lt);
		PrintWriter writer = res.getWriter();
		
		if(u==null) {
			//login failed
			res.setStatus(400);
			
			writer.println("Username or password was incorrect");
			return;
		}
		
		//login successful
		

		
		session.setAttribute("currentUser", u);
		
		res.setStatus(200);
		
		writer.println(om.writeValueAsString(u));
		
		res.setContentType("application/json");
	}
}
