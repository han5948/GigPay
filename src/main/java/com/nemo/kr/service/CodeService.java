package com.nemo.kr.service;

import java.util.List;

import com.nemo.kr.dto.CodeDTO;


/**
 * 일가자 회사관리 Service
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-13
 */
public interface CodeService {
	public int getCodeTotalCnt(CodeDTO codeDTO);

	public void setCodeCell(CodeDTO codeDTO);                                     // Insert

	public void setCodeInfo(CodeDTO codeDTO);                                     // Update

	public void removeCodeInfo(CodeDTO codeDTO);                                  // Delete

	public CodeDTO getCodeInfo(CodeDTO codeDTO);									// select

	public int selectGroupListCnt(CodeDTO codeDTO);

	public Object selectGroupList(CodeDTO codeDTO);

	public void insertCodeGroup(CodeDTO codeDTO);

	public CodeDTO selectCodeGroup(CodeDTO codeDTO);

	public int selectListCnt(CodeDTO codeDTO);
	
	public void insertInfo(CodeDTO codeDTO);

	public CodeDTO selectInfo(CodeDTO codeDTO);

	public void updateInfo(CodeDTO codeDTO);

	public void deleteInfo(CodeDTO codeDTO);

	public void updateCodeGroup(CodeDTO codeDTO);

	public String selectCodeNameGroup(String[] jobCode);
	
	public String getCodeJqgridSelectFormat(CodeDTO codeDTO);
/**
 * 
  * @Method Name : getCodeList
  * @작성일 : 2020. 6. 19.
  * @작성자 : Park YunSoo
  * @필수 param : code_type
  * @선택 param :
  * @Method 설명 :
 */
	public List<CodeDTO> getCodeList(CodeDTO codeDTO);
/**
 * 
  * @Method Name : insertCode
  * @작성일 : 2020. 6. 19.
  * @작성자 : Park YunSoo
  * @필수 param : CodeDTO
  * @선택 param :
  * @Method 설명 : 코드 insert
 */
	public void insertCode(CodeDTO codeDTO);

/**
 * 
  * @Method Name : deleteCode
  * @작성일 : 2020. 6. 19.
  * @작성자 : Park YunSoo
  * @필수 param : code_seq
  * @선택 param :
  * @Method 설명 : 코드 delete
 */
	public void deleteCode(CodeDTO codeDTO);

/**
 * 
  * @Method Name : updateCode
  * @작성일 : 2020. 6. 19.
  * @작성자 : Park YunSoo
  * @필수 param : CodeDTO
  * @선택 param :
  * @Method 설명 : 코드 update
 */
	public void updateCode(CodeDTO codeDTO);
}
