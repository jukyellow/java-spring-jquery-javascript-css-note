package com.test.annotation;

import java.lang.reflect.Method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class JavaAnnoTestMain {
	@Autowired
	ApplicationContext applicationContext;
	
	public static void main( String[] args ) throws Exception {
		System.out.println("--JavaAnnoTestMain start--");
		
		//context bean 로딩
        ConfigurableListableBeanFactory beanFactory 
        	= (new GenericXmlApplicationContext(new String[] {"classpath*:spring/context*.xml"})).getBeanFactory();
        
        JavaAnnoTestMain testMain = beanFactory.getBean(JavaAnnoTestMain.class);
        testMain.process();
	}
	
	//annotation이 적용된 메소드 실행
	@TestAnnoDef(value="test code execute!", jobs = TestJob.class)
	public void printInfo() {
		System.out.println("called printInfo!");		
	}
	
	//annotation 설정값을 출력하고, annotation에 매핑된 객체를 동적으로 찾아서 메소드를 호출함
	private void process() throws Exception {
		JavaAnnoTestMain newInstance = JavaAnnoTestMain.class.newInstance();
		Method[] methods = newInstance.getClass().getDeclaredMethods();  // Reflect으로 해당 클래스의 Method를 전부 조회합니다
		
		newInstance.printInfo();
		
		for(Method method : methods) {
			TestAnnoDef testAnno = method.getAnnotation(TestAnnoDef.class);  // Method들 중에 TestAnno을 찾습니다.
			if(testAnno!=null) {
				//annotation 설정값 확인
				System.out.println(testAnno.value());
				
				Class<? extends TestJob>[] testJobs = testAnno.jobs(); //annotation 변수(객체) 획득
				if(testJobs.length == 0) {
					throw new Exception("no jobs");
				}
				
				for(Class job : testJobs) {
					TestJob testJ = (TestJob)applicationContext.getBean(job); //context에서 동적으로 객체획득
					System.out.println(testJ.doJob());
				}
			}
		}
	}
}
