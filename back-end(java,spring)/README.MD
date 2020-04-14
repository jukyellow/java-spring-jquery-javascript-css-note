# Java & Spring

#### 1. 영문,숫자를 제외한 특수문자는 공백으로 치환(정규식을 사용하는 방법)   
- https://github.com/jukyellow/spring-jquery-css-note/blob/master/back-end(java%2Cspring)/01_Pattern_Matcher_ReplaceAll.java  
- 정규식 관련 정리: https://www.evernote.com/shard/s222/sh/4b4bd0fd-da5a-4299-957f-f5373e269dbd/6e9caa0849178198e77c934cc3f86f03  
![image](https://user-images.githubusercontent.com/45334819/58895126-34777480-872e-11e9-992b-334688d44d07.png)  

#### 2. Spring (https://github.com/jukyellow/spring-jquery-css-note/tree/master/back-end(java%2Cspring)/02_Spring)  
2-1) AOP  
2-2) Logging  
2-3) Reflection  
2-4) Annotation  
2-5) enum  
2-6) Log4j Filter  
2-7) Dynamic Log4j  
2-8) xml (load->list/map)  
2-9) Transaction  
2-10) Async

#### 3. Jsoup XML paring & Get Http Rest Request/Response
``` java
String xml = getHttpRestData();
Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
```
- https://github.com/jukyellow/java-spring-jquery-javascript-css-note/blob/master/back-end(java%2Cspring)/02_XmlJsoupTestMain.java  

#### 4. Exception Overriding, printStackTrace + 날짜추가  
https://github.com/jukyellow/java-spring-jquery-javascript-css-note/blob/master/back-end(java%2Cspring)/3_StackTraceException.java  

#### 5. API Server Polling  

#### 6. Spring Boot (https://github.com/jukyellow/java-spring-jquery-javascript-css-note/tree/master/back-end(java%2Cspring)/03_SpringBoot)


#### 7. Java 프로세스 안전하게 종료시키기 
- 자바는 이런 문제를 방지하기 위해서 1.3 버전부터 '셧다운 후크(Shutdown Hook)'라는 기능을 도입하였다. 셧다운 후크는 JVM이 셧다운될 때에 셧다운 이벤트를 가로채서 어떤 코드를 수행한 후 최종적으로 셧다운 되도록 한다. 참고로, JVM은 다음과 같은 상황인 경우 셧다운 이벤트를 발생시킨다.  
> 정상적으로 종료될 때. 예) 마지막 논데몬 쓰레드가 종료되었거나 System.exit()가 호출될 때.  
> JVM이 강제적으로 종료될 때. 예) 시스템 셧다운, 사용자 로그아웃, Ctrl+C 누름  
- Sutdownhook 동작과 리소스해제 작업 과정 설명
> 1)Java Signal 수신(kill 기본 옵션 -15, SIGTERM:정상종료) 또는 JVM 종료
> 2)Non-daemon Thread 종료  
> 3)정상적인 종료(SIGTERM, System.exit() 등)인 경우에만 ShutdownHook 수행  
> 4)ShutdownHook 메소드 종료전까지 프로세스는 종료되지 않음  
> 5)ShutdownHook 메소드 종료시, 리소스 clear작업 수행  
```
Runtime.getRuntime().addShutdownHook(new Thread(instance));

@Override
public void run() {
  //stop 처리...
}
```
- 샘플(spring framework) 소스: https://github.com/jukyellow/java-spring-jquery-javascript-css-note/tree/master/back-end(java%2Cspring)/java-stop-signal   

