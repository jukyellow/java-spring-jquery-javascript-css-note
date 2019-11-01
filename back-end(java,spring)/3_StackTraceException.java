package com.ktnet.acps.customsapi.exception;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public final class StackTraceException extends Exception {
	private static final long serialVersionUID = -10823481055387270L;
	public StackTraceException(String msg){
		super(msg);
	}
	public StackTraceException(Exception e){
		super(e);
	}
	
	/**********************************************************
	 *print DateTime at StackTrace
	[2019/11/01 09:51:05] java.lang.ArithmeticException: / by zero
	CustomException.CustomsExceptionTest.testMethod1(CustomsExceptionTest.java:17)
	CustomException.CustomsExceptionTest.main(CustomsExceptionTest.java:7)
	 */
	@Override
	public void printStackTrace(){
		String dateTime = "[" + getDate("yyyy/MM/dd") + " " + getDate("HH:mm:ss") + "] ";
		System.out.println(dateTime + super.getMessage());		
		
		StackTraceElement[] steList = super.getStackTrace();
		for(StackTraceElement ste : steList){
			System.out.println(ste.toString());
		}
	}

	private static String getDate(String rule) {
		SimpleDateFormat formatter = new SimpleDateFormat(rule, Locale.getDefault());
		return formatter.format(new Date());
	}
}
