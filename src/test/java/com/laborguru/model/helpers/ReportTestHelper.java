package com.laborguru.model.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.laborguru.model.Store;

public class ReportTestHelper {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public static Store getStore() {
		Store store = new Store();
		store.setId(1);
		return store;
	}
	
	public static Date getStartDate() throws ParseException {
		return sdf.parse("2008-11-30");
	}
	
	public static Date getEndDate() throws ParseException {
		return sdf.parse("2008-12-06");
	}
}
