package com.example.project.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("MemberDTO")
public class MemberDTO{
	int id;
	
	@NotEmpty(message = "이메일을 입력해주세요.")
	@Email
	String email;
	
	@NotEmpty(message = "비밀번호를 입력해주세요.")
	String passwd;
	String post;
	String addr1;
	String addr2;
	String registrationId; // 소셜인지 일반인지 구분
	String registrationNum; // 주민등록번호
	Role roles;
}