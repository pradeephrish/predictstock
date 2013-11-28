package com.asu.nlp.utils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.math3.stat.regression.MultipleLinearRegression;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import com.asu.nlp.model.SentimentData;
import com.asu.nlp.model.VolumeData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class Utils {
	public static GsonBuilder gsonBuilder = new GsonBuilder(); 
	public static Gson gson = gsonBuilder.create(); 
	
	public static List<SentimentData> getSentiment(String stock){	
		String rootS ="resources//data//sentiments";
		String fileNameS=rootS+File.separator+stock;
			String dataS = null;
			try {
				dataS = FileUtils.readFileToString(new File(fileNameS));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return gson.fromJson(dataS, new TypeToken<List<SentimentData>>(){}.getType());
			
	}
	
	public static List<SentimentData> getVolume(String stock){	
		String rootV ="resources//data//volume";
		String fileNameV=rootV+File.separator+stock;
		String dataV = null;
		try {
			dataV = FileUtils.readFileToString(new File(fileNameV));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gson.fromJson(dataV, new TypeToken<List<VolumeData>>(){}.getType());
	}
	
	public static Integer compare(String firstDate,String secondDate){
		//the value 0 if the argument Date is equal to this Date; a value less than 0 if this Date is before the Date argument; and a value greater than 0 if this Date is after the Date argument. 
	
		
		SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			Date first = myFormat.parse(firstDate.replace("\"", ""));
			Date second = myFormat.parse(secondDate.replace("\"", ""));
			
			return first.compareTo(second);
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public SimpleRegression executeBaseLineModel(double[][] data){
		SimpleRegression regression = new SimpleRegression();
		regression.addData(data);
		System.out.println(regression.getIntercept());
		// displays intercept of regression line

		System.out.println(regression.getSlope());
		// displays slope of regression line

		System.out.println(regression.getSlopeStdErr());
		// displays slope standard error
		
		return regression;
	}
	
	public MultipleLinearRegression executeMultiLiniearRegression(double[] y,double[][] x){
		OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
		regression.newSampleData(y, x);
		double[] beta = regression.estimateRegressionParameters();       
		double[] residuals = regression.estimateResiduals();
		double[][] parametersVariance = regression.estimateRegressionParametersVariance();
		double regressandVariance = regression.estimateRegressandVariance();
		double rSquared = regression.calculateRSquared();
		double sigma = regression.estimateRegressionStandardError();
		
		return regression;
	}
	
	
	
}
