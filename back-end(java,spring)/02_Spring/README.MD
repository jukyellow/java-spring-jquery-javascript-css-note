# Spring  

### 1. AOP (Aspect Oriented Programming)  
- 애플리케이션의 핵심적인 기능에서 부가적인 기능(로깅, 보안 등 공통기능)을 Aspect(Advice + PointCut)라는 모듈로 분리해서 설계,개발하는 프로그램 기법  
- 정리: https://www.evernote.com/shard/s222/sh/f436bba3-6962-46b4-b32e-022ba654d9a3/c48e3a8aa8e451087afcbbeb7400e6e0  
- AOP 적용시 오류해결: https://www.evernote.com/shard/s222/sh/3addf8b5-a067-4856-ae6b-d770d82dda91/a0de1b0c4586c9053f72c07779a6955a  
- 전자정부 설명 및 샘플코드 : http://www.egovframe.go.kr/wiki/doku.php?id=egovframework:rte:fdl:aop:aspectj  
<pre>
    classpath resource 추가  
    실행: Run as Configuratuin > com.test.aop.AopMain  
</pre>
- Advice의 수행순서 Around -> Before -> After -> Around  
![image](https://user-images.githubusercontent.com/45334819/59117125-09885d00-8988-11e9-9809-e513498a920c.png)  
-관련소스: https://github.com/jukyellow/spring-jquery-css-note/tree/master/back-end(java%2Cspring)/02_Spring/MAVEN_SPRING_TEST/src/main/java/com/test/aop  

### 2. Logging (Log4j, Slf4j : simple logging facade for java)
- 정리 : https://www.evernote.com/shard/s222/sh/12c9a642-bd83-48f7-92fb-f995e3831285/3c74a4a011bf4c596783baee7835b7c2  
<pre>
    classpath resource 추가 및 Home 환경변수 추가  
    실행: Run as Configuratuin > com.test.logging.LoggingTest  
</pre>
![image](https://user-images.githubusercontent.com/45334819/59117939-ef4f7e80-8989-11e9-975a-888c7dfd05de.png)
- 관련소스: https://github.com/jukyellow/spring-jquery-css-note/tree/master/back-end(java%2Cspring)/02_Spring/MAVEN_SPRING_TEST/src/main/java/com/test/logging  

### 3. Reflection  
- JVM에서 적재되어 구동중인 클래스를 동적으로 호출하여 사용(ex: Spring Bean객체 로딩하는 방식)  
<pre>
Class targetClass = Class.forName("com.test.reflection.ReflectionSample");
ReflectionSample rSample = (ReflectionSample) targetClass.newInstance();
Method methods[] = targetClass.getDeclaredMethods();
for(Method method : methods) {
    if(method.getName().equals("printInfo")) {
        method.invoke(rSample, null);
    }
}
</pre>
- https://github.com/jukyellow/spring-jquery-css-note/tree/master/back-end(java%2Cspring)/02_Spring/MAVEN_SPRING_TEST/src/main/java/com/test/reflection  

### 4. Annotation  
- 소스코드 클래스/메소드/변수등에 메타 데이타정보를 제공  
<pre>
@Target(value={java.lang.annotation.ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface TestAnnoDef {
	public String value() default "test annotation val";
	public String name() default "test annotation name";
	Class<? extends TestJob>[] jobs() default {};
}
</pre>
- https://github.com/jukyellow/spring-jquery-css-note/tree/master/back-end(java%2Cspring)/02_Spring/MAVEN_SPRING_TEST/src/main/java/com/test/annotation  

### 5. enum
- 클래스처럼 보이게 하는 상수  
- 새로운 열거형을 선언하면, 내부적으로 Enum 클래스형 기반의 새로 클래스형이 만들어짐  
<pre>
public class EnumTestMain {
	private static enum  PERSON_TYPE {
		MAN("남자"), WOMAN("여자"), CHILDREN("아이");
		
		private String desc;
		private PERSON_TYPE(String desc) {
			System.out.println("PERSON_TYPE called: " + desc);
			this.desc = desc;
		}
		public String getTypeDesc() {
			return this.desc;
		}
	}
	
	public static void main( String[] args ) {
		for(PERSON_TYPE type : PERSON_TYPE.values()) {
			System.out.println("type: " + type + ",desc:" + type.getTypeDesc());
		}
	}
}	
</pre>

### 6. Log4j Filter
- Filter를 적용해 runtime시 동적으로 다양한 제어 가능  
``` java
public class ConsoleFilter extends Filter {
	@Override
	public int decide(LoggingEvent event) {
		if(event.getLogger().getName().equals("com.test.logging.LoggingTest")) {
			return DENY;
		}else {		
			return NEUTRAL;
		}
	}
}
```
``` xml
	<appender name="CONSOLE_APPENDER" class="org.apache.log4j.ConsoleAppender">
		<layout class="org.apache.log4j.PatternLayout">
			<param name="ConversionPattern" value="%5p (%F[%M]:%L) [%d{yyyy/MM/dd HH:mm:ss.SSS}] - %m%n" />
		</layout>
		<filter class="com.test.logging.ConsoleFilter" />
	</appender>
```

### 7. Daynamic Log4j
- 참고: https://www.programcreek.com/java-api-examples/?api=org.apache.log4j.RollingFileAppender
- Log4j를 xml configuration 방식외에, java code로 runtime시 동적으로 생성가능  
- 특정 업무별로 로그패턴이나 로그파일을 분리할 때 활용  
``` java
if(!APPENDER_MAP.containsKey(logDir)) {	// Appender 
			String clsName = rClass.getSimpleName();
			
			RollingFileAppender drfa = new RollingFileAppender();
			drfa.setName(clsName);
			drfa.setLayout(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} %-5p %M(%F:%L) - %m%n"));
			drfa.setAppend(true);
			
			TimeBasedRollingPolicy tbrp = new TimeBasedRollingPolicy();
			String fNamePattern = System.getProperty(SYS_PROP_LOG4J_DIR) + "/" + logDir + "/daily_" + System.getProperty(SYS_PROP_SERVER_NAME) + "_" + ".%d{" + DATE_PATTERN + "}.log";
			tbrp.setFileNamePattern(fNamePattern);
			drfa.setRollingPolicy(tbrp);				
			drfa.activateOptions();				
			APPENDER_MAP.put(logDir + SUCCESS_LOG_SUFFIX, drfa);
			
			
			RollingFileAppender drfaE = new RollingFileAppender();
			drfaE.setName(clsName + ERROR_LOG_SUFFIX);
			drfaE.setLayout(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} %-5p %M(%F:%L) - %m%n"));
			
			TimeBasedRollingPolicy tbrpE = new TimeBasedRollingPolicy();
			String errFileNamePattern = System.getProperty(SYS_PROP_LOG4J_DIR) + "/" + logDir + "/daily_error_" + System.getProperty(SYS_PROP_SERVER_NAME) + "_" + ".%d{" + DATE_PATTERN + "}.log";
			tbrpE.setFileNamePattern(errFileNamePattern);
			drfaE.setRollingPolicy(tbrpE);
			drfaE.setAppend(true);
			drfaE.activateOptions();				
			APPENDER_MAP.put(logDir + ERROR_LOG_SUFFIX, drfaE);
		}
		
		ConsoleAppender console = new ConsoleAppender();
		console.setLayout(new PatternLayout("%d{yyyy/MM/dd HH:mm:ss} %-5p %M(%F:%L) - %m%n"));
		console.setThreshold(Level.INFO);
		console.activateOptions();
		
		Logger logger = Logger.getLogger(cls.getName() + SUCCESS_LOG_SUFFIX);
		logger.addAppender(APPENDER_MAP.get(logDir + SUCCESS_LOG_SUFFIX));			
		logger.addAppender(console);
		
		Logger loggerE = Logger.getLogger(cls.getName() + ERROR_LOG_SUFFIX);
		loggerE.addAppender(APPENDER_MAP.get(logDir + ERROR_LOG_SUFFIX));
		loggerE.addAppender(console);
		
		LOGGERS_MAP.add(cls.getName());
```
![image](https://user-images.githubusercontent.com/45334819/61415892-042a1500-a92d-11e9-80c9-87ec60ca1f92.png)  
-https://github.com/jukyellow/spring-jquery-css-note/tree/master/back-end(java%2Cspring)/02_Spring/MAVEN_SPRING_TEST/src/main/java/com/test/logging/Util

### 8. XML handling
- 사용자가 정의한 xml형식을 읽어서 map형태로 저장하고, 순회하면서 data를 가져다 쓰기위한 로딩작업  
``` java
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
```
- ![image](https://user-images.githubusercontent.com/45334819/61416175-cbd70680-a92d-11e9-8d07-24bc38ebd684.png)
- https://github.com/jukyellow/spring-jquery-css-note/tree/master/back-end(java%2Cspring)/02_Spring/MAVEN_SPRING_TEST/src/main/java/com/test/xml  

### 9. Spring Transaction 관리  
#### 9-1. Annotation 방식
- 스프링은 기본적으로 Unchecked Exception을 rollback 대상으로 지정한다.  
- Checked Exception 발생 시 롤백을 원한다면 RollbackFor 옵션에 Exception.class를 지정해 주면 된다.  
- propagation 옵션 NESTED, REQUIRES_NEW을 사용할려면, 새로운 Service Bean객체로 Transaction을 구성해야한다.  
![image](https://user-images.githubusercontent.com/45334819/63722030-5c89f600-c88d-11e9-9152-1cdafc7eaf06.png)  
![image](https://user-images.githubusercontent.com/45334819/63722055-64e23100-c88d-11e9-8d8d-76dd16dd0d55.png)  

#### 9-2. Programming 방식  
##### 9-2-1. spring jdbc
```
private int deleteRequestForExternkeByJdbcBatch(List delReqList, ParamSet pset) throws Exception {
    CommonDaoImpl Dao1 = null;;
    CommonDaoImpl Dao2 = null;
    Connection extConn = null;
    Connection acpsConn = null;
    PreparedStatement[] extPstmt = null;
    PreparedStatement[] acpsPstmt = null;
    
    try {           
        Dao1 = daoFactory.getExternkeDao();                
        Dao2 = externkeDao.getDataSource().getConnection();
        extConn.setAutoCommit(false);    
        extPstmt = getExtkePstmtAboutInsert(extConn);
        
        acpsConn = acpsDao.getDataSource().getConnection();
        acpsConn.setAutoCommit(false);                
        acpsPstmt = getAcpsPstmtAboutKeDel(acpsConn);
        
        //1.삭제요청
        ResultSet rs = null;
        int inKeCnt = 0;
        for(int i=0; i<delReqList.size(); ){
            Map param = (Map)delReqList.get(i);
            
            rs = extPstmt[0].executeQuery(); //hawb가 삭제요청 가능하면 seq생성하도록 보강가능..
            Integer seqId = 0;
            if(rs.next()){
                seqId = rs.getInt("SEQID");
                param.put("SEQID", seqId);
            }    
            rs.close();
	    
            extPstmt[1].setInt(1, seqId);
            extPstmt[1].addBatch();
            
            ++i; //executeBatch를 0에서는 제외하기 위해 loop하단에 배치
            if(i % 1000 == 0){
                int inSCnt[] = extPstmt[1].executeBatch();
                inKeCnt += inSCnt.length;
            }
        }
        int inSCnt[] = extPstmt[1].executeBatch();
        inKeCnt += inSCnt.length;        
        
        //2. 삭제요청 업데이트 및 로그저장
        int inSuccCnt = 0;
        //Hawb단위로 addBatch됨            
        for(int i=0; i<delReqList.size(); ){
            Map param = (Map)delReqList.get(i);         
            acpsPstmt[1].setInt(1, (Integer) param.get("SEQID"));
            acpsPstmt[1].addBatch();
            
            ++i; //executeBatch를 0에서는 제외하기 위해 loop하단에 배치
            if(i % 1000 == 0){
                int inAcpCnt[] = acpsPstmt[1].executeBatch();
                inSuccCnt += inAcpCnt.length;
            }
        }    
        int inAcpCnt[] = acpsPstmt[1].executeBatch();
        inSuccCnt += inAcpCnt.length;    
                    
        acpsConn.commit();
        extConn.commit();
        
    } catch (BizException e){
        throw e;
    }catch (Exception e) {
        e.printStackTrace();
        if(acpsConn!=null){ acpsConn.rollback(); }
        if(extConn!=null){ extConn.rollback(); }
        throw new Exception("삭제요청중 오류가 발생하였습니다(" + e.getMessage() + ")");
    }finally{
        if(extPstmt!=null){
            if(extPstmt[0]!=null){    extPstmt[0].close(); }
            if(extPstmt[1]!=null){    extPstmt[1].close(); }
        }
        if(acpsPstmt!=null){
            if(acpsPstmt[0]!=null){    acpsPstmt[0].close(); }
            if(acpsPstmt[1]!=null){    acpsPstmt[1].close(); }        
        }
        if(acpsConn!=null){    acpsConn.setAutoCommit(true); }
        if(extConn!=null){ extConn.setAutoCommit(true); }
        if(acpsConn!=null){    acpsConn.close(); }
        if(extConn!=null){    extConn.close(); }
    }
    
    return delReqList.size();
}
```

##### 9-2-2. ibatis transaction
```
CommonDaoImpl  acpsDao = daoFactory.getDao();
SqlMapClient acpsSqlClient = null;    // ibatis transaction commit/rollback위해 추가 : acpsSqlClient.update()호출용    
int delReqUpdateCnt = 0;
try{
    acpsSqlClient = acpsDao.getSqlMapClient();    
    acpsSqlClient.startTransaction();
    acpsSqlClient.getCurrentConnection().setAutoCommit(false); //중요! auto-commit=false
    
    delReqUpdateCnt = acpsSqlClient.update("DAE_MFAW.updateFhl01MfcsStatusByMawb", index_0_map);
    
    if(delReqSize!=delReqUpdateCnt){
        //SqlMapClient commit없이 종료하면 rollback됨!
        throw new BizException("삭제 가능한 대상이 변경되었습니다. 다시 시도하세요(삭제가능 건수:" + delReqUpdateCnt + ")");
    }else{
        acpsSqlClient.commitTransaction();
        acpsSqlClient.getCurrentConnection().setAutoCommit(true); //auto-commit=false 해제
        acpsSqlClient.getCurrentConnection().commit();  //중요! ibatis auto-commit 사용시 Spring-IOC 버그?로인해 강제로 commit필요
    }
}finally{
    acpsSqlClient.endTransaction();
}    
```
<br>

#### 10. 스프링 비동기 처리 @Async
- 참고: http://dveamer.github.io/java/SpringAsync.html
- 설명: ThreadPool로 Thread갯수를 조정하면서 비동기 메소드 호출
- https://github.com/jukyellow/java-spring-jquery-javascript-css-note/blob/master/back-end(java%2Cspring)/02_Spring/10_Aysnc_ThreadPool.java  

