
//다국어(UTF-8)인 경우, 3byte씩 계산하고 추출시에는 char길이로 추출
public static int substrb(String source, StringBuffer targetSB, int beginCharIdx, int BYTE_DATA_LEN) throws UnsupportedEncodingException {
    if(source == null) {
        targetSB.append("");
        return 0;
    }
    if(source.substring(beginCharIdx).getBytes("UTF-8").length < BYTE_DATA_LEN) {
        targetSB.append(source.substring(beginCharIdx));
        return source.substring(beginCharIdx).length();
    }

    String tmp = source;
    int charLen = 0;
    int byteLen = 0;

    //byte길이가 문자열길이가 초과하지 않을때까지 char의 길이(갯수)를 구함
    while(byteLen < BYTE_DATA_LEN) {
        char ch = tmp.charAt(beginCharIdx + charLen); //시작 인덱스부터 진행
        byteLen++;
        charLen++;

        if(ch > 127) byteLen = byteLen + 2; //다국어이면, 2byte 추가(UTF-8인 경우 +2, EUC-KR인경우 +1)            
    }            

    tmp = tmp.substring(beginCharIdx, beginCharIdx + charLen); //자를때는 charCnt로 종료index(시작+길이)로 짜름
    //System.out.println("byteLen:"+ byteLen + ", charLen:"+charLen);

    targetSB.append(tmp);
    return charLen;
}
