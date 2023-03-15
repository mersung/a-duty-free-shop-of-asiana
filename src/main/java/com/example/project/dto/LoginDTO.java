package com.example.project.dto;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("LoginDTO")
public class LoginDTO {
	String email;
	String passwd;
	
}
