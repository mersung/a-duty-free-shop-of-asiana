package com.example.project.service;

import java.util.List;

import com.example.project.dto.CartItemDTO;
import com.example.project.dto.order.OrderDTO;

public interface OrderService {
	public int saveOrder(OrderDTO orderDto);
	
	public List<OrderDTO> findAll(int userId) throws Exception;
	
	public List<OrderDTO> findByTid(String tid) throws Exception;
	
	public List<CartItemDTO> orderToItem(List<OrderDTO> orderList) throws Exception;
	
}
