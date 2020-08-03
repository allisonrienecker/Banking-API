package com.revature.dao;

import java.util.List;

import com.revature.models.User;
import com.revature.templates.AddUserTemplate;
import com.revature.templates.UpdateUserTemplate;
import com.revature.templates.UpgradeTemplate;

public interface IUserDAO {
	
	public int insert(User u); //CREATE
	
	public List<User> findAll(); //READ
	public User findById(int id);
	public User findByUsername(String username);
	
	public int update(UpdateUserTemplate ut); //UPDATE

	public int upgradeToPremium(UpgradeTemplate ugt);

	int addUserToAccount(AddUserTemplate aut);
	
}
