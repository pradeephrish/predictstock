package com.asu.nlp.computeindex;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.asu.nlp.model.SentimentData;
import com.asu.nlp.model.SentimentReturnFeature;
import com.asu.nlp.utils.Utils;

public class ComputeReturnFeatures {
	public static void main(String[] args) {
		
		ComputeReturnFeatures computeReturnIndex = new ComputeReturnFeatures();
		
		List<SentimentReturnFeature> stockFeatures = computeReturnIndex.getSentimentReturnFeatures("GOOG");
		for (int i = 0; i < stockFeatures.size(); i++) {
			System.out.println(stockFeatures.get(i).toString());
		}
	}
	
	public  static List<SentimentReturnFeature> getSentimentReturnFeatures(String stockSymbol){
		//read data from file, assuming all files will have same time span for data
		List<SentimentData> data = Utils.getSentiment(stockSymbol);
		
		List<SentimentReturnFeature> sentimentReturnFeatures = new ArrayList<SentimentReturnFeature>();
		
		for (int i = 0; i < data.size(); i++){
			Double bullish = Double.parseDouble(data.get(i).getBullish());
			Double bearish = Double.parseDouble(data.get(i).getBearish());
			Double bind =  Math.log((1+bullish)/(1+bearish)); //taking natural log
			Double tis =   (bullish+1)/(bullish+bearish+1);
			Double rtis = tis/(tis-1); //ratio
			sentimentReturnFeatures.add(new SentimentReturnFeature(bind,tis,data.get(i).getTimestamp()));
		}		
		return sentimentReturnFeatures;
	}
}
