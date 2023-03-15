package com.example.project.config;


import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.project.dto.Role;
import com.example.project.service.MemberServiceImpl;
import com.example.project.service.oauth2.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final MemberServiceImpl memberServiceImpl;
	private final CustomOAuth2UserService customOAuth2UserService;
	
	@Override // 권한
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
		http.authorizeRequests()
        	.antMatchers("/css/**","/js/**","/fonts/**","/images/**","/","/shop","/login","/signup").permitAll()
        	.antMatchers("/product/new").hasRole(Role.ADMIN.toString())
        	.antMatchers("/order/**","/mypage","/cart/**").hasAnyRole(Role.USER.toString(),Role.ADMIN.toString())
            .anyRequest().permitAll();
		
        http.formLogin()
	        .loginPage("/login")
	        .defaultSuccessUrl("/")
	        .usernameParameter("email")
	        .passwordParameter("passwd")
	        .failureUrl("/login/error")
	        .and()
	        .logout()
	        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	        .logoutSuccessUrl("/")
	    .and()
	        .oauth2Login()
	        .loginPage("/oauth2/authorization/google")
	        .defaultSuccessUrl("/")
	        .userInfoEndpoint()
	        .userService(customOAuth2UserService);
	}
	
	
	@Bean // 비밀번호 암호화를 위해 Bean으로 주입
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberServiceImpl).passwordEncoder(passwordEncoder());
    }
}
