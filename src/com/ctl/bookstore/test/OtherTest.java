package com.ctl.bookstore.test;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Date;

import org.junit.Test;

public class OtherTest {

	@Test
	public void test() {
		String iStr = "50.2";
		String string = null;
		float i = 1;
		try {
			i = Float.parseFloat(string);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		System.out.println("123");
	}
	
	@Test
	public void test2() {
//		System.out.println(new Date().getTime());
		DateFormat df = DateFormat.getDateTimeInstance();
		String date = df.format(new Timestamp(new Date().getTime()));
//		System.out.println(new Timestamp(new Date().getTime()));
		System.out.println(date);
	}

}
