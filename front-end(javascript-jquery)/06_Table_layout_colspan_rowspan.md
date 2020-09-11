# html table 정리  

### table layout
> 참고 : https://m.blog.naver.com/PostView.nhn?blogId=inyoung313&logNo=220586946742&proxyReferer=https:%2F%2Fwww.google.co.kr%2F

![image](https://user-images.githubusercontent.com/45334819/92977557-436bdb80-f4c8-11ea-8caf-29740b39c359.png)


<br>

### colspan, rowspan
> 참고: https://zetawiki.com/wiki/HTML_%ED%85%8C%EC%9D%B4%EB%B8%94_colspan,_rowspan

```
<style>  
table { border-collapse:collapse; }  
th, td { border:1px solid black; }
</style>
<table>
	<tr>
		<th rowspan='2'>구분</th>
		<th colspan='3'>내행성</th>
		<th colspan='5'>외행성</th>
	</tr>
	<tr>
		<th>수성</th>
		<th>금성</th>
		<th>지구</th>
		<th>화성</th>
		<th>목성</th>
		<th>토성</th>
		<th>천왕성</th>
		<th>해왕성</th>
	<tr>
		<th>지름비</th>
		<td>0.383</td>
		<td>0.949</td>
		<td>1.000</td>
		<td>0.532</td>
		<td>11.209</td>
		<td>9.449</td>
		<td>4.007</td>
		<td>3.883</td>
	</tr>
</table>
```
![image](https://user-images.githubusercontent.com/45334819/92977488-20412c00-f4c8-11ea-81a6-349f8abd558d.png)

