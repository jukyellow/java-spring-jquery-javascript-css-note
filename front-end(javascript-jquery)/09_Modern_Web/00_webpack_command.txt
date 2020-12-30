# webpack 설치명령어, 참고사이트, 설정파일 샘플

### 참고 사이트
1-0. webpack 학습가이드 추천: https://joshua1988.github.io/webpack-guide/guide.html  
1-1. webpack 기본 사용법: https://www.daleseo.com/webpack-basics/  
1-2. webpack config 기본 사용법: https://www.daleseo.com/webpack-config/  
2-1. ES5/ES6 차이점: https://ui.toast.com/fe-guide/ko_ES5-TO-ES6  
2-2. webpack bundler 상세 사용법: https://ui.toast.com/fe-guide/ko_BUNDLER  

### 설치 명령어
```
1) webpack 설정 설치
>npm init -y
2) 기타 모듈 설치
>npm i -S moment
3) webpack, webpack-cli 설치
>npm i -D webpack
>npm i -D webpack-cli
4) Webpack Bundle 만들기
>src,dist 경로 생성
>src밑에 js파일 등 번들할 파일 넣기
>npx webpack
>dist밑에 main.js 생성 및html에 경로변경

5)webpack config 사용
>webpack.config.js 파일생성
>각종설정(entry,output등 설정)
>npx webpack
6)Loader 설정
>npm i --D style-loader css-loader

7)package.json 및 webpack.config.js 추가설정
-개발/운영 배포모드 적용( package.json 설정추가 : https://ui.toast.com/fe-guide/ko_BUNDLER 참고)
npm run bundle
npm run production

8)플러그인 설치
npm install --save-dev html-webpack-plugin clean-webpack-plugin

9)정적분석 도구
npx eslint --init
npm install -save-dev eslint eslint-loader
```


### 설정파일
- package.json  
```
"scripts": {
"bundle": "webpack --watch --mode=development",
"production": "webpack --watch --mode=production"
},
```
- webpack.config.js  
```
const path = require('path');
const webpack = require('webpack');

module.exports = {
entry: './src/model_api.js',
output: {
path: path.resolve(__dirname, 'dist'),
filename: 'main.js'
},
module: {
rules: [
{
test: /\.css$/,
use: ['css-loader', 'eslint-loader']
}
]
},
plugins: [
new webpack.HotModuleReplacementPlugin()
],
devServer: {
hot: true,
inline: true
},
devtool: 'source-map'
};
```
