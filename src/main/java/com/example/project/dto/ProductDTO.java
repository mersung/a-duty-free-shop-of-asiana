package com.example.project.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("ProductDTO")
@Component
public class ProductDTO {
	int id;
	@NotNull(message = "가격을 입력해주세요.")
	Long price;
	@NotNull(message = "재고를 입력해주세요.")
	int stock;
	@NotEmpty(message = "상품명을 입력해주세요.")
	String name;
	@NotEmpty(message = "상품 설명을 입력해주세요.")
	String content;
	Category category;
	int userId;
}
