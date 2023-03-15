package com.example.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.dto.AjaxReviewDTO;
import com.example.project.dto.InsertReviewDTO;
import com.example.project.dto.MemberDTO;
import com.example.project.dto.ReviewDTO;
import com.example.project.mapper.MemberMapper;
import com.example.project.mapper.ReviewMapper;

@Service
public class ReviewServiceImpl implements ReviewService{
	@Autowired
	ReviewMapper mapper;
	@Autowired
	MemberMapper memberMapper;
	
	@Transactional
	@Override
	public List<ReviewDTO> getReviewList(int productId) throws Exception {
		// TODO Auto-generated method stub
		List<ReviewDTO> dto = mapper.getReviewList(productId);
		return dto;
	}
//	@Transactional
//	@Override
//	public int insertReview(ReviewDTO dto) throws Exception {
//		// TODO Auto-generated method stub
//		int n = mapper.insertReview(dto);
//		return n;
//	}
	@Override
	public int insertReview(AjaxReviewDTO dto) throws Exception {
		MemberDTO member = memberMapper.findByEmail(dto.getUserId());
		InsertReviewDTO dto2 = new InsertReviewDTO();
		dto2.setContent(dto.getContent());
		dto2.setProductId(dto.getProductId());
		dto2.setUserId(member.getId());
		return mapper.insertReview(dto2);
	}
	@Override
	public int updateReview(InsertReviewDTO dto) throws Exception {
		return mapper.updateReview(dto);
	}
	@Override
	public int deleteReview(int id) throws Exception {
		// TODO Auto-generated method stub
		return mapper.deleteReview(id);
	}

	
}
