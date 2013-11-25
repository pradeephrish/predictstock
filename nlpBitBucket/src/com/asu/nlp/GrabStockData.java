package com.asu.nlp;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftTagTypes;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

import org.apache.commons.io.FileUtils;

import com.asu.nlp.model.SentimentData;
import com.asu.nlp.model.VolumeData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class GrabStockData {
	
	public static GsonBuilder gsonBuilder = new GsonBuilder(); 
	public static Gson gson = gsonBuilder.create(); 
	
	public void init(String[] urls,String[] stocks){
		for (int i = 0; i < urls.length; i++) {
			try {
				String stock = urls[i].substring(urls[i].lastIndexOf("/")+1,urls[i].lastIndexOf("?"));
				String data = this.getStringSource(urls[i]);
				this.processData(data, stock);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("Data Stored in JSON Object Store");
	}
	
	public static void main(String[] args) throws IOException {
		GrabStockData grabStockData = new GrabStockData();
		
		String urls[] = {"http://stocktwits.com/symbol/GOOG?q=goog","http://stocktwits.com/symbol/AAPL?q=AAPL","http://stocktwits.com/symbol/GM?q=GM","http://stocktwits.com/symbol/TWTR?q=TWTR"};
		String stocks[] = {"GOOG"};
		grabStockData.init(urls, stocks);
	}
	
	enum Path{
		SENTIMENT,
		VOLUME;
	}
	
	public static void saveToFile(String data, String fileName,Path path){
		
		String root = null;
		if(path.equals(Path.SENTIMENT)){
			root = "resources//data//sentiments";
		}else{
			root = "resources//data//volume";
		}

		fileName=root+File.separator+fileName;
	
		try {
			FileUtils.write(new File(fileName), data+"\n",true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static List<SentimentData> processSentimentString(String jsonSentimentString,String stock) {
		// TODO Auto-generated method stub
		List<SentimentData> listElements = new ArrayList<SentimentData>();
		JsonArray jsonArray = gson.fromJson(jsonSentimentString, JsonArray.class);	
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonElement = (JsonObject) jsonArray.get(i);
			listElements.add(new SentimentData(jsonElement.get("bullish").toString(), jsonElement.get("bearish").toString(), jsonElement.get("timestamp").toString(), stock));
		}
		return listElements;
	}

	public String getStringSource(String inputUrl) throws IOException{
		URL url = new URL(inputUrl);
		MicrosoftTagTypes.register();
		MasonTagTypes.register();
		Source source = new Source(new InputStreamReader(url.openStream()));
		List<Element> scriptElements = source.getAllElements(HTMLElementName.SCRIPT);
		
		for (int i = 0; i < scriptElements.size(); i++) {
			String data = scriptElements.get(i).getContent().toString();
			//need to check only
			if(data.contains("var sentiment_data")){
				return data;
			}
		}
		return "";
	}
	
	public void processData(String source,String stock){
			String[] lines = source.split(";");
			for (int i = 0; i < lines.length; i++) {
				if(lines[i].contains("var sentiment_data")||lines[i].contains("var vol_data")){
				String sentimentString = lines[i]; 
//				System.out.println(lines[i]);
				String jsonString = sentimentString.substring(sentimentString.indexOf("["),sentimentString.length());
				if(lines[i].contains("var sentiment_data")){
					List<SentimentData> results = processSentimentString(jsonString, stock);
					saveToFile(gson.toJson(results), stock, Path.SENTIMENT);
//					System.out.println(lines[i]);
				}else if(lines[i].contains("var vol_data")){
					List<VolumeData> results = processVolumeString(jsonString, stock);
					saveToFile(gson.toJson(results), stock, Path.VOLUME);
					System.out.println(lines[i]);
				}
			}
			}
	}	
		
	
	
	
	public static List<VolumeData> processVolumeString(String jsonVolumeString,String stock){
		
		List<VolumeData> listElements = new ArrayList<VolumeData>();
		JsonArray jsonArray = gson.fromJson(jsonVolumeString, JsonArray.class);	
		for (int i = 0; i < jsonArray.size(); i++) {
			JsonObject jsonElement = (JsonObject) jsonArray.get(i);
			listElements.add(new VolumeData(jsonElement.get("timestamp").toString(), jsonElement.get("volume_score").toString(), jsonElement.get("volume_change").toString(), stock));
		}
		return listElements;
	}
	
	
	
	
}
