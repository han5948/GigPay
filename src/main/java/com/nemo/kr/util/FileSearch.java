package com.nemo.kr.util;

import java.io.File;
import java.io.IOException;

import com.nemo.kr.common.Const;

public class FileSearch {

	public void subDirList(String path, String dPath){

		File dir = new File(path); 
		File[] fileList = dir.listFiles();
		
		
		
		try{

			for(int i = 0 ; i < fileList.length ; i++){

				File file = fileList[i]; 

				if(file.isFile()){

					System.out.println("-------------------------");
					String destPath = dPath + "/" + file.getParentFile().getParentFile().getName() + "/" + file.getParentFile().getName();
					
					File tempDir = new File(destPath);
					if ( !tempDir.exists() ) {
						//저장할 디렉토리가 없을 경우 디렉토리 생성
						tempDir.mkdirs();
					}

					
					// 파일이 있다면 파일 이름 출력
					String fileName =  file.getName();
					
					System.out.println("\t 파일 이름 = "+ fileName);
					System.out.println("\t 암호화 : " + destPath);
							
					if ( isAllowedExtension(fileName)) {
						//File source = new File(sourcePath);
						File dest = new File(destPath+"/" + fileName);
					FileCoder coder = new FileCoder("D:/secretKey/fileSecretKey.txt");
						coder.encrypt(file, dest);
						
					}else {
						System.out.println("이미지 파일이 아님");
					}
				
				}else if(file.isDirectory()){
					
					
					System.out.println("디렉토리 이름 = " + file.getName());

					// 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색

					subDirList(file.getCanonicalPath().toString(), dPath); 

				}

			}

		}catch(IOException e){
				

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public  boolean isAllowedExtension(String fileName) {

		String[] allowedExtensions = {"bmp", "jpg", "jpeg", "gif", "png"};

		String fileExt = getFileExt(fileName);
		for ( String allowedExt : allowedExtensions ) {
			if ( fileExt.equalsIgnoreCase(allowedExt) ) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * 파일 확장자 get
	 * @param fileName
	 * @return String
	 */
	public  String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}

	
	/**
	  * @Method Name : main
	  * @작성일 : 2021. 3. 5.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 암호화할 디렉토리 , 암호화 파일 저장 디렉토리
	
	  */
	public static void main(String[] args) {
		
		System.out.println(args[0]);
		System.out.println(args[1]);
		
		FileSearch fs = new FileSearch();
		fs.subDirList(args[0], args[1]);
		
		/*
		File tempDir = new File("D:/data/encdata/upload/upload202003/worker/");
		if ( !tempDir.exists() ) {
			//저장할 디렉토리가 없을 경우 디렉토리 생성
			tempDir.mkdirs();
		}
		
		File source = new File("D:/data/ilgajawebdata/upload/upload202003/worker/202004061526491.jpg");
		File dest = new File("D:/data/encdata/upload/upload202003/worker/202004061526491.jpg");
		FileCoder coder = new FileCoder();
		try {
			//coder.encrypt(source, dest);
			
			coder.decrypt(dest,new File("D:/data/encdata/upload/upload202003/worker/1.jpg"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		//String a = "D:\\data\\encdata\\upload\\upload202101\\worker\\";
		//System.out.println(a.replaceAll("\\\\","/"));
	}
}
