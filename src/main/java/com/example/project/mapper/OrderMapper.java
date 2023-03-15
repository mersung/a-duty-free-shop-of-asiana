package com.example.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project.dto.order.OrderDTO;

@Mapper
public interface OrderMapper {
	public int saveOrder(OrderDTO orderDTO);
	
	public List<OrderDTO> findAll(int userId) throws Exception;
	
	public List<OrderDTO> findByTid(String tid) throws Exception;
}
