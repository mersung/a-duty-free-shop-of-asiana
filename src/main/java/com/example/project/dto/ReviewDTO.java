package com.example.project.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("ReviewDTO")
public class ReviewDTO {
	int id;
	String email;
	String content;
//	String writeDate;


}
