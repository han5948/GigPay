package com.nemo.kr.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.nemo.kr.common.Const;
import com.nemo.kr.dto.FileDTO;

import net.coobird.thumbnailator.Thumbnails;


public class FileUtil{

	private static final int THUMB_PIXCEL = 660;

	public static boolean isMobile(HttpServletRequest request){
		boolean result = false;

		String userAgent = request.getHeader("User-Agent").toLowerCase();

		if ( userAgent.indexOf("android") > -1 ) {
			result = true;
		}
		if ( userAgent.indexOf("iphone") > -1 ) {
			result = true;
		}

		return result;
	}

	
	/**
	  * @Method Name : deleteFileInDir
	  * @작성일 : 2021. 2. 26.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 디렉토리 안에 파일만 삭제
	
	  */
	public static void deleteFileInDir(File fDir) {
		if ( !fDir.exists() ) {
			return;
		}

		File[] fList = fDir.listFiles();
		for ( int i = 0; i < fList.length; i++ ) {
			if ( fList[i].isDirectory() ) {
				deleteDirAll(fList[i]);
			} else {
				fList[i].delete();
			}
		}
	}

	/**
	  * @Method Name : deleteDir
	  * @작성일 : 2021. 2. 26.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 디렉토리 삭제
	
	  */
	public static void deleteDir(File fDir) {
		if ( !fDir.exists() ) {
			return;
		}

		File[] fList = fDir.listFiles();
		for ( int i = 0; i < fList.length; i++ ) {
			if ( fList[i].isDirectory() ) {
				deleteDirAll(fList[i]);
			} else {
				fList[i].delete();
			}
		}

		fDir.delete();
	}

	/**
	  * @Method Name : deleteDirAll
	  * @작성일 : 2021. 2. 26.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 디렉토리 안에 파일 삭제
	
	  */
	public static void deleteDirAll(File fDir) {
		if ( !fDir.exists() ) {
			return;
		}

		File[] fList = fDir.listFiles();
		for ( int i = 0; i < fList.length; i++ ) {
			if ( fList[i].isDirectory() ) {
				deleteDirAll(fList[i]);
			} else {
				fList[i].delete();
			}
		}

		fDir.delete();
	}

	/**
	  * @Method Name : deleteFile
	  * @작성일 : 2021. 2. 26.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 파일 삭제
	
	  */
	public static void deleteFile(File file) {
		boolean fileResult = false;

		while ( !fileResult ) {
			fileResult = file.delete();
		}
	}

	/**
	  * @Method Name : deleteFile
	  * @작성일 : 2021. 2. 26.
	  * @작성자 : Jangjaeho
	  * @변경이력 : 
	  * @Method 설명 : 파일 삭제
	
	  */
	public static void deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			file.delete();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}


	public static void copyFile(File fileTarget, File fileDest) {
		try {
			FileInputStream fileInputStream = new FileInputStream(fileTarget);
			FileOutputStream fileOutputStream = new FileOutputStream(fileDest);

			FileChannel fileChannelIn = fileInputStream.getChannel();
			FileChannel fileChannelOut = fileOutputStream.getChannel();

			long nSize = fileChannelIn.size();

			fileChannelIn.transferTo(0L, nSize, fileChannelOut);

			fileChannelOut.close();
			fileChannelIn.close();
			fileOutputStream.close();
			fileInputStream.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 파일 다운
	 * @param fileName
	 * @param request
	 * @param response
	 */
	public static void fileDown(String fileName, String path, String fileOrgName ,HttpServletRequest request, HttpServletResponse response) {
		try {
			String Agent=request.getHeader("USER-AGENT");
			//System.out.println("Agent : "+Agent);
			String docName = URLEncoder.encode(fileOrgName,"UTF-8");

			if ( Agent.indexOf("MSIE") >= 0 ) {  //IE
				int i = Agent.indexOf('M', 2);//두번째 'M'자가 있는 위치

				String IEV=Agent.substring(i+5, i+8);

				if ( IEV.equalsIgnoreCase("5.5") ) {
					response.setHeader( "content-disposition","filename="+docName+";");
				} else {
					response.setHeader( "content-disposition","attachment;filename="+docName+";");
				}
			} else if ( Agent.indexOf("Chrome") > -1 ) {

				StringBuffer sb = new StringBuffer();

				for ( int i = 0; i < fileOrgName.length(); i++ ) {
					char c = fileOrgName.charAt(i);
					if ( c > '~') {
						sb.append(URLEncoder.encode("" + c, "UTF-8"));
					} else {
						sb.append(c);
					}
				}
				docName = sb.toString();
				response.setHeader( "content-disposition","attachment;filename="+docName);
			} else {  //파이어 폭스, 사파리
				response.setHeader( "content-disposition","attachment;filename="+docName);
			}


			byte[] buf = new byte[4096];
			File file = new File(path+fileName);
			/*
				System.out.println("path+fileName : "+path+fileName);
				System.out.println("file.length() : "+file.length());
			 */
			if ( file.exists() ) {
				BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
				BufferedOutputStream outs = new BufferedOutputStream(response.getOutputStream());

				int read = 0;
				while( (read = fin.read(buf) ) != -1 ) {
					outs.write(buf,0,read);
				}
				outs.close();
				fin.close();
			}
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
	}




	/**
	 * 파일 업로드_에디터에 이미지 첨부 기능
	 * @param fileName
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static FileDTO editorImageFileUpload(HttpServletRequest request, String Path, String inputName) throws Exception {
		FileDTO fileDTO = new FileDTO();

		try {
			MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;
			MultipartFile thumbName = multiReq.getFile(inputName); //실제 넘어 온 파일

			String tempFileOriName = thumbName.getOriginalFilename();
			String _date = DateUtil.getNowDate("yyyyMMddHH");

			//String saveDirPath = Path+"/"+_date+"/";    // 기본 디렉토리에 날짜 조합(연월일시)으로 디렉토리 생성
			String saveDirPath = Path ;
			String saveFileExt = tempFileOriName.substring(tempFileOriName.lastIndexOf(".") + 1).toLowerCase();
			String saveFileName =DateUtil.getNowDate("yyyyMMddHHmmss")+ "t." + saveFileExt;

			File dir = new File(saveDirPath);
			if(!dir.exists()) {
				//저장할 디렉토리가 없을 경우 디렉토리 생성
				dir.mkdirs();
			}
			thumbName.transferTo(new File(saveDirPath + saveFileName));

			fileDTO.setFile_ext(saveFileExt);
			fileDTO.setFile_name(saveFileName);
			fileDTO.setFile_org_name(tempFileOriName);
			fileDTO.setFile_path(saveDirPath);
			fileDTO.setFile_size(thumbName.getSize());
			//fileDTO.setFileUrl(Const.DOWN_PATH_EDITOR+_date+"/"+saveFileName);    // 에디터 다운로드 path + 위에서 생성한 폴더명 조합

		} catch (Exception e) {
			throw new Exception();
		}
		return fileDTO;
	}

	/**
	 * 이미지 1건 업로드
	 * @param request
	 * @param Path
	 * @param inputName
	 * @return
	 * @throws Exception
	 */
	public static FileDTO oneImageFileUpload(HttpServletRequest request, String Path, String fileUrl,String inputName) throws Exception {
		FileDTO fileDTO = new FileDTO();

		try {
			MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;
			MultipartFile thumbName = multiReq.getFile(inputName); //실제 넘어 온 파일

			if ( thumbName != null ) {
				if ( !thumbName.isEmpty() ) {
					String tempFileOriName = thumbName.getOriginalFilename();
					String _date = DateUtil.getNowDate("yyyyMMddHH");

					//String saveDirPath = Path+"/"+_date+"/";    // 기본 디렉토리에 날짜 조합(연월일시)으로 디렉토리 생성
					String saveDirPath = Path ;
					String saveFileExt = tempFileOriName.substring(tempFileOriName.lastIndexOf(".") + 1).toLowerCase();
					String saveFileName =DateUtil.getNowDate("yyyyMMddHHmmss")+ "." + saveFileExt;

					File dir = new File(saveDirPath);
					if ( !dir.exists() ) {
						//저장할 디렉토리가 없을 경우 디렉토리 생성
						dir.mkdirs();
					}

					File target = new File(saveDirPath + saveFileName);
					//file중복체크
					boolean targetChk = true;
					int whileCnt = 0;
					while ( targetChk ) {
						if ( target.exists() ) {
							whileCnt++;
							saveFileName =DateUtil.getNowDate("yyyyMMddHHmmss")+ "_" + Integer.toString(whileCnt) + "." + saveFileExt;
							target = new File(saveDirPath + saveFileName);
							targetChk = true;
						} else {
							targetChk = false;
							break;
						}
					}


					thumbName.transferTo(target);

					fileDTO.setFile_ext(saveFileExt);
					fileDTO.setFile_name(saveFileName);
					fileDTO.setFile_org_name(tempFileOriName);
					fileDTO.setFile_path(saveDirPath);
					fileDTO.setFile_size(thumbName.getSize());
					fileDTO.setFile_url(fileUrl+saveFileName);
				} else {
					return null;
				}
			} else {
				return null;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new Exception();
		}

		return fileDTO;
	}

	/**
	 * 파일 업로드
	 * @author nemo
	 *  2016. 3. 3.
	 * @param request
	 * @param Path
	 * @param inputName
	 * @param downUrl
	 * @return
	 * @throws Exception List<FileDTO>
	 *
	 */
	public static List<FileDTO> commonFileUpload(HttpServletRequest request, String Path, String fileUrl, String inputName) throws Exception {

		List<FileDTO> listFileDTO = new ArrayList<FileDTO>();

		try {
			MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;
			List<MultipartFile> fileNameArr = multiReq.getFiles(inputName); //실제 넘어 온 파일

			for ( int i = 0; i < fileNameArr.size(); i++ ) {
				if ( !fileNameArr.get(i).isEmpty() ) {
					String tempFileOriName = fileNameArr.get(i).getOriginalFilename();
					String saveDirPath = Path ;
					String saveFileExt = tempFileOriName.substring(tempFileOriName.lastIndexOf(".") + 1).toLowerCase();
					String saveFileName =DateUtil.getNowDate("yyyyMMddHHmmss")+ i + "." + saveFileExt;


					File dir = new File(saveDirPath);
					if ( !dir.exists() ) {
						//저장할 디렉토리가 없을 경우 디렉토리 생성
						dir.mkdirs();
					}


					File target = new File(saveDirPath + saveFileName);
					boolean targetChk = true;
					int whileCnt = 0;
					while ( targetChk ) {
						if ( target.exists() ) {
							whileCnt++;
							saveFileName =DateUtil.getNowDate("yyyyMMddHHmmss")+ i + "_" + Integer.toString(whileCnt) + "." + saveFileExt;
							target = new File(saveDirPath + saveFileName);
							targetChk = true;
						} else {
							targetChk = false;
							break;
						}
					}


					fileNameArr.get(i).transferTo(target);

					FileDTO fileDTO = new FileDTO();

					fileDTO.setFile_ext(saveFileExt);
					fileDTO.setFile_name(saveFileName);
					fileDTO.setFile_org_name(tempFileOriName);
					fileDTO.setFile_path(saveDirPath);
					fileDTO.setFile_size(fileNameArr.get(i).getSize());
					fileDTO.setFile_url(fileUrl+saveFileName);

					listFileDTO.add(fileDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}

		return listFileDTO;
	}


	public static List<FileDTO> commonThumbFileUpload(HttpServletRequest request, String Path, String fileUrl, String inputName) throws Exception {

		List<FileDTO> listFileDTO = new ArrayList<FileDTO>();

		try {
			MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;
			List<MultipartFile> fileNameArr = multiReq.getFiles(inputName); //실제 넘어 온 파일

			for ( int i = 0; i < fileNameArr.size(); i++ ) {
				if ( !fileNameArr.get(i).isEmpty() ) {
					String tempFileOriName = fileNameArr.get(i).getOriginalFilename();
					String saveDirPath = Path ;
					String saveFileExt = tempFileOriName.substring(tempFileOriName.lastIndexOf(".") + 1).toLowerCase();
					String saveFileName =DateUtil.getNowDate("yyyyMMddHHmmss")+ i + "." + saveFileExt;


					File dir = new File(saveDirPath);
					if ( !dir.exists() ) {
						//저장할 디렉토리가 없을 경우 디렉토리 생성
						dir.mkdirs();
					}
					//          fileNameArr.get(i).transferTo(new File(saveDirPath + saveFileName));

					createThumbnailImage(fileNameArr.get(i), saveDirPath, saveFileName);

					FileDTO fileDTO = new FileDTO();

					fileDTO.setFile_ext(saveFileExt);
					fileDTO.setFile_name(saveFileName);
					fileDTO.setFile_org_name(tempFileOriName);
					fileDTO.setFile_path(saveDirPath);
					fileDTO.setFile_size(fileNameArr.get(i).getSize());
					fileDTO.setFile_url(fileUrl+saveFileName);

					listFileDTO.add(fileDTO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}

		return listFileDTO;
	}


	/**
	 * 
	 * @author : nemojjang
	 * @date   : 2018. 7. 6.
	 * @dec    : 파일 업로드 게시판에서 쓰임
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static List<FileDTO> fileUpload(HttpServletRequest request, String serviceType, String serviceSeq, String path, String url) throws Exception {

		List<FileDTO> listFileDTO = new ArrayList<FileDTO>();

		try {
			boolean isMobile = isMobile(request);
			MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;


			if(serviceSeq == null) serviceSeq ="0";

			String saveDir 		= (Integer.parseInt(serviceSeq)/1000 + 1) + "/";
			String saveDirPath 	= path +serviceType +"/" + saveDir;
			String fileUrl     		= url +serviceType +"/" + saveDir;

			Iterator<String> it = multiReq.getFileNames();
			int fileCnt 		= 0;

			while ( it.hasNext() ) {
				String tagName = it.next();
				System.out.println("tagName :" + tagName);

				MultipartFile mFile = multiReq.getFile(tagName);

				if ( !mFile.isEmpty() ) {
					UUID uid       = UUID.randomUUID();	//랜덤 유니크 생성
					String fileName 	= DateUtil.getNowDate("yyyyMMddHHmmss")+"_"+uid;
					String tempFileOriName 	= mFile.getOriginalFilename();
					String saveFileExt 		= getFileExt(tempFileOriName);
					String saveFileName 	= fileName + "." + saveFileExt;
					String thumFileName 	= "";

					File dir = new File(saveDirPath);
					System.out.println("@@savePath:" + saveDirPath);
					if ( !dir.exists() ) { //저장할 디렉토리가 없을 경우 디렉토리 생성
						dir.mkdirs();
					}

					File target = new File(saveDirPath + saveFileName);

					// 1메가를 넘어가면 리사이징을 한다.
					// 모바일 카메라로 찍은 사진을 자동으로 회전시킨다.
					//원본 파일업로드
					if ( isAllowedExtension(saveFileName) && ((mFile.getSize() > 1048576) || isMobile) ) {

						InputStream inputStream =  new BufferedInputStream(mFile.getInputStream());

						int orientation = getOrientation(mFile.getInputStream());
						System.out.println("회전==============>" +orientation);

						int rotation = 1;
						if(orientation ==1) {
							rotation = 1;
						}else if(orientation == 3){
							rotation = -180;
						}else if(orientation == 6){
							rotation = 90;
						}else if(orientation == 8){
							rotation = -90;
						}

						if(rotation ==1) {
							System.out.println("====>" + rotation);
							Thumbnails.of(inputStream).scale(1).toFile(target);
						}else {
							System.out.println("====>" + rotation);
							Thumbnails.of(inputStream).scale(1).rotate(rotation).toFile(target);
						}


					} else {
						mFile.transferTo(target);
					}

					//썸네일만들기
					if(isAllowedExtension(saveFileName)){//이미지 파일 일때만...
						// 썸네일 생성
						thumFileName = fileName+"_thum.png";
						File thumbnail = new File(saveDirPath + thumFileName);
						if ( target.exists() ) {
							thumbnail.getParentFile().mkdirs();
							Thumbnails.of(target).size(90, 67).outputFormat("png").toFile(thumbnail);
							//Thumbnails.of(target).size(190, 150).outputFormat(saveFileExt).toFile(thumbnail);
						}
					}

					//=====================================================
					FileDTO fileDTO = new FileDTO();

					fileDTO.setFile_ext(saveFileExt);
					fileDTO.setFile_name(saveFileName);
					fileDTO.setFile_org_name(tempFileOriName);
					fileDTO.setFile_path(saveDirPath);
					fileDTO.setFile_size(mFile.getSize());
					fileDTO.setFile_thum_name(thumFileName);
					fileDTO.setService_type(serviceType);
					fileDTO.setService_code(tagName);
					fileDTO.setService_seq(serviceSeq);
					fileDTO.setFile_url(fileUrl);

					listFileDTO.add(fileDTO);
					fileCnt++;

					System.out.println("파일갯수 : " + fileCnt);
				}
			}


		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}

		return listFileDTO;
	}


	public static int getOrientation(InputStream inputStream)  {
		int orientation = 1;
		try {
			Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
			Directory directory = (Directory) metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

			if(directory != null) {
				try {
					orientation = directory.getInt(ExifIFD0Directory. TAG_ORIENTATION);
				} catch (MetadataException me) {
					System. out.println("Could not get orientation" );
				}
			}else {
				System. out.println("directory == null" );
			}
		} catch (ImageProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return orientation;
	}

	/**
	 * 파일 확장자 get
	 * @param fileName
	 * @return String
	 */
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}

	/**
	 * 파일 확장자 검사
	 * @param fileName
	 * @return boolean
	 */
	public static boolean isAllowedExtension(String fileName) {

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
	 * 파일 사이즈
	 * @param request
	 * @param inputName
	 * @return
	 */
	public static long getFileSize(HttpServletRequest request, String inputName) {

		long fileSize=0;

		MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;
		MultipartFile fileName = multiReq.getFile(inputName); //실제 넘어 온 파일

		if ( fileName != null ) {
			fileSize = fileName.getSize();
		}

		return fileSize;
	}


	//common 에서 호출됨
	public static List<FileDTO> fileUploadTagName(HttpServletRequest request, String Path, String fileUrl, String fileSecretKeyPath) throws Exception {

		List<FileDTO> listFileDTO = new ArrayList<FileDTO>();

		try {

			String saveDirPath = Path ;
			File dir = new File(saveDirPath);
			if ( !dir.exists() ) {
				//저장할 디렉토리가 없을 경우 디렉토리 생성
				dir.mkdirs();
			}


			File tempDir = new File(Const.TEMP_IMAGE_PATH );
			if ( !tempDir.exists() ) {
				//저장할 디렉토리가 없을 경우 디렉토리 생성
				tempDir.mkdirs();
			}

			boolean isMobile = isMobile(request);
			MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;

			Iterator<String> it = multiReq.getFileNames();
			int fileCnt = 0;
			while ( it.hasNext() ) {
				String tagName = it.next();

				System.out.println("tagName : " + tagName);

				MultipartFile mFile = multiReq.getFile(tagName);

				if ( !mFile.isEmpty() ) {
					UUID uid       = UUID.randomUUID();	//랜덤 유니크 생성
					String fileName 	= DateUtil.getNowDate("yyyyMMddHHmmss")+"_"+uid;
					String tempFileOriName = mFile.getOriginalFilename();

					String saveFileExt = tempFileOriName.substring(tempFileOriName.lastIndexOf(".") + 1).toLowerCase();
					String saveFileName = fileName + "." + saveFileExt;
					String thumFileName = "thumb_"+ fileName +"." + saveFileExt;

					String targetFile = Const.TEMP_IMAGE_PATH + saveFileName;


					File target = new File(targetFile);


					if ( isAllowedExtension(saveFileName)) {	
						InputStream inputStream =  new BufferedInputStream(mFile.getInputStream());

						int orientation = getOrientation(mFile.getInputStream());
						System.out.println("회전==============>" +orientation);

						int rotation = 0;
						if(orientation ==1) {
							rotation = 0;
						}else if(orientation == 3){
							rotation = -180;
						}else if(orientation == 6){
							rotation = 90;
						}else if(orientation == 8){
							rotation = -90;
						}
						System.out.println("rotation====>" + rotation);

						double targetScale = 1;
						System.out.println("mFile.getSize()===>" + mFile.getSize());

						if(mFile.getSize() > 3145728) { //3M 보다 크면

							targetScale=0.75;

							if(rotation ==0) {
								Thumbnails.of(inputStream).scale(0.75).toFile(target);
							}else {
								Thumbnails.of(inputStream).scale(0.75).rotate(rotation).toFile(target);
							}


						}else {

							if(rotation ==0) {
								mFile.transferTo(target);
							}else {
								Thumbnails.of(inputStream).scale(1).rotate(rotation).toFile(target);
							}

						}

						// 썸네일 만들기	
						String thumbnailTemp = Const.TEMP_IMAGE_PATH + thumFileName;
						File thumbnail = new File(thumbnailTemp);
						if ( target.exists() ) {
							thumbnail.getParentFile().mkdirs();
							//Thumbnails.of(target).size(90, 67).outputFormat("png").toFile(thumbnail);
							Thumbnails.of(target).size(190, 150).outputFormat(saveFileExt).toFile(thumbnail);
						}

						//파일 암호화 하고 기존 파일 삭제 하기....=========================================
						FileCoder coder = new FileCoder(fileSecretKeyPath);
						File dest = new File(saveDirPath + saveFileName);
						coder.encrypt(target, dest);
						deleteFile(targetFile);
						//---------------------------------------------------------------------------------------------------------------
						File thumDest = new File(saveDirPath + thumFileName);
						coder.encrypt(thumbnail, thumDest);
						deleteFile(thumbnailTemp);
						//=============================================================


					} else {
						mFile.transferTo(target);	//일반파일일 경우..
					}

					FileDTO fileDTO = new FileDTO();

					fileDTO.setFile_ext(saveFileExt);
					fileDTO.setFile_name(saveFileName);
					fileDTO.setFile_org_name(tempFileOriName);
					fileDTO.setFile_thum_name(thumFileName);
					fileDTO.setFile_path(saveDirPath);
					fileDTO.setFile_size(mFile.getSize());
					fileDTO.setFile_url(fileUrl + saveFileName);
					fileDTO.setFileTagName(tagName);


					listFileDTO.add(fileDTO);
					fileCnt ++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}

		return listFileDTO;
	}


	public static List<FileDTO> fileUploadTagNameThumb(HttpServletRequest request, String Path, String fileUrl) throws Exception {

		List<FileDTO> listFileDTO = new ArrayList<FileDTO>();

		try {
			MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;

			Iterator<String> it = multiReq.getFileNames();
			int fileCnt = 0;
			while ( it.hasNext() ) {
				String tagName = it.next();

				MultipartFile mFile = multiReq.getFile(tagName);

				if ( !mFile.isEmpty() ) {
					String tempFileOriName = mFile.getOriginalFilename();
					String saveDirPath = Path ;
					String saveFileExt = tempFileOriName.substring(tempFileOriName.lastIndexOf(".") + 1).toLowerCase();
					String saveFileName =DateUtil.getNowDate("yyyyMMddHHmmss")+ fileCnt + "." + saveFileExt;

					File dir = new File(saveDirPath);
					if ( !dir.exists() ) {
						//저장할 디렉토리가 없을 경우 디렉토리 생성
						dir.mkdirs();
					}
					//          mFile.transferTo(new File(saveDirPath + saveFileName));
					createThumbnailImage(mFile, saveDirPath, saveFileName);


					FileDTO fileDTO = new FileDTO();

					fileDTO.setFile_ext(saveFileExt);
					fileDTO.setFile_name(saveFileName);
					fileDTO.setFile_org_name(tempFileOriName);
					fileDTO.setFile_path(saveDirPath);
					fileDTO.setFile_size(mFile.getSize());
					fileDTO.setFile_url(fileUrl + saveFileName);
					fileDTO.setFileTagName(tagName);

					listFileDTO.add(fileDTO);
					fileCnt ++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}

		return listFileDTO;
	}


	/**
	 * 파일 업로드 (상점첨부이미지)
	 * @author
	 * @param request
	 * @return List<FileDTO>
	 * @throws Exception
	 *
	 */
	public static List<FileDTO> commonFileUpload(HttpServletRequest request, String path, String url) throws Exception {

		List<FileDTO> listFileDTO = new ArrayList<FileDTO>();

		try {
			boolean isMobile = isMobile(request);
			MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;

			Iterator<String> it =  multiReq.getFileNames();

			int fileCnt = 0;
			while ( it.hasNext() ) {
				String tagName = it.next();

				MultipartFile mFile = multiReq.getFile(tagName);

				if ( !mFile.isEmpty() ) {

					String tempFileOriName = mFile.getOriginalFilename();
					String saveDirPath = path;
					String saveFileExt = tempFileOriName.substring(tempFileOriName.lastIndexOf(".") + 1).toLowerCase();
					String saveFileName =DateUtil.getNowDate("yyyyMMddHHmmss")+ fileCnt + "." + saveFileExt;

					File dir = new File(saveDirPath);
					if ( !dir.exists() ) {
						//저장할 디렉토리가 없을 경우 디렉토리 생성
						dir.mkdirs();
					}


					// 1메가를 넘어가면 리사이징을 한다.
					// 모바일 카메라로 찍은 사진을 자동으로 회전시킨다.
					if ( (mFile.getSize() > 1048576) || isMobile ) {
						InputStream inputStream =  new BufferedInputStream(mFile.getInputStream());
						Thumbnails.of(inputStream).scale(1).toFile(new File(saveDirPath + saveFileName));
					} else {
						mFile.transferTo(new File(saveDirPath + saveFileName));
					}


					FileDTO fileDTO = new FileDTO();

					fileDTO.setFile_ext(saveFileExt);
					fileDTO.setFile_name(saveFileName);
					fileDTO.setFile_org_name(tempFileOriName);
					fileDTO.setFile_path(saveDirPath);
					fileDTO.setFile_size(mFile.getSize());
					fileDTO.setFile_url(url + saveFileName);
					fileDTO.setFile_type("O");
					fileDTO.setFileTagName(tagName);

					if(tagName.indexOf("SHOPIMG_") > -1){
						String[] tmp = tagName.split("_");
						fileDTO.setFileTagName(tmp[0]);
						fileDTO.setFile_order(tmp[1]);
						fileDTO.setFile_type("M");
					}

					listFileDTO.add(fileDTO);
					fileCnt++;

					//System.out.println("=======================:::::::"+fileDTO.getFile_url());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}

		return listFileDTO;
	}


	public static void createThumbnailImage(MultipartFile multiFile, String imagePath, String imageName) throws Exception {
		/*
			System.out.println("createThumbnailImage >>>>>>>>>>>>");
			System.out.println(multiFile.getSize());
		 */
		int thumbPixelWidth = THUMB_PIXCEL;
		int thumbPixelHeight = THUMB_PIXCEL;

		InputStream is = null;
		StringBuilder thumbImageName = new StringBuilder();

		try {
			is = multiFile.getInputStream();
			BufferedImage bimg = ImageIO.read(multiFile.getInputStream());

			int originWidth = bimg.getWidth();
			int originHeight = bimg.getHeight();

			double calculateWidth = 0d;
			double calculateHeight = 0d;

			int resizedWidth = 0;
			int resizedHeight = 0;

			/*
			 * 축소비율 계산
			 */
			if ( originWidth >= originHeight ) {
				if (originWidth > thumbPixelWidth) {
					calculateWidth = thumbPixelWidth;

					double ratio = originWidth / calculateWidth;
					calculateHeight = originHeight / ratio;
				} else {
					calculateWidth = originWidth;
					calculateHeight = originHeight;
				}
			} else {
				if ( originHeight > thumbPixelHeight ) {
					calculateHeight = thumbPixelHeight;

					double ratio = originHeight / calculateHeight;
					calculateWidth = originWidth / ratio;
				} else {
					calculateWidth = originWidth;
					calculateHeight = originHeight;
				}
			}

			/*
			 * 섬네일 이미지 생성
			 */

			resizedWidth = (int) calculateWidth;
			resizedHeight = (int) calculateHeight;

			int imageType = bimg.getType();

			BufferedImage resizedImage = new BufferedImage(resizedWidth, resizedHeight, imageType);

			Graphics2D g = resizedImage.createGraphics();
			g.drawImage(bimg, 0, 0, resizedWidth, resizedHeight, null);
			g.dispose();
			g.setComposite(AlphaComposite.Src);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			String[] orginFileSplit = imageName.split("\\.");
			String fileName = orginFileSplit[0];
			String fileExt = orginFileSplit[1];
			String fileFormat = null;


			fileFormat = fileExt;


			thumbImageName.append(fileName).append(".").append(fileExt);

			StringBuilder imageFullPath = new StringBuilder();
			imageFullPath.append(imagePath).append(thumbImageName.toString());


			System.out.println("imageFullPath.toString() : "+imageFullPath.toString());
			System.out.println("fileFormat : "+fileFormat);
			ImageIO.write(resizedImage, fileFormat, new File(imageFullPath.toString()));
			//      ImageIO.write(resizedImage, fileFormat, new File(imagePath));
		} catch(Exception e) {
			e.printStackTrace();
			throw new Exception();
		} finally {
			IOUtils.closeQuietly(is);
		}

		//    return thumbImageName.toString();
	}


	public static String readLine(String filePath) {
		String line = "";
		try{
			//파일 객체 생성
			File file = new File(filePath);
			//입력 스트림 생성
			FileReader filereader = new FileReader(file);
			
			int cur = 0;
	         while((cur = filereader.read()) != -1){
	        	 line += (char)cur;
	        	
	         }
	         filereader.close();
	        }catch (FileNotFoundException e) {
	            e.getStackTrace();
	        }catch(IOException e){
	            e.getStackTrace();
	        }

		return line;

	}
	
	public static void main(String[] args) throws Exception {
		String strKey = FileUtil.readLine("D:/secretKey/fileSecretKey.txt");
		System.out.println("strKey = " + strKey);
	}
}