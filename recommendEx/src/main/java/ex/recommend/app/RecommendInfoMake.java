package ex.recommend.app;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import ex.recommend.common.RecommendPicture;
import ex.recommend.dto.UserFeedbackInfoDto;
import ex.recommend.common.RandomUser;
import ex.recommend.dao.RecommendDao;
import ex.recommend.dto.LikeDto;
import ex.recommend.dto.UserDto;
import ex.recommend.dto.ViewDto;



public class RecommendInfoMake {
	
public Integer createRecommendData() {
		
		RecommendPicture recommendPicture = new RecommendPicture();
		RecommendDao recommendDao = new RecommendDao();
		Integer count = 0;
		
		try {
			List<UserDto> userList = recommendDao.selectUserForRecommend();
			Map<Integer, Object> userFeedback = new HashMap<Integer, Object>();
			System.out.println(userList.size());
			for(UserDto user : userList) {
				
				List<LikeDto> likeList = recommendDao.selectLikeForRecommendByUserSeq(user.getSeq());
				List<ViewDto> viewList = recommendDao.selectViewForRecommendByUserSeq(user.getSeq());
				
				
				//sort된 결과를 가져와서 
				int likeInd = 0, viewInd = 0;
				Map<Integer, Object> postFeedback = new HashMap<Integer, Object>();
				while(likeInd < likeList.size() && viewInd < viewList.size()) {
					UserFeedbackInfoDto userFeedbackInfoDto = new UserFeedbackInfoDto();
					int likePostSeq = likeList.get(likeInd).getPostId();
					int viewPostSeq = viewList.get(viewInd).getPostSeq();
					int postSeq = 0;
					
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
				break;
			}
			
			
			try {
				recommendPicture.createDataFile(userFeedback);
				
			}catch(IOException e) {
				
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
	
}
