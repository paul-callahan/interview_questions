package com.redacted.model;

import java.util.Date;
import java.util.List;

/**
 * Created by kumar on 8/10/16.
 */
public interface TimeKeeper {
    void employeeEntered(Date entryTime, Employee entryEmployee);

    void employeeExited(Date exitTime, Employee exitEmployee);

    boolean inBuilding(Date checkTime, Employee employee);

    /**
     * Returns a page of users who are in the building at any time. Since the number of people in the building
     * can be very large, we want to return one page at a time. Lets say the size of the page is 20, i.e. we show
     * 20 users per page, a user can ask for results that should go on third page - which would be all the users with
     * index (0-based) 60-79 (or 61-80, if you are using 1 based index)
     * @param checkTime Time at which the caller wants to know which employees are in the building.
     * @param pageOffset Offset index of the first element on this page.
     * @param pageSize No of max elements to shown on one page.
     * @return
     */
    List<Employee> getPageOfUsersInBuilding(Date checkTime,  int pageOffset, int pageSize);
}
