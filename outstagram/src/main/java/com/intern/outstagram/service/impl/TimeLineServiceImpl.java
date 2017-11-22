/*
 * timeline에 대한 비지니스 로직 
 * 
 * file server로 이미지요청 url 넣어주기
 * (지금은 파일서버 노드의 주소를 직접 입력해)
 *
 * 게시물이 로그인한 사용자가 좋아요 눌렀는지 확인
 * 조회도 중복 허용이기 때문에 확인을 하지 않는다. 
 * @Author Sejun
 */

package com.intern.outstagram.service.impl;

import java.io.IOException;
import java.net.URI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.apache.mahout.cf.taste.common.TasteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.intern.outstagram.common.FileServerClient;
import com.intern.outstagram.common.RandomUser;
import com.intern.outstagram.common.RecommendPicture;
import com.intern.outstagram.dao.PostInfoDao;
import com.intern.outstagram.dao.TimeLineDao;
import com.intern.outstagram.domain.PostDomain;
import com.intern.outstagram.domain.dto.FileLocationDto;
import com.intern.outstagram.domain.dto.FileSearchDto;
import com.intern.outstagram.domain.dto.IsLikeForPostDto;
import com.intern.outstagram.domain.dto.ItemBasedDto;
import com.intern.outstagram.domain.dto.PageDto;
import com.intern.outstagram.domain.dto.PostViewDto;
import com.intern.outstagram.domain.dto.RPageDto;
import com.intern.outstagram.domain.dto.RPostViewDto;
import com.intern.outstagram.domain.dto.RecommendResultDto;
import com.intern.outstagram.domain.dto.UserBasedDto;
import com.intern.outstagram.service.RecommendCalculService;
import com.intern.outstagram.service.TimeLineService;


@Service
@Transactional
public class TimeLineServiceImpl implements TimeLineService {
	
	private static final Logger logger = LoggerFactory.getLogger(TimeLineServiceImpl.class);
	
	private PostInfoDao postInfoDao;
	private TimeLineDao timeLineDao;
	private RecommendCalculService recommendCalculService;
	@Value("${app.target.url}")
	private String targeturl;
	
	@Autowired
	public TimeLineServiceImpl(PostInfoDao postInfoDao, TimeLineDao timeLineDao, RecommendCalculService recommendCalculService) {
		this.postInfoDao = postInfoDao;
		this.timeLineDao = timeLineDao;
		this.recommendCalculService = recommendCalculService;
	}
	

	@Override
	public List<PostViewDto> getTimeLineList(Integer userSeq) throws IOException{
		logger.info("TimeLine data 생성 ");
		RandomUser randomUser = new RandomUser();
		//String jsonResult = null;
		
		try {
			logger.info("PostInfo Select ");
			List<PostViewDto> postDtoList = (ArrayList<PostViewDto>) postInfoDao.selectPost();
			
			logger.info("Lenth : " + postDtoList.size());
			for(PostViewDto pvd : postDtoList) {
				try {
					logger.info("post seq : " + pvd.getSeq());
					pvd.setPath(targeturl+"fileserver?postSeq="+pvd.getSeq());	
					
					IsLikeForPostDto isLikeForPostDto = new IsLikeForPostDto();
					isLikeForPostDto.setPostSeq(pvd.getSeq());
					isLikeForPostDto.setUserSeq(userSeq);
					Integer isLike = postInfoDao.selectIsLikeByUserSeqAndPostSeq(isLikeForPostDto);
					
					pvd.setUserId(pvd.getUserId() + "  [" + randomUser.getCategory(pvd.getUserId()) + "]");
					if(isLike == 0) {
						pvd.setIsLike(false);
					}else if(isLike > 0) {
						pvd.setIsLike(true);
					}else {
						logger.info("post_like table data integrity 문제 발생");
						pvd.setIsLike(true);
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
			
			logger.info("return");
			return postDtoList;
		} catch(SQLException e ) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public List<PostViewDto> getNewPostList(PageDto pageDto) {
		List<PostViewDto> postList = null;
		RandomUser randomUser = new RandomUser();
		try {
			postList = timeLineDao.selectNewPostList(pageDto);
			for(PostViewDto pvd : postList) {
				try {
					pvd.setPath(targeturl+"fileserver?postSeq="+pvd.getSeq());	
						
					IsLikeForPostDto isLikeForPostDto = new IsLikeForPostDto();
					isLikeForPostDto.setPostSeq(pvd.getSeq());
					isLikeForPostDto.setUserSeq(pageDto.getUserSeq());
					Integer isLike = postInfoDao.selectIsLikeByUserSeqAndPostSeq(isLikeForPostDto);
					
					pvd.setUserId(pvd.getUserId() + "  [" + randomUser.getCategory(pvd.getUserId()) + "]");
					if(isLike == 0) {
						pvd.setIsLike(false);
					}else if(isLike > 0) {
						pvd.setIsLike(true);
					}else {
						logger.info("post_like table data integrity 문제 발생");
						pvd.setIsLike(true);
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return postList;
	}
	
	
	@Override
	public List<PostViewDto> getLikePostList(PageDto pageDto){
		List<PostViewDto> postList = new ArrayList<>();
		List<Integer> postSeqList = null;
		RandomUser randomUser = new RandomUser();
		try {
			postSeqList = timeLineDao.selectPostLikeByUserSeq(pageDto);
			logger.info("post size : " + postSeqList.size());
			for(Integer postSeq : postSeqList) {
				try {
					pageDto.setPostSeq(postSeq);
					PostViewDto pvd = timeLineDao.selectLikePostListByPostSeq(pageDto);
					pvd.setUserId(timeLineDao.selectUserIdByUserSeq(pvd.getUserSeq()));
					pvd.setSeq(postSeq);
					pvd.setPath(targeturl+"fileserver?postSeq="+pvd.getSeq());	
					
					IsLikeForPostDto isLikeForPostDto = new IsLikeForPostDto();
					isLikeForPostDto.setPostSeq(pvd.getSeq());
					isLikeForPostDto.setUserSeq(pageDto.getUserSeq());
					Integer isLike = postInfoDao.selectIsLikeByUserSeqAndPostSeq(isLikeForPostDto);
					pvd.setUserId(pvd.getUserId() + "  [" + randomUser.getCategory(pvd.getUserId()) + "]");
					if(isLike == 0) {
						pvd.setIsLike(false);
					}else if(isLike > 0) {
						pvd.setIsLike(true);
					}else {
						logger.info("post_like table data integrity 문제 발생");
						pvd.setIsLike(true);
					}
					postList.add(pvd);
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return postList;
	}

	@Override
	public List<RPostViewDto> getRecommendPostList(RPageDto pageDto){
		// TODO Auto-generated method stub
		RandomUser randomUser = new RandomUser();
		//data append
		recommendCalculService.appendRecommendPostToUser(pageDto, "operatorRecommend.csv", "picture.csv");
		///////////////////////////////////
		RecommendPicture recommendPicture = new RecommendPicture();
		UserBasedDto userBasedDto = new UserBasedDto();
		
		ItemBasedDto itemBasedDto = new ItemBasedDto();
		
		List<RecommendResultDto> recommendItemBasedList = null;
		List<RecommendResultDto> recommendUserBasedList = null;
		List<RPostViewDto> postList = new ArrayList<>();
		List<RPostViewDto> postUserBasedList = new ArrayList<>();
		List<RPostViewDto> postItemBasedList = new ArrayList<>();
		itemBasedDto.setItemCount(10);
		itemBasedDto.setUserSeq(pageDto.getUserSeq());
		userBasedDto.setItemCount(10);
		userBasedDto.setUserSeq(pageDto.getUserSeq());
		try {
			recommendUserBasedList = recommendPicture.UserBasedRecommender(userBasedDto);
			
			recommendCalculService.createRecommendClusterData(pageDto);
			recommendCalculService.appendRecommendPostToUser(pageDto, "operatorRecommendCluster.csv", "pictureCluster.csv");
		
			recommendItemBasedList = recommendPicture.ItemBasedRecommender(itemBasedDto);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<Long> pCheckList = new ArrayList<>();
		
		for(RecommendResultDto itemBased : recommendItemBasedList) {
			try {
				pageDto.setPostSeq(itemBased.getPostSeq());
				RPostViewDto pvd = timeLineDao.selectRecommendPostByPostSeq(pageDto);
				pvd.setUserId(timeLineDao.selectUserIdByUserSeq(pvd.getUserSeq()));
				pvd.setSeq(itemBased.getPostSeq());
				
				pvd.setPath(targeturl+"fileserver?postSeq="+pvd.getSeq());	
				
				pvd.setIsLike(false);
				pvd.setUserId(pvd.getUserId() + "  [" + randomUser.getCategory(pvd.getUserId()) + "]");
				postUserBasedList.add(pvd);
				pCheckList.add(itemBased.getPostSeq());
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		for(RecommendResultDto userBased : recommendUserBasedList) {
			if(postCheck(pCheckList, userBased.getPostSeq())) {
				continue;
			}
			try {
				
				pageDto.setPostSeq(userBased.getPostSeq());
				RPostViewDto pvd = timeLineDao.selectRecommendPostByPostSeq(pageDto);
				pvd.setUserId(timeLineDao.selectUserIdByUserSeq(pvd.getUserSeq()));
				pvd.setSeq(userBased.getPostSeq());

				pvd.setPath(targeturl+"fileserver?postSeq="+pvd.getSeq());	
				
				pvd.setIsLike(false);
				pvd.setUserId(pvd.getUserId() + "  [" + randomUser.getCategory(pvd.getUserId()) + "]");
				postItemBasedList.add(pvd);
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		int ibSize = postItemBasedList.size();
		int ubSize = postUserBasedList.size();
		int iInd = 0, uInd = 0;
		while(iInd < ibSize && uInd < ubSize) {
			postList.add(postItemBasedList.get(iInd));
			postList.add(postUserBasedList.get(uInd));
			iInd++;
			uInd++;
		}
		while(iInd < ibSize) {
			postList.add(postItemBasedList.get(iInd));
			iInd++;
		}
		while(uInd < ubSize) {
			postList.add(postUserBasedList.get(uInd));
			uInd++;
		}
//		postList.addAll(postItemBasedList);
//		postList.addAll(postUserBasedList);
		return postList;
	}

	public Boolean postCheck(List<Long> pCheckList, long pSeq) {
		
		for(long pCheck : pCheckList) {
			if(pCheck == pSeq) {
				return true;
			}
		}
		
		return false;
	}

	
	
}

