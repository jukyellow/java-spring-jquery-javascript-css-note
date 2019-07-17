package com.test.spring.bean;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class SpringBeanTest {
	public static void main( String[] args ) throws Exception {
		System.out.println("--SpringBeanTest Main called--");
		
		//load context info from XML and create ApplicationContext
		new GenericXmlApplicationContext(new String[] {"classpath*:spring/context*.xml"}); 	
		
		(new SpringBeanTest()).process();
	}
	
	public void process() {
		ApplicationContext ctx = ApplicationContextAwareImpl.getApplicationContext() ;
		ConfigurableListableBeanFactory beanFactory = ((ConfigurableApplicationContext)ctx).getBeanFactory();	
		
		String appName = beanFactory.resolveEmbeddedValue("${app.name}");
		System.out.println("appName:"+appName);
	}
}
