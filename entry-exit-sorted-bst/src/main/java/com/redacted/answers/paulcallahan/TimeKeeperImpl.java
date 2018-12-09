package com.redacted.answers.paulcallahan;

import com.redacted.model.Employee;
import com.redacted.model.TimeKeeper;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Test solution
 * Created by paul on 11/8/16.
 */
public class TimeKeeperImpl implements TimeKeeper {

    private final Map<Employee, Temp> entryHolder = new HashMap<>();
    private final Map<Employee, List<EnterExit>> employeeIndex = new HashMap<>();
    private final AugmentedBST intervalSearchTree = new AugmentedBST();


    private static class Temp {
        final Employee employee;
        final Date entry;

        Temp(Employee employee, Date entry) {
            this.employee = employee;
            this.entry = entry;
        }
    }


    @Override
    public void employeeEntered(Date entryTime, Employee entryEmployee) {
        Temp temp = new Temp(entryEmployee, entryTime);
        entryHolder.put(entryEmployee, temp);
    }

    @Override
    public void employeeExited(Date exitTime, Employee employee) {
        Temp temp = entryHolder.remove(employee);
        if (temp == null) {
            throw new RuntimeException("exit without corresponding entry!");
        }
        EnterExit ee = new EnterExit(temp.entry.getTime(), exitTime.getTime());
        intervalSearchTree.add(ee, employee);
        employeeIndex.computeIfAbsent(employee, emp -> new LinkedList<>()).add(ee);
    }


    @Override
    public boolean inBuilding(Date checkTime, Employee employee) {
        List<EnterExit> enterExits = employeeIndex.get(employee);
        if (enterExits == null) {
            return false;
        }
        long ct = checkTime.getTime();
        for (EnterExit ee : enterExits) {
            if (ee.contains(ct))
                return true;
        }
        return false;
    }

    @Override
    public List<Employee> getPageOfUsersInBuilding(Date checkTime, int pageOffset, int pageSize) {
        return intervalSearchTree.search(checkTime, pageOffset, pageSize);
    }

}
