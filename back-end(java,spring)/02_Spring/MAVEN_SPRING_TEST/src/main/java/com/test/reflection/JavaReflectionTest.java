package com.test.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//
public class JavaReflectionTest {
	public static void main( String[] args ) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, InstantiationException, SecurityException, NoSuchMethodException {
		System.out.println("--JavaReflectionTest start!--");
		
		//객체를 찾는다
		Class targetClass = Class.forName("com.test.reflection.ReflectionSample");
		
		//객체 하나 생성
		ReflectionSample rSample = (ReflectionSample) targetClass.newInstance();
        
		//1. 메소드 호출
		Method methods[] = targetClass.getDeclaredMethods();
		for(Method method : methods) {
			if(method.getName().equals("printInfo")) {
				method.invoke(rSample, null);
			}
		}
		
		//2. 메소드 페라미터 전달 및 반환값 출력
		Class[] param = {int.class, int.class};
		Method method = targetClass.getMethod("sum", param); 
		int sum = (Integer) method.invoke(rSample, 1, 2);
		System.out.println("sum:"+sum);
	}
}
