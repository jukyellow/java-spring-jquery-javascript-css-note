package com.test.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.test.logging.Util.Log4JUtil;

@SuppressWarnings({"rawtypes", "unchecked"})
public class XmlObject {
	public final static String ROOT_ELEMENT_NAME = "root";
	public final String OBJ_TYPE_KEY = "type";
	
	private Map xmlObject =  new LinkedHashMap();	
	public static Resource[] locations;
	
	private Logger logger = Log4JUtil.getLogger();
	
	public XmlObject() {
		try {
			String rootHome = System.getenv("HOME");
	        System.getProperties().put(Log4JUtil.SYS_PROP_SERVER_NAME, "xml");
			System.getProperties().put(Log4JUtil.SYS_PROP_LOG4J_DIR, rootHome + "/log");
			Log4JUtil.addLoggerSimple(this, "Log4jUtil");
		} catch (IOException e) {
			e.printStackTrace(); 
		}
	}

	public void setLocations(Resource[] locations) throws Exception {
		logger.debug("XmlObject setLocations called!");
		logger.debug(">>locations:"+locations[0].getDescription());
		
		this.locations = locations;
		
		loadXml();
	}
	
	public Resource[] getLocations() {
		return this.locations;
	}
	
	private void loadXml() throws Exception {
		if (this.locations != null) {
			for (Resource location : this.locations) {				
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(location.getFile());
				doc.getDocumentElement().normalize();
				
				Node rootNode = doc.getDocumentElement();
				
				Map item = new LinkedHashMap();
				
				xmlObject.put(FilenameUtils.removeExtension(location.getFile().getName()), item);
				
				xmlToObject(rootNode, item);
			}
		}
	}
	
	/**
	 * XML을 맵과 리스트의 중첩구조로 반환해준다.
	 * type 에트리뷰트는 list, map으로 정의 할 수있으며, 생략된 경우는 하위 노드를 보고 판단한다.
	 * @param node
	 * @param data
	 * @throws Exception
	 */
	private void xmlToObject(Node node, Object data) throws Exception {
		if (node.getNodeType() == Node.ELEMENT_NODE) {
			String objType = "";	
			
			if (node.hasAttributes()) {
				objType = (node.getAttributes().getNamedItem(OBJ_TYPE_KEY)).getNodeValue();
			} 
			
			if(StringUtils.isEmpty(objType)) {
				if(node.getChildNodes().getLength() > 1) { 
					objType = "map";
				} else if(node.getChildNodes().getLength() == 1 && node.getChildNodes().item(0).getNodeType() != Node.TEXT_NODE) { 
					objType = "map";
				}
			}
			
			// check if 하위 노드가 전부 comment node 인지
			if("map".equals(objType)) {	
				NodeList nodeList = node.getChildNodes();
				
				boolean isAllComment = true;
				for (int count = 0; count < nodeList.getLength(); count++) {
					if(nodeList.item(count).getNodeType() != Node.COMMENT_NODE) {
						isAllComment = false;
						break;
					}
				}
				
				if(isAllComment) {
					objType = "";
				}
			}
			
			if("list".equals(objType)) {
				NodeList nodeList = node.getChildNodes();
				
				if(nodeList.getLength() > 1) {
					String nodeName = "";
					for (int count = 0; count < nodeList.getLength(); count++) {
						if(!nodeList.item(count).getNodeName().startsWith("#")) {
							if(!StringUtils.isEmpty(nodeName) && !nodeName.equals(nodeList.item(count).getNodeName())) {
								throw new Exception("list type의 엘리먼트 하위 엘리먼트의 노드명이 동일해야 합니다.");
							}
							
							nodeName = nodeList.item(count).getNodeName();
						}
					}
					
					List childNodeData = new ArrayList();
					((Map)data).put(node.getNodeName() + "/" + nodeList.item(1).getNodeName(), childNodeData);
					
					logger.debug(">>1, node.getNodeName():" + node.getNodeName());
					
					for (int count = 0; count < nodeList.getLength(); count++) {
						if(nodeList.item(count).getNodeType() != Node.TEXT_NODE) {
							logger.debug(">>1, call xmlToObject(Node Name):" + nodeList.item(count).getNodeName());
							xmlToObject(nodeList.item(count), childNodeData);
						}
					}
				}
			} else if("map".equals(objType)) {	
				Map childNodeData = new LinkedHashMap();
				
				if(data instanceof List) {
					((List)data).add(childNodeData);
				} else {
					((Map)data).put(node.getNodeName(), childNodeData);
				}
				
				NodeList nodeList = node.getChildNodes();
				for (int count = 0; count < nodeList.getLength(); count++) {
					if(nodeList.item(count).getNodeType() != Node.TEXT_NODE) {
						logger.debug(">>2, call xmlToObject(Node Name):" + nodeList.item(count).getNodeName());
						xmlToObject(nodeList.item(count), childNodeData);						
					}
				}
			} else {	// objType 값이 없고 하위노드가 1개(Text Node) 이하인 경우 경우는 Terminal 이며 data는 Map
				if(node.getChildNodes().getLength() == 1 && node.getChildNodes().item(0).getNodeType() == Node.TEXT_NODE) {
					if(data instanceof List) {
						((List)data).add(node.getChildNodes().item(0).getNodeValue());
					} else {
						((Map)data).put(node.getNodeName(), node.getChildNodes().item(0).getNodeValue());
						logger.debug(">>2, node.getNodeName():" + node.getNodeName());
					}
				}
			}
		}
	}	
	
	public Map getXmlObject() throws Exception {
		Object obj = null;
		ByteArrayOutputStream bos = null;
		ObjectOutputStream out = null;
		
		ObjectInputStream in = null;
		try { 
			// Write the object out to a byte array
			bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			out.writeObject(xmlObject);
			out.flush();
			out.close();

			// Make an input stream from the byte array and read
			// a copy of the object back in.
			in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			obj = in.readObject();
		} finally {
			if(in != null) {
				in.close();
			}
			if(out != null) {
				out.close();
			}
			if(bos != null) {
				bos.close();
			}
		}
		return (Map) obj;	// 카피본을 반환함
	}
	
	/**
	 * 
	 * @param cls 호출 하는 ScheduleJob 상속객체
	 * @throws Exception
	 */
	public Object getJobConfig(Class cls) throws Exception {
		Object obj = null;
		if(!xmlObject.containsKey(cls.getSimpleName())) {	// xml 파일이 없는 경우
			return null;
		}
		
		Map tot = (Map) xmlObject.get(cls.getSimpleName());
		Set keys = tot.keySet();	// root에는 1개의 키만 존재함
		if(keys.size() == 0) {	// xml 파일에 파싱된 데이터가 없는 경우
			return null;
		}
		
		Object jobConf = tot.get(keys.iterator().next());
		
		ByteArrayOutputStream bos = null;
		ObjectOutputStream out = null;
		
		ObjectInputStream in = null;
		try { 
			// Write the object out to a byte array
			bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			out.writeObject(jobConf);
			out.flush();
			out.close();

			// Make an input stream from the byte array and read
			// a copy of the object back in.
			in = new ObjectInputStream(new ByteArrayInputStream(bos.toByteArray()));
			obj = in.readObject();
		} finally {
			if(in != null) {
				in.close();
			}
			if(out != null) {
				out.close();
			}
			if(bos != null) {
				bos.close();
			}
		}
		
		return obj;
	}
}
