package com.test.logging;

import java.io.IOException;

import org.apache.log4j.LogManager;
import org.apache.log4j.joran.JoranConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class LoggingTest {
	private Logger logger;
	
	public static void main( String[] args )
    {
		System.out.println( "--Start Logging Test--" );
		new LoggingTest();
	    System.out.println( "--End Logging Test--" );
    }
	
	public LoggingTest() {	
		
	        String rootHome = System.getenv("HOME"); //환경변수 정보중 HOME 가져오기
	        
	        //context bean 로딩
	        ConfigurableListableBeanFactory beanFactory 
	        	= (new GenericXmlApplicationContext(new String[] {"classpath*:spring/context*.xml"})).getBeanFactory();
	        
	        //properties값 로딩
	        String appName = ((ConfigurableBeanFactory) beanFactory).resolveEmbeddedValue("${app.name}");
	        
	        //log4j등 xml파일에서 System properties로 사용가능
	        System.getProperties().put("test.home", rootHome); 
	        System.getProperties().put("daily.filename", "logging_test");
	                
	        PathMatchingResourcePatternResolver pathMatchingResourcePatternResolver = new PathMatchingResourcePatternResolver();
			String log4jFilePath = "";
			try {
				log4jFilePath = pathMatchingResourcePatternResolver.getResource("log4j/log4j.xml").getFile().getAbsolutePath();
			} catch (IOException e) {
				System.out.println("Log4J 설정파일을 찾지 못했습니다.");
				e.printStackTrace();			
				System.exit(-1);
			}
			
			JoranConfigurator joran = new JoranConfigurator();
		    joran.doConfigure(log4jFilePath, LogManager.getLoggerRepository()); //log설정파일 세팅
			  
		    try {	    
				logger = LoggerFactory.getLogger(this.getClass());		
				logger.info("-- print app info (Home:"+ rootHome+ ",appName:" + appName + ") --");
				
				throw new Exception("err Test...");
	       }catch(Exception e) {
	    	   
	    	   logger = LoggerFactory.getLogger("com.test.error");	
	    	   logger.error("Exception occurs:"+e.getMessage());
	       }
	}
}
