package com.redacted;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import com.redacted.answers.paulcallahan.TimeKeeperImpl;
import com.redacted.model.Employee;
import com.redacted.model.EnterExitRecord;
import com.redacted.model.TimeKeeper;
import org.junit.Assert;
import org.junit.Test;

public class TimeKeeperTests {
	
	SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyyy HH:mm:ss");

	// replace this will an actual time keeper
	private TimeKeeper createTimeKeeper() {
		return new TimeKeeperImpl();
	}


	ArrayList<Employee> generate100xEmployees(int x) {
		ArrayList<Employee> returnList = new ArrayList<Employee>();
		String [] firstNames = {"Adam", "Jose", "Kumar", "Dave", "Susan", "Yuan", "Karen", "Max", "Amit", "Darryl"};
		String [] lastNames = {"Munez", "Draper", "Saurabh", "Smith", "Kwong", "Tom", "Jones", "Winslow", "Barry", "Monica"};
		
		for (int passNum = 0; passNum < x; passNum++) {
			for (String curFirstName:firstNames) {
				for (String curLastName:lastNames) {
					returnList.add(new Employee(curFirstName+" "+passNum+" "+curLastName));
				}
			}
		}
		return returnList;
	}


	@Test
	public void bigTest() throws Exception {
		long seed = System.currentTimeMillis();
		System.out.println("Seed = "+seed);
		Random randomGenerator = new Random(seed);	// Use the same seed for repeatability
		TimeKeeper testTimeKeeper = createTimeKeeper();

		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		System.out.println(dateFormat.format(new Date()));

		// Generate 5 million records - 1000 employees entering/exiting across 5000 days
		int [] runs = new int[]{1, 10, 100, 300, 1000, 3000};
		for (int kk=0; kk < runs.length; kk++) {
			int run = runs[kk];
			ArrayList<Employee> employees = generate100xEmployees(run);
			ArrayList<EnterExitRecord> records = new ArrayList<EnterExitRecord>();
			Date startDate = formatter.parse("8/11/2016 00:00:00");
			Date curDate = new Date(startDate.getTime());
			for (Employee curEmployee:employees) {
				EnterExitRecord curRecord = generateEntryExitRecord(randomGenerator, curDate, curEmployee);
				testTimeKeeper.employeeEntered(curRecord.getEntry(), curRecord.getEmployee());
				testTimeKeeper.employeeExited(curRecord.getExit(), curRecord.getEmployee());
				records.add(curRecord);
			}

			System.out.println(dateFormat.format(new Date()));

			long startTime = System.currentTimeMillis();
			for(int i=0; i < 1000; i++){
                int index = Math.abs(randomGenerator.nextInt()) % records.size();
                EnterExitRecord checkRecord = records.get(index);
                Assert.assertTrue(testTimeKeeper.inBuilding(checkRecord.getEntry(), checkRecord.getEmployee()));
            }
			long endTime = System.currentTimeMillis();
			long elapsed = endTime - startTime;
			double opsPerSecond =  elapsed == 0 ? Double.POSITIVE_INFINITY : 1000 * 1000 / elapsed;
			System.out.println(dateFormat.format(new Date()));
			System.out.println("records.size=" + records.size() + " opsPerSecond = "+ opsPerSecond + " total elapsed MS = "+elapsed);
		}
	}

	@Test
	public void pagingTest() throws Exception {
		long seed = System.currentTimeMillis();
		System.out.println("Seed = "+seed);
		Random randomGenerator = new Random(seed);	// Use the same seed for repeatability
		TimeKeeper testTimeKeeper = createTimeKeeper();

		SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
		System.out.println(dateFormat.format(new Date()));

		// Generate 5 million records - 1000 employees entering/exiting across 5000 days
		int [] runs = new int[]{1, 10, 100, 300, 1000, 3000};
		for (int kk=0; kk < runs.length; kk++) {
			int run = runs[kk];
			ArrayList<Employee> employees = generate100xEmployees(run);
			ArrayList<EnterExitRecord> records = new ArrayList<EnterExitRecord>();
			Date startDate = formatter.parse("8/11/2016 00:00:00");
			Date curDate = new Date(startDate.getTime());
			for (Employee curEmployee:employees) {
				EnterExitRecord curRecord = generateEntryExitRecord(randomGenerator, curDate, curEmployee);
				testTimeKeeper.employeeEntered(curRecord.getEntry(), curRecord.getEmployee());
				testTimeKeeper.employeeExited(curRecord.getExit(), curRecord.getEmployee());
				records.add(curRecord);
			}

			System.out.println(dateFormat.format(new Date()));

			long startTime = System.currentTimeMillis();
			for(int i=0; i < 1000; i++){
				int index = Math.abs(randomGenerator.nextInt()) % records.size();
				EnterExitRecord checkRecord = records.get(index);
				testTimeKeeper.getPageOfUsersInBuilding(checkRecord.getEntry(), 0, 20);
            }

			long endTime = System.currentTimeMillis();
			long elapsed = Math.max(1, endTime - startTime);
			double opsPerSecond =  1000 * 1000 / elapsed;
			System.out.println(dateFormat.format(new Date()));
			System.out.println("records.size=" + records.size() + " opsPerSecond = "+ opsPerSecond + " total elapsed MS = "+elapsed);
		}
	}

	private EnterExitRecord generateEntryExitRecord(Random randomGenerator, Date curDate, Employee curEmployee) {
		long millisInADay = 1000L * 3600 * 24L;

		long startOffset = Math.abs(randomGenerator.nextLong()) % millisInADay;
		long remainingMillis = millisInADay - startOffset;
		long endOffset = startOffset + Math.abs(randomGenerator.nextLong())% remainingMillis;
		if (endOffset - startOffset < 1) {
            if (endOffset < 24 * 60 * 60 * 1000)	// Make sure we don't edge into the following day
                endOffset++;
            else
                startOffset--;
        }

		Date startTime = new Date(curDate.getTime() + startOffset);
		Date endTime = new Date(curDate.getTime() + endOffset);


		return new EnterExitRecord(curEmployee, startTime, endTime);
	}
}
