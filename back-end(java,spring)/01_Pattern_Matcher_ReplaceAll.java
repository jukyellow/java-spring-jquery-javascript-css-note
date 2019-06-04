//Patternm,Matcher since JDK 1.4

String originStr= "TEST/ ABC-STR 111 OK!";
Pattern p = Pattern.compile("[^a-zA-Z0-9]*"); //숫자, 알파벳이 아닌 패턴만 
Matcher m = p.matcher(errMsg);

String newStr = m.replaceAll(""); //공백으로 치환

System.out.println("originStr:"+originStr);
System.out.println("newStr   :"+newStr);
