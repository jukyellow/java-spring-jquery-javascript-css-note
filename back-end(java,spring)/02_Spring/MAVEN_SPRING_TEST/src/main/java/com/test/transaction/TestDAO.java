package com.test.transaction;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import egovframework.rte.psl.dataaccess.EgovAbstractDAO;

@Service
public class TestDAO {
	@Autowired
	EgovAbstractDAO dao;
	
	@Autowired 
	TestDAO2 dao2Service;
	
	//1. 스프링은 기본적으로 Unchecked Exception을 rollback 대상으로 지정한다.
	//2. FileNotFoundException은 CheckedException이다.
	//3. CheckedException 발생시 롤백을 원한다면 rollbackFor옵션에 Exception.class를 지정하면됨 
	//=>testCheckedException 메소드: CheckedException이고 rollbackFor가 없으므로 롤백되지 않는다. 
	@Transactional(value="transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	public void testCheckedException() throws FileNotFoundException {		
		Map param = new HashMap();
		param.put("KEY", "1");
		param.put("NUM", "NUM3");
		int upCnt = dao.update((String) "TEST_SQL.updateTestQuery1", param);
		System.out.println("upCnt:"+upCnt);
		
		throw new FileNotFoundException("TEST FileNotFoundException");		
	}
	@Transactional(value="transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = {Exception.class,SQLException.class}, readOnly = false)
	public void testCheckedExceptionRollbackFor() throws FileNotFoundException {		
		Map param = new HashMap();
		param.put("KEY", "1");
		param.put("NUM", "NUM3");
		int upCnt = dao.update((String) "TEST_SQL.updateTestQuery1", param);
		System.out.println("upCnt:"+upCnt);
		
		throw new FileNotFoundException("TEST FileNotFoundException");		
	}
	//UnCheckedException은 기본 롤백대상이므로, RollbackFor가 없어도 롤백된다.
	@Transactional(value="transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false)
	public void testUnCheckedException() throws NullPointerException {		
		Map param = new HashMap();
		param.put("KEY", "1");
		param.put("NUM", "NUM3");
		int upCnt = dao.update((String) "TEST_SQL.updateTestQuery1", param);
		System.out.println("upCnt:"+upCnt);
		
		throw new NullPointerException("TEST NullPointerException");		
	}
	
	@Transactional(value="transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = {Exception.class,SQLException.class}, readOnly = false)
	public void testPropagationREQUIRED1() throws Exception {		
		Map param = new HashMap();
		param.put("KEY", "1");
		param.put("NUM", "NUM3");
		int upCnt = dao.update((String) "TEST_SQL.updateTestQuery1", param);
		System.out.println("upCnt:"+upCnt);
		
		int a = 1/0; //RuntimeException
		
		param.put("KEY", "2");
		param.put("NUM", "NUM4");
		int upCnt2 = dao.update((String) "TEST_SQL.updateTestQuery2", param);
		System.out.println("upCnt2:"+upCnt2);		
	}
	
	@Transactional(value="transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = {Exception.class,SQLException.class}, readOnly = false)
	public void testPropagationREQUIRED_ParentRollback() throws Exception {
		try {
			Map param = new HashMap();
			param.put("KEY", "1");
			param.put("NUM", "0802_1");
			int upCnt = dao.update((String) "TEST_SQL.updateTestQuery1", param);
			System.out.println("upCnt:"+upCnt);
			
			processNestedMetohd1();
			
			int a = 1/0;
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void processNestedMetohd1() {		
		Map param = new HashMap();
		param.put("KEY", "2");
		param.put("NUM", "0802_2");
		int upCnt2 = dao.update((String) "TEST_SQL.updateTestQuery2", param);
		System.out.println("upCnt2:"+upCnt2);		
	}
	
	@Transactional(value="transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = {Exception.class,SQLException.class}, readOnly = false)
	public void testPropagationREQUIRED2_ChildrenRollback() throws Exception {
		try {
			Map param = new HashMap();
			param.put("KEY", "1");
			param.put("NUM", "0802_3");
			int upCnt = dao.update((String) "TEST_SQL.updateTestQuery1", param);
			System.out.println("upCnt:"+upCnt);
			
			processNestedMetohd2_Exception();
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	public void processNestedMetohd2_Exception() throws Exception {
		Map param = new HashMap();
		param.put("KEY", "4");
		param.put("NUM", "0802_5");
		int upCnt1 = dao.update((String) "TEST_SQL.updateTestQuery2", param);
		System.out.println("upCnt1:"+upCnt1);
		
		int a = 1/0;
	}
	
	@Transactional(value="transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = {Exception.class,SQLException.class}, readOnly = false)
	public void testPropagationNESTED_ChildrenRollback() throws Exception {
		Map param = new HashMap();
		param.put("KEY", "1");
		param.put("NUM", "0802_3");
		int upCnt = dao.update((String) "TEST_SQL.updateTestQuery1", param);
		System.out.println("upCnt:"+upCnt);
		
		try {
			dao2Service.processNestedMetohd();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(value="transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = {Exception.class,SQLException.class}, readOnly = false)
	public void testPropagationNESTED_ParentRollback() throws Exception {
		try {
			Map param = new HashMap();
			param.put("KEY", "1");
			param.put("NUM", "0802_3");
			int upCnt = dao.update((String) "TEST_SQL.updateTestQuery1", param);
			System.out.println("upCnt:"+upCnt);
			
			dao2Service.processNestedMetohd2();
			
			int a=1/0;
			
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional(value="transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = {Exception.class,SQLException.class}, readOnly = false)
	public void testPropagationREQUIRES_NEW_ChildrenRollbak() throws Exception {		
		Map param = new HashMap();
		param.put("KEY", "1");
		param.put("NUM", "0802_3");
		int upCnt = dao.update((String) "TEST_SQL.updateTestQuery1", param);
		System.out.println("upCnt:"+upCnt);
		
		try {
			dao2Service.processRequiresNewMetohd1();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(value="transactionManager", propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = {Exception.class,SQLException.class}, readOnly = false)
	public void testPropagationREQUIRES_NEW_ParentRollbak() throws Exception {		
		Map param = new HashMap();
		param.put("KEY", "1");
		param.put("NUM", "0802_3");
		int upCnt = dao.update((String) "TEST_SQL.updateTestQuery1", param);
		System.out.println("upCnt:"+upCnt);
		
		try { dao2Service.processRequiresNewMetohd2(); }
		catch(Exception ee) { ee.printStackTrace(); }
		
		int a=1/0;
	}
}
