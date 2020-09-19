# Javascript & Jquery

#### 1. Ctrl+C/V(붙여넣기)로 inputbox 입력시, filter(특수문자제거) 기능 추가  
- https://github.com/jukyellow/spring-jquery-css-note/blob/master/front-end(javascript-jquery)/01_InputboxMaxlengthImpl.jsp  
![image](https://user-images.githubusercontent.com/45334819/58353545-b43d4d80-7ea9-11e9-8d08-7df9c44269fc.png)  
<br>

#### 2. Java HashMap -> Javascript로 구현  
- https://github.com/jukyellow/spring-jquery-css-note/blob/master/front-end(javascript-jquery)/02_JavaHashmap2JS.jsp  
![image](https://user-images.githubusercontent.com/45334819/58353554-b8696b00-7ea9-11e9-831f-a4ca8fa94bd4.png)   다
<br>

#### 3. 크롬(Chrome) SelectBox 엔터시 DropDown메뉴 대신 화면조회(다른 기능수행) 하기
- 설명: 크롬 select box에서 엔터시 기본동작은, dropdown메뉴 표시이다.
- 소스설명: 콤보박스에서 항목을 선택하고, 엔터(enter)시 dropdown메뉴 대신에 다른기능(화면 조회등)을 적용할 수 있다.
- https://github.com/jukyellow/java-spring-jquery-javascript-css-note/blob/master/front-end(javascript-jquery)/03_Chrome_Dropdown_disable.html
``` html  
window.onload = function() {
    document.getElementById('keyPressDisable').onclick = function() {
        if(this.checked) { document.getElementById('testSelectBox').onkeypress = false; }  
        else { document.getElementById('testSelectBox').onkeypress = null; }  

        document.getElementById('testSelectBox').onkeyup = function(e) {
            var el = e.srcElement ? e.srcElement : e.target;
            if (e.which == 13) { //enter
                alert('선택 data: ' + el.value);
            }
        }
        document.getElementById('testSelectBox').focus();
    }
};  
```  
<br>

#### 4. Javascript Base64 UTF-8 Encode/Decode source
https://github.com/jukyellow/java-spring-jquery-javascript-css-note/blob/master/front-end(javascript-jquery)/04.Javascript_Base64_UTF8.js  
<br>

#### 5. Javascript 정규식을 활용한 data 포맷검증  
https://github.com/jukyellow/java-spring-jquery-javascript-css-note/blob/master/front-end(javascript-jquery)/05_%EC%9E%90%EB%B0%94%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8%20%EC%A0%95%EA%B7%9C%EC%8B%9D.md  
<br>

#### 6. Html Table 작업
https://github.com/jukyellow/java-spring-jquery-javascript-css-note/blob/master/front-end(javascript-jquery)/06_Table_layout_colspan_rowspan.md
<br>

#### 7. Html Location Layout + 이미지 가운데 정렬 + 이미지 팝업  
https://github.com/jukyellow/java-spring-jquery-javascript-css-note/blob/master/front-end(javascript-jquery)/07_position_layout.md
<br>

#### 8. 자바스크립트 차트(billboard chart)  
https://github.com/jukyellow/java-spring-jquery-javascript-css-note/blob/master/front-end(javascript-jquery)/08_chart.md  
<br>
