# java 압축 (파일명 한글깨짐 방지)  

- 참고 : https://veloper.tistory.com/57  

``` maven
<!-- https://mvnrepository.com/artifact/org.apache.commons/commons-compress -->
<dependency>
<groupId>org.apache.commons</groupId>
<artifactId>commons-compress</artifactId>
<version>1.8</version>
</dependency>
```

``` java
//(20.12.03,juk) csv파일을 압축(zip)하여 다운로드
private void createZipFile(File csvFile, String fullPathName) {
  ArchiveOutputStream archgOut = null;
  FileOutputStream fileOut = null;
  BufferedInputStream inStream = null;

  try {
    if (!csvFile.isFile()) { return ; }

    fileOut = new FileOutputStream(fullPathName);
    archgOut = new ArchiveStreamFactory().createArchiveOutputStream("zip", fileOut);
    ZipArchiveEntry entry = new ZipArchiveEntry(csvFile.getName()); //ZipEntry사용시 한글파일명 깨짐
    archgOut.putArchiveEntry(entry);

    inStream = new BufferedInputStream(new FileInputStream(csvFile));

    byte[] data = new byte[2048]; 
    int read = 0;
    while ((read = inStream.read(data, 0, 2048)) != -1){
      archgOut.write(data, 0, read);
    }

    archgOut.flush();
    System.out.println("Zip파일 생성성공:"+ fullPathName);
  } catch (FileNotFoundException e) {
    e.printStackTrace();
  } catch (IOException e) {
    e.printStackTrace();
  } catch (ArchiveException e) {
    e.printStackTrace();
  } finally {
    if(archgOut!=null){
      try{ archgOut.closeArchiveEntry(); }
      catch(Exception ee){ ee.printStackTrace(); }
    }
    if(inStream!=null){
      try{ inStream.close(); }
      catch(Exception ee){ ee.printStackTrace(); }
    }
    if(archgOut!=null){
      try{ archgOut.close(); }
      catch(Exception ee){ ee.printStackTrace(); }
    }
    if(fileOut!=null){
      try{ fileOut.close(); }
      catch(Exception ee){ ee.printStackTrace(); }
    }
  }
}
```
