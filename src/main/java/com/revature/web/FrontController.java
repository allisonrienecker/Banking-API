package com.revature.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.authorization.AuthService;
import com.revature.controllers.AccountController;
import com.revature.controllers.UserController;
import com.revature.exceptions.AuthorizationException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.UnsupportedEnpointException;
import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.UserService;
import com.revature.templates.AccountTransactionTemplate;
import com.revature.templates.AddUserTemplate;
import com.revature.templates.LoginTemplate;
import com.revature.templates.MessageTemplate;
import com.revature.templates.PassTimeTemplate;
import com.revature.templates.SubmitAccountTemplate;
import com.revature.templates.TransferTemplate;
import com.revature.templates.UpdateAccountTemplate;
import com.revature.templates.UpdateUserTemplate;
import com.revature.templates.UpgradeTemplate;

public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 3970239174131171157L;
	private static final UserController userController = new UserController();
	private static final UserService service = new UserService();
	private static final AccountController accountController = new AccountController();
	private static final ObjectMapper om = new ObjectMapper(); //Jackson
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		res.setContentType("application/json");
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		String[] portions = URI.split("/");
		res.setStatus(400);
		try {
			switch(portions[0]) {
			
			case "users":
				if(portions.length == 2) { 
					//find user by id
					int id = Integer.parseInt(portions[1]);
					AuthService.guard(req.getSession(false), id, "Employee", "Admin");
					User u = userController.findById(id);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(u));
				} else if (portions.length == 1) {
					//find all users
					AuthService.guard(req.getSession(false), "Employee", "Admin");
					List<User> all = userController.findAll();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(all));
				}
				break;
				
			case "accounts":
				if(portions.length == 1) {
					//find all accounts
					AuthService.guard(req.getSession(false), "Employee", "Admin");
					List<Account> all = accountController.findAll();
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(all));
				
				} else if (portions.length == 2) {
					//find accounts by id
					int accountId = Integer.parseInt(portions[1]);
					int userId = accountController.findUserByAccountNumber(accountId);
					AuthService.guard(req.getSession(false), userId, "Employee", "Admin");
					Account a = accountController.findById(accountId);
					res.setStatus(200);
					res.getWriter().println(om.writeValueAsString(a));
					
				} else if (portions.length ==3 ) {
					switch(portions[1]) {
						case "status":
						//find accounts by status
						int statusId = Integer.parseInt(portions[2]);
						AuthService.guard(req.getSession(false), "Employee", "Admin");
						List<Account> statusAccounts = accountController.findByStatus(statusId);
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(statusAccounts));
						break;
					
						case "owner":
						//find accounts by user
						int userId = Integer.parseInt(portions[2]);
						AuthService.guard(req.getSession(false), userId, "Employee", "Admin");
						List<Account> userAccounts = accountController.findByUser(userId);
						res.setStatus(200);
						res.getWriter().println(om.writeValueAsString(userAccounts));
						break;
					}
				}
			}	
				
		} catch(AuthorizationException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
			}
		}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		res.setContentType("application/json");
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		String[] portions = URI.split("/");
		
		try {
			switch(portions[0]) {

			case "logout":
				if(userController.logout(req.getSession(false))) {
					res.setStatus(200);
					res.getWriter().println("You have been successfully logged out");
				} else {
					res.setStatus(400);
					res.getWriter().println("You were not logged in to begin with");
				}
				break;
			
			case "accounts":
				if (portions.length == 1) {
					//submit account
					SubmitAccountTemplate sat = om.readValue(req.getReader(), SubmitAccountTemplate.class);
					AuthService.guard(req.getSession(false), sat.getUserId(), "Admin");
					accountController.submitAccount(sat);
					Account newAccount = accountController.findById(0);
					res.setStatus(201);
					res.getWriter().println(om.writeValueAsString(newAccount));

				} else {
					switch(portions[1]) {
					case "withdraw":
						AccountTransactionTemplate attWithdraw = om.readValue(req.getReader(), AccountTransactionTemplate.class);
						int withdrawUserId = accountController.findUserByAccountNumber(attWithdraw.getAccountId());
						AuthService.guard(req.getSession(false), withdrawUserId, "Admin");
						accountController.withdraw(attWithdraw.getAccountId(), attWithdraw.getAmount());
						res.setStatus(200);
						MessageTemplate messageWithdraw = new MessageTemplate("$" + attWithdraw.getAmount() + " has been withdrawn from Account #" + attWithdraw.getAccountId());
						res.getWriter().println(om.writeValueAsString(messageWithdraw));
						break;
					case "deposit":
						AccountTransactionTemplate attDeposit = om.readValue(req.getReader(), AccountTransactionTemplate.class);
						int depositUserId = accountController.findUserByAccountNumber(attDeposit.getAccountId());
						AuthService.guard(req.getSession(false), depositUserId, "Admin");
						accountController.deposit(attDeposit.getAccountId(), attDeposit.getAmount());
						res.setStatus(200);
						MessageTemplate messageDeposit = new MessageTemplate("$" + attDeposit.getAmount() + " has been deposited into Account #" + attDeposit.getAccountId());
						res.getWriter().println(om.writeValueAsString(messageDeposit));
						break;
					case "transfer":
						TransferTemplate tt = om.readValue(req.getReader(), TransferTemplate.class);
						int transferUserId = accountController.findUserByAccountNumber(tt.getSourceAccountId());
						AuthService.guard(req.getSession(false), transferUserId, "Admin");
						accountController.transfer(tt.getSourceAccountId(), tt.getTargetAccountId(), tt.getAmount());
						res.setStatus(200);
						MessageTemplate messageTransfer = new MessageTemplate("$" + tt.getAmount() + 
								" has been transferred from Account #" + tt.getSourceAccountId() + " to Account #" + tt.getTargetAccountId());
						res.getWriter().println(om.writeValueAsString(messageTransfer));
						break;
					}
				}
			case "passTime":
				PassTimeTemplate ptt = om.readValue(req.getReader(), PassTimeTemplate.class);
				AuthService.guard(req.getSession(false), "Admin");
				accountController.passtime(ptt.getNumOfMonths());
				res.setStatus(200);
				MessageTemplate messagePassTime = new MessageTemplate(ptt.getNumOfMonths() + " months of interest has been accrued for all Savings Accounts");
				res.getWriter().println(om.writeValueAsString(messagePassTime));
				break;
			
			case "upgrade":
				UpgradeTemplate ugt = om.readValue(req.getReader(), UpgradeTemplate.class);
				User upgradeUser = userController.findById(ugt.getUserId());
				int currentRole = upgradeUser.getRole().getId();
				if (currentRole != 1) {
					res.setStatus(400);
					MessageTemplate messagePremium = new MessageTemplate("You are already a Premium User.");
					res.getWriter().println(om.writeValueAsString(messagePremium));
					break;
				} else {
				accountController.withdraw(ugt.getPaymentAccountId(), 59); //cost $59 one time fee to upgrade
				userController.upgradeToPremium(ugt);
				res.setStatus(200);
				MessageTemplate messageUpgrade = new MessageTemplate("You were upgraded to Premium for $59.00.");
				res.getWriter().println(om.writeValueAsString(messageUpgrade));
				}
				break;
			case "addUser":
				AddUserTemplate aut = om.readValue(req.getReader(), AddUserTemplate.class);
				int requestingUserId = accountController.findUserByAccountNumber(aut.getAccountId());
				User requestingUser = userController.findById(requestingUserId);
				HttpSession session = req.getSession();	
				User loggedInUser = (User) session.getAttribute("currentUser");
				
				int requestingUserRole = requestingUser.getRole().getId();
				List<Account> requestingUserAccounts = accountController.findByUser(requestingUserId);
				if (requestingUserRole == 1) {
					res.setStatus(401);
					MessageTemplate messageNotPremium = new MessageTemplate("You must be a Premium User to add another User to your account.");
					res.getWriter().println(om.writeValueAsString(messageNotPremium));
					break;
				} else if (requestingUserRole == 2 && requestingUser.equals(loggedInUser)) {
					for (Account accounts : requestingUserAccounts) {
					if (accounts.getAccountId() == aut.getAccountId()) {
						userController.addUserToAccount(aut);
						res.setStatus(200);
						MessageTemplate messageAddUser = new MessageTemplate("User ID #" + aut.getUserToAddId() + " has been added to Account ID #" + aut.getAccountId());
						res.getWriter().println(om.writeValueAsString(messageAddUser));
						break;
						} 
					} 
					}else if (requestingUserRole ==2 && requestingUser.equals(loggedInUser)==false) {
						res.setStatus(401);
						MessageTemplate messageNotAuthorized = new MessageTemplate("You are not authorized to add another User to someone else's account.");
						res.getWriter().println(om.writeValueAsString(messageNotAuthorized));
						break;	
					} else {
					userController.addUserToAccount(aut);
					res.setStatus(200);
					MessageTemplate messageAddUser = new MessageTemplate("User ID #" + aut.getUserToAddId() + " has been added to Account ID #" + aut.getAccountId());
					res.getWriter().println(om.writeValueAsString(messageAddUser));
					break;
				}
		}
		} catch(NotLoggedInException e) {
			res.setStatus(401);
			MessageTemplate message = new MessageTemplate("The incoming token has expired");
			res.getWriter().println(om.writeValueAsString(message));
			}
		}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
		res.setContentType("application/json");
		final String URI = req.getRequestURI().replace("/rocp-project", "").replaceFirst("/", "");
		String[] portions = URI.split("/");
		
		try {
			switch(portions[0]) {
			case "users":
				UpdateUserTemplate ut = om.readValue(req.getReader(), UpdateUserTemplate.class);
				AuthService.guard(req.getSession(false), ut.getId(), "Admin");
				userController.update(ut);
				res.setStatus(200);
				res.getWriter().println(om.writeValueAsString(userController.findById(ut.getId())));
				break;
			
			case "accounts":
				UpdateAccountTemplate uat = om.readValue(req.getReader(), UpdateAccountTemplate.class);
				AuthService.guard(req.getSession(false), "Admin");
				accountController.update(uat);
				res.setStatus(200);
				res.getWriter().println(om.writeValueAsString(accountController.findById(uat.getId())));
				break;
			}
			
	
		} catch (AuthorizationException e) {
			res.setStatus(400);
		}
	}	
}
