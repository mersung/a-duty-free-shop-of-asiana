package com.example.project.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.dto.CartDTO;
import com.example.project.dto.CartItemDTO;
import com.example.project.dto.ImageDTO;
import com.example.project.dto.ProductDTO;
import com.example.project.mapper.CartMapper;

import lombok.extern.log4j.Log4j2;

@Service
@Transactional
public class CartServiceImpl implements CartService{
	
	@Autowired
	CartMapper mapper;
	@Autowired
	ProductImgService productImgService;
	
	@Autowired
	ProductService productService;

	@Override
	public List<CartDTO> cartList(int userId) throws Exception {
		return mapper.cartList(userId);
	}
	
	@Override
	public List<CartItemDTO> getCartItemList(int userId) throws Exception {
		List<CartDTO> cartDto = cartList(userId);
		List<CartItemDTO> result = new ArrayList<>();
		
		// cartDTO를 순회하면서 productId로 product 테이블에서 가격과 이름을 받고,
		// productImage테이블에서 imgUrl을 받아 temp(CartItemDTO)객체에 담아 result에 담아서 return
		for (CartDTO dto: cartDto) {
			CartItemDTO temp = new CartItemDTO();
			ProductDTO productDto = productService.getProductDtl(dto.getProductId());
			temp.setCartId(dto.getId());
			temp.setCnt(dto.getCnt());
			temp.setName(productDto.getName());
			temp.setPrice(productDto.getPrice());
			ImageDTO imageDto = productImgService.findByProductId(dto.getProductId());
			temp.setImgUrl(imageDto.getImgUrl());
			result.add(temp);
		}
		
		return result;
		
	}

	@Override
	public int updateCart(int cartId, int cnt) throws Exception {
		int n = mapper.updateCart(cartId, cnt);
		return n;
	}

	
	@Override
	public int deleteCart(int id) throws Exception {
		System.out.println(id);
		int n = mapper.deleteCart(id);
		return n;
	}

	@Override
	public int createCart(int userId, int productId, int cnt) throws Exception {
		// TODO Auto-generated method stub
		CartDTO dto = new CartDTO();
		dto.setCnt(cnt);
		dto.setUserId(userId);
		dto.setProductId(productId);
		
		int n = mapper.createCart(dto);
		return n;
	}

	@Override
	public int deleteByUserId(int userId) {
		return mapper.deleteByUserId(userId);
	}
}
