package com.nemo.kr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemo.kr.common.Const;
import com.nemo.kr.dto.CodeDTO;
import com.nemo.kr.mapper.ilgaja.write.CodeMapper;
import com.nemo.kr.service.CodeService;
import com.nemo.kr.util.CommonUtil;


/**
 * 일가자 회사관리 Service Impl
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-27
 */
@Service
public class CodeServiceImpl implements CodeService {

	@Autowired CodeMapper codeMapper;

	@Override
	public int getCodeTotalCnt(CodeDTO codeDTO) {
		// TODO Auto-generated method stub

		return codeMapper.getCodeTotalCnt(codeDTO);
	}

	@Override
	public List<CodeDTO> getCodeList(CodeDTO codeDTO) {
		// TODO Auto-generated method stub

		return (List<CodeDTO>) codeMapper.getCodeList(codeDTO);
	}

	@Override
	@Transactional
	public void setCodeCell(CodeDTO codeDTO) {
		// TODO Auto-generated method stub

		codeMapper.setCodeCell(codeDTO);
	}

	@Override
	@Transactional
	public void setCodeInfo(CodeDTO codeDTO) {
		// TODO Auto-generated method stub

		codeMapper.setCodeInfo(codeDTO);
	}

	@Override
	@Transactional
	public void removeCodeInfo(CodeDTO codeDTO) {
		// TODO Auto-generated method stub

		codeMapper.removeCodeInfo(codeDTO);
	}

	@Override
	public CodeDTO getCodeInfo(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
		return codeMapper.getCodeInfo(codeDTO);
	}

	@Override
	public int selectGroupListCnt(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object selectGroupList(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertCodeGroup(CodeDTO codeDTO) {
	// TODO Auto-generated method stub
	
	}

	@Override
	public CodeDTO selectCodeGroup(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int selectListCnt(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void insertInfo(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
	}

	@Override
	public CodeDTO selectInfo(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateInfo(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
	}

	@Override
	public void deleteInfo(CodeDTO codeDTO) {
	// TODO Auto-generated method stub
	}

	@Override
	public void updateCodeGroup(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
	}

	@Override
	public String selectCodeNameGroup(String[] jobCode) {
		//TODO Auto-generated method stub
		return codeMapper.selectCodeNameGroup(jobCode);
	}

	@Override
	@Transactional
	public void insertCode(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
		codeMapper.insertCode(codeDTO);
	}

	@Override
	@Transactional
	public void deleteCode(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
		codeMapper.deleteCode(codeDTO);
	}

	@Override
	@Transactional
	public void updateCode(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
		codeMapper.updateCode(codeDTO);
	}

	@Override
	public String getCodeJqgridSelectFormat(CodeDTO codeDTO) {
		// TODO Auto-generated method stub
		List<CodeDTO> codeList = codeMapper.getCodeList(codeDTO);
		StringBuilder joinRouteOpt = new StringBuilder();
		
		for (int i = 0; i < codeList.size(); i++) {
			joinRouteOpt.append(codeList.get(i).getCode_value())
				.append(":")
				.append(codeList.get(i).getCode_name())
				.append(";");
		}
		
		if( joinRouteOpt.length() > 0 ) {
			joinRouteOpt.deleteCharAt(joinRouteOpt.length()-1);
		}
		return joinRouteOpt.toString();
	}
}
