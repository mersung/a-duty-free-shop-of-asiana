package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.project.dto.AjaxReviewDTO;
import com.example.project.dto.InsertReviewDTO;
import com.example.project.dto.ReviewDTO;
import com.example.project.service.ReviewService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class ReviewController {
	@Autowired
	ReviewService service;
	
//	@GetMapping("/review/{productId}")
//	public String a(@PathVariable("productId") long productId) throws Exception {
//		service.getReviewList(productId);
//		return "reviewtest";
//	}
	@PostMapping("/review")
	public String insertReview(@RequestBody AjaxReviewDTO dto) throws Exception {
		log.info("reivew들어옴");
		service.insertReview(dto);
		return "shop";
	}
	
	@PutMapping("/review")
	public String updateReview(InsertReviewDTO dto) throws Exception {
		service.updateReview(dto);
		return "shop";
	}
	
	@DeleteMapping("/review")
	public String deleteReview(@PathVariable("id") int id) throws Exception {
		service.deleteReview(id);
		return "shop";
	}
	
}
