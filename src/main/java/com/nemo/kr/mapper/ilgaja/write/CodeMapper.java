package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.CodeDTO;


/**
 * 일가자 코드 mapper
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-27
 */
public interface CodeMapper {
	public int getCodeTotalCnt(CodeDTO codeDTO);

	public List<CodeDTO> getCodeList(CodeDTO codeDTO);

	public void setCodeCell(CodeDTO codeDTO);                                     // Insert

	public void setCodeInfo(CodeDTO codeDTO);                                     // Update

	public void removeCodeInfo(CodeDTO codeDTO);                                  // delete

	public CodeDTO getCodeInfo(CodeDTO codeDTO);

	public String selectCodeNameGroup(String[] jobCode);
	
	public void insertCode(CodeDTO codeDTO);
	
	public void deleteCode(CodeDTO codeDTO);
	
	public void updateCode(CodeDTO codeDTO);
}
