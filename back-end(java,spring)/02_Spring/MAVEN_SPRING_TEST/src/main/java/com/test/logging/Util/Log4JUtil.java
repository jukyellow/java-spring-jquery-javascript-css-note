package com.test.logging.Util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.rolling.RollingFileAppender;
import org.apache.log4j.rolling.TimeBasedRollingPolicy;
import org.springframework.aop.framework.Advised;

@SuppressWarnings({"rawtypes"})
public class Log4JUtil {
	public static final String DAILY_APPENDER_NM = "DAILY_APPENDER";
	public static final String DAILY_ERROR_APPENDER_NM = "DAILY_ERROR_APPENDER";
	
	public static final String ERROR_LOG_SUFFIX = ".error";
	public static final String SUCCESS_LOG_SUFFIX = ".success";
	
	public static String SYS_PROP_LOG4J_DIR = "log4j.dir";
	public static String SYS_PROP_SERVER_NAME = "server.name";	

	private static Map<String, RollingFileAppender> APPENDER_MAP = new ConcurrentHashMap<String, RollingFileAppender>();
	
	private static Set<String> LOGGERS_MAP = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());		
	
	public final static String DATE_PATTERN = "yyyy-MM-dd";
	
//	private static AtomicBoolean runComplete = new AtomicBoolean(false);
	
	public static void addDefaulLogger() throws Exception {
//		RollingFileAppender drfa = new RollingFileAppender();
//		drfa.setName(DAILY_APPENDER_NM);
//		drfa.setLayout(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} %-5p %M(%F:%L) - %m%n"));
//		drfa.setAppend(true);
//		
//		TimeBasedRollingPolicy tbrp = new TimeBasedRollingPolicy();
//		tbrp.setFileNamePattern(System.getProperty(AbstractRun.SYS_PROP_LOG4J_DIR) + "/daily_" + System.getProperty(AbstractRun.SYS_PROP_SERVER_NAME_ABBR) + ".%d{" + DATE_PATTERN + "}.log");
//		drfa.setRollingPolicy(tbrp);
//		
//		drfa.activateOptions();
//		
//		Logger logger = Logger.getLogger(APP_ROOT_LOGGER_NM);
//		logger.setAdditivity(false);
//		logger.setLevel(Level.DEBUG);
//		logger.addAppender(drfa);
//		logger.addAppender(Logger.getRootLogger().getAppender("CONSOLE_APPENDER"));
//		
//		
//		RollingFileAppender drfaE = new RollingFileAppender();
//		drfaE.setName(DAILY_ERROR_APPENDER_NM);
//		drfaE.setLayout(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} %-5p %M(%F:%L) - %m%n"));
//		
//		TimeBasedRollingPolicy tbrpE = new TimeBasedRollingPolicy();
//		tbrpE.setFileNamePattern(System.getProperty(AbstractRun.SYS_PROP_LOG4J_DIR) + "/daily_" + System.getProperty(AbstractRun.SYS_PROP_SERVER_NAME_ABBR) + ".%d{" + DATE_PATTERN + "}.log");
//		drfaE.setRollingPolicy(tbrpE);
//		drfaE.setAppend(true);
//		drfaE.activateOptions();
//		
//		Logger loggerE = Logger.getLogger(APP_ROOT_ERR_LOGGER_NM);
//		loggerE.setLevel(Level.WARN);
//		loggerE.addAppender(drfaE);
	}

	
	public static void addLogger(Object obj, String logDir) throws IOException {
		final Class cls = obj.getClass();
		final Class rClass = getRealClass(obj, cls);
		
		for(Method method : cls.getDeclaredMethods()) {
			method = getRealMethod(obj, method);
			
			//if(!method.isAnnotationPresent(//Annotaion Class
			//		)) { continue; }
			
			if(!APPENDER_MAP.containsKey(logDir)) {	// Appender 
				String clsName = rClass.getSimpleName();
				
				RollingFileAppender drfa = new RollingFileAppender();
				drfa.setName(clsName);
				drfa.setLayout(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} %-5p %M(%F:%L) - %m%n"));
				drfa.setAppend(true);
				
				TimeBasedRollingPolicy tbrp = new TimeBasedRollingPolicy();
				String fNamePattern = System.getProperty(SYS_PROP_LOG4J_DIR) + "/" + logDir + "/daily_" + System.getProperty(SYS_PROP_SERVER_NAME) + "_" + ".%d{" + DATE_PATTERN + "}.log";
				tbrp.setFileNamePattern(fNamePattern);
				drfa.setRollingPolicy(tbrp);				
				drfa.activateOptions();				
				APPENDER_MAP.put(logDir, drfa);
				
				
				RollingFileAppender drfaE = new RollingFileAppender();
				drfaE.setName(clsName + ERROR_LOG_SUFFIX);
				drfaE.setLayout(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} %-5p %M(%F:%L) - %m%n"));
				
				TimeBasedRollingPolicy tbrpE = new TimeBasedRollingPolicy();
				String errFileNamePattern = System.getProperty(SYS_PROP_LOG4J_DIR) + "/" + logDir + "/daily_error_" + System.getProperty(SYS_PROP_SERVER_NAME) + "_" + ".%d{" + DATE_PATTERN + "}.log";
				tbrpE.setFileNamePattern(errFileNamePattern);
				drfaE.setRollingPolicy(tbrpE);
				drfaE.setAppend(true);
				drfaE.activateOptions();				
				APPENDER_MAP.put(logDir + ERROR_LOG_SUFFIX, drfaE);
			}
			
			//최초 Logger정보(xml config)에서 사용자가 정의한 Appender를 추가한다. 
			//(If the named logger already exists, then the existing instance will be reutrned. Otherwise, a new instance is created.)
			//이 때, Logger의 식별정보는 Class-Name으로 사용한다.
			System.out.println("addSchLogger cls/method:"+cls.getName() + "/" + method.getName());
			//Logger logger = Logger.getLogger(cls.getName() + "." + method.getName());
			Logger logger = Logger.getLogger(cls.getName() + SUCCESS_LOG_SUFFIX);
			//logger.setAdditivity(true);
			logger.setLevel(Level.DEBUG);			
			logger.addAppender(APPENDER_MAP.get(logDir + SUCCESS_LOG_SUFFIX));			
			logger.addAppender(Logger.getRootLogger().getAppender("CONSOLE_APPENDER")); //console appender 추가
			
			//Logger loggerE = Logger.getLogger(cls.getName() + "." + method.getName() + ERROR_LOG_SUFFIX);
			Logger loggerE = Logger.getLogger(cls.getName() + ERROR_LOG_SUFFIX);
			loggerE.addAppender(APPENDER_MAP.get(logDir + ERROR_LOG_SUFFIX));
			loggerE.setLevel(Level.DEBUG);
			
			LOGGERS_MAP.add(cls.getName() + "." + method.getName());
		}
	}
	
	public static void addLoggerSimple(Object obj, String logDir) throws IOException {
		final Class cls = obj.getClass();
		final Class rClass = getRealClass(obj, cls);
		
		if(!APPENDER_MAP.containsKey(logDir)) {	// Appender 
			String clsName = rClass.getSimpleName();
			
			RollingFileAppender drfa = new RollingFileAppender();
			drfa.setName(clsName);
			drfa.setLayout(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} %-5p %M(%F:%L) - %m%n"));
			drfa.setAppend(true);
			
			TimeBasedRollingPolicy tbrp = new TimeBasedRollingPolicy();
			String fNamePattern = System.getProperty(SYS_PROP_LOG4J_DIR) + "/" + logDir + "/daily_" + System.getProperty(SYS_PROP_SERVER_NAME) + "_" + ".%d{" + DATE_PATTERN + "}.log";
			tbrp.setFileNamePattern(fNamePattern);
			drfa.setRollingPolicy(tbrp);				
			drfa.activateOptions();				
			APPENDER_MAP.put(logDir + SUCCESS_LOG_SUFFIX, drfa);
			
			
			RollingFileAppender drfaE = new RollingFileAppender();
			drfaE.setName(clsName + ERROR_LOG_SUFFIX);
			drfaE.setLayout(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} %-5p %M(%F:%L) - %m%n"));
			
			TimeBasedRollingPolicy tbrpE = new TimeBasedRollingPolicy();
			String errFileNamePattern = System.getProperty(SYS_PROP_LOG4J_DIR) + "/" + logDir + "/daily_error_" + System.getProperty(SYS_PROP_SERVER_NAME) + "_" + ".%d{" + DATE_PATTERN + "}.log";
			tbrpE.setFileNamePattern(errFileNamePattern);
			drfaE.setRollingPolicy(tbrpE);
			drfaE.setAppend(true);
			drfaE.activateOptions();				
			APPENDER_MAP.put(logDir + ERROR_LOG_SUFFIX, drfaE);
		}
		
		ConsoleAppender console = new ConsoleAppender();
		console.setLayout(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} %-5p %M(%F:%L) - %m%n"));
		console.setThreshold(Level.INFO);
		console.activateOptions();
		
		//최초 Logger정보(xml config)에서 사용자가 정의한 Appender를 추가한다. 
		//(If the named logger already exists, then the existing instance will be reutrned. Otherwise, a new instance is created.)
		//이 때, Logger의 식별정보는 Class-Name으로 사용한다.
		System.out.println("addLogger cls/method:"+cls.getName()); //com.test.logging.Util.Log4jUtilTestMain
		
		//패키지명이 포함되거나 같으면 같은 Logger에 찍히므로 주의
		Logger logger = Logger.getLogger(cls.getName() + SUCCESS_LOG_SUFFIX);
		logger.addAppender(APPENDER_MAP.get(logDir + SUCCESS_LOG_SUFFIX));			
		//logger.addAppender(Logger.getRootLogger().getAppender("CONSOLE_APPENDER")); //console appender 추가		
		logger.addAppender(console);
		
		Logger loggerE = Logger.getLogger(cls.getName() + ERROR_LOG_SUFFIX);
		loggerE.addAppender(APPENDER_MAP.get(logDir + ERROR_LOG_SUFFIX));
		loggerE.addAppender(console);
		
		LOGGERS_MAP.add(cls.getName());
	}	
	
	public static Class getRealClass(Object bean, Class cls) {
	    try {
	        if (cls.getName().contains("CGLIB")) {
	        	return ((Advised) bean).getTargetSource().getTarget().getClass();
	        }
	    } catch (Exception e) {
	    	e.printStackTrace();
	        // 발생하면 안됩니다.
	    }
	    
	    return cls;
	}
	
	public static Method getRealMethod(Object bean, Method method) {
	    try {
			Class declaringClass = method.getDeclaringClass();
	        if (declaringClass.getName().contains("CGLIB")) {
	            return ((Advised) bean).getTargetSource().getTarget().getClass().getMethod(method.getName());
	        }
	    } catch (Exception e) {
	        // 발생하면 안됩니다.
	    }
	    return method;
	}
	
	//현재 수행중인 쓰레드에 할당되는 Logger를 받아옴
	public static Logger getLogger() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		for (int i = 0; i < stElements.length; i++) {
			try {
//				if(LOGGERS_MAP.contains(stElements[i].getClassName() + "." + stElements[i].getMethodName())) {
//					System.out.println("getSchLogger1 :"+stElements[i].getClassName() + "." + stElements[i].getMethodName());
//					return Logger.getLogger(stElements[i].getClassName() + "." + stElements[i].getMethodName());
//				}
				if(LOGGERS_MAP.contains(stElements[i].getClassName())) {
					System.out.println("getLogger1 :"+stElements[i].getClassName() + SUCCESS_LOG_SUFFIX);
					return Logger.getLogger(stElements[i].getClassName() + SUCCESS_LOG_SUFFIX);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return Logger.getLogger(stElements[2].getClassName() + SUCCESS_LOG_SUFFIX);
	}

	//현재 수행중인 쓰레드에 할당되는 Logger를 받아옴
	public static Logger getErrorLogger() {
		StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
		for (int i = 0; i < stElements.length; i++) {
			try {
				if(LOGGERS_MAP.contains(stElements[i].getClassName())) {
					System.out.println("getErrorLogger1 :"+stElements[i].getClassName() + ERROR_LOG_SUFFIX);
					return Logger.getLogger(stElements[i].getClassName() + ERROR_LOG_SUFFIX);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return Logger.getLogger(stElements[2].getClassName() + ERROR_LOG_SUFFIX);
	}
	
	public static boolean isSchLogger(String loggerNm) {
		for(String schLoggerNm : LOGGERS_MAP) {
			if(schLoggerNm.equals(loggerNm)) return true;
			if((schLoggerNm + ERROR_LOG_SUFFIX).equals(loggerNm)) return true;
		}
		
		return false;
	}
	
}
