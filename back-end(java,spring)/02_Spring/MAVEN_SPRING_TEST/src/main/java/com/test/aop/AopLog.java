package com.test.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect //pointcut(where) + advice(what)
public class AopLog {//extends CommonPointcut {
	@Pointcut("execution(* com.test.aop.AopTestService.*(..))") 
	public void logPointcut() {	}
	
	@Before("logPointcut()") //before advice
	public void printAdviceBefore() {
		System.out.println("printSysLog before main-logic");
	}
	
	@After("com.test.aop.AopCommon.commonPointcut()") //After advice
	public void printAdviceAfter() {
		System.out.println("printSysLog after main-logic");
	}
	
	@Around("within(com.test.aop.*)")
	//@Around("logPointcut()")
	public void printSysLogOnAround(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("Here is previous joinPoint.proceed()");
		joinPoint.proceed();
		System.out.println("Here is next joinPoint.proceed()");
	}	
}
