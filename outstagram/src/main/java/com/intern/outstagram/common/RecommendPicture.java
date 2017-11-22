/*
 * 
 * 추천 관련 클래스
 * 
 * RecommendPicture - DB에서 가져온 값을 (user_seq, post_seq, like, view, viewTime) set으로 만들어 파일에 저장 like와 follow는 true false 의 값을 가진다.   
 *
 * 
 * DataConverter = RecommendPicture에서 만들어진 데이터를 (user_seq, post_seq, score) set으로 만들어 파일에 저장 
 * 
 * @Author Sejun
 */

package com.intern.outstagram.common;

import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.intern.outstagram.domain.dto.ItemBasedDto;
import com.intern.outstagram.domain.dto.RecommendResultDto;
import com.intern.outstagram.domain.dto.UserBasedDto;
import com.intern.outstagram.domain.dto.UserFeedbackInfoDto;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.AveragingPreferenceInferrer;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;


public class RecommendPicture {
	private static final Logger logger = LoggerFactory.getLogger(RecommendPicture.class);
	

	
	public void createDataFile(Map<Integer, Object> featureDataMap, String fileName) throws IOException {
		logger.info("userSize : " + featureDataMap.size());
		
		String dir = "data";
		Resource resource1 = new ClassPathResource(dir + File.separator + fileName);
		Resource resource2 = new ClassPathResource(dir);
		String target = resource1.getURI().getPath();
		logger.info(dir + File.separator + fileName);
		File dirctory = new File(resource2.getURI().getPath());
		if(!dirctory.exists()) {
			dirctory.mkdirs();
		}
		File file = new File(target);
		if(file.exists()) {
			file.delete();
		}
		
//
//		file = new File(target);
	
		for(Map.Entry<Integer, Object> feedbackList : featureDataMap.entrySet()) {
			Integer userSeq = feedbackList.getKey();
			Map<Integer, Object> userFeedbackList = (Map<Integer, Object>) feedbackList.getValue();

			for(Map.Entry<Integer, Object> feedback : userFeedbackList.entrySet()) {
				
				Integer postSeq = feedback.getKey();
				
				UserFeedbackInfoDto userFeedbackInfoDto = (UserFeedbackInfoDto) feedback.getValue();
			
				String line = userSeq + "," + postSeq + "," + userFeedbackInfoDto.getLike().toString() + ","
						+ userFeedbackInfoDto.getViewCount() +","+ userFeedbackInfoDto.getViewTime() +"\n";
			
				//logger.info("line add" + line);
				
				FileUtils.writeStringToFile(file, line, true);		
			}
		}
	}
	
	public void dataAppendToCSV(Map<Integer, Object> featureDataMap, String userInfoAppendFile, String csvFileName) throws IOException{
		//String csvFileName = "picture.csv";
		String dirc = "data";
		//String userInfoAppendFile = "operatorRecommend.csv";
		Resource resource1 = new ClassPathResource(dirc + File.separator + csvFileName);
		Resource resource2 = new ClassPathResource(dirc + File.separator + userInfoAppendFile);
		String cpTarget = resource1.getURI().getPath();
		String target = resource2.getURI().getPath();
		
		File csvFile = FileUtils.getFile(target);
		
		
		FileUtils.copyFile(FileUtils.getFile(cpTarget), csvFile);
		
	
		for(Map.Entry<Integer, Object> feedbackList : featureDataMap.entrySet()) {
			Integer userSeq = feedbackList.getKey();
			Map<Integer, Object> userFeedbackList = (Map<Integer, Object>) feedbackList.getValue();
			for(Map.Entry<Integer, Object> feedback : userFeedbackList.entrySet()) {
				Integer postSeq = feedback.getKey();
				UserFeedbackInfoDto userFeedbackInfoDto = (UserFeedbackInfoDto) feedback.getValue();
				String line = userSeq + "," + postSeq + "," + 
						makeSimpleScore(userFeedbackInfoDto.getLike(), userFeedbackInfoDto.getViewCount(), userFeedbackInfoDto.getViewTime()) + "\n";
				
				FileUtils.writeStringToFile(csvFile, line,true);
				//logger.info("line add" + line);
			}
		}
	}
	
	public Integer dataConverter(String dataFileName, String csvFileName) throws IOException{
		Integer count = 0;

		String dirc = "data";
		logger.info(dataFileName);
		Resource resource1 = new ClassPathResource(dirc + File.separator + dataFileName);
		logger.info(csvFileName);
		Resource resource2 = new ClassPathResource(dirc + File.separator + csvFileName);
		logger.info(dirc);
		Resource resource3 = new ClassPathResource(dirc);
		logger.info("?????");
		logger.info( resource1.getURI().getPath());
		
		String data = resource1.getURI().getPath();
		logger.info(resource2.getURI().getPath());
		String csv = resource2.getURI().getPath();
		logger.info(data);
		logger.info(csv);
		File dircetory = FileUtils.getFile(resource3.getURI().getPath());
		if(!dircetory.exists()) {
			dircetory.mkdirs();
		}
		
		File dataFile = FileUtils.getFile(data);
		LineIterator iter = FileUtils.lineIterator(dataFile);
		
		File csvFile = FileUtils.getFile(csv);
		
		while(iter.hasNext()) {
			String[] dataSet = iter.next().split(",");
			String csvSet = dataSet[0] + "," + dataSet[1] + ","+
					makeSimpleScore(Boolean.parseBoolean(dataSet[2]), Integer.parseInt(dataSet[3]), Float.parseFloat(dataSet[4])) + "\n";
			FileUtils.writeStringToFile(csvFile, csvSet,true);
			//logger.info("line add" + csvSet);
			count++;
		}
		iter.close();
		
		return count;
	}

	
	
	public Double makeSimpleScore(Boolean isLike, Integer viewCount, Float viewTime) {

		Double score = 0.d;
		
		if(isLike) {
			score += 2;
		}
		if(viewCount > 5) {
			score = 2.d;
		}
		else {
			for(int vc = 0; vc < viewCount; vc++) {
				score += Math.pow(0.5, vc);
				
			}
		}
		score += viewTime/5;
		
		return Math.round(score*100d)/100d;
	}
	
	public long[] neareastNUserSearch(Integer userSeq) throws IOException, TasteException {
		logger.info("User NearestN ");
		
		Resource operatorCSV = new ClassPathResource("data/operatorRecommend.csv");
		
		DataModel model = new FileDataModel(new File(operatorCSV.getURI().getPath()));
		 
		//similarity relation 분석 
		
	    UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
	    //선호도 추론 
	
	    similarity.setPreferenceInferrer(new AveragingPreferenceInferrer(model));
	    //선호도 기준으로 n명 이웃 추론 
	
	    UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
	    //유사 이웃 
	    //long[] user = neigborhood.getUserNeighnorhood(userid);
	    //사용자 기준 추천 
	
	    long[] nearUser = neighborhood.getUserNeighborhood(userSeq);
	    for(long u : nearUser) {
	    		logger.info(userSeq+" user nearestUser : " + u);
	    		
	    }
	    return nearUser;
	}
	
	public List<RecommendResultDto> ItemBasedRecommender(ItemBasedDto itemBasedDto) throws IOException, TasteException {
		logger.info("Item Based Recommender");
		
		List<RecommendResultDto> recommendList = new ArrayList<>();
		Resource operatorCSV = new ClassPathResource("data/operatorRecommendCluster.csv");
		
		DataModel model = new FileDataModel(new File(operatorCSV.getURI().getPath()));

        ItemSimilarity sim = new LogLikelihoodSimilarity(model);
        //TanimotoCoefficientSimilarity sim = new TanimotoCoefficientSimilarity(dm);
        
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(model, sim);
        
        Recommender cachingRecommender = new CachingRecommender(recommender);
        
        List<RecommendedItem> recommendations = cachingRecommender.recommend(itemBasedDto.getUserSeq(), itemBasedDto.getItemCount());
        	
		for(RecommendedItem recommendation : recommendations){
			RecommendResultDto recommendResultDto = new RecommendResultDto();
			recommendResultDto.setUserSeq(itemBasedDto.getUserSeq());
			recommendResultDto.setPostSeq(recommendation.getItemID());
			recommendResultDto.setScore(recommendation.getValue());
			recommendList.add(recommendResultDto);
			logger.info(itemBasedDto.getUserSeq() + ", " + recommendation.getItemID() + ", " + recommendation.getValue());
		
		}
		
		return recommendList;
	}
	
	public List<RecommendResultDto> UserBasedRecommender(UserBasedDto userBasedDto) throws IOException, TasteException {
		
		logger.info("User Based Recommender");
		
		List<RecommendResultDto> recommendList = new ArrayList<>();
		Resource operatorCSV = new ClassPathResource("data/operatorRecommend.csv");
		
		DataModel model = new FileDataModel(new File(operatorCSV.getURI().getPath()));
		 
		//similarity relation 분석 
	    UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
	    //선호도 추론 
	    similarity.setPreferenceInferrer(new AveragingPreferenceInferrer(model));
	    //선호도 기준으로 n명 이웃 추론 
	    UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
	    //유사 이웃 
	    
	    
	    Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
	    
	    Recommender cachingRecommender = new CachingRecommender(recommender);
	    
	    List<RecommendedItem> recommendations = cachingRecommender.recommend(userBasedDto.getUserSeq(), userBasedDto.getItemCount());
	    for (RecommendedItem recommendation : recommendations) {
	    		RecommendResultDto recommendResultDto = new RecommendResultDto();
			recommendResultDto.setUserSeq(userBasedDto.getUserSeq());
			recommendResultDto.setPostSeq(recommendation.getItemID());
			recommendResultDto.setScore(recommendation.getValue());
			recommendList.add(recommendResultDto);
	    		logger.info(userBasedDto.getUserSeq() + ", " + recommendation.getItemID() + ", " + recommendation.getValue());
	    		
	    }
	    return recommendList;
	}
}
