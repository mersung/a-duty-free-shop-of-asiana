package com.example.project.service.oauth2;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.example.project.dto.Role;
import com.example.project.oauth2.ProviderUser;

@Service
public class CustomOAuth2UserService extends AbstractOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override 
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
        // 전달받은 사용자 정보 서비스에 저장
        OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
        
        ProviderUser providerUser = super.providerUser(clientRegistration, oAuth2User);
        super.register(providerUser,userRequest); // 회원가입

        return oAuth2User;
    }
}
