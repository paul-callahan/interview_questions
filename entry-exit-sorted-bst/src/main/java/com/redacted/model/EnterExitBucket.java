package com.redacted.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by kumar on 8/10/16.
 */
public class EnterExitBucket implements Iterable<EnterExitRecord>{
	private final Date bucketStart;
	private ArrayList<EnterExitRecord> entryRecords = new ArrayList<EnterExitRecord>();

	public EnterExitBucket(Date bucketStart) {
		this.bucketStart = bucketStart;
	}

	public Date getBucketStart() {
		return bucketStart;
	}

	public void add(EnterExitRecord addRecord) {
		entryRecords.add(addRecord);
	}

	public Iterator<EnterExitRecord> iterator() {
		return Collections.unmodifiableList(entryRecords).iterator();
	}
}
