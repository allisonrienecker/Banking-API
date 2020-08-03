package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.AccountStatus;
import com.revature.models.AccountType;
import com.revature.templates.SubmitAccountTemplate;
import com.revature.templates.UpdateAccountTemplate;
import com.revature.util.ConnectionUtil;

public class AccountDAO implements IAccountDAO {

	public AccountDAO() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int submit(Account a) {
		try (Connection conn = ConnectionUtil.getConnection()){
		String sql = "INSERT INTO ACCOUNTS (accountId, balance, status, type) VALUES (?,?,?,?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, a.getAccountId());
		stmt.setDouble(2, a.getBalance());
		stmt.setString(3, a.getStatus().getStatus());
		stmt.setString(4, a.getType().getType());
		
		return stmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int submitAccount(SubmitAccountTemplate sat) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "INSERT INTO ACCOUNTS (ID, BALANCE, STATUS_ID, TYPE_ID) VALUES (?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 0);
			stmt.setDouble(2, 0);
			stmt.setInt(3, 1);
			stmt.setInt(4, sat.getTypeId());
			
			return stmt.executeUpdate();
			
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
	}

	@Override
	public List<Account> findAll() {
		List<Account> allAccounts = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM ACCOUNTS "
					+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id "
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id";
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int accountId = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int statusId = rs.getInt("status_id");
				String statusName = rs.getString("status");
				int typeId = rs.getInt("type_id");
				String typeName = rs.getString("type");
				
				AccountStatus status = new AccountStatus(statusId, statusName);
				AccountType type = new AccountType(typeId, typeName);

				Account a = new Account(accountId, balance, status, type);
				allAccounts.add(a);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<>(); //if something goes wrong, return an empty list
		}
		return allAccounts;
	}

	@Override
	public Account findById(int accountId) {
		Account foundAccount = null;
		try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM ACCOUNTS "
            		+ "INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id "
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id "
					+ "WHERE ACCOUNTS.id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);
		
            ResultSet rs = stmt.executeQuery();
		
			while(rs.next()) {
				double balance = rs.getDouble("balance");
				int statusId = rs.getInt("status_id");
				String statusName = rs.getString("status");
				int typeId = rs.getInt("type_id");
				String typeName = rs.getString("type");
				
				AccountStatus status = new AccountStatus(statusId, statusName);
				AccountType type = new AccountType(typeId, typeName);
				foundAccount = new Account(accountId, balance, status, type);
            }
			
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
		return foundAccount;
	}

	@Override
	public List<Account> findByStatus(int statusId) {
		List<Account> statusAccounts = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM ACCOUNTS INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id "
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id WHERE ACCOUNT_STATUS.id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, statusId);
		
            ResultSet rs = stmt.executeQuery();
		
			while(rs.next()) {
				int accountId = rs.getInt("id");
				double balance = rs.getDouble("balance");
				String statusName = rs.getString("status");
				int typeId = rs.getInt("type_id");
				String typeName = rs.getString("type");
				
				AccountStatus status = new AccountStatus(statusId, statusName);
				AccountType type = new AccountType(typeId, typeName);
				Account a = new Account(accountId, balance, status, type);
				statusAccounts.add(a);
            }
			
        } catch(SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
		return statusAccounts;
	}
	
	@Override
	public List<Account> findByType(int typeId) {
		List<Account> typeAccounts = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM ACCOUNTS INNER JOIN ACCOUNT_STATUS ON ACCOUNTS.status_id = ACCOUNT_STATUS.id "
					+ "INNER JOIN ACCOUNT_TYPE ON ACCOUNTS.type_id = ACCOUNT_TYPE.id WHERE ACCOUNTS.type_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, typeId);
		
            ResultSet rs = stmt.executeQuery();
		
			while(rs.next()) {
				int accountId = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int statusId = rs.getInt("status_id");
				String statusName = rs.getString("status");
				String typeName = rs.getString("type");
				
				AccountStatus status = new AccountStatus(statusId, statusName);
				AccountType type = new AccountType(typeId, typeName);
				Account a = new Account(accountId, balance, status, type);
				typeAccounts.add(a);
            }
			
        } catch(SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
		return typeAccounts;
	}
	
	@Override
	public List<Account> findByUser(int userId) {
		List<Account> userAccounts = new ArrayList<>();
		List<Integer> userAccountNumbers = new ArrayList<>();
		
		try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM USERS_ACCOUNTS WHERE USER_ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
		
            ResultSet rs = stmt.executeQuery();
			while(rs.next()) {
				int accountId = rs.getInt("account_id");
				userAccountNumbers.add(accountId);
			}
			
			for (Integer accountId : userAccountNumbers) {
				Account a = findById(accountId);
				userAccounts.add(a);
            }
			
        } catch(SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
		
		return userAccounts;
	}

	@Override
	public int findUserByAccountNumber(int accountId) {
		int userId = 0;
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "SELECT * FROM USERS_ACCOUNTS WHERE ACCOUNT_ID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
			
            while(rs.next()) {
				userId = rs.getInt("user_id");
            }
			} catch(SQLException e) {
	            e.printStackTrace();
	        }
		return userId;
	}
	
	@Override
	public int update(UpdateAccountTemplate uat) {
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String sql = "UPDATE ACCOUNTS SET STATUS_ID = ?, TYPE_ID = ? WHERE ID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, uat.getStatusId());
			stmt.setInt(2, uat.getTypeId());
			stmt.setInt(3, uat.getId());
			
			return stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public int updateBalance(int accountId, double newBalance) {
		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "UPDATE ACCOUNTS SET BALANCE = ? WHERE ID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, newBalance);
			stmt.setInt(2, accountId);
			
			return stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}


}
