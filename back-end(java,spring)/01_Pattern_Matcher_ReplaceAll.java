package com.test.juk;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class A1_Pattern_Matcher_ReplaceAll {
	public static void main( String[] args ) {
		//Patternm,Matcher since JDK 1.4

		String originStr= "TEST/ ABC-STR 111 OK!";
		Pattern p = Pattern.compile("[^a-zA-Z0-9]*"); //숫자, 알파벳이 아닌 패턴만 
		Matcher m = p.matcher(originStr);

		String newStr = m.replaceAll(""); //공백으로 치환

		System.out.println("originStr:"+originStr);
		System.out.println("newStr   :"+newStr);
	}
}
