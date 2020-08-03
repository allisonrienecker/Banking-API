package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.dao.AccountDAO;
import com.revature.dao.IAccountDAO;
import com.revature.models.Account;
import com.revature.templates.SubmitAccountTemplate;
import com.revature.templates.UpdateAccountTemplate;

public class AccountService {
	
	private IAccountDAO dao = new AccountDAO();
	
	public int submit(Account a) {
		return dao.submit(a);
	}
	
	public int submitAccount(SubmitAccountTemplate sat) {
		return dao.submitAccount(sat);
	}
	
	public List<Account> findAll() {
		return dao.findAll();
	}
	
	public Account findById(int accountId) {
		return dao.findById(accountId);
	}
	
	public List<Account> findByStatus(int statusId) {
		return dao.findByStatus(statusId);
	}
	
	public List<Account> findByType(int typeId) {
		return dao.findByType(typeId);
	}
	
	public List<Account> findByUser(int userId) {
		return dao.findByUser(userId);
	}
	
	public int findUserByAccountNumber(int accountId) {
		return dao.findUserByAccountNumber(accountId);
	}
	
	public int update(UpdateAccountTemplate uat) {
		return dao.update(uat);
	}
	
	public Account withdraw(int accountId, double amount) {
		Account currentAccount = dao.findById(accountId);
		double currentBalance = currentAccount.getBalance();
		
		if (currentBalance - amount < 0) {
			throw new IllegalArgumentException();
		}
		dao.updateBalance(accountId, (currentBalance - amount));
		return dao.findById(accountId);
	}
		
	public Account deposit(int accountId, double amount) {
		Account currentAccount = dao.findById(accountId);
		double currentBalance = currentAccount.getBalance();
		
		dao.updateBalance(accountId, (currentBalance + amount));
		return dao.findById(accountId);
	}	
	
	public List<Account> transfer(int sourceAccountId, int targetAccountId, double amount){
		List<Account> updatedAccounts = new ArrayList<>();
		
		Account sourceAccount = dao.findById(sourceAccountId);
		double sourceAccountBalance = sourceAccount.getBalance();
		Account targetAccount = dao.findById(targetAccountId);
		double targetAccountBalance = targetAccount.getBalance();
		
		if (sourceAccountBalance < amount) {
			throw new IllegalArgumentException();
		}
		dao.updateBalance(sourceAccountId, (sourceAccountBalance - amount));
		dao.updateBalance(targetAccountId, (targetAccountBalance + amount));
		updatedAccounts.add(findById(sourceAccountId));
		updatedAccounts.add(findById(targetAccountId));
		return updatedAccounts;
	}
	
	public List<Account> passtime(int numOfMonths){
		List<Account> allSavingsAccounts = dao.findByType(2);
		double monthlyInterestRate = 0.0115/12;
		for (Account savingsAccount : allSavingsAccounts) {
			double balanceWithInterestAdded = savingsAccount.getBalance()*Math.pow((1+monthlyInterestRate), numOfMonths);
			//interest compounded monthly
			int savingsAccountId = savingsAccount.getAccountId();
			dao.updateBalance(savingsAccountId, balanceWithInterestAdded);
		}
		return dao.findByType(2);
	}

	
	
}
