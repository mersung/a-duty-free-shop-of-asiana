package com.example.project.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.*;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPageResultDTO {
	
	private List<ProductListDTO> dtoList; // 페이지에 보일 dto
	
	private int totalRecord; //전체 데이터 개수( 전체 레코드 )
	
	private int totalPage; //총 페이지 번호
	
	private int page; //현재 페이지 번호
	
	private int size; // 목록 사이즈
	
	private int start, end; //시작 페이지 번호, 끝 페이지 번호
	
	private boolean prev, next; 
	
	private List<Integer> pageList;
	
	private Category category;
	
	public void makeTotalPages(int totalRecord) {
		this.totalRecord = totalRecord;
		if (totalRecord%4 == 0){
			this.totalPage = totalRecord/4;
		}else {
			this.totalPage = (totalRecord/4)+1;
		}
		System.out.println("pages : "+totalPage);
		
	}
	
	public void makePageList(ProductPageRequestDTO request) {
		this.page = request.getPage();
		this.size = request.getSize();
		
		int tempEnd = (int)(Math.ceil(page/10.0))*10;

		start = tempEnd-9;
		
		prev = start > 1;
		
		end = (totalPage > tempEnd)?tempEnd:totalPage;

		next = totalPage > tempEnd;

		pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}
	
	

}
