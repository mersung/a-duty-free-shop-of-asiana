package com.example.project.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project.dto.CartItemDTO;
import com.example.project.dto.MemberDTO;
import com.example.project.dto.PaymentDetailDTO;
import com.example.project.dto.ProductDetailDTO;
import com.example.project.dto.UpdateMemberDTO;
import com.example.project.dto.order.OrderDTO;
import com.example.project.service.CartService;
import com.example.project.service.MemberService;
import com.example.project.service.OrderServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
public class MemberController {
	
	@Autowired
	MemberService service;
	@Autowired
	CartService cartService;
	
	@Autowired
	OrderServiceImpl orderService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@GetMapping("/signUp")
	public String signUpForm(Model model) {
		model.addAttribute("memberDTO",new MemberDTO());
		return "memberForm";
	}
	
	@PostMapping("/signUp")
	public String signUp(@Valid MemberDTO memberDTO,BindingResult bindingResult, Model model) throws Exception{
		if(bindingResult.hasErrors()) {
			return "memberForm";
		}
		int n = service.memberAdd(memberDTO, passwordEncoder);
		return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String loginForm(Model model) {
		model.addAttribute("memberDTO",new MemberDTO());
		return "loginForm";
	}
	
	@GetMapping("/login/error") // 에러 발생 시 에러메시지 전달
    public String loginError(Model model) { 
        model.addAttribute("errorMsg", "아이디 또는 비밀번호를 확인해주세요.");
        return "loginForm";
    }


	@GetMapping("/member/{id}")
	public String updateForm(Principal principal, Model model) throws Exception{
		MemberDTO dto = service.findByEmail(principal.getName());
		model.addAttribute("memberDTO", dto);
		return "updateForm";
	}
	
	@PostMapping("/member/{userId}")
	public String memberUpdate(@PathVariable("userId") int userId, MemberDTO dto, Model model) throws Exception {
		dto.setId(userId);
		service.updateMember(dto);
		return "redirect:/";
	}
	
	@DeleteMapping("/member/{userId}")
	public String memberDelete(@PathVariable("userId") int userId) {
		service.deleteMember(userId);
		return "redirect:/logout";
	}
	
	@GetMapping("/orderForm")
	public String orderForm() {
		return "orderForm";
	}
	
	@GetMapping("/mypage")
	public String mypage(Principal principal, Model model) throws Exception {
		MemberDTO memberDto= service.findByEmail(principal.getName());
		List<OrderDTO> orderList = orderService.findAll(memberDto.getId());
		HashMap<String, PaymentDetailDTO> res = new HashMap<String, PaymentDetailDTO>();
		for(OrderDTO order : orderList) {
			
			PaymentDetailDTO pay = new PaymentDetailDTO();
			pay.setDpAirport(order.getDpAirport());
			pay.setDpDate(order.getDpDate());
			pay.setOrderDate(order.getOrderDate());
			pay.setOrderTime(order.getOrderTime());
			pay.setTotalPrice(order.getTotalPrice());
			
			List<OrderDTO> tidList = orderService.findByTid(order.getTid());
			List<CartItemDTO> items = orderService.orderToItem(tidList);
			pay.setItemList(items);
			
			res.put(order.getTid(), pay);
		}
		
		
		model.addAttribute("payList",res);
		return "mypage";
	}

	@ExceptionHandler({Exception.class})
	public String error() {
		return "";	//error.html에서 예외메시지 출력
	}
}