# NPM 설치 및 http-server 설치

### NPM(node package manager)
- npm 설치파일 다운로드: https://nodejs.org/en/download/  
- 초간단 설치 설명: https://hello-bryan.tistory.com/95  

- 설치버전 확인  
```
npm -v
node -v
```

### http-server 설치
```
npm install http-server -g (-g:전역으로 설치?)
- http-server 실행
npx http-server
- 특정경로를 http-server로 배포
npx http-server path
- http-server auto refresh(reload,deploy) : -c-1  disable cache
npx http-server path -c-1
```

### CORS 문제 해결 (자바스크립트 모듈구조 사용시 발생하는 문제)
https://velog.io/@takeknowledge/%EB%A1%9C%EC%BB%AC%EC%97%90%EC%84%9C-CORS-policy-%EA%B4%80%EB%A0%A8-%EC%97%90%EB%9F%AC%EA%B0%80-%EB%B0%9C%EC%83%9D%ED%95%98%EB%8A%94-%EC%9D%B4%EC%9C%A0-3gk4gyhreu  

- http-sever를 구동시켜, 서버에서 배포  
: module 기능으로 구동시 import 경로문제로 인해 발생->서버방식으로 구동시 같은 서버에서 발생한 요청으로 간주!  

