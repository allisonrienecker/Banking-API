package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.templates.AddUserTemplate;
import com.revature.templates.UpdateUserTemplate;
import com.revature.templates.UpgradeTemplate;
import com.revature.util.ConnectionUtil;

public class UserDAO implements IUserDAO {
	
	@Override
	public int insert(User u) {
		
		try (Connection conn = ConnectionUtil.getConnection()){
		String sql = "INSERT INTO USERS (username, password, first_name, last_name, email, role_id) VALUES (?,?,?,?,?,?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1,  u.getUsername());
		stmt.setString(2, u.getPassword());
		stmt.setString(3, u.getFirstName());
		stmt.setString(4, u.getLastName());
		stmt.setString(5, u.getEmail());
		stmt.setInt(6,  u.getRole().getId());
		
		return stmt.executeUpdate();
		
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	@Override
	public List<User> findAll() {
		List<User> allUsers = new ArrayList<>();

		try (Connection conn = ConnectionUtil.getConnection()){
			String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id";
			
			Statement stmt = conn.createStatement();
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstName = rs.getString("first_name");
				String lastName = rs.getString("last_name");
				String email = rs.getString("email");
				int roleId = rs.getInt("role_id");
				String roleName = rs.getString("role");
				
				Role role = new Role(roleId, roleName);
				User u = new User(id, username, password, firstName, lastName, email, role);
				
				allUsers.add(u);
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			return new ArrayList<>(); //if something goes wrong, return an empty list
		}
		return allUsers;
	}

	@Override
	public User findById(int id) {
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id WHERE USERS.id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                String username = rs.getString("username");
            	String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role");
                
                // And use that data to create a User object accordingly
                Role role = new Role(roleId, roleName);
                return new User(id, username, password, firstName, lastName, email, role);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
		return null;
	}

	@Override
	public User findByUsername(String username) {        
        try (Connection conn = ConnectionUtil.getConnection()) {
            String sql = "SELECT * FROM USERS INNER JOIN ROLES ON USERS.role_id = ROLES.id WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()) {
                int id = rs.getInt("id");
                String password = rs.getString("password");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                int roleId = rs.getInt("role_id");
                String roleName = rs.getString("role");
                
                // And use that data to create a User object accordingly
                Role role = new Role(roleId, roleName);
                return new User(id, username, password, firstName, lastName, email, role);
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return null;
	}

	@Override
	public int update(UpdateUserTemplate ut) {
		try (Connection conn = ConnectionUtil.getConnection()){
			
			String username = ut.getUsername();
			String password = ut.getPassword();
			String firstName = ut.getFirstName();
			String lastName = ut.getLastName();
			String email = ut.getEmail();
			int id = ut.getId();
			
			String sql = "UPDATE USERS SET USERNAME = ?, PASSWORD = ?, FIRST_NAME = ?, "
					+ "LAST_NAME = ?, EMAIL = ? WHERE ID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1,  username);
			stmt.setString(2, password);
			stmt.setString(3, firstName);
			stmt.setString(4, lastName);
			stmt.setString(5, email);
			stmt.setInt(6, id);
			
			return stmt.executeUpdate();
			
			} catch (SQLException e) {
				e.printStackTrace();
				return 0;
			}
	}
	
	@Override
	public int upgradeToPremium(UpgradeTemplate ugt) {
		try (Connection conn = ConnectionUtil.getConnection()){

			String sql = "UPDATE USERS SET ROLE_ID = ? WHERE ID = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, 2);
			stmt.setInt(2, ugt.getUserId());
			
			return stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public int addUserToAccount(AddUserTemplate aut) {
		try (Connection conn = ConnectionUtil.getConnection()){

			String sql = "INSERT INTO USERS_ACCOUNTS (USER_ID, ACCOUNT_ID) VALUES (?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, aut.getUserToAddId() );
			stmt.setInt(2, aut.getAccountId() );
			
			return stmt.executeUpdate();
			
		} catch(SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	

}
