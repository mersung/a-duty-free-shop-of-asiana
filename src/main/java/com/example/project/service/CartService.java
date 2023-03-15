package com.example.project.service;

import java.util.List;

import com.example.project.dto.CartDTO;
import com.example.project.dto.CartItemDTO;

public interface CartService {
	public List<CartItemDTO> getCartItemList(int userId) throws Exception;
	public List<CartDTO> cartList(int userId) throws Exception;
	public int updateCart(int cartId, int cnt) throws Exception;
	public int deleteCart(int id) throws Exception;
	public int createCart(int userId, int productId, int cnt) throws Exception;
	public int deleteByUserId(int userId);
}
