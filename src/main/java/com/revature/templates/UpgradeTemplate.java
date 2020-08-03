package com.revature.templates;

public class UpgradeTemplate {

	int userId;
	int paymentAccountId;
	
	public UpgradeTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpgradeTemplate(int userId, int paymentAccountId) {
		super();
		this.userId = userId;
		this.paymentAccountId = paymentAccountId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getPaymentAccountId() {
		return paymentAccountId;
	}

	public void setPaymentAccountId(int paymentAccountId) {
		this.paymentAccountId = paymentAccountId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + paymentAccountId;
		result = prime * result + userId;
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
		UpgradeTemplate other = (UpgradeTemplate) obj;
		if (paymentAccountId != other.paymentAccountId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UpgradeTemplate [userId=" + userId + ", paymentAccountId=" + paymentAccountId + "]";
	}
	
	
	
}
