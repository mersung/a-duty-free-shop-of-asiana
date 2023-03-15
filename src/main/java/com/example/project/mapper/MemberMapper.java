package com.example.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.project.dto.LoginDTO;
import com.example.project.dto.MemberDTO;
import com.example.project.dto.UpdateMemberDTO;
import com.example.project.oauth2.ProviderUser;

@Mapper
public interface MemberMapper {
	public int memberAdd(MemberDTO dto);
	public MemberDTO login(LoginDTO dto);
	public MemberDTO findByEmail(String email);
	public int updateMember(MemberDTO dto);
	public int deleteMember(int id);
}