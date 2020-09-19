# 자바스크립트 차트(네이버 오픈소스: 빌보드차트)

- URL : https://naver.github.io/billboard.js/release/latest/doc/index.html  
- 필수파일: d3.js, billboard.js, billboard.css  

```javascript
// css import
import "billboard.js/dist/theme/insight.css";
import bb from "billboard.js";

var chart = bb.generate({
  data: {
    columns: [
	//["data1", 30]
	//,["data2", 120]
    ],
    type: "pie",
    onclick: function(d, i) {
	console.log("onclick", d, i);
   },
    onover: function(d, i) {
	console.log("onover", d, i);
   },
    onout: function(d, i) {
	console.log("onout", d, i);
   }
  },
  bindto: "#pieChart"
});

//timeout 중요
setTimeout(function() {
	chart.load({
		columns: [
			["top1-21", 0.7],
			["top2-01", 0.2],
			["top3-95", 0.1],
		]
	});
}, 1500);

// setTimeout(function() {
// 	chart.unload({ ids: "data1" });
// 	//chart.unload({ ids: "data2" });
// }, 2500);
```

