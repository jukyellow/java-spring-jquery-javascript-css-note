package com.test.spring.bean;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextAwareImpl implements ApplicationContextAware {
	
	private static ApplicationContext ctx=null;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		System.out.println("ApplicationContextAwareImpl is called!");
		ctx = applicationContext;
	}
	
	public static ApplicationContext getApplicationContext() {
		if(ctx==null) {
			System.out.println("getApplicationContext is null");
			return null;
		}else {
			return ctx;
		}
	}

}
