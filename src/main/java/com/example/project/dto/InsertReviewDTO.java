package com.example.project.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("InsertReviewDTO")
public class InsertReviewDTO {
	int id;
	String content;
//	String userId;
	int userId;
	int productId;
}
