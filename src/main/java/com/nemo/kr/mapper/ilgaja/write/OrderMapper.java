package com.nemo.kr.mapper.ilgaja.write;

import java.util.List;

import com.nemo.kr.dto.OrderDTO;
import com.nemo.kr.dto.OrderLogDTO;
import com.nemo.kr.dto.OrderWorkDTO;


/**
 * 일가자 현장 관리 mapper
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-19
 */
public interface OrderMapper {

	public int getOrderTotalCnt(OrderDTO orderDTO);

	public List<OrderDTO> getOrderList(OrderDTO orderDTO);

	public void setOrderCell(OrderDTO orderDTO);                                     // Insert

	public void setOrderInfo(OrderDTO orderDTO);                                     // Update

	public void removeOrderInfo(OrderDTO orderDTO);                                  // delete

	public int getOrderWorkTotalCnt(OrderWorkDTO orderWorkDTO);

	public List<OrderDTO> getOrderWorkList(OrderWorkDTO orderWorkDTO);

	public OrderDTO getSelectOrder(OrderDTO orderDTO);

	
	public OrderLogDTO getSelectOrderLog(OrderLogDTO orderLogDTO);
	
	public List<OrderLogDTO> getOrderLogList(OrderLogDTO orderLogDTO);
	

	public void insertOrderLog(OrderLogDTO orderLogDTO);                                     // Insert

	public OrderDTO getSelectInfo(OrderDTO paramDTO);

	public OrderWorkDTO getOrderWorkInfo(OrderWorkDTO paramDTO);

	public void setOrderWorkInfo(OrderWorkDTO paramDTO);

	public void insertOrder(OrderDTO orderDTO);

	public void insertOrderWork(OrderWorkDTO paramDTO);

	public void updateOrderInfo(OrderDTO orderParam);

	public int getTotalWork(OrderDTO orderDTO);

	public void initWorkLatlng(OrderDTO orderDTO);
}
