package com.intern.outstagram.service;

import com.intern.outstagram.domain.dto.ItemBasedDto;
import com.intern.outstagram.domain.dto.RPageDto;
import com.intern.outstagram.domain.dto.RandomUserDto;
import com.intern.outstagram.domain.dto.UserBasedDto;

public interface RecommendCalculService {
	public Integer createRecommendData();

	public Integer createRecommendCSV();
	
	public Integer createRandomUserData();
	
	public Integer createRandomUser();
	
	public void getItemBasedRecommend(ItemBasedDto itemBasedDto);
	
	public void getUserBasedRecommend(UserBasedDto userBasedDto);

	public Integer createRecommendClusterData(RPageDto pageDto);

	public void appendRecommendPostToUser(RPageDto pageDto, String fileName, String originFile);
}
