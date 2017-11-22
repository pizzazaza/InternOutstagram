package com.intern.datanode.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.intern.datanode.domain.FileDomain;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FileUtils {
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

	public static HttpServletResponse sendFileResponse(FileDomain fileDomain, HttpServletResponse response, String envDir) {
		logger.info("============== Send File Response ==============");
		String originalFilename = fileDomain.getOriginalFile(); //저장되있는 원본 파일명 
		String extention = originalFilename.substring(originalFilename.lastIndexOf(".") + 1, originalFilename.length());
		//파일 확장자
		String contentType = "image/" + extention;
		//우선은 서버에서 image 파일만 다루기 때문에 image 문자에 확장자만 추가 
		Long fileSize = fileDomain.getFileLength();
		//
		
		String path = fileDomain.getPath();
		//파일 저장 경로 
		response.setHeader("Content-Disposition", "attachment; filename=\"" + originalFilename + "\";");
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Type", contentType);
		response.setHeader("Content-Length", "" + fileSize);
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		//response header 생성 및 추가 
		File readFile = new File(envDir + path + originalFilename);
		
		if (!readFile.exists()) { // 파일이 존재하지 않다면 Exception throw
			logger.info("============== file check ==============");
			throw new RuntimeException("file not found");
		}
		
		
		logger.info("============== File Exists ==============");
		FileInputStream fis = null;
		try { 
			fis = new FileInputStream(readFile);
			FileCopyUtils.copy(fis, response.getOutputStream());
			response.getOutputStream().flush();
		} catch (Exception ex) {
			logger.info("============== File Response에 추가 실패 ==============");
			throw new RuntimeException(ex);
		} finally {
			try {
				fis.close();
			} catch (Exception ex) {
				logger.info("============== File Close 실패 ==============");
				// 아무것도 하지 않음 (필요한 로그를 남기는 정도의 작업만 함.)
			}
		}
		
		return response;
		//response객체를 리턴하여 해당 사진을 전달 
	}

	public static List<FileDomain> saveFileInLocal(MultipartFile[] files, String baseDir, String envDir) {
		List<FileDomain> fileDomainList = new ArrayList<>();

		if (files != null && files.length > 0) {
			String extention = files[0].getOriginalFilename().substring(files[0].getOriginalFilename().lastIndexOf(".") + 1, files[0].getOriginalFilename().length());
			//  baseDir/yyyy/mm/dd/file_name 경로에 파일저
			String formattedDate = baseDir
					+ new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd").format(new Date());
			File f = new File(envDir+formattedDate);
			
			if (!f.exists()) { // 파일이 존재하지 않는다면, 여기서는 폴더가 있는지 없는지 확인
				f.mkdirs(); // 해당 디렉토리를 만든다. 하위폴더까지 한꺼번에 만든다.
			}

			//사진을 한 게시물당 5장까지 업로드 할 수 있기 때문에 MultipartFile을 순회하며 파일 저장을 진행 
			for (MultipartFile file : files) {
				FileDomain fileDomain = new FileDomain();
				//파일 저장 후 DB에 insert에 필요한 정보를 담을 FileDomain
				if (file.isEmpty()) {
					continue;
				}

				Long size = file.getSize();
				String uuid1 = UUID.randomUUID().toString(); // uuid를 이해서 파일을 저장하기 때문에 중복될 일이 거의 없다.
				String uuid2 = UUID.randomUUID().toString(); // thumbnail의 파일명 

				fileDomain.setOriginalFile(uuid1+"."+extention);
				fileDomain.setThumnailFile(uuid2+"."+extention);
				String path = formattedDate + File.separator;
				fileDomain.setPath(path);
				fileDomain.setFileLength(size);
				//Domain에 해당 파일 정보를 set
				
				
				
				logger.info("============== File 생성 시작 ==============");
				try {
					File originFile = new File(envDir + path+uuid1+"."+extention);
					file.transferTo(originFile);
				} catch(Exception e) {
					logger.error("============== File : " + file.getOriginalFilename() + " 생성 오류 ==============");
					e.printStackTrace();
				}
				logger.info("============== thumnail 생성 시작 ==============");
				try {
					File thumbFile = new File(envDir + path+uuid2+"."+extention);
					file.transferTo(thumbFile);
				} catch(Exception e) {
					logger.error("============== File : " + file.getOriginalFilename() + " thumbnail 생성 오류 ==============");
					e.printStackTrace();
				}
				
				fileDomainList.add(fileDomain);
				//생성된 파일 정보를 리스트에 저장 
			}
			logger.info("============== File 생성 성공 ==============");
		}
		
		return fileDomainList;
	}
}

/*
 * static method로 만들어 객체를 생성하지 않고도 사용가능하도록 작성
 * 이미지를 저장하는 용도로 서버를 사용하기 때문에 자주 많이 사용될 기능이라
 * static method로 만들어 사
 */