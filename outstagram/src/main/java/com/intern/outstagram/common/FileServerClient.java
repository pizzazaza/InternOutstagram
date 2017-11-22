package com.intern.outstagram.common;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.intern.outstagram.domain.dto.ImageUploadDto;

public class FileServerClient {
	private static final Logger logger = LoggerFactory.getLogger(FileServerClient.class);

	public static String sendGET(URI GET_URL) throws IOException {
		String result = "";

		//HttpHeaders requestHeaders = new HttpHeaders();


		//RestTemplate에서 file에 대한 header 자동으로 추가 
		//HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multiValueMap, requestHeaders);
		logger.info("submit " + GET_URL );
		RestTemplate restTemplate = new RestTemplate();
		result = restTemplate.getForObject(GET_URL, String.class); 
		
		logger.info("result : " + result );
		
		return result;
	}

	public static String sendPOST(URI POST_URL, Integer postSeq, MultipartFile[] multipartFiles) throws IOException {
		String result = "";

		HttpHeaders requestHeaders = new HttpHeaders();
		MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

		for (MultipartFile file : multipartFiles) {
			params.add("multipartFile", new ByteArrayResource(file.getBytes()) {
				@Override
				public String getFilename() throws IllegalStateException {
					return file.getOriginalFilename();
				}
			});
		}
		//MultipartFile을 Resource르 바꾸고 익명 클래스의 setFilename을 오버라이딩 
		//스프링은 multipartfile에 대해 filename으로 식별하기 때문에 원본 파일명을 이용
		
		params.add("postSeq", postSeq);

		//RestTemplate에서 file에 대한 header 자동으로 추가 
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(params, requestHeaders);
		logger.info("submit " + POST_URL + " ==========");
		RestTemplate restTemplate = new RestTemplate();
		result = restTemplate.postForObject(POST_URL, requestEntity, String.class);
		logger.info("result : " + result + " exit ==========");
		
		return result;
	}
}

/*
 * static method로 만들어 객체를 생성하지 않고도 사용가능하도록 작성
 * 이미지를 저장하는 용도로 서버를 사용하기 때문에 자주 많이 사용될 기능이라
 * static method로 만들어 사
 */