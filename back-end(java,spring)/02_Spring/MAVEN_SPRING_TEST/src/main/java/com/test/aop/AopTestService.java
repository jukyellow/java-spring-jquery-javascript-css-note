package com.test.aop;

import org.springframework.stereotype.Service;

@Service
public class AopTestService {
	private String name="tester";
	private String age="30";
	private String gender="MAN"; //Man, Woman	
	
	public String printAll() {
		String msg =  "AopTestService[name:"+name+"|age:"+age+"|gender:"+gender+"]";
		System.out.println(msg);
		return msg;
	}
	
}
