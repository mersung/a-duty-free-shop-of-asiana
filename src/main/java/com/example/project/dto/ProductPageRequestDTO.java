package com.example.project.dto;

import java.awt.print.Pageable;

import org.apache.ibatis.type.Alias;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;
import lombok.Setter;

@Getter
@Setter
@ToString
@Alias("ProductPageRequestDTO")
public class ProductPageRequestDTO {
	
	private int page; // 페이지 번호
	private int size; // 페이지당 개수
	private Category category;
	private String keyword;
	
	public ProductPageRequestDTO() {
		this.page = 1;
		this.size = 4; // 기본 1페이지, 10개씩
	}
	
	public ProductPageRequestDTO(int page, int size) {
		this.page = page;
		this.size = size;
	}
}
