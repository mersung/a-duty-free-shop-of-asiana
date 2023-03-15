package com.example.project.service;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.project.dto.LoginDTO;
import com.example.project.dto.MemberDTO;
import com.example.project.dto.UpdateMemberDTO;
import com.example.project.oauth2.ProviderUser;

public interface MemberService {
	public int memberAdd(MemberDTO dto,PasswordEncoder passwordEncoder);
	public MemberDTO login(LoginDTO dto);
	public MemberDTO findByEmail(String email);
	public int saveSocialUser(String registrationId, ProviderUser providerUser) throws Exception;
	public int updateMember(MemberDTO dto);
	public int deleteMember(int id);
}
