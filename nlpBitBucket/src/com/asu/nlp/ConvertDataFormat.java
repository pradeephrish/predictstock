package com.asu.nlp;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import au.com.bytecode.opencsv.CSVWriter;

import com.asu.nlp.GrabStockData.Path;
import com.asu.nlp.model.SentimentData;
import com.asu.nlp.model.VolumeData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/*
 * This one will convert JSON to Data to redable tabular format and it will also compute indexes
 */
public class ConvertDataFormat {
	
	public static GsonBuilder gsonBuilder = new GsonBuilder(); 
	public static Gson gson = gsonBuilder.create(); 
	
	public void init(String stocks[]){
		for (int i = 0; i < stocks.length; i++) {
			formatFromJSON(stocks[i]);
		}
	}
	
	
	public void formatFromJSON(String stock){
	
		String rootS ="resources//data//sentiments";
		String rootV ="resources//data//volume";
		try {
			String fileNameS=rootS+File.separator+stock;
			String fileNameV=rootV+File.separator+stock;
			String dataS= FileUtils.readFileToString(new File(fileNameS));
			String dataV= FileUtils.readFileToString(new File(fileNameV));
			List<SentimentData> sentimentData = gson.fromJson(dataS, new TypeToken<List<SentimentData>>(){}.getType());
			List<VolumeData> volumeData = gson.fromJson(dataV, new TypeToken<List<VolumeData>>(){}.getType());
			
			List<String> rowsS= new ArrayList<String>();
			List<String> rowsV= new ArrayList<String>();
			
			for (int i = 0; i < sentimentData.size(); i++) {
				rowsS.add(sentimentData.get(i).toString());
			}
			//rows may not be same
			for (int i = 0; i < volumeData.size(); i++) {
				rowsV.add(volumeData.get(i).toString());
			}
			
			FileUtils.writeLines(new File(fileNameS+"F"), rowsS);
			
			FileUtils.writeLines(new File(fileNameV+"F"), rowsV);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		
		String stocks[] = {"GOOG","AAPL","GM","TWTR"};
		ConvertDataFormat convertDataFormat =new ConvertDataFormat();
		convertDataFormat.init(stocks);
		
	}
}
