package com.intern.datanode.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;

import com.intern.datanode.domain.FileDomain;
import com.intern.datanode.domian.dto.ImageUploadDto;

public interface FileService {
	public List<FileDomain> setFileInfoByUpladedFile(ImageUploadDto imageUploadDto);
	
	public List<Integer> getFileUriByPostSeq(List<Integer> postSeqList);
	
	public void getThumbnailImageByPostId(Integer postSeq, HttpServletResponse response);

	public void getOriginalImageByFileId(Integer fileSeq, HttpServletResponse response);

}
