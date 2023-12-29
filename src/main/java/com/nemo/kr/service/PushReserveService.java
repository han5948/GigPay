package com.nemo.kr.service;

import com.nemo.kr.dto.PushReservekDTO;


/**
 * PushService
 * @author nemojjang
 *
 */
public interface PushReserveService {

    public PushReservekDTO  selectPushReserveInfo(PushReservekDTO pushReservekDTO);

	public void updatePushReserveStatus(PushReservekDTO pushReservekDTO);                                 

}
