package com.nemo.kr.util;

import java.io.File;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.ContractFileDTO;

public class ContractFileUtil {
	public static ContractFileDTO fileUploadTagName(HttpServletRequest request, String Path, String fileSecretKeyPath) throws Exception {
		ContractFileDTO contractFileDTO = new ContractFileDTO();
		
		try {
			String saveDirPath = Path ;
			
			File dir = new File(saveDirPath);
			
			if(!dir.exists()) {
				//저장할 디렉토리가 없을 경우 디렉토리 생성
				dir.mkdirs();
			}

			MultipartHttpServletRequest multiReq = (MultipartHttpServletRequest) request;

			Iterator<String> it = multiReq.getFileNames();
			
			if(it.hasNext()) {
				while(it.hasNext()) {
					String tagName = it.next();
	
					MultipartFile mFile = multiReq.getFile(tagName);
					
					if(!mFile.isEmpty()) {
						UUID uid = UUID.randomUUID();	//랜덤 유니크 생성
						String fileName = DateUtil.getNowDate("yyyyMMddHHmmss") + "_" + uid;
						String tempFileOriName = mFile.getOriginalFilename();
						String saveFileExt = tempFileOriName.substring(tempFileOriName.lastIndexOf(".") + 1).toLowerCase();
						String saveFileName = fileName + "." + saveFileExt;
						//String targetFile = Const.TEMP_IMAGE_PATH + saveFileName;
						String targetFile = saveDirPath + saveFileName;
	
						File target = new File(targetFile);
	
						mFile.transferTo(target);	//일반파일일 경우..
	
						//파일 암호화 하고 기존 파일 삭제 하기....=========================================
						//FileCoder coder = new FileCoder(fileSecretKeyPath);
						//File dest = new File(saveDirPath + saveFileName);
						
						//coder.encrypt(target, dest);
						
						//deleteFile(targetFile);
						//=============================================================
						
						contractFileDTO.setFileName(saveFileName);
						contractFileDTO.setFileUrl(saveDirPath);
						contractFileDTO.setOrgFileName(tempFileOriName);
					}
				}
			}

			else {
				contractFileDTO = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return contractFileDTO;
	}
	
	public static String getFileExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	
	public static void deleteFile(String filePath) {
		try {
			File file = new File(filePath);
			file.delete();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
