package com.example.project.oauth2;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;

public interface ProviderUser {
	String getId();
    String getUserName();
    String getPassword(); // 랜덤으로 만들거라서 의미는 없음
    String getEmail();
    String getProvider(); // 서비스 제공자
    List<? extends GrantedAuthority> getAuthorities();
    Map<String,Object> getAttributes();
}
