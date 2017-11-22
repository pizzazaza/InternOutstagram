package com.intern.datanode.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.intern.datanode.common.FileUtils;
import com.intern.datanode.dao.FileInfoMapper;
import com.intern.datanode.domain.FileDomain;
import com.intern.datanode.domian.dto.ImageUploadDto;
import com.intern.datanode.service.FileService;

@Service
@Transactional
public class FileServiceImpl implements FileService {
	
	private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	private FileInfoMapper fileInfoMapper;

	@Value("${app.file.tmp.envdir}")
	private String envDir;
	@Value("${app.file.tmp.basedir}")
	private String baseDir;

	@Autowired
	public FileServiceImpl(FileInfoMapper fileInfoMapper) {
		this.fileInfoMapper = fileInfoMapper;
	}
	
	@Override
	public List<FileDomain> setFileInfoByUpladedFile(ImageUploadDto imageUploadDto) {
		
		List<FileDomain> fileDomainList = FileUtils.saveFileInLocal(imageUploadDto.getMultipartFile(), baseDir, envDir);
		//image 저장
		
		Integer fileId = null;
		
		try {
			for(FileDomain fd : fileDomainList) {
				logger.info(imageUploadDto.getPostSeq()+" post의 파일 info insert" );
				fd.setPostSeq(imageUploadDto.getPostSeq());
				fileId = fileInfoMapper.insertFileInfoByFileDomain(fd);
			}
			
			return fileDomainList;
			
		} catch(SQLException e) {
			
			return null;
		}
	}
	/*
	 * FileDomain List를 돌면서 insert 실행 
	 * 
	 */
	
	@Override
	public List<Integer> getFileUriByPostSeq(List<Integer> postSeqList) {
		
		try {
			logger.info("File Uri List Request" );
			List<Integer> uriList = new ArrayList<>();
			for (Integer postSeq : postSeqList) {
				uriList.add(fileInfoMapper.selectFileUriByPostSeq(postSeq));
			}
			
			return uriList;
		} catch(SQLException e) {
			e.printStackTrace();
			return null;
		}	
	}
	/*
	 * 파라미터로 전달 받은 postSeqList의 file 의 uri를 가져온다
	 */

	@Override
	public void getThumbnailImageByPostId(Integer postSeq, HttpServletResponse response) {
		try {
			FileDomain fileDomain = fileInfoMapper.selectThumbnailImageByPostSeq(postSeq);
			FileUtils.sendFileResponse(fileDomain, response, envDir);
			
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void getOriginalImageByFileId(Integer fileSeq, HttpServletResponse response) {
		try {
			FileDomain fileDomain = fileInfoMapper.selectOriginalImageByFileSeq(fileSeq);
			FileUtils.sendFileResponse(fileDomain, response, envDir);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
}

