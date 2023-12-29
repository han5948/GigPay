package com.nemo.kr.service;

import com.nemo.kr.dto.OrderJsonDTO;

public interface OrderJsonService {
	public void insertOrderJson(OrderJsonDTO orderJsonDTO);
	
	public OrderJsonDTO selectOrderJsonInfo(String order_json_seq);
	
	public void updateOrderJson(OrderJsonDTO orderJsonDTO);
}	
