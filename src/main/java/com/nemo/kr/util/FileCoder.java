package com.nemo.kr.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletResponse;


/**
 * @FileName : FileCoder.java
 * @Project : ilgaja
 * @Date : 2021. 2. 15. 
 * @작성자 : Jangjaeho
 * @변경이력 :
 * @프로그램 설명 : 파일 암호화
 */
public class FileCoder {


	private static final String algorithm = "AES";
	private static final String transformation = algorithm + "/ECB/PKCS5Padding";
	private String strKey = "";
	
	private Key key;

	public FileCoder(String filePath) {
		
		strKey = FileUtil.readLine(filePath) ;
		
		this.key = new SecretKeySpec(toBytes(strKey, 16), algorithm);

	}

	/**
	 * <p>원본 파일을 암호화해서 대상 파일을 만든다.</p>
	 *
	 * @param source 원본 파일
	 * @param dest 대상 파일
	 * @throws Exception
	 */
	public void encrypt(File source, File dest) throws Exception {

		crypt(Cipher.ENCRYPT_MODE, source, dest);

	}


	/**
	 * <p>원본 파일을 복호화해서 대상 파일을 만든다.</p>
	 *
	 * @param source 원본 파일
	 * @param dest 대상 파일
	 * @throws Exception
	 */
	public void decrypt(File source, File dest) throws Exception {
		crypt(Cipher.DECRYPT_MODE, source, dest);
	}

	
	/**
	 * <p>원본 파일을 암/복호화해서 대상 파일을 만든다.</p>
	 *
	 * @param mode 암/복호화 모드
	 * @param source 원본 파일
	 * @param dest 대상 파일
	 * @throws Exception
	 */

	private void crypt(int mode, File source, File dest) throws Exception {

		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(mode, key);
		InputStream input = null;
		OutputStream output = null;

		try {

			input = new BufferedInputStream(new FileInputStream(source));
			output = new BufferedOutputStream(new FileOutputStream(dest));
			byte[] buffer = new byte[1024];
			int read = -1;
			while ((read = input.read(buffer)) != -1) {
				output.write(cipher.update(buffer, 0, read));
			}

			output.write(cipher.doFinal());
			
		} finally {

			if (output != null) {
				try { output.close(); } catch(IOException ie) {}
			}

			if (input != null) {
				try { input.close(); } catch(IOException ie) {}
			}
		}

	}
		

	/**
	  * @Method Name : decryptWeb
	  * @작성일 : 2021. 2. 23.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 파일을 복호화 해서 웹으로  response 한다.
	
	  */
	public void decryptWeb(int mode, File source, HttpServletResponse response) throws Exception {

		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(mode, key);
		InputStream input = null;
		OutputStream output = null;

		try {

			input 	= new BufferedInputStream(new FileInputStream(source));
			output 	= new BufferedOutputStream(response.getOutputStream());
			
			byte[] buffer = new byte[1024];
			int read = -1;
			while ((read = input.read(buffer)) != -1) {
				output.write(cipher.update(buffer, 0, read));
			}
			output.write(cipher.doFinal());

		} finally {

			if (output != null) {
				try { output.close(); } catch(IOException ie) {}
			}

			if (input != null) {
				try { input.close(); } catch(IOException ie) {}
			}
		}

	}
	

	public byte[] decryptByte(File file) throws Exception, IOException {
		/*
		if (file.length() > MAX_FILE_SIZE) {
		    throw new FileTooBigException(file);
		}
		*/
		
		Cipher cipher = Cipher.getInstance(transformation);
		cipher.init(Cipher.DECRYPT_MODE, key);
		
	    ByteArrayOutputStream ous = null;
	    InputStream ios = null;
	    try {
	        byte[] buffer = new byte[1024];
	        
	        ous = new ByteArrayOutputStream();
	        ios = new FileInputStream(file);
	        int read = 0;
	        while ((read = ios.read(buffer)) != -1) {
	            ous.write(buffer, 0, read);
	        }
	    }finally {
	        try {
	            if (ous != null)
	                ous.close();
	        } catch (IOException e) {
	        }

	        try {
	            if (ios != null)
	                ios.close();
	        } catch (IOException e) {
	        }
	    }
	    return ous.toByteArray();
	}
	
	
	public static void main(String[] args) throws Exception {

		// 128비트의 키

		SecretKeySpec key = new SecretKeySpec(toBytes("196d697373796f7568616e6765656e61", 16), algorithm);

		String filePath = "";
		FileCoder coder = new FileCoder("D:/secretKey/fileSecretKey.txt");

		coder.encrypt(new File("D:/ilgajawebdata/temp/1.jpg"), new File("C:/data/ilgajawebdata/upload/upload202003/worker/20210215190215_92b43853-4d83-41c6-8d2f-fdcc2ef67127.jpg"));
		//coder.decrypt(new File("C:/SDB/201904221127270.jpg"), new File("C:/SDB/111.jpg"));
		
		//coder.decrypt(new File(args[0]), new File(args[1]));

	}



	/**

	 * <p>문자열을 바이트배열로 바꾼다.</p>

	 *

	 * @param digits 문자열

	 * @param radix 진수

	 * @return

	 * @throws IllegalArgumentException

	 * @throws NumberFormatException

	 */

	public static byte[] toBytes(String digits, int radix) throws IllegalArgumentException, NumberFormatException {

		if (digits == null) {

			return null;

		}

		if (radix != 16 && radix != 10 && radix != 8) {

			throw new IllegalArgumentException("For input radix: \"" + radix + "\"");

		}

		int divLen = (radix == 16) ? 2 : 3;

		int length = digits.length();

		if (length % divLen == 1) {

			throw new IllegalArgumentException("For input string: \"" + digits + "\"");

		}

		length = length / divLen;

		byte[] bytes = new byte[length];

		for (int i = 0; i < length; i++) {

			int index = i * divLen;

			bytes[i] = (byte)(Short.parseShort(digits.substring(index, index+divLen), radix));

		}

		return bytes;

	}





}
