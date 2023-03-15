package com.example.project.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("AjaxReviewDTO")
public class AjaxReviewDTO {
//	int id;
	String content;
	String userId;
//	int uId;
	int productId;
}
