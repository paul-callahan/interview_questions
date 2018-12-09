package com.redacted.answers.dummy;

import com.redacted.model.Employee;
import com.redacted.model.TimeKeeper;

import java.util.Date;
import java.util.List;

/**
 * Created by kumar on 9/7/16.
 */
public class TimeKeeperImpl implements TimeKeeper{
    public void employeeEntered(Date entryTime, Employee entryEmployee) {

    }

    public void employeeExited(Date exitTime, Employee exitEmployee) {

    }

    public boolean inBuilding(Date checkTime, Employee employee) {
        return false;
    }

    public List<Employee> getPageOfUsersInBuilding(Date checkTime, int pageOffset, int pageSize) {
        return null;
    }
}
