package com.intern.datanode.domian.dto;

import java.util.List;

import com.intern.datanode.domain.FileDomain;

public class FileInfoMapperDto {

	private Integer postSeq;
	private List<FileDomain> fileDomainList;
	
	
	public Integer getPostSeq() {
		return postSeq;
	}
	public void setPostSeq(Integer postSeq) {
		this.postSeq = postSeq;
	}
	public List<FileDomain> getFileDomainList() {
		return fileDomainList;
	}
	public void setFileDOmainList(List<FileDomain> fileDomainList) {
		this.fileDomainList = fileDomainList;
	}
}
/*
 * DB insert시 리스트의 각 원소씩 insert 
 */