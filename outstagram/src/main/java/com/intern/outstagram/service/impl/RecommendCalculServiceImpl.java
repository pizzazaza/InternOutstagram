/*
 * 추천에 필요한 정보를 DB에서 가져와서 모델을 만드는 작업과 유사도 측정
 * 
 * user_seq로 각 유저의 피드백을 정리하는 해시맵을 가지고 object로  
 * post_seq로 키를 가지는 해시맵을 넣어준다. 
 * 마지막으로 추천에 사용할 피처들을 저장 
 * 
 * @author 김세준 
 */

package com.intern.outstagram.service.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.mahout.cf.taste.common.TasteException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.intern.outstagram.common.RandomUser;
import com.intern.outstagram.common.OutstagramEvaluator;
import com.intern.outstagram.common.RecommendPicture;
import com.intern.outstagram.dao.RecommendDao;
import com.intern.outstagram.domain.dto.FollowDto;
import com.intern.outstagram.domain.dto.ItemBasedDto;
import com.intern.outstagram.domain.dto.LikeDto;
import com.intern.outstagram.domain.dto.RPageDto;
import com.intern.outstagram.domain.dto.RandomUserDto;
import com.intern.outstagram.domain.dto.RecommendDataFeatureDto;
import com.intern.outstagram.domain.dto.UserBasedDto;
import com.intern.outstagram.domain.dto.UserCategoryDto;
import com.intern.outstagram.domain.dto.UserDto;
import com.intern.outstagram.domain.dto.UserFeedbackInfoDto;
import com.intern.outstagram.domain.dto.ViewDto;
import com.intern.outstagram.service.RandomDataService;
import com.intern.outstagram.service.RecommendCalculService;


@Service
@Transactional
public class RecommendCalculServiceImpl implements RecommendCalculService {
	private static final Logger logger = LoggerFactory.getLogger(RecommendCalculServiceImpl.class);
	private RecommendDao recommendDao;
	private RandomDataService randomDataService;
	
	@Autowired
	public RecommendCalculServiceImpl(RecommendDao recommendDao, RandomDataService randomDataService) {
		this.recommendDao = recommendDao;
		this.randomDataService = randomDataService;
	}
//학습 데이터와 테스트 데이터의 구분도 필요 
	//data file 생성 
	@Override
	public Integer createRecommendData() {
		logger.info("Data search" );
		
		RecommendPicture recommendPicture = new RecommendPicture();
		Integer count = 0;
		try {
			Map<Integer, Object> userFeedback = new HashMap<>();
			
			for(int offset = 0; offset < 350; offset = offset+50) {	
				List<UserDto> userList = recommendDao.selectUserForRecommendSmall(offset);
				logger.info("UserList Length : " + userList.size());
				for(UserDto user : userList) {
					logger.info("User : " + user.getSeq() + " search");
					List<LikeDto> likeList = recommendDao.selectLikeForRecommendByUserSeq(user.getSeq());
					List<ViewDto> viewList = recommendDao.selectViewForRecommendByUserSeq(user.getSeq());
					
					
					logger.info("User : " + user.getSeq() + " LikeList : " + likeList.size());
					logger.info("User : " + user.getSeq() + " ViewList : " + viewList.size());
					
					//sort된 결과를 가져와서 
					int likeInd = 0, viewInd = 0;
					Map<Integer, Object> postFeedback = new HashMap<>();
					while(likeInd < likeList.size() && viewInd < viewList.size()) {
						UserFeedbackInfoDto userFeedbackInfoDto = new UserFeedbackInfoDto();
						int likePostSeq = likeList.get(likeInd).getPostId();
						int viewPostSeq = viewList.get(viewInd).getPostSeq();
						int postSeq = 0;
						//logger.info("Post View Count : " + viewList.get(viewInd).getViewCount());
						if(likePostSeq > viewPostSeq) {
							userFeedbackInfoDto.setViewCount(viewList.get(viewInd).getViewCount());
							userFeedbackInfoDto.setViewTime(viewList.get(viewInd).getViewTime());
							userFeedbackInfoDto.setLike(false);
							postSeq = viewPostSeq;
							viewInd++;
						}else if(likePostSeq < viewPostSeq) {
							userFeedbackInfoDto.setLike(true);
							userFeedbackInfoDto.setViewCount(0);
							userFeedbackInfoDto.setViewTime(0.f);
							postSeq = likePostSeq;
							likeInd++;
						}else if(likePostSeq == viewPostSeq) {
							userFeedbackInfoDto.setViewCount(viewList.get(viewInd).getViewCount());
							userFeedbackInfoDto.setViewTime(viewList.get(viewInd).getViewTime());
							userFeedbackInfoDto.setLike(true);
							postSeq = viewPostSeq;
							viewInd++;
							likeInd++;
						}
						count++;
						postFeedback.put(postSeq, userFeedbackInfoDto);
						
					}
					while(likeInd < likeList.size()) {
						UserFeedbackInfoDto userFeedbackInfoDto = new UserFeedbackInfoDto();
						userFeedbackInfoDto.setLike(true);
						userFeedbackInfoDto.setViewCount(0);
						userFeedbackInfoDto.setViewTime(0.f);
						
						postFeedback.put(likeList.get(likeInd).getPostId(), userFeedbackInfoDto);
						
						likeInd++;
						count++;
					}
					while(viewInd < viewList.size()) {
						UserFeedbackInfoDto userFeedbackInfoDto = new UserFeedbackInfoDto();
						userFeedbackInfoDto.setViewCount(viewList.get(viewInd).getViewCount());
						userFeedbackInfoDto.setViewTime(viewList.get(viewInd).getViewTime());
						userFeedbackInfoDto.setLike(false);
						
						postFeedback.put(viewList.get(viewInd).getPostSeq(), userFeedbackInfoDto);
						
						viewInd++;
						count++;
					}
					
					
					userFeedback.put(user.getSeq(), postFeedback);
				}
			}
			try {
				logger.info("check userFeedback : " + userFeedback.size() + "  " + userFeedback.keySet());
				recommendPicture.createDataFile(userFeedback, "preProcessing.data");
				
			}catch(IOException e) {
				logger.info("data file create exception");
				e.printStackTrace();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		}

		return count;
		
	}

	
	@Override
	public Integer createRecommendClusterData(RPageDto pageDto) {
		logger.info("Data search" );
		
		RecommendPicture recommendPicture = new RecommendPicture();
		Integer count = 0;
		try {
			long[] userList = null;
			try {
				userList = recommendPicture.neareastNUserSearch(pageDto.getUserSeq());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (TasteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			Map<Integer, Object> userFeedback = new HashMap<>();
			List<Integer> uList = new ArrayList<>();
			
			for(int uInd = 0; uInd < userList.length; uInd++) {
				Boolean flag = false;
				for(int uCom = uInd+1; uCom < userList.length; uCom++) {
					if(userList[uInd] == userList[uCom]) {
						flag = true;
						break;
					}
				}
				if(!flag) {
					uList.add((int)userList[uInd]);
					logger.info("add user : " + userList[uInd]);
				}
			}
			//logger.info("UserList Length : " + userList.length);
			for(Integer userSeq : uList) {
				//logger.info("User : " + userSeq + " search");
				List<LikeDto> likeList = recommendDao.selectLikeForRecommendByUserSeq(userSeq);
				List<ViewDto> viewList = recommendDao.selectViewForRecommendByUserSeq(userSeq);
				
				
				//logger.info("User : " + userSeq + " LikeList : " + likeList.size());
				//logger.info("User : " + userSeq + " ViewList : " + viewList.size());
			
				//sort된 결과를 가져와서 
				int likeInd = 0, viewInd = 0;
				Map<Integer, Object> postFeedback = new HashMap<>();
				while(likeInd < likeList.size() && viewInd < viewList.size()) {
					UserFeedbackInfoDto userFeedbackInfoDto = new UserFeedbackInfoDto();
					int likePostSeq = likeList.get(likeInd).getPostId();
					int viewPostSeq = viewList.get(viewInd).getPostSeq();
					int postSeq = 0;
					//logger.info("Post View Count : " + viewList.get(viewInd).getViewCount());
					if(likePostSeq > viewPostSeq) {
						userFeedbackInfoDto.setViewCount(viewList.get(viewInd).getViewCount());
						userFeedbackInfoDto.setViewTime(viewList.get(viewInd).getViewTime());
						userFeedbackInfoDto.setLike(false);
						postSeq = viewPostSeq;
						viewInd++;
					}else if(likePostSeq < viewPostSeq) {
						userFeedbackInfoDto.setLike(true);
						userFeedbackInfoDto.setViewCount(0);
						userFeedbackInfoDto.setViewTime(0.f);
						postSeq = likePostSeq;
						likeInd++;
					}else if(likePostSeq == viewPostSeq) {
						userFeedbackInfoDto.setViewCount(viewList.get(viewInd).getViewCount());
						userFeedbackInfoDto.setViewTime(viewList.get(viewInd).getViewTime());
						userFeedbackInfoDto.setLike(true);
						postSeq = viewPostSeq;
						viewInd++;
						likeInd++;
					}
					count++;
					postFeedback.put(postSeq, userFeedbackInfoDto);
					
				}
				while(likeInd < likeList.size()) {
					UserFeedbackInfoDto userFeedbackInfoDto = new UserFeedbackInfoDto();
					userFeedbackInfoDto.setLike(true);
					userFeedbackInfoDto.setViewCount(0);
					userFeedbackInfoDto.setViewTime(0.f);
					
					postFeedback.put(likeList.get(likeInd).getPostId(), userFeedbackInfoDto);
					
					likeInd++;
					count++;
				}
				while(viewInd < viewList.size()) {
					UserFeedbackInfoDto userFeedbackInfoDto = new UserFeedbackInfoDto();
					userFeedbackInfoDto.setViewCount(viewList.get(viewInd).getViewCount());
					userFeedbackInfoDto.setViewTime(viewList.get(viewInd).getViewTime());
					userFeedbackInfoDto.setLike(false);
					
					postFeedback.put(viewList.get(viewInd).getPostSeq(), userFeedbackInfoDto);
					
					viewInd++;
					count++;
				}
				
				
				userFeedback.put(userSeq, postFeedback);
			}
			
			
			try {
				recommendPicture.createDataFile(userFeedback, "preProcessingCluster.data");
				recommendPicture.dataConverter("preProcessingCluster.data", "pictureCluster.csv");
			}catch(IOException e) {
				logger.info("data file create exception");
				e.printStackTrace();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		}

		return count;
		
	}
	//csv file 생성 
	@Override
	public Integer createRecommendCSV() {
		RecommendPicture recommendPicture = new RecommendPicture();
		Integer count = -1;
		try {
			count = recommendPicture.dataConverter("preProcessing.data", "picture.csv");

		}catch(IOException e){
			e.printStackTrace();
		}
		return count;
	}
	
	//like view data insert
	@Override
	public Integer createRandomUserData() {
		Integer count = 0;
		RandomUser randomUser = new RandomUser();
		Random random = new Random();
		
		
		for(int postGroup = 1; postGroup < 8; postGroup++) {//그룹 
			String[] myGroup = randomUser.getMyGroup(postGroup-1);
			
			for(int gind = 0; gind < myGroup.length; gind++) {//각 그룹의 작가
				List<Integer> post = null;
				try {
					post = recommendDao.selectPostByUserId(myGroup[gind]);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for(Integer pSeq : post) {//like and view, view time
				//////// 게시물 48*77 = 3696
					int likeC = 0;
					int viewC = 0;
					int preferLikeC = 0;
					
					for(int uInd = 1; uInd < 8; uInd++) {
						List<Integer> userList = null;
						UserCategoryDto userCategoryDto = new UserCategoryDto();
						userCategoryDto.setLimit(50);
						userCategoryDto.setOffset((uInd-1)*50);
						try {
							userList = recommendDao.selectUserSeq(userCategoryDto);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						for(Integer userSeq : userList) {
							if (postGroup == uInd) {
								if(random.nextInt(100) <= (70 + errorBoundary(20))) {
	//									logger.info("Prefer data set : " + prefer+user + ", " + pSeq + " like and view");
									randomDataService.createPostLikeAndViewRandomData(pSeq, userSeq);
									likeC++;
									viewC++;
									preferLikeC++;
									
									
								}
								else if(random.nextInt(100) <= (50 + errorBoundary(10))) {
	//									logger.info("Prefer data set : " + prefer+user + ", " + pSeq + " view");
									//view, view time
									randomDataService.createPostViewRandomData(pSeq, userSeq);
									
									viewC++;
								
								}
							}
							else {
								if(random.nextInt(100) <= 3) {
									//like and view, view time
	//									logger.info("Non data set : " + prefer+user + ", " + pSeq + " like and view");
									randomDataService.createPostLikeAndViewRandomData(pSeq, userSeq);
									likeC++;
									viewC++;
								
	
								}
								else if(random.nextInt(100) <= 7) {
									//view and view time
	//									logger.info("Non data set : " + prefer+user + ", " + pSeq + " view");
									randomDataService.createPostViewRandomData(pSeq, userSeq);
									
									viewC++;
									
								}
							}
							count++;
							
						}
					}
					logger.info("PostSeq : " + pSeq + "[ Like : " + likeC + ", " + "View : " + viewC + " ]" + " Prefer Like: " + preferLikeC );
					
				}
			}
			
		}
		
		return count;
	}

	public Float errorBoundary(int bound) {
		Random random = new Random();
		Boolean pm = random.nextBoolean();
		Float returnValue = 0.f;
		returnValue = random.nextFloat()+ random.nextInt(bound);
		if(pm) {//+
			return returnValue; 	
		}
		else {
			return -returnValue;
		}//-
		 
	}
	@Override
	public void getItemBasedRecommend(ItemBasedDto itemBasedDto) {
		RecommendPicture recommendPicture = new RecommendPicture();
		OutstagramEvaluator outstagramEvaluator = new OutstagramEvaluator();
		try {
			recommendPicture.ItemBasedRecommender(itemBasedDto);
			
			outstagramEvaluator.itemBasedEvaluator();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void getUserBasedRecommend(UserBasedDto userBasedDto) {
		RecommendPicture recommendPicture = new RecommendPicture();
		OutstagramEvaluator outstagramEvaluator = new OutstagramEvaluator();
		try {
			recommendPicture.UserBasedRecommender(userBasedDto);
			
			outstagramEvaluator.userBasedEvaluator();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//user insert 
	@Override
	public Integer createRandomUser() {
		RandomUserDto randomUserDto = new RandomUserDto();
		
		
		String userId = "user";
		
		
		for(int i = 1; i <= 30000; i++) {
			randomUserDto.setDate(new Date());
			String blank = "";
			if(i < 10) {
				blank = "0000";
			}
			else if(i <100) {
				blank = "000";
			}
			else if(i < 1000) {
				blank = "00";
			}
			else if(i < 10000) {
				blank = "0";
			}
			randomUserDto.setNickname(userId + blank + i);
			randomUserDto.setUserId(userId + blank + i);
			try {
				recommendDao.insertRandomUser(randomUserDto);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
		return null;
	}
	
	@Override
	public void appendRecommendPostToUser(RPageDto pageDto, String fileName, String originFile) {
		//logger.info("User : " + pageDto.getUserSeq() + " search");
		RecommendPicture recommendPicture = new RecommendPicture();
		List<LikeDto> likeList = null;
		List<ViewDto> viewList = null;
		try {
			likeList = recommendDao.selectLikeForRecommendByUserSeq(pageDto.getUserSeq());
			viewList = recommendDao.selectViewForRecommendByUserSeq(pageDto.getUserSeq());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<Integer, Object> userFeedback = new HashMap<>();
		
		//logger.info("User : " + pageDto.getUserSeq() + " LikeList : " + likeList.size());
		//logger.info("User : " + pageDto.getUserSeq() + " ViewList : " + viewList.size());
	
		//sort된 결과를 가져와서 
		int likeInd = 0, viewInd = 0;
		Map<Integer, Object> postFeedback = new HashMap<>();
		while(likeInd < likeList.size() && viewInd < viewList.size()) {
			UserFeedbackInfoDto userFeedbackInfoDto = new UserFeedbackInfoDto();
			int likePostSeq = likeList.get(likeInd).getPostId();
			int viewPostSeq = viewList.get(viewInd).getPostSeq();
			int postSeq = 0;
			//logger.info("Post View Count : " + viewList.get(viewInd).getViewCount());
			if(likePostSeq > viewPostSeq) {
				userFeedbackInfoDto.setViewCount(viewList.get(viewInd).getViewCount());
				userFeedbackInfoDto.setViewTime(viewList.get(viewInd).getViewTime());
				userFeedbackInfoDto.setLike(false);
				postSeq = viewPostSeq;
				viewInd++;
			}else if(likePostSeq < viewPostSeq) {
				userFeedbackInfoDto.setLike(true);
				userFeedbackInfoDto.setViewCount(0);
				userFeedbackInfoDto.setViewTime(0.f);
				postSeq = likePostSeq;
				likeInd++;
			}else if(likePostSeq == viewPostSeq) {
				userFeedbackInfoDto.setViewCount(viewList.get(viewInd).getViewCount());
				userFeedbackInfoDto.setViewTime(viewList.get(viewInd).getViewTime());
				userFeedbackInfoDto.setLike(true);
				postSeq = viewPostSeq;
				viewInd++;
				likeInd++;
			}
	
			postFeedback.put(postSeq, userFeedbackInfoDto);
			
		}
		while(likeInd < likeList.size()) {
			UserFeedbackInfoDto userFeedbackInfoDto = new UserFeedbackInfoDto();
			userFeedbackInfoDto.setLike(true);
			userFeedbackInfoDto.setViewCount(0);
			userFeedbackInfoDto.setViewTime(0.f);
			
			postFeedback.put(likeList.get(likeInd).getPostId(), userFeedbackInfoDto);
			
			likeInd++;

		}
		while(viewInd < viewList.size()) {
			UserFeedbackInfoDto userFeedbackInfoDto = new UserFeedbackInfoDto();
			userFeedbackInfoDto.setViewCount(viewList.get(viewInd).getViewCount());
			userFeedbackInfoDto.setViewTime(viewList.get(viewInd).getViewTime());
			userFeedbackInfoDto.setLike(false);
			
			postFeedback.put(viewList.get(viewInd).getPostSeq(), userFeedbackInfoDto);
			
			viewInd++;
	
		}
		
		
		userFeedback.put(pageDto.getUserSeq(), postFeedback);
		try {
			recommendPicture.dataAppendToCSV(userFeedback, fileName, originFile);
		
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	
}
