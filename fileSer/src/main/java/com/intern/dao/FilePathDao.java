package com.intern.dao;

import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.intern.domain.dto.FilePathDto;

@Repository
public interface FilePathDao {
	
	public FilePathDto selectFilePathByPostSeq() throws SQLException;
	
	public FilePathDto selectFilePathByFileSeq() throws SQLException;

}
