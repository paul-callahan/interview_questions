package com.redacted.model;

public class Employee implements Comparable<Employee>{
	private final String employeeName;
	
	public Employee(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public String toString() {
		return employeeName;
	}
	
	public String getEmployeeName() {
		return employeeName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((employeeName == null) ? 0 : employeeName.hashCode());
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
		Employee other = (Employee) obj;
		if (employeeName == null) {
			if (other.employeeName != null)
				return false;
		} else if (!employeeName.equals(other.employeeName))
			return false;
		return true;
	}

	@Override
	public int compareTo(Employee o) {
		if (o == null)
			throw new IllegalArgumentException("Can't compare null to an employee");
		return employeeName.compareTo(o.employeeName);
	}
}
