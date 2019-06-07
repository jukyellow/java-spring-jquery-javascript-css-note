package com.test.aop;

import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


public class AopCommon {
	@Pointcut("execution(* com.test.aop.AopTestService.*(..))") //
	public void commonPointcut() {	}
}
