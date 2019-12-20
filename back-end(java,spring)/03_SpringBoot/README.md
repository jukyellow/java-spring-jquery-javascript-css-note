# Spring Boot

#### 1. Error 처리  
- Rest API 요청시 잘못된 URL(mapping controller 없음)인 경우 404(NOT FOUND) 오류 발생(BasicErrorController)  
- customizing하는 경우, 아래와 같이 DefaultErrorAttributes를 상속받아서 override하여 처리가능  
- https://github.com/jukyellow/java-spring-jquery-javascript-css-note/blob/master/back-end(java%2Cspring)/03_SpringBoot/01_CustomErrorAttributes.java  
![image](https://user-images.githubusercontent.com/45334819/71294188-12d49680-23bb-11ea-8582-001636750fc3.png)  

