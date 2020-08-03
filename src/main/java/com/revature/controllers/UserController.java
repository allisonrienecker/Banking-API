package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.templates.AddUserTemplate;
import com.revature.templates.LoginTemplate;
import com.revature.templates.UpdateUserTemplate;
import com.revature.templates.UpgradeTemplate;

public class UserController {
	
	private final UserService userService = new UserService();
	
	public int insert(User u) {
		return userService.insert(u);
	}
	
	public List<User> findAll() {
		return userService.findAll();	
	}
	
	public User findById(int id) {
		return userService.findById(id);
	}	
	
	public User findByUsername(String username) {
		return userService.findByUsername(username);
	}
	
	public int update(UpdateUserTemplate ut) {
		return userService.update(ut);
	}
	
	public int upgradeToPremium(UpgradeTemplate ugt) {
		return userService.upgradeToPremium(ugt);
	}
	
	public int addUserToAccount(AddUserTemplate aut) {
		return userService.addUserToAccount(aut);
	}
	
	public User login(LoginTemplate lt) {
		return userService.login(lt);
	}
	
	public boolean logout(HttpSession session) {
		try {
			userService.logout(session);
		} catch(NotLoggedInException e) {
			return false;
		}
		return true;
	}
	
	

}
