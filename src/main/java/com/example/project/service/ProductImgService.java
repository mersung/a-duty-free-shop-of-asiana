package com.example.project.service;

import org.springframework.web.multipart.MultipartFile;

import com.example.project.dto.ImageDTO;

public interface ProductImgService {
	public void saveProductImg(MultipartFile productImgFile, int productId) throws Exception;
	public void updateProductImg(Long imgId, MultipartFile productImgFile) throws Exception;
	public ImageDTO findByProductId(int productId);
}
