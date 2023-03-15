package com.example.project.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.project.dto.CartDTO;
import com.example.project.dto.CartItemDTO;
import com.example.project.dto.ImageDTO;
import com.example.project.dto.MemberDTO;
import com.example.project.dto.ProductDTO;
import com.example.project.dto.order.OrderDTO;
import com.example.project.service.CartServiceImpl;
import com.example.project.service.MemberServiceImpl;
import com.example.project.service.OrderServiceImpl;
import com.example.project.service.ProductImgServiceImpl;
import com.example.project.service.ProductServiceImpl;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.parser.JSONParser;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
public class OrderController {
	
	private final MemberServiceImpl memberService;
	private final ProductServiceImpl productService;
	private final ProductImgServiceImpl productImgService;
	private final OrderServiceImpl orderService;
	private final CartServiceImpl cartService;
	
	@PostMapping("/order/{productId}")
	public String getOrderForm(@PathVariable("productId") int productId, @RequestParam("cnt") int cnt, Model model) {
		ProductDTO productDto = productService.getProductDtl(productId);
		
		if(productDto.getStock()>cnt) {
			productDto.setStock(productDto.getStock()-cnt);
			productService.updateStock(productDto);
		} else {
			ProductDTO dto = productService.getProductDtl(productId);
			ImageDTO productImg = productImgService.findByProductId(productId);
			
			model.addAttribute("productDto",dto);
			model.addAttribute("productImg", productImg);
			
			model.addAttribute("cnt",1);
			model.addAttribute("errorMsg","재고가 부족합니다.");
			return "productDtl";
		}
		
		
		OrderDTO orderDto = new OrderDTO();
		orderDto.setCnt(cnt);
		orderDto.setProductId(productId);
		model.addAttribute("orderDto",orderDto);
		model.addAttribute("totalPrice",productDto.getPrice()*cnt);
		return "orderForm";
	}
	
	@PostMapping("/order/cart")
	public String getCartOrderForm(Principal principal,Model model) throws Exception {
		MemberDTO memberDto= memberService.findByEmail(principal.getName());
		List<CartDTO> cartList = cartService.cartList(memberDto.getId());
		
		if(cartList.isEmpty()) {
			return "redirect:/cart";
		}
		
		int totalPrice=0;
		for(CartDTO item : cartList) {			
			ProductDTO product = productService.getProductDtl(item.getProductId());
			long price = item.getCnt()* product.getPrice();
			totalPrice+=price;
		}
		model.addAttribute("orderDto",new OrderDTO());
		model.addAttribute("totalPrice",totalPrice);
		return "orderForm";
	}

	
//	@PostMapping("/order/pay")
//	@ResponseBody
//	@RequestMapping(value="/order/pay", method= {RequestMethod.GET, RequestMethod.POST})
//	@PostMapping("/order/pay")
//	@ResponseBody
	@PostMapping("/order/pay")
	@ResponseBody
	public HashMap<String, Object> saveOrder(Principal principal, @RequestBody OrderDTO orderDto, Model model, HttpSession session) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		//카카오 페이
		URL url = new URL("https://kapi.kakao.com//v1/payment/ready");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "KakaoAK fd2bfc62f2328f30f828987b0d7edeba");
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		conn.setDoOutput(true);//서버에게 전해줄것이 있음.
		
		String parameter = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=partner_user_id&"
				+ "item_name=초코파이&quantity=1&total_amount=2200&vat_amount=200&tax_free_amount=0&approval_url=http://localhost:8080/team0/paymentform&"
				+ "fail_url=http://localhost:8080/team0&cancel_url=http://localhost:8080/team0";
		OutputStream giver = conn.getOutputStream();
		DataOutputStream dataGiver = new DataOutputStream(giver);
		dataGiver.writeBytes(parameter);
//		dataGiver.flush();
		dataGiver.close();
		int result = conn.getResponseCode();
		
		InputStream receiver;
		if(result == 200) {
			receiver = conn.getInputStream();
		}else {
			receiver = conn.getErrorStream();
		}
		InputStreamReader reader = new InputStreamReader(receiver);
		BufferedReader changer = new BufferedReader(reader);
		//카카오페이 끝
		
		
		System.out.println("principal>>>"+principal.getName());
		log.info("orderdto::~~"+orderDto);
		
		long totalPrice = 0;
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(0,10);

		LocalTime now = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		String curTime = now.format(formatter);
		LocalDate curDate = LocalDate.now();
		
		MemberDTO memberDto = memberService.findByEmail(principal.getName());
		List<CartItemDTO> res = new ArrayList<>();
		orderDto.setUserId(memberDto.getId());
		orderDto.setOrderTime(curTime);
		orderDto.setTid(uuid);
		
		try{
			int productId = orderDto.getProductId();
			ProductDTO productDto = productService.getProductDtl(productId);
			orderDto.setTotalPrice(productDto.getPrice()*orderDto.getCnt());
			totalPrice = orderDto.getTotalPrice();
			orderService.saveOrder(orderDto);	
			CartItemDTO item = new CartItemDTO();
			item.setCnt(orderDto.getCnt());
			item.setName(productDto.getName());
			res.add(item);
			
		} catch(NullPointerException e) {
			List<CartDTO> cartList = cartService.cartList(memberDto.getId());
			for(CartDTO cart : cartList) {				
				ProductDTO productDto = productService.getProductDtl(cart.getProductId());
				
				orderDto.setUserId(memberDto.getId());
				orderDto.setProductId(productDto.getId());
				orderDto.setOrderTime(curTime);
				orderDto.setCnt(cart.getCnt());
				orderDto.setTotalPrice(productDto.getPrice()*cart.getCnt());
				orderDto.setTid(uuid);
				totalPrice += orderDto.getTotalPrice();
				
				orderService.saveOrder(orderDto);
				
				CartItemDTO item = new CartItemDTO();
				item.setCnt(orderDto.getCnt());
				item.setName(productDto.getName());
				res.add(item);
			}
			
			cartService.deleteByUserId(memberDto.getId());
		}
		orderDto.setOrderDate(curDate.toString());
		
//		model.addAttribute("orderDto",orderDto);
//		model.addAttribute("productDto", res);
//		model.addAttribute("totalPrice",totalPrice);
//		model.addAttribute("changer", changer.readLine());
		log.info("됐다");
		HashMap<String, Object> map = new HashMap<>();
		map.put("orderDto", orderDto);
		map.put("productDto", res);
		map.put("totalPrice",totalPrice);
//		map.put("changer", changer.readLine());
		JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(changer.readLine());
        map.put("changer",jsonObject.get("next_redirect_pc_url"));
		log.info(map.get("changer"));
		
		
		session.setAttribute("orderDto", orderDto);
		session.setAttribute("productDto", res);
		session.setAttribute("totalPrice",totalPrice);
		return map;
	}
	
	@GetMapping("/paymentform")
	public String test(HttpSession session, Model model) {
//		System.out.println(request.getParameterMap());
//		Enumeration enu = request.getParameterNames();
//		while(enu.hasMoreElements()) {
//			System.out.println(enu.nextElement());
//		}
		model.addAttribute("orderDto", session.getAttribute("orderDto"));
		model.addAttribute("productDto", session.getAttribute("productDto"));
		model.addAttribute("totalPrice", session.getAttribute("totalPrice"));
		log.info("도착"+ session.getAttribute("productDto"));
		
		
		return "paymentform";
	}

//	@PostMapping("/test")
//	public String test(@RequestBody HashMap<String, String> map) {
//		System.out.println(map);
//		return "orderForm";
//	}

}
