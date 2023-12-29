package com.nemo.kr.mapper.ilgaja.write;

import com.nemo.kr.dto.PushReservekDTO;


/**
 * @author nemojjang
 *
 */
public interface PushReserveMapper {
	public PushReservekDTO selectPushReserveInfo(PushReservekDTO pushReservekDTO);

	public void updatePushReserveStatus(PushReservekDTO pushReservekDTO);
}
