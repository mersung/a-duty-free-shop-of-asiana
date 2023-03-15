package com.example.project.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.json.XML;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.project.dto.MemberDTO;
import com.nimbusds.jose.shaded.json.JSONObject;
import com.nimbusds.jose.shaded.json.JSONValue;
import com.nimbusds.jose.shaded.json.parser.JSONParser;

@Controller
public class MainController {
	
//	@GetMapping("/exchange")
//	public String exchange() throws Exception{
//		StringBuffer sb = new StringBuffer("http://api.visitkorea.or.kr/openapi/service/rest/KorService/areaBasedList?");
//		
//		
//		sb.append("ServiceKey={서비스키}");
//		sb.append("&areaCode=35");
//		sb.append("&MobileOS=ETC");
//		sb.append("&MobileApp=AppTest");
//		sb.append("&pageNo=1");
//		sb.append("&numOfRows=10");
//		
//		URL url = new URL(sb.toString());
//		
//		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//	}
	@GetMapping("/exchange")
	@ResponseBody
    public String exchange() {
        StringBuffer result = new StringBuffer();
        String jsonPrintString = null;
        LocalDateTime now = LocalDateTime.now().minusDays(7);
        String dateString = now.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(">>>>>>>>>>>date" + dateString);
        try {
            String apiUrl = "https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey=pgWMUDFIfwylEL86mSoV31xVMpUMEgn0&searchdate="
                    + dateString + "&data=AP01";
            URL url = new URL(apiUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;

            while ((returnLine = bufferedReader.readLine()) != null) {
                result.append(returnLine);
            }
            //            org.json.JSONObject jsonObject = XML.toJSONObject(result.toString());

            //            System.out.println(">>>>>>>>>>>"+jsonObject);
            jsonPrintString = result.toString();
            System.out.println(">>>>>>>>>>>" + jsonPrintString);

            //            JSONParser parser = new JSONParser();
            //            JSONObject jsonObject2 = (JSONObject) parser.parse(result);

            //            System.out.println(">>>>>>>>>>>"+jsonPrintString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //        return ResponseEntity.status(HttpStatus.OK).body(jsonObject);
        return jsonPrintString;
    }
    
    @GetMapping("/notification")
    public String main() {
        return "notification";
    }
}
