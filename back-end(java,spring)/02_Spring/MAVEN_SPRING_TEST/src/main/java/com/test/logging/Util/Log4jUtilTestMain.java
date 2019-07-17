package com.test.logging.Util;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class Log4jUtilTestMain {
	public static void main(String[] args) throws IOException {
		System.out.println( "--Start Log4jUtilTestMain Test--" );
		
		//context bean 로딩
        ConfigurableListableBeanFactory beanFactory 
        	= (new GenericXmlApplicationContext(new String[] {"classpath*:spring/context*.xml"})).getBeanFactory();
        
        String rootHome = System.getenv("HOME");
        String serverNameAbbr = beanFactory.resolveEmbeddedValue("${server.name}");
		String log4jDir = beanFactory.resolveEmbeddedValue("${log4j.dir}");
        System.getProperties().put(Log4JUtil.SYS_PROP_SERVER_NAME, serverNameAbbr);
		System.getProperties().put(Log4JUtil.SYS_PROP_LOG4J_DIR, rootHome + log4jDir);
		
        Log4jUtilTestMain log4jMain = beanFactory.getBean(Log4jUtilTestMain.class);
        log4jMain.process();
        
        System.out.println( "--End Log4jUtilTestMain Test--" );
	}
	
	public void process() throws IOException {
		Log4JUtil.addLoggerSimple(this, "Log4jUtil");
		
		Logger logger = Log4JUtil.getLogger();
		Logger errorLogger = Log4JUtil.getErrorLogger();
		
		logger.info("success log test");
		errorLogger.error("error log test");		
	}
}




