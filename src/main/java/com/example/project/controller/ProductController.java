package com.example.project.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.project.dto.Category;
import com.example.project.dto.ImageDTO;
import com.example.project.dto.ProductDTO;
import com.example.project.dto.ProductPageRequestDTO;
import com.example.project.dto.ProductPageResultDTO;
import com.example.project.dto.ReviewDTO;
import com.example.project.service.ProductImgService;
import com.example.project.service.ProductService;
import com.example.project.service.ReviewService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class ProductController {
	
	@Autowired
	ProductService service;
	
	@Autowired
	ProductImgService productImgService;
	
	@Autowired
	ReviewService reviewService;
	
//	@GetMapping("/product/{id}")//%%%
//	// @ResponseBody ==> 리턴하는 DTO를 JSON 변경해서 바로 브라우저로 응답
//	public String getOne(@PathVariable("id") int product_img_id, Model m) throws Exception {
////		List<ProductDetailDTO> dto = service.getOne(product_img_id);
//		System.out.println("sss");
////		log.info("dto :: "+ dto);
//		m.addAttribute("dto", service.getOne(product_img_id));
//		return "productDtl";
//	}
	

	@GetMapping("/product/{id}") // 나정 
	public String getProductDtl( @PathVariable("id") int id, Model model) throws Exception {
		ProductDTO dto = service.getProductDtl(id);
		ImageDTO productImg = productImgService.findByProductId(id);
		
		model.addAttribute("productDto",dto);
		model.addAttribute("productImg", productImg);
		
		int cnt=1;
		model.addAttribute("cnt",cnt);
		return "productDtl";
	}


	
	@PostMapping("/product")
	public String insertProduct(ProductDTO dto)  {
		log.info(dto);
		try {
			service.productInsert(dto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "productList";
	}
	
	@PutMapping("/product")
	public String updateProduct(ProductDTO dto) throws Exception{
		service.productUpdate(dto);
		return "productList";
	}
	
//	@GetMapping("/product/list")
//	public String getProductList(Model m) throws Exception {
//		List<ProductListDTO> dto = service.getProductList();
//		m.addAttribute("list", dto);
//		log.info("dto :::"+dto);
//		return "productList";
//	}
	
	@GetMapping("/shop")
	public String getList(@RequestParam(required=false, defaultValue = "") String cat, ProductPageRequestDTO dto, Model model) throws Exception{
		if(dto.getKeyword() == null) {
			dto.setKeyword("");
		}
		log.info("category:"+cat + "dto :" + dto);
		switch(cat) {
			case "cosmetic" :
			case "코스메틱" :
				dto.setCategory(Category.코스메틱);
				break;
			case "sool":
			case "주류":
				dto.setCategory(Category.주류);
				break;
			case "fashion":
			case "패션":
				dto.setCategory(Category.패션);
				break;
			case "etc":
			case "잡화":
				dto.setCategory(Category.잡화);
				break;
			case "foods":
			case "식품":
				dto.setCategory(Category.식품);
				break;
			default:
				dto.setCategory(null);	
				break;
		}
		log.info("keyword::"+dto.getKeyword());
		log.info("get::"+dto);
		ProductPageResultDTO dto2 = service.getProductListWithPaging(dto);
		dto2.setCategory(dto.getCategory());
		log.info("dto ::"+dto2);
		model.addAttribute("productList", dto2);
		log.info("here!!");
		log.info(model.getAttribute("productList"));
		return "shop";
	}
//	
//	@RequestMapping(value = "/test.action", method = { RequestMethod.POST })
//	@ResponseBody // 자바 객체를 HTTP 응답 본문의 객체로 변환
//	public Object test(ProductPageRequestDTO dto) throws Exception{
//		ProductPageResultDTO dto2 = service.getProductListWithPaging(dto);
//		return dto2;
//	    	
//	}
	
	@GetMapping("/product/new")
	public String productForm(Model model) {
		model.addAttribute("productDTO", new ProductDTO());
		return "productForm";
	}
	
	@PostMapping("/product/new")
	public String uploadProductImg(@RequestParam("productImgFile") MultipartFile productImgFile,
			@Valid ProductDTO dto,BindingResult bindingResult, Model model) throws Exception {
		if(bindingResult.hasErrors()) {
			return "productForm";
		}
		if(productImgFile.isEmpty()) {
			// 비어있을 때
		}
		service.productInsert(dto);
		productImgService.saveProductImg(productImgFile,service.getCurrId());
		
		return "redirect:/";
	}
	

//	@GetMapping("/product/all")
//	public String getProductList(Model model, ProductPageRequestDTO requestDTO) throws Exception {
//		log.info("dto::"+requestDTO);
//		ProductPageResultDTO productList = service.getResponseProductDTO(requestDTO);
//		log.info("list::"+productList);
//
//	@GetMapping("/shop")
//	public String getProductList(Model model) throws Exception {
//		List<ResponseProductDTO> productList = service.getResponseProductDTO();
//		model.addAttribute("productList", productList);
//		return "shop";
//	}
	
	
}
