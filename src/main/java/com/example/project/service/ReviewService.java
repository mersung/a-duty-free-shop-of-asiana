package com.example.project.service;

import java.util.List;

import com.example.project.dto.AjaxReviewDTO;
import com.example.project.dto.InsertReviewDTO;
import com.example.project.dto.ReviewDTO;

public interface ReviewService {
	public List<ReviewDTO> getReviewList(int productId) throws Exception;
	public int insertReview(AjaxReviewDTO dto) throws Exception;
	public int updateReview(InsertReviewDTO dto) throws Exception;
	public int deleteReview(int id) throws Exception;
}
