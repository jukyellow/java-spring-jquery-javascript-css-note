# 화면 위치 정렬과 이미지 정렬, 팝업

### 1. 화면 위치 정렬(가운데 정렬) 
> 참고: https://firerope.tistory.com/4  
![image](https://user-images.githubusercontent.com/45334819/93689685-d8478800-fb0b-11ea-9d84-90988d88ddf4.png)  

### 2. 이미지 가운데 정렬 css
``` css
.thumbnail-wrappper { width: 25%; } 

.thumbnail { 
	position: relative; 
	padding-top: 100%; /* 1:1 ratio */ 
	overflow: hidden; } 
	
.thumbnail .centered { 
	position: 
	absolute; 
	top: 0; 
	left: 0; 
	right: 0; 
	bottom: 0; 
	-webkit-transform: translate(50%,50%); 
	-ms-transform: translate(50%,50%); 
	transform: translate(50%,50%); 
} 

.thumbnail .centered img {
	position: absolute; 
	top: 0; 
	left: 0; 
	max-width: 100%; 
	height: auto; 
	-webkit-transform: translate(-50%,-50%); 
	-ms-transform: translate(-50%,-50%); 
	transform: translate(-50%,-50%); 
}
```

### 3. 이미지 팝업  
```html 
<body>
<img id="imgControll" name="imgControll" src="https://c1.staticflickr.com/8/7166/6616140965_81faceea87_b.jpg" width="150" height="100" onclick="fnImgPop(this.src)">
 </body>

<script type="text/javascript">
 function fnImgPop(url){
  var img=new Image();
  img.src=url;
  var img_width=img.width;
  var win_width=img.width+25;
  var img_height=img.height;
  var win=img.height+30;
  var OpenWindow=window.open('','_blank', 'width='+img_width+', height='+img_height+', menubars=no, scrollbars=auto');
  OpenWindow.document.write("<style>body{margin:0px;}</style><img src='"+url+"' width='"+win_width+"'>");
 }
</script>
```
