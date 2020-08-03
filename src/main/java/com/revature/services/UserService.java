package com.revature.services;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.exceptions.NotLoggedInException;
import com.revature.models.User;
import com.revature.templates.AddUserTemplate;
import com.revature.templates.LoginTemplate;
import com.revature.templates.UpdateUserTemplate;
import com.revature.templates.UpgradeTemplate;

public class UserService {
	private IUserDAO dao = new UserDAO();
	
	//A recommendation from Matt is to create CRUD methods in the service layer
	//that will be used to interact with the DAO
	
	public int insert(User u) {
		return dao.insert(u);
	}
	
	public List<User> findAll() {
		return dao.findAll();
	}
	
	public User findById(int id) {
		return dao.findById(id);
	}
	
	public User findByUsername(String username) {
		return dao.findByUsername(username);
	}
	
	public int update(UpdateUserTemplate ut) {
		return dao.update(ut);
	}
	
	
	//Then additionally, can have extra methods to enforce whatever features/rules you want
	//For example, might also have a login/logout method
	
	public User login(LoginTemplate lt) {
		
		//Username was incorrect
		User userFromDB = findByUsername(lt.getUsername());
		if(userFromDB == null) {
			return null;
		}
		
		//Username was correct and so was password
		if(userFromDB.getPassword().equals(lt.getPassword())) {
			return userFromDB;
		}
		
		//Username was correct, but password was not
		return null;
	}
	
	public void logout(HttpSession session) {
		//never logged in to begin with
		if(session == null || session.getAttribute("currentUser")==null) {
			throw new NotLoggedInException("User must be logged in, in order to logout.");
		}
		
		session.invalidate();
	}
	
	public int upgradeToPremium(UpgradeTemplate ugt) {
		return dao.upgradeToPremium(ugt);
	}
	
	public int addUserToAccount(AddUserTemplate aut) {
		return dao.addUserToAccount(aut);
	}
}
