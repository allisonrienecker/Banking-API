package com.revature.templates;

public class AddUserTemplate {

	int userToAddId;
	int accountId;
	public AddUserTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}
	public AddUserTemplate(int userToAddId, int accountId) {
		super();
		this.userToAddId = userToAddId;
		this.accountId = accountId;
	}
	public int getUserToAddId() {
		return userToAddId;
	}
	public void setUserToAddId(int userToAddId) {
		this.userToAddId = userToAddId;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + accountId;
		result = prime * result + userToAddId;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddUserTemplate other = (AddUserTemplate) obj;
		if (accountId != other.accountId)
			return false;
		if (userToAddId != other.userToAddId)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AddUserTemplate [userToAddId=" + userToAddId + ", accountId=" + accountId + "]";
	}
	
	
}
