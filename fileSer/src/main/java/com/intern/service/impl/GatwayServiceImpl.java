package com.intern.service.impl;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.intern.common.FileServerClient;
import com.intern.dao.FilePathDao;
import com.intern.domain.dto.FilePathDto;
import com.intern.domain.dto.UploadFileDto;
import com.intern.service.GatwayService;

@Service
@Transactional
public class GatwayServiceImpl implements GatwayService {
	private static final Logger logger = LoggerFactory.getLogger(GatwayServiceImpl.class);
	
	private FilePathDao filePathDao;
	@Value("${app.target.url}")
	private String targetUrl;
	
	@Autowired
	public GatwayServiceImpl(FilePathDao filePathDao) {
		this.filePathDao = filePathDao;
	}
	
	@Override
	public String imageUpload(UploadFileDto uploadFileDto) {

		URI uri = URI.create(targetUrl+"file");
		//datanode uri 
		
		String result = null;
		logger.info("Upload To datanode "+ uri + " Method : POST");
		try {
			result = FileServerClient.sendPOST(uri, uploadFileDto.getPostSeq(), uploadFileDto.getMultipartFile());
			logger.info("Result : " + result);
			return result;
		}catch(Exception e) {
			logger.info("Requeset Exception To " + uri);
			return result;
		}
	}

	@Override
	public ResponseEntity<byte[]> downloadImage(Integer postSeq) {
//		FilePathDto filePathDto = null;
//		try {
//			filePathDto = filePathDao.selectFilePathByPostSeq();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} 
//		URI uri = URI.create(targetUrl+"+filePathDto.getPath()+filePathDto.getThumnailFile());
		
		URI uri = URI.create(targetUrl+"image?postSeq="+postSeq);
		
		ResponseEntity<byte[]> res = null;
		try {
			res =FileServerClient.sendGET(uri);
			logger.info("sadf");
		}catch(Exception e) {
			logger.info("Requeset Exception To " + uri);
		}
		
		return res;
	}

	@Override
	public ResponseEntity<byte[]> downloadOtherImage(Integer fileSeq) {
//		FilePathDto filePathDto = null;
//		try {
//			filePathDto = filePathDao.selectFilePathByPostSeq();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//		URI uri = URI.create(targetUrl+"+filePathDto.getPath()+filePathDto.getOriginalFile());
		
		URI uri = URI.create(targetUrl+"file?fileSeq="+fileSeq);
		
		ResponseEntity<byte[]> res = null;
		try {
			res = FileServerClient.sendGET(uri);
			logger.info("sadf");
		}catch(Exception e) {
			logger.info("Requeset Exception To " + uri);
		}

		return res;
	}

}
/*
 * 적절한 datanode를 찾아 이미지를 전달해주는 비지니스 로직
 * 아직 datanode는 지정해준 노드로 이동 
 */