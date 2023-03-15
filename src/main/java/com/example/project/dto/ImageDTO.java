package com.example.project.dto;

import org.apache.ibatis.type.Alias;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Alias("ImageDTO")
@Component
public class ImageDTO {
	
	int id;
	String imgName;
	String imgUrl;
	String originName;
	int productId;
	
	public void updateUserImg(String originName, String imgName, String imgUrl) {
        this.originName = originName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
