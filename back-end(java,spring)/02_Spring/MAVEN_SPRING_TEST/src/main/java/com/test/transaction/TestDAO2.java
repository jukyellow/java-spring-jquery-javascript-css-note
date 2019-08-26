package com.test.transaction;

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
public class TestDAO2 {
	@Autowired
	EgovAbstractDAO dao;
	
	@Transactional(value="transactionManager", propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, rollbackFor = {Exception.class,SQLException.class}, readOnly = false)
	public void processNestedMetohd() throws Exception {
		Map param = new HashMap();
		param.put("KEY", "4");
		param.put("NUM", "0802_5");
		int upCnt1 = dao.update((String) "TEST_SQL.updateTestQuery2", param);
		System.out.println("upCnt1:"+upCnt1);
		
		int a = 1/0;
	}
	
	
	@Transactional(value="transactionManager", propagation = Propagation.NESTED, isolation = Isolation.DEFAULT, rollbackFor = {Exception.class,SQLException.class}, readOnly = false)
	public void processNestedMetohd2() throws Exception {
		try {
			Map param = new HashMap();
			param.put("KEY", "4");
			param.put("NUM", "0802_5");
			int upCnt1 = dao.update((String) "TEST_SQL.updateTestQuery2", param);
			System.out.println("upCnt1:"+upCnt1);
		}catch(Exception e) {
			throw e;
		}
	}
	
	@Transactional(value="transactionManager", propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = {Exception.class,SQLException.class}, readOnly = false)
	public void processRequiresNewMetohd1()  {
		Map param = new HashMap();
		param.put("KEY", "4");
		param.put("NUM", "0802_5");
		int upCnt1 = dao.update((String) "TEST_SQL.updateTestQuery2", param);
		System.out.println("upCnt1:"+upCnt1);
		
		int a=1/0;
	}
	
	@Transactional(value="transactionManager", propagation = Propagation.REQUIRES_NEW, isolation = Isolation.DEFAULT, rollbackFor = {Exception.class,SQLException.class}, readOnly = false)
	public void processRequiresNewMetohd2() {
		Map param = new HashMap();
		param.put("KEY", "4");
		param.put("NUM", "0802_5");
		int upCnt1 = dao.update((String) "TEST_SQL.updateTestQuery2", param);
		System.out.println("upCnt1:"+upCnt1);
	}
}
