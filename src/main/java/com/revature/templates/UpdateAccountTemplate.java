package com.revature.templates;

public class UpdateAccountTemplate {
	private int id;
	private int statusId;
	private int typeId;
	
	public UpdateAccountTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UpdateAccountTemplate(int id, int statusId, int typeId) {
		super();
		this.id = id;
		this.statusId = statusId;
		this.typeId = typeId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
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
		result = prime * result + id;
		result = prime * result + statusId;
		result = prime * result + typeId;
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
		UpdateAccountTemplate other = (UpdateAccountTemplate) obj;
		if (id != other.id)
			return false;
		if (statusId != other.statusId)
			return false;
		if (typeId != other.typeId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UpdateAccountTemplate [id=" + id + ", statusId=" + statusId + ", typeId=" + typeId + "]";
	}
	
	
}
