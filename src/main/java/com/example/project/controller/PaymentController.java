package com.example.project.controller;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project.dto.PayDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
@RequiredArgsConstructor
public class PaymentController {
	
	@GetMapping("/payForm")
	public String getPayForm() {
		return "payForm";
	}
	
	@PostMapping("/pay")
	@ResponseBody
	public String kakaopay() throws IOException {
		log.info("aaa");
		URL url = new URL("https://kapi.kakao.com//v1/payment/ready");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Authorization", "KakaoAK fd2bfc62f2328f30f828987b0d7edeba");
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		conn.setDoOutput(true);//서버에게 전해줄것이 있음.
		
		String parameter = "cid=TC0ONETIME&partner_order_id=partner_order_id&partner_user_id=partner_user_id&"
				+ "item_name=초코파이&quantity=1&total_amount=2200&vat_amount=200&tax_free_amount=0&approval_url=http://localhost:8080/team0&"
				+ "fail_url=http://localhost:8080/team0&cancel_url=http://localhost:8080/team0";
		OutputStream giver = conn.getOutputStream();
		DataOutputStream dataGiver = new DataOutputStream(giver);
		dataGiver.writeBytes(parameter);
//		dataGiver.flush();
		dataGiver.close();
		int result = conn.getResponseCode();
		
		InputStream receiver;
		if(result == 200) {
			receiver = conn.getInputStream();
		}else {
			receiver = conn.getErrorStream();
		}
		InputStreamReader reader = new InputStreamReader(receiver);
		BufferedReader changer = new BufferedReader(reader);
		log.info("zzz:"+changer.readLine());
		return changer.readLine();

	}
	

	
}
