package com.revature.dao;

import java.util.List;

import com.revature.models.Account;
import com.revature.templates.SubmitAccountTemplate;
import com.revature.templates.UpdateAccountTemplate;

public interface IAccountDAO {
	
	public int submit(Account a); //CREATE
	public int submitAccount(SubmitAccountTemplate sat);
	public List<Account> findAll(); //READ
	public Account findById(int accountId);
	public List<Account> findByStatus(int status);
	public List<Account> findByType(int type);
	public List<Account> findByUser(int userId);
	
	public int update(UpdateAccountTemplate uat); //UPDATE

	int updateBalance(int accountId, double newBalance);
	int findUserByAccountNumber(int accountId);


	
	


}
