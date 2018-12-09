package com.redacted.model;

import java.util.Date;

/**
 * Created by kumar on 8/10/16.
 */
public class EnterExitRecord {
	private final Employee employee;
	private final Date entry, exit;

	public EnterExitRecord(Employee employee, Date entry, Date exit) {
		this.employee = employee;
		this.entry = entry;
		this.exit = exit;
	}

	public Employee getEmployee() {
		return employee;
	}

	public Date getEntry() {
		return entry;
	}

	public Date getExit() {
		return exit;
	}

	public boolean inBuilding(Date checkTime) {
		return entry.getTime() <= checkTime.getTime() && exit.getTime() >= checkTime.getTime();
	}
}
