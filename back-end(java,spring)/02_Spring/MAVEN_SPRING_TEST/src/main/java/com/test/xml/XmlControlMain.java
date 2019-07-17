package com.test.xml;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class XmlControlMain {
	//case 1: @Autowired
	@Autowired
	XmlObject info;
	
	public static void main( String[] args ) throws Exception {
		System.out.println("--XmlControl Main called--");
		
		//load context info from XML and create ApplicationContext
		ConfigurableListableBeanFactory beanFactory = 
				new GenericXmlApplicationContext(new String[] {"classpath*:spring/context*.xml"}).getBeanFactory();	
				
		//get bean by Spring IOC Container and Execute
		XmlControlMain xmlMain = beanFactory.getBean(XmlControlMain.class);
		xmlMain.process();
	}
	
	public void process() throws Exception {	
		//case 2: get bean by ApplicationContext
		//ApplicationContext ctx = ApplicationContextAwareImpl.getApplicationContext() ;
		//info = ctx.getBean(XmlObject.class);
		
		Map conf = info.getXmlObject();
		System.out.println(conf.toString());
		//{test_xml_file={root/info=[{step=1, queryId=Test.selectFromDual, queryType=select}, {step=2, queryId=Test.selectFromDual2, queryType=select}]}}
		
		Map jobConf = (Map) conf.get("test_xml_file");		
		List qList = (List) jobConf.get(XmlObject.ROOT_ELEMENT_NAME + "/info");
		for(Object obj : qList) {
			String step = (String)((Map)obj).get("step");
			String queryId = (String)((Map)obj).get("queryId");
			System.out.println("step:"+step+",queryId:"+queryId);			
		}
		
		System.out.println("---------------");
		
//		List qList2 = (List) info.getJobConfig(this.getClass());
//		for(Object obj : qList2) {
//			String step = (String)((Map)obj).get("step");
//			String queryId = (String)((Map)obj).get("queryId");
//			System.out.println("step:"+step+",queryId:"+queryId);			
//		}
	}
}
