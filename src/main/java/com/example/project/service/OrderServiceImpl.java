package com.example.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.dto.CartItemDTO;
import com.example.project.dto.ProductDTO;
import com.example.project.dto.order.OrderDTO;
import com.example.project.mapper.OrderMapper;

@Transactional
@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	OrderMapper mapper;
	
	@Autowired
	ProductService productService;

	@Override
	public int saveOrder(OrderDTO orderDto) {
		return mapper.saveOrder(orderDto);
	}

	@Override
	public List<OrderDTO> findAll(int userId) throws Exception {
		return mapper.findAll(userId);
	}

	@Override
	public List<OrderDTO> findByTid(String tid) throws Exception {
		return mapper.findByTid(tid);
	}
	
	public List<CartItemDTO> orderToItem(List<OrderDTO> orderList) throws Exception {
		List<CartItemDTO> res = new ArrayList<>();
		for(OrderDTO order : orderList) {
			ProductDTO product = productService.getProductDtl(order.getProductId());
			CartItemDTO cart = new CartItemDTO();
			cart.setName(product.getName());
			cart.setCnt(order.getCnt());
			res.add(cart);
		}
		return res;
	}

}
