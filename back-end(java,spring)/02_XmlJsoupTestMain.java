package xml.jsoup;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class XmlJsoupTestMain {
    private final String URL = "http://localhost/8080/rest/";
    private final String URI = "service?auth=TEST1234&searh=TEST";
    
    public static void main(String args[]){
        (new XmlJsoupTestMain()).process();
    }
    
    public void process(){        
        Document doc = null;
        
        try {
            String xml = getHttpRestData();
            doc = Jsoup.parse(xml, "", Parser.xmlParser());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        String epn = null;
        String blNo = null;
        Elements customsEpn = doc.getElementsByTag("expDclrNoPrExpFfmnBrkdQryRsltVo");
        if(customsEpn.size() > 0) {
            epn = customsEpn.get(0).getElementsByTag("expDclrNo").text();
            if(epn != null && epn.length() > 0) { 
                Elements customsEpnBl = doc.getElementsByTag("expDclrNoPrExpFfmnBrkdDtlQryRsltVo");                
                if(customsEpnBl.size() > 0) {
                    blNo = customsEpnBl.get(0).getElementsByTag("blNo").text();
                }
            }
        }
        
        System.out.println("epn:"+epn+",blNo:"+blNo);
    }
 
    private String getHttpRestData() throws HttpException, IOException{
        MultiThreadedHttpConnectionManager connectionManager =  new MultiThreadedHttpConnectionManager();
        HttpConnectionManagerParams params = connectionManager.getParams();
        params.setConnectionTimeout(5000); // set connection timeout (how long it takes to connect to remote host)
        params.setSoTimeout(5000); // set socket timeout (how long it takes to retrieve data from remote host)

        HttpClient httpClient = new HttpClient(connectionManager);
        httpClient.getParams().setParameter("http.connection-manager.timeout", 5000L); // set timeout on how long we’ll wait for a connection from the pool

        HttpMethodBase baseMethod = new GetMethod(URL+URI);
        httpClient.executeMethod(baseMethod);

        return new String(baseMethod.getResponseBodyAsString().getBytes("8859_1"), "utf-8");
    }
}