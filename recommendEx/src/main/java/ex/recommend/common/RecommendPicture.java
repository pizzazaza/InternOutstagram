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

package ex.recommend.common;

import java.io.IOException;
import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;



import ex.recommend.dto.UserFeedbackInfoDto;


public class RecommendPicture {

	public void createDataFile(Map<Integer, Object> featureDataMap) throws IOException {
		System.out.println("create data file function");
		String dir = "/Users/user/Desktop/project/intern/intern/recommendEx/src/main/resources/data";
		String fileName = "preProcessing.data";
	
		File dirctory = new File(dir);
		if(!dirctory.exists()) {
			dirctory.mkdirs();
		}
		File file = new File(dir + File.separator + fileName);
		file.createNewFile();
		
		for(Map.Entry<Integer, Object> feedbackList : featureDataMap.entrySet()) {
			Integer userSeq = feedbackList.getKey();
			Map<Integer, Object> userFeedbackList = (Map<Integer, Object>) feedbackList.getValue();

			for(Map.Entry<Integer, Object> feedback : userFeedbackList.entrySet()) {
				
				Integer postSeq = feedback.getKey();
				
				UserFeedbackInfoDto userFeedbackInfoDto = (UserFeedbackInfoDto) feedback.getValue();
			
				String line = userSeq + "," + postSeq + "," + userFeedbackInfoDto.getLike().toString() + ","
						+ userFeedbackInfoDto.getViewCount() +","+ userFeedbackInfoDto.getViewTime() +"\n";
			
				
				FileUtils.writeStringToFile(file, line, true);
			
				
			}
		}
		
	}
	
	
	
	public Integer dataConverter() throws IOException{
		Integer count = 0;
		String dataFileName = "preProcessing.data";
		String csvFileName = "picture.csv";
		String dirc = "/Users/user/Desktop/project/intern/intern/outstagram/src/main/resources/data";
		
		List<String> lines = null;

		File dircetory = FileUtils.getFile(dirc);
		if(!dircetory.exists()) {
			dircetory.mkdirs();
		}
		File dataFile = FileUtils.getFile(dirc + File.separator + dataFileName);
		LineIterator iter = FileUtils.lineIterator(dataFile);
		
		File csvFile = FileUtils.getFile(dirc + File.separator + csvFileName);
		
		while(iter.hasNext()) {
			String[] dataSet = iter.next().split(",");
			String csvSet = dataSet[0] + "," + dataSet[1] + ","+
					makeSimpleScore(Boolean.parseBoolean(dataSet[2]), Integer.parseInt(dataSet[3]), Float.parseFloat(dataSet[4])) + "\n";
			FileUtils.writeStringToFile(csvFile, csvSet,true);
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
			for(int vc = 1; vc <= viewCount; vc++) {
				for(int powInd = 0; powInd < viewCount; powInd++) {
					score += Math.pow(0.5, powInd);
				}
			}
		}
		score += viewTime/5;
		
		return Math.round(score*100d)/100d;
	}
	
//	public void ItemBasedRecommender(ItemBasedDto itemBasedDto) throws IOException, TasteException {
//		
//		DataModel dm = new FileDataModel(new File("/Users/user/Desktop/project/intern/intern/outstagram/src/main/resources/data/picture.csv"));
//
//        ItemSimilarity sim = new LogLikelihoodSimilarity(dm);
//        //TanimotoCoefficientSimilarity sim = new TanimotoCoefficientSimilarity(dm);
//        
//        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender(dm, sim);
//        
//        Recommender cachingRecommender = new CachingRecommender(recommender);
//        
//        List<RecommendedItem>recommendations = cachingRecommender.recommend(itemBasedDto.getUserSeq(), itemBasedDto.getItemCount());
//        	
//		for(RecommendedItem recommendation : recommendations){
//			
////			logger.info(itemBasedDto.getUserSeq() + ", " + recommendation.getItemID() + ", " + recommendation.getValue());
//		
//		}
//	}
//	
//	public void UserBasedRecommender(UserBasedDto userBasedDto) throws IOException, TasteException {
//		
//		
//		DataModel model = new FileDataModel(new File("/Users/user/Desktop/project/intern/intern/outstagram/src/main/resources/data/picture.csv"));
//		 
//		//similarity relation 분석 
//	    UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
//	    //선호도 추론 
//	    similarity.setPreferenceInferrer(new AveragingPreferenceInferrer(model));
//	    //선호도 기준으로 n명 이웃 추론 
//	    UserNeighborhood neighborhood = new NearestNUserNeighborhood(3, similarity, model);
//	    //유사 이웃 
//	    //long[] user = neigborhood.getUserNeighnorhood(userid);
//	    //사용자 기준 추천 
//	    Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
//	    
//	    Recommender cachingRecommender = new CachingRecommender(recommender);
//	    
//	    List<RecommendedItem> recommendations = cachingRecommender.recommend(userBasedDto.getUserSeq(), userBasedDto.getItemCount());
//	    for (RecommendedItem recommendation : recommendations) {
////	    		logger.info(userBasedDto.getUserSeq() + ", " + recommendation.getItemID() + ", " + recommendation.getValue());
//	    		
//	    }
//	}
}
