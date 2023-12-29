package com.nemo.kr.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.nemo.kr.dto.*;


/**
 * 일가자 현장 관리 Service
 *
 * @author  kimgh
 * @version 1.0
 * @since   2017-04-19
 */
public interface OrderService {

	public int getOrderTotalCnt(OrderDTO orderDTO);

	public List<OrderDTO> getOrderList(OrderDTO orderDTO);

	public OrderDTO getSelectInfo(OrderDTO paramDTO);	//select
	
	public void setOrderCell(OrderDTO orderDTO);                                     // Insert

	public void setOrderInfo(OrderDTO orderDTO);                                     // Update

	public void removeOrderInfo(OrderDTO orderDTO);                                  // delete

	public int getOrderWorkTotalCnt(OrderWorkDTO orderWorkDTO);

	public List<OrderDTO> getOrderWorkList(OrderWorkDTO orderWorkDTO);

	public OrderDTO getSelectOrder(OrderDTO orderDTO);

	public List<OrderLogDTO> getOrderLog(OrderLogDTO orderLogDTO);

	public void setOrderLogRestore(OrderLogDTO orderLogDTO);

	public OrderWorkDTO getOrderWorkInfo(OrderWorkDTO paramDTO);	//select


	public Map<String, String> setOrderProcess(OrderDTO orderDTO, HttpSession session) throws Exception ;

	public void setOrderWorkInfo(OrderWorkDTO wParamDTO);

	public Map<String, String> regWorkProcess(OrderDTO orderDTO,	OrderWorkDTO orderWorkDTO);

	public int getTotalWork(OrderDTO orderDTO);

	/**
	 * 사업자(주민)번호로 구인처 조회
	 * @param orderDTO
	 * @return EmployerDTO
	 */
	public EmployerDTO getEmployerByEmployerNum(OrderDTO orderDTO);
	public List<WorkDTO> getWorkListByWorkLatlng(OrderDTO orderDTO);
}
