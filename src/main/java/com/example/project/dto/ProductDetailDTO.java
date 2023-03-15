package com.example.project.dto;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("ProductDetailDTO")
public class ProductDetailDTO {
	ProductDTO productDTO;
	ReviewDTO reviewDTO;
	ImageDTO imageDTO;
}
