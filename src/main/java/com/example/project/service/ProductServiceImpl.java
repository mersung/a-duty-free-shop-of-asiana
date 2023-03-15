package com.example.project.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.dto.ImageDTO;
import com.example.project.dto.ProductDTO;
import com.example.project.dto.ProductDetailDTO;
import com.example.project.dto.ProductListDTO;
import com.example.project.dto.ProductPageRequestDTO;
import com.example.project.dto.ProductPageResultDTO;
import com.example.project.dto.ResponseProductDTO;
import com.example.project.exception.NoProductException;
import com.example.project.exception.NoProductStockException;
import com.example.project.exception.handler.ErrorCode;
import com.example.project.mapper.ProductMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	ProductMapper mapper;
	@Autowired
	ProductImgService productImgService;
	
	@Transactional
	@Override
	public List<ProductListDTO> getProductList() throws Exception {
		List<ProductListDTO> dto = mapper.getProductList();
		return dto;
	}

	@Transactional
	@Override
	public List<ProductDetailDTO> getOne(int id) throws Exception {
		List<ProductDetailDTO> dto = mapper.getOne(id);
		if(dto.get(0).getProductDTO().getStock() == 0) {
			throw new NoProductStockException("이 상품은 재고가 없습니다.", ErrorCode.NOT_FOUND);
		}
		return dto;
	}

	
	@Transactional
	@Override
	public void productInsert(ProductDTO productDTO) throws Exception {
		mapper.productInsert(productDTO);
		
	}
	@Transactional
	@Override
	public void productUpdate(ProductDTO productDTO) throws Exception {
		int n = mapper.productUpdate(productDTO);
		if(n==0) {
			throw new NoProductException("존재하지 않는 상품입니다.", ErrorCode.NOT_FOUND);
		}
	}


	@Override
	public void productDelete(int id) throws Exception {
		int n = mapper.productDelete(id);
	}

	@Transactional
	@Override
	public ProductDTO getProductDtl(int id) {
		return mapper.getProductDtl(id);
	}

	@Override
	public int getCurrId() {
		return mapper.getCurrId();
	}

	@Override
	public List<ProductDTO> getProductList2() throws Exception {
		return mapper.getProductList2();
	}

	@Override

	public ProductPageResultDTO getResponseProductDTO(ProductPageRequestDTO requestDTO) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductPageResultDTO getProductListWithPaging(ProductPageRequestDTO requestDTO) throws Exception {
		ProductPageResultDTO pageDTO = new ProductPageResultDTO();
		//List<ProductListDTO>저장
		List<ProductListDTO> dtoList;
		int totalRecord;
		if(requestDTO.getCategory() == null) {
			log.info("null임1");
			dtoList = mapper.NoCategoryList(requestDTO);
			log.info("null임2");
			totalRecord = mapper.totalRecordNoCategory(requestDTO);
			log.info("null임3" + totalRecord);
			
		}else {
			dtoList = mapper.getProductListWithPaging(requestDTO);
			totalRecord = mapper.totalRecord(requestDTO);
		}
		
		log.info("dtoList:"+dtoList);
		//totalRecord저장
		
		log.info("total:"+totalRecord);
		pageDTO.setDtoList(dtoList); // 1.dto List 저장
		pageDTO.makeTotalPages(totalRecord); // 2. 전체 개수 저장
		pageDTO.makePageList(requestDTO); // 3. 페이지 리스트 저장
		log.info("keyword:"+requestDTO.getKeyword());
	
		return pageDTO;
	}

	@Override
	public int updateStock(ProductDTO productDto) {
		
		return mapper.updateStock(productDto);
	}

//	@Override
//	public ProductPageResultDTO getResponseProductDTO(ProductPageRequestDTO requestDTO) throws Exception {
//		ProductPageResultDTO pageDTO = new ProductPageResultDTO();
//		List<ResponseProductDTO> res = new ArrayList<>();
//		List<ProductDTO> productDto = this.getProductList2();
//		
//		for(ProductDTO dto : productDto) {
//			ResponseProductDTO temp = new ResponseProductDTO();
//			temp.setCategory(dto.getCategory());
//			temp.setContent(dto.getContent());
//			temp.setName(dto.getName());
//			temp.setPrice(dto.getPrice());
//			temp.setStock(dto.getStock());
//			
//			ImageDTO image = productImgService.findByProductId(dto.getId());
//			temp.setImgUrl(image.getImgUrl());
//			
//			res.add(temp);
//		}
//		log.info("res::"+res);
//		
//		int totalRecord = mapper.totalRecord(requestDTO);
//		pageDTO.setDtoList(res);
//		pageDTO.makeTotalPages(totalRecord); // 2. 전체 개수 저장
//		pageDTO.makePageList(requestDTO); // 3. 페이지 리스트 저장
//		log.info("pageDTO::"+pageDTO);
//		
//		return pageDTO;
//
//	}


}
