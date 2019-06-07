package com.test.aop;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;

//@SuppressWarnings({"rawtypes", "unchecked"}) warning 생략
public class AopMain {
    public static void main( String[] args )
    {
    	System.out.println( "--Start AopTest--" );
    	
    	//context bean 로딩
        ConfigurableListableBeanFactory beanFactory 
        	= (new GenericXmlApplicationContext(new String[] {"classpath*:spring/context*.xml"})).getBeanFactory();
        
        System.out.println( "context bean 로딩!" );
        
        AopTestService tService = (AopTestService) beanFactory.getBean(AopTestService.class);
        tService.printAll();
        
        System.out.println( "--End AopTest--" );
    }
}
