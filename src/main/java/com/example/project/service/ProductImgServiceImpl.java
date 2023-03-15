package com.example.project.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.example.project.dto.ImageDTO;
import com.example.project.mapper.ProductImgMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductImgServiceImpl implements ProductImgService {
	
	private final ProductImgMapper mapper;
    private final FileService fileService;

    @Override
    public void saveProductImg(MultipartFile productImgFile, int productId) throws Exception {
    	ImageDTO productImg = new ImageDTO();
        String oriImgName = productImgFile.getOriginalFilename();
        String imgName = "";
        String imgUrl = "";

        // 파일 업로드
        if (!StringUtils.isEmpty(oriImgName)) {
            imgName = fileService.uploadFile(productImgFile);
            imgUrl = "/images/"+imgName;
        }

        // 이미지 정보 저장
        productImg.updateUserImg(oriImgName,imgName,imgUrl);
        productImg.setProductId(productId);
        
        mapper.saveProductImg(productImg);
    }

    @Override
    public void updateProductImg(Long imgId, MultipartFile productImgFile) throws Exception {
        if(!productImgFile.isEmpty()){
        	ImageDTO savedProductImg = mapper.findById(imgId);
        	// 이미지 없을 때 에러 처리
            if(!StringUtils.isEmpty(savedProductImg.getImgName())){
                fileService.deleteFile(savedProductImg.getImgName());
            }
            
            String oriImgName = productImgFile.getOriginalFilename();
            String imgName = fileService.uploadFile(productImgFile);
            String imgUrl = "/images/"+imgName;
            savedProductImg.updateUserImg(oriImgName,imgName,imgUrl);
            
            mapper.updateProductImg(savedProductImg);
        }
    }

	@Override
	public ImageDTO findByProductId(int productId) {
		return mapper.findByProductId(productId);
	}
}
