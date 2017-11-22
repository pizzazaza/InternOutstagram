package com.intern.datanode.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.intern.datanode.domain.FileDomain;

@Repository
public interface FileInfoMapper {
	
	public Integer insertFileInfoByFileDomain(FileDomain fileDomain) throws SQLException;
	
	public Integer selectFileUriByPostSeq(Integer postId) throws SQLException;
	
	public FileDomain selectThumbnailImageByPostSeq(Integer postId) throws SQLException;

	public FileDomain selectOriginalImageByFileSeq(Integer fileSeq) throws SQLException;
}
