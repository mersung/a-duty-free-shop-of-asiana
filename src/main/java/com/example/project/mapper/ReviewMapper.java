package com.example.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project.dto.InsertReviewDTO;
import com.example.project.dto.ReviewDTO;

@Mapper
public interface ReviewMapper {
	public List<ReviewDTO> getReviewList(int productId) throws Exception;
	public int insertReview(ReviewDTO dto) throws Exception;
	public int insertReview(InsertReviewDTO dto) throws Exception;
	public int updateReview(InsertReviewDTO dto) throws Exception;
	public int deleteReview(int id) throws Exception;
	
}
