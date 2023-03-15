package com.example.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.project.dto.ImageDTO;
import com.example.project.dto.ProductDTO;
import com.example.project.dto.ProductDetailDTO;
import com.example.project.dto.ProductListDTO;
import com.example.project.dto.ProductPageRequestDTO;
import com.example.project.dto.ProductPageRequestDTO;

@Mapper
public interface ProductImgMapper {
	public ImageDTO findById(Long id);
	public int saveProductImg(ImageDTO imageDTO);
	public int updateProductImg(ImageDTO imageDTO);
	public ImageDTO findByProductId(int productId);
}
