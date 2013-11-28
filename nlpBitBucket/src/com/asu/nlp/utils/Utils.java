package com.asu.nlp.utils;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

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
	
}
