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
