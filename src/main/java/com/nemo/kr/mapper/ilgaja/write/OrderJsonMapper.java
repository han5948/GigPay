package com.nemo.kr.mapper.ilgaja.write;

import com.nemo.kr.dto.OrderJsonDTO;




public interface OrderJsonMapper {
	public void insertOrderJson(OrderJsonDTO orderJsonDTO);
	
	public OrderJsonDTO selectOrderJsonInfo(String order_json_seq);
	
	public void updateOrderJson(OrderJsonDTO orderJsonDTO);
}
