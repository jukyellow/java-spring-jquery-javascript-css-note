<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<script
  src="https://code.jquery.com/jquery-2.2.4.min.js"
  integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
  crossorigin="anonymous"></script>
<script>
$(document).ready(function(){
	fn_setAllowAlphaNumericByKeyEvent($("#NEW_FILTER_INPUT"), "10");
	fn_setAllowAlphaNumericBasic($("#BASIC_INPUT"));
});

//dash(-) 포함하여 maxlength(12)자리까지 입력받은뒤, 숫자/알파벳만 최대자릿수(MAX_LENGTH:10)까지 허용
function fn_setAllowAlphaNumericByKeyEvent(jInputObj, MAX_LENGTH){
    jInputObj.keydown(function(event){ //jInputObj: Jquery Input Object
        var preInputVal = jInputObj.val();
        
        var isAllowedKeyDown = null;
        if(preInputVal.length >= MAX_LENGTH){
            var keyCode = event.keyCode ? event.keyCode : event.which; //which:FireFox
            
            if(keyCode==13 || keyCode==8 || keyCode==46 || keyCode==37 || keyCode==39){
            	//enter:13, backspace=8, delete=46, <-:37, ->:39
                isAllowedKeyDown = true;
            }else if(event.ctrlKey && (event.keyCode==86 || event.keyCode==67 || event.keyCode==65)){
            	//Ctrl + v, Ctrl + c, Ctrl + a
                isAllowedKeyDown = true;
            } else{ 
                isAllowedKeyDown = false; //prevent key-input  
            }
        }else{
            isAllowedKeyDown = true;
        }
        
        return isAllowedKeyDown;
    });
     
    jInputObj.keyup(function(event){
        var currValidVal = jInputObj.val().toUpperCase().replace(/[^0-9A-Z]/g,''); //숫자,알파벳 외에는 공백으로 치환
        
        if(jInputObj.val()!=currValidVal || currValidVal.length>MAX_LENGTH){
            jInputObj.val(currValidVal.substr(0,MAX_LENGTH)); //jquery.val(값) 수행시 IE에서는 커서 포커스가 reset되어 key-move가 안됨으로 val세팅 최소화
        }
    });
}

function fn_setAllowAlphaNumericBasic(jInputObj){
	jInputObj.keydown(function(event){
		var currValidVal = jInputObj.val().toUpperCase().replace(/[^0-9A-Z]/g,'');
		jInputObj.val(currValidVal);
	});
}
</script>
<style>
    /* input box 우측의 자동생성 x버튼 제거: 마우스 포커스시 backspace 안먹히는것처럼 오해할수 있음 */
    input[type=text]::-ms-clear { display:none; }
</style>
</head>
<body>
	<p>1. inputbox 필터링 및 maxlength 별도설정 기능</p>
	<p>2. input box 우측의 자동생성 x버튼 제거: 마우스 포커스시 backspace 오류로 오해할 수 있음</p>
	<hr />
	
	<div>
		<p>데모</p>
		<table>
			<tr>
				<td>검색어 입력 basic(알파,숫자 포함 최대 10자리까지 입력)</td>
				<td><input type="text" id="BASIC_INPUT" maxlength="10" /></td>
				<td>(ex. 123-456 => 123-456)</td>
			</tr>
			<tr>
				<td>검색어 입력 NEW(basic기능 + Ctrl+V로 붙여넣기시, Dash(-)제거하고 입력)</td>
				<td><input type="text" id="NEW_FILTER_INPUT" maxlength="12" /></td>	
				<td>(ex. 123-456-7890 => 1234567890)</td>
			</tr>			
		</table>
	</div>
</body>
</html>