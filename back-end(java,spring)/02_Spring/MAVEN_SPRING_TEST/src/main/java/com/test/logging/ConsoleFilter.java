package com.test.logging;

import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

public class ConsoleFilter extends Filter {

	@Override
	public int decide(LoggingEvent event) {
		System.out.println(">> event.getLogger().getName():"+event.getLogger().getName());
		
		//test > 특정 패키지만 로그를 남기도록 함
		if(event.getLogger().getName().equals("com.test.logging.LoggingTest")) {
			return DENY;
		}else {		
			return NEUTRAL;
		}
	}
}
