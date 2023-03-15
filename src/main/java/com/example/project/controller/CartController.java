package com.example.project.controller;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.project.dto.CartDTO;
import com.example.project.dto.CartItemDTO;
import com.example.project.dto.MemberDTO;
import com.example.project.dto.ProductDTO;
import com.example.project.service.CartService;
import com.example.project.service.MemberService;
import com.example.project.service.ProductImgService;
import com.example.project.service.ProductService;

import lombok.extern.log4j.Log4j2;


@Log4j2
@Controller
public class CartController {
	
	// 카트 내 모든 상품을 볼 때 FE로 리턴하는 객체
	// ArrayList 내에 cartInfo 객체를 주입하여 반환

	@Autowired
	CartService cartService;

	@Autowired
	MemberService memberService;
	
	// 장바구니 상품 전체 List
	// 각 객체 cnt, name, price, imgUrl
	@GetMapping("/cart")
	public ModelAndView cartList(Principal principal) throws Exception{
		ModelAndView mav = new ModelAndView();
		MemberDTO memberDto= memberService.findByEmail(principal.getName());
		List<CartItemDTO> result = cartService.getCartItemList(memberDto.getId());
		mav.addObject("cartList", result);
		mav.setViewName("cart");
		return mav;
	}
	
	
	// 장바구니 상품 개수 변경
//	@PostMapping("/cart/{cartId}")
//	public String updateCart(@PathVariable("cartId")int cartId, @RequestParam int cnt) throws Exception {
//		int n = cartService.updateCart(cartId, cnt);
//		return "redirect:cart";
//	}
	
	
	@GetMapping("/cart/{cartId}")
	public String deleteCart(@PathVariable("cartId")int cartId) throws Exception {
		int n = cartService.deleteCart(cartId);
		return "redirect:/cart";
	}
	
	@PostMapping("/cart")
	public String createCart(Principal principal, CartDTO pdto) throws Exception {
		MemberDTO memberDto = memberService.findByEmail(principal.getName());
		System.out.println(pdto);
		cartService.createCart(memberDto.getId(), pdto.getProductId(), pdto.getCnt());
		return "cart";
	}
	
	
	
//	@DeleteMapping("/multiDeleteInCart")
//	public String multiDeleteInCart(HttpServletRequest request) {
//		
//		try {
//			String [] checks = request.getParameterValues("check");
//			Integer[] checkInt = Stream.of(checks).mapToInt(Integer::parseInt).boxed().toArray(Integer[]::new);
//			List<Integer> list = Arrays.asList(checkInt);
//			
//			//int n = service.boardMultiDelete(list);
//	//		List<String> list = Arrays.asList(checks);
//	//		int n = service.boardMultiDelete(list);
//			
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
//		
//		return "forward:list";
//}	
	
	
	@ExceptionHandler({Exception.class})
	public String error() {
		return "";	//error.html에서 예외메시지 출력
	}
}