package com.revature.controllers;

import java.util.List;

import com.revature.models.Account;
import com.revature.services.AccountService;
import com.revature.templates.SubmitAccountTemplate;
import com.revature.templates.UpdateAccountTemplate;

public class AccountController {

	private final AccountService accountService = new AccountService();
	
	public int submit(Account a) {
		return accountService.submit(a);
	}
	
	public int submitAccount(SubmitAccountTemplate sat) {
		return accountService.submitAccount(sat);
	}
	
	public List<Account> findAll() {
		return accountService.findAll();
	}
	
	public Account findById(int accountId) {
		return accountService.findById(accountId);
	}
	
	public List<Account> findByStatus(int statusId) {
		return accountService.findByStatus(statusId);
	}
	
	public List<Account> findByType(int typeId) {
		return accountService.findByType(typeId);
	}
	
	public List<Account> findByUser(int userId) {
		return accountService.findByUser(userId);
	}
	
	public int findUserByAccountNumber(int accountId) {
		return accountService.findUserByAccountNumber(accountId);
	}
	
	public int update(UpdateAccountTemplate uat) {
		return accountService.update(uat);
	}
	
	public Account withdraw(int accountId, double amount) {
		return accountService.withdraw(accountId, amount);
	}
	
	public Account deposit(int accountId, double amount) {
		return accountService.deposit(accountId, amount);
	}
	
	public List<Account> transfer(int sourceAccountId, int targetAccountId, double amount){
		return accountService.transfer(sourceAccountId, targetAccountId, amount);
	}
	
	public List<Account> passtime(int numOfMonths){
		return accountService.passtime(numOfMonths);
	}
	
	
}
