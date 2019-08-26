package com.test.transaction;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.stereotype.Component;

//iBatis Version
@Component
public class DeclarativeTRMgrMain {
	static ConfigurableListableBeanFactory beanFactory = null;
	
	public static void main(String[] args) {
		//load context info from XML and create ApplicationContext
		beanFactory = new GenericXmlApplicationContext(new String[] {"classpath*:spring/context*.xml"}).getBeanFactory();	
				
		//get bean by Spring IOC Container and Execute
		DeclarativeTRMgrMain declarativeTrMgr = beanFactory.getBean(DeclarativeTRMgrMain.class);		
		try {
			declarativeTrMgr.doTest();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doTest() throws Exception {
		TestDAO tDao = beanFactory.getBean(TestDAO.class);
		
		//A. Checked, UnChcked Exception 확인
		//tDao.testCheckedException(); 			  //1. CheckedException -> 롤백되지 않음
		//tDao.testCheckedExceptionRollbackFor(); //2. CheckedException + RollbackFor -> 롤백됨
		//tDao.testUnCheckedException();	      //3. UnChckedException -> 롤백됨(RollbackFor가 없어도)
		
		//B. @Transactional 옵션 설정 확인
		//ArithmeticException -> RuntimeException -> Rollback대상
		//tDao.testPropagationREQUIRED1();		              // 롤백됨
		//tDao.testPropagationREQUIRED_ParentRollback();      // REQUIRED로 구성되었고 자식메소드 존재하지만 TR구분이 없으면, 부모가 롤백되면 자식도 롤백됨 
		//tDao.testPropagationREQUIRED2_ChildrenRollback();   // REQUIRED로 구성되었고 자식메소드 존재하지만 TR구분이 없으면, 자식이 롤백되면 부모도 롤백됨 
		//Propagation.NESTED는 지원하는 WAS가 정해져있음 WebLogic은 지원안함 ??
		//tDao.testPropagationNESTED_ChildrenRollback(); 	  // NESTED로 생성된 자식에게서 오류 발생시, 자식만 롤백되고 부모는 커밋됨
		tDao.testPropagationNESTED_ParentRollback();          // *NESTED로 생성된 자식에게서 오류 발생시, 부모가 롤백시 자식도 롤백됨
		//tDao.testPropagationREQUIRES_NEW_ChildrenRollbak(); // REQUIRES_NEW로 생성된 자식에게서 오류 발생시, 자식만 롤백되고 부모는 커밋됨
		//tDao.testPropagationREQUIRES_NEW_ParentRollbak();   // REQUIRES_NEW로 생성된 자식이 있으면, 부모롤백시 부모만 롤백되고 자식은 커밋됨
	}
	
	
	
}
