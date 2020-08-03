package com.revature.templates;

public class SubmitAccountTemplate {
	private int userId;
	private int typeId;
	
	public SubmitAccountTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SubmitAccountTemplate(int userId, int typeId) {
		super();
		this.userId = userId;
		this.typeId = typeId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + typeId;
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
		SubmitAccountTemplate other = (SubmitAccountTemplate) obj;
		if (typeId != other.typeId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SubmitAccountTemplate [userId=" + userId + ", typeId=" + typeId + "]";
	}
	
	
	
}
