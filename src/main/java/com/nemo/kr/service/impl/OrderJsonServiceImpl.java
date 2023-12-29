package com.nemo.kr.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nemo.kr.dto.OrderJsonDTO;
import com.nemo.kr.mapper.ilgaja.write.OrderJsonMapper;
import com.nemo.kr.service.OrderJsonService;

@Service
public class OrderJsonServiceImpl implements OrderJsonService {
	@Autowired OrderJsonMapper orderJsonMapper;

	@Override
	public void insertOrderJson(OrderJsonDTO orderJsonDTO) {
		// TODO Auto-generated method stub
		orderJsonMapper.insertOrderJson(orderJsonDTO);
	}

	@Override
	public OrderJsonDTO selectOrderJsonInfo(String order_json_seq) {
		// TODO Auto-generated method stub
		return orderJsonMapper.selectOrderJsonInfo(order_json_seq);
	}

	@Override
	public void updateOrderJson(OrderJsonDTO orderJsonDTO) {
		// TODO Auto-generated method stub
		orderJsonMapper.updateOrderJson(orderJsonDTO);
	}
}
