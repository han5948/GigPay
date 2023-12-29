package com.nemo.kr.mapper.ilgaja.read;

import java.util.List;

import com.nemo.kr.dto.*;

public interface ReplicationOrderMapper {

	public int getOrderTotalCnt(OrderDTO orderDTO);

	public List<OrderDTO> getOrderList(OrderDTO orderDTO);

	public int getOrderWorkTotalCnt(OrderWorkDTO orderWorkDTO);

	public List<OrderDTO> getOrderWorkList(OrderWorkDTO orderWorkDTO);

	public OrderDTO getSelectOrder(OrderDTO orderDTO);
	
	public OrderLogDTO getSelectOrderLog(OrderLogDTO orderLogDTO);
	
	public List<OrderLogDTO> getOrderLogList(OrderLogDTO orderLogDTO);

	public OrderDTO getSelectInfo(OrderDTO paramDTO);

	public OrderWorkDTO getOrderWorkInfo(OrderWorkDTO paramDTO);

	public int getTotalWork(OrderDTO orderDTO);
	public EmployerDTO getEmployerByEmployerNum(OrderDTO orderDTO);
	public List<WorkDTO> getWorkListByWorkLatlng(OrderDTO orderDTO);

}
