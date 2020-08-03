package com.revature.templates;

public class PassTimeTemplate {
	private int numOfMonths;

	public PassTimeTemplate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PassTimeTemplate(int numOfMonths) {
		super();
		this.numOfMonths = numOfMonths;
	}

	public int getNumOfMonths() {
		return numOfMonths;
	}

	public void setNumOfMonths(int numOfMonths) {
		this.numOfMonths = numOfMonths;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numOfMonths;
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
		PassTimeTemplate other = (PassTimeTemplate) obj;
		if (numOfMonths != other.numOfMonths)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "passTimeTemplate [numOfMonths=" + numOfMonths + "]";
	}
	
	
}
