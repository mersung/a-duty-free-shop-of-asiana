package com.example.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductDTO {

	int id;
	long price;
	int stock;
	String name;
	String content;
	Category category;
	String imgUrl;
}
