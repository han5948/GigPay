package com.nemo.kr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nemo.kr.dto.DeviceDTO;
import com.nemo.kr.mapper.ilgaja.write.DeviceMapper;
import com.nemo.kr.service.DeviceService;



/**
 * 일가자 단말기 Service Impl
 * @author nemojjang
 *
 */
@Service
public class DeviceServiceImpl implements DeviceService {
	@Autowired DeviceMapper deviceMapper;
  
	@Override
	public DeviceDTO getDeviceInfo(DeviceDTO deviceDTO) throws Exception {
		// TODO Auto-generated method stub
		return deviceMapper.selectDeviceInfo(deviceDTO);
	}

	@Override
	@Transactional
	public void updateDeviceInfo(DeviceDTO deviceDTO) {
		// TODO Auto-generated method stub
		deviceMapper.updateDeviceInfo(deviceDTO);
	}

	@Override
	@Transactional
	public void insertDeviceInfo(DeviceDTO deviceDTO) {
		// TODO Auto-generated method stub
		deviceMapper.insertDeviceInfo(deviceDTO);
	}

	@Override
	public DeviceDTO getAppVersion(DeviceDTO deviceDTO) throws Exception {
		// TODO Auto-generated method stub
		return deviceMapper.getAppVersion(deviceDTO);
	}
}
