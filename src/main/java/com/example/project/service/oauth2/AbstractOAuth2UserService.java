package com.example.project.service.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.project.dto.MemberDTO;
import com.example.project.mapper.MemberMapper;
import com.example.project.oauth2.GoogleUser;
import com.example.project.oauth2.ProviderUser;
import com.example.project.service.MemberServiceImpl;

import lombok.Getter;

@Service
@Getter
public class AbstractOAuth2UserService {
	
	@Autowired
    private MemberMapper mapper;
	
    @Autowired
    private MemberServiceImpl service;

    public void register(ProviderUser providerUser, OAuth2UserRequest userRequest) {

        MemberDTO dto  = mapper.findByEmail(providerUser.getEmail());

        if(dto==null){ // db에 user 정보가 없는 경우, db에 저장
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            service.saveSocialUser(registrationId,providerUser);
        }
    }

    public ProviderUser providerUser(ClientRegistration clientRegistration, OAuth2User oAuth2User) {
        String registrationId = clientRegistration.getRegistrationId();

        if (registrationId.equals("google")) { // 소셜 로그인 중 google 인 경우
            return new GoogleUser(oAuth2User, clientRegistration);
        }
        return null;
    }
}
