package com.nemo.kr.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import javax.xml.bind.DatatypeConverter;
import org.apache.commons.codec.binary.Base64;


public class FdeskUtils{

	public static String creatHtmlImage(String imagePath) throws Exception {
		//String urltext = "http://chart.apis.google.com/chart?cht=tx&chl=2+2%20\\frac{3}{4}";

		String urltext = imagePath;
		URL url = new URL(urltext);
		BufferedInputStream bis = new BufferedInputStream(url.openStream());
		byte[] imageBytes = new byte[0];

		for(byte[] ba = new byte[bis.available()];  bis.read(ba) != -1;) {

			//System.out.println(imageBytes.length);
			//System.out.println(ba.length);

			byte[] baTmp = new byte[imageBytes.length + ba.length];

			System.arraycopy(imageBytes, 0, baTmp, 0, imageBytes.length);
			System.arraycopy(ba, 0, baTmp, imageBytes.length, ba.length);
			imageBytes = baTmp;
		}

		String imgHtml = "data:image/png;base64," + DatatypeConverter.printBase64Binary(imageBytes) ; 
		//System.out.println(imgHtml);
		return imgHtml;
	}

	/* 
  public static String creatHtmlImage2(String imagePath) throws Exception {
	    //String urltext = "http://chart.apis.google.com/chart?cht=tx&chl=2+2%20\\frac{3}{4}";


	    //String urltext = imagePath;
	   // URL url = new URL(urltext);

	    FileInputStream inputStream = null; // 파일 스트림
		BufferedInputStream bis = null; // 버퍼 스트림

		inputStream = new FileInputStream(imagePath);// 파일 입력 스트림 생성
		bis = new BufferedInputStream(inputStream);// 파일 출력 스트림 생성


	    byte[] imageBytes = new byte[0];
	    for(byte[] ba = new byte[bis.available()];
	        bis.read(ba) != -1;) {
	        byte[] baTmp = new byte[imageBytes.length + ba.length];
	        System.arraycopy(imageBytes, 0, baTmp, 0, imageBytes.length);
	        System.arraycopy(ba, 0, baTmp, imageBytes.length, ba.length);
	        imageBytes = baTmp;
	    }

	    return "data:image/png;base64," + DatatypeConverter.printBase64Binary(imageBytes) ;
	}
	 */

	public static String getBase64String( String imageUrl ) throws Exception{

		String content = "";
		String imageString;

		String filePathName = imageUrl.replace("file:///", "");
		String fileExtName = filePathName.substring( filePathName.lastIndexOf(".") + 1);

		FileInputStream inputStream = null;
		ByteArrayOutputStream byteOutStream = null;

		try
		{
			File file = new File( filePathName );

			if( file.exists() ){
				inputStream = new FileInputStream( file );
				byteOutStream = new ByteArrayOutputStream();

				int len = 0;
				byte[] buf = new byte[1024];
				while( (len = inputStream.read( buf )) != -1 ) {
					byteOutStream.write(buf, 0, len);
				}

				byte[] fileArray = byteOutStream.toByteArray();
				imageString = new String( Base64.encodeBase64( fileArray ) );

				content = "data:image/"+ fileExtName +";base64, "+ imageString;

			}
		}
		catch( IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			inputStream.close();
			byteOutStream.close();
		}


		return content;
	}


	public static void main(String[] args) throws Exception {
		//String imageHtml = creatHtmlImage("http://chart.apis.google.com/chart?cht=tx&chl=2+2%20\\frac{3}{4}");
		//  String imageHtml = creatHtmlImage2("http://chart.apis.google.com/chart?cht=tx&chl=2+2%20\\frac{3}{4}");
		//  System.out.println(imageHtml);
		//C:/workspace-sts-3.5.0/ilgaja/src/main/webapp/resources/fdesk/content/
		//D:/WEB_workspace-sts-2017/ilgaja
		//  System.out.println( getBase64String("D:/WEB_workspace-sts-2017/ilgaja/src/main/webapp/resources/fdesk/content/images/page00001/backgnd.png"));
		System.out.println( getBase64String("C:/workspace-sts-3.5.0/ilgaja/src/main/webapp/resources/fdesk/content/images/page00001/backgnd.png"));
	}
}