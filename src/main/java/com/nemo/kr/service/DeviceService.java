package com.nemo.kr.service;

import com.nemo.kr.dto.DeviceDTO;


/**
 * 일가자 단말기
 * @author nemojjang
 *
 */
public interface DeviceService {

	public DeviceDTO getAppVersion(DeviceDTO deviceDTO) throws Exception;
	
	public DeviceDTO getDeviceInfo(DeviceDTO deviceDTO) throws Exception;

	public void updateDeviceInfo(DeviceDTO deviceDTO);

	public void insertDeviceInfo(DeviceDTO deviceDTO);

}
