package com.intern.outstagram.service;

public interface RandomDataService {
	public void createPostLikeAndViewRandomData(Integer postSeq, Integer prefer);
	
	public void createPostViewRandomData(Integer postSeq, Integer nonPrefer);
}
