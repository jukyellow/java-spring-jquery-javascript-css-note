<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<script
  src="https://code.jquery.com/jquery-2.2.4.min.js"
  integrity="sha256-BbhdlvQf/xTY9gja0Dq3HiwQF8LaCRTXxZKRutelT44="
  crossorigin="anonymous"></script>
<script>
var javaJs={};
javaJs.util={};

$(document).ready(function(){
	fn_setJavaHashmap();
	var hashMapJs = new javaJs.util.HashMap();
	
	$("#put_hashmap").click(function(){
		var inputKey = $("#input_key").val();
		var inputVal = $("#input_val").val();		
		hashMapJs.put(inputKey, inputVal);
	});
	
	$("#get_hashmap").click(function(){		
		var keyList = hashMapJs.keys();
		for(var key in keyList){			
			var mapVal = hashMapJs.get(keyList[key]);
			$("#hashmap_list").html($("#hashmap_list").html() + "<br>" + "key:"+keyList[key]+" , "+ "val:"+mapVal);
		}
	});
});

function fn_setJavaHashmap(){
	//외부 js파일로 분리해서 사용 필요
	(function(){
		/* Java HashMap */
		javaJs.util.HashMap = function(){   
		    this.map = new Array();
		};   
		javaJs.util.HashMap.prototype = {   
		    put : function(key, value){   
		        this.map[key] = value;
		    },   
		    get : function(key){   
		        return this.map[key];
		    },   
		    getAll : function(){   
		        return this.map;
		    },   
		    containsKey : function(key){    
		        return key in this.map; //true, false
		    },
		    containsValue : function(value){    
		        for(var prop in this.map){
		            if(this.map[prop] == value) return true;
		        }
		        return false;
		    },
		    clear : function(){   
		        this.map = new Array();
		    },   
		    isEmpty : function(){     
		         return (this.size() == 0);
		    },
		    remove : function(key){     
		         delete this.map[key];
		    },
		    toString : function(){
		        var temp = '';
		        for(i in this.map){   
		            temp = temp + ',' + i + ':' +  this.map[i];
		        }
		        temp = temp.replace(',','');
		          return temp;
		    },
		    keys : function(){   
		        var keys = new Array();   
		        for(var prop in this.map){   
		            keys.push(prop);
		        }   
		        return keys;
		    },
		    values : function(){   
		     var values = new Array();   
		        for(var prop in this.map){   
		         values.push(this.map[prop]);
		        }   
		        return values;
		    },
		    size : function(){
		        var count = 0;
		        for (var prop in this.map) {
		            count++;
		        }
		        return count;
		    }
		};
	}());
}

</script>
<style>
</style>
</head>
<body>
	<p>1. Java HashMap(key, value)을 Javascript로 구현</p>
	<hr />
	
	<div>
		<p>데모</p>
		<table>
			<tr>
				<td>입력 key : <input type="input" id="input_key" maxlength=10 /></td>
				<td>입력 Value : <input type="input" id="input_val" maxlength=10 /></td>
				<td><input type="button" id="put_hashmap" value="HashMap Put(입력)" /></td>
			</tr>			
			<tr><td colspan="2"><br></td></tr>	 
			<tr>
				
				<td><p>HashMap 목록:</p> <input type="button" id="get_hashmap" value="HashMap Get(꺼내기)" /></td>
				<td><span id="hashmap_list"></span></td>
			</tr>
			<tr>
				<td>(ex. key1:aaa, key2:bbb, key3:ccc)</td>
				<td></td>
			</tr>
		</table>
	</div>
</body>
</html>