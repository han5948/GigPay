package com.nemo.kr.mapper.ilgaja.write;

import com.nemo.kr.dto.DeviceDTO;


/**
 * 일가자 단말기 mapper
 *
 * @author  nemojjang
 * @version 1.0
 * @since   2017-04-13
 */
public interface DeviceMapper {


	public DeviceDTO selectDeviceInfo(DeviceDTO deviceDTO);

	public void updateDeviceInfo(DeviceDTO deviceDTO);

	public void insertDeviceInfo(DeviceDTO deviceDTO);

	public DeviceDTO getAppVersion(DeviceDTO deviceDTO);

	
}
