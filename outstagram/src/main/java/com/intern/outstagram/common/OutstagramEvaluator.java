package com.intern.outstagram.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.eval.AbstractDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class OutstagramEvaluator {
	private static final Logger logger = LoggerFactory.getLogger(OutstagramEvaluator.class);
	
	public void userBasedEvaluator() throws IOException, TasteException {
	
		double score = 0.0;
		DataModel model = new FileDataModel(new File("/Users/user/Desktop/project/intern/intern/outstagram/src/main/resources/data/picture.csv"));
		
		RecommenderBuilder builder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel model) throws TasteException {
				UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(model, Weighting.WEIGHTED);
				
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(900, userSimilarity, model);
				//UserNeighborhood neighborhood = new ThresholdUserNeighborhood(0.5, userSimilarity, dataModel)
				Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, userSimilarity);
				return recommender;
			}
		};
		
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
	
		score = evaluator.evaluate(builder, null, model, 0.9, 0.1);
	
		logger.info("score : " + score);
		
	}
	
	public void itemBasedEvaluator() throws IOException, TasteException{
		double score = 0.0;
		
		DataModel model = new FileDataModel(new File("/Users/user/Desktop/project/intern/intern/outstagram/src/main/resources/data/picture.csv"));
		
		RecommenderBuilder builder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel model) throws TasteException{
				ItemSimilarity itemSimilarity = new LogLikelihoodSimilarity(model);
				Recommender recommender = new GenericItemBasedRecommender(model, itemSimilarity);
				return recommender;
			}
		};
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		score = evaluator.evaluate(builder, null, model, 0.9, 0.1);
		
		logger.info("score : " + score);
		
	}
	
	
}

//new PearsonCorrelationSimilarity(model); == cosin 유사도 
//new EuclideanDistanceSimilarity(model);
//new SpearmanCorrelationSimilarity(model);

