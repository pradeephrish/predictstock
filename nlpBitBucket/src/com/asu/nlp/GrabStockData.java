package com.asu.nlp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.MasonTagTypes;
import net.htmlparser.jericho.MicrosoftTagTypes;
import net.htmlparser.jericho.Segment;
import net.htmlparser.jericho.Source;

public class GrabStockData {
	public static void main(String[] args) throws IOException {
		GrabStockData grabStockData = new GrabStockData();
		String data = grabStockData.getStringSource("http://stocktwits.com/symbol/GOOG?q=goog");
		grabStockData.processData(data);
	}
	
	public String getStringSource(String inputUrl) throws IOException{

		BufferedWriter output;
		URL url = new URL(inputUrl);
		MicrosoftTagTypes.register();
		MasonTagTypes.register();
		Source source = new Source(new InputStreamReader(url.openStream()));
		
		
		List<Element> scriptElements = source.getAllElements(HTMLElementName.SCRIPT);
		/*for (int i = 0; i < scriptElements.size(); i++) {
			System.out.println("Element no  " + i);
			System.out.println(scriptElements.get(i).getContent());
			System.out.println("*************************");
		}*/
		
		Segment data = scriptElements.get(24).getContent(); //
		return data.toString();
	}
	
	public void processData(String source){
			String[] lines = source.split(";");
			
			for (int i = 0; i < lines.length; i++) {
				if(lines[i].contains("var sentiment_data")){
					//process line
					System.out.println(lines[i]);
				}else if(lines[i].contains("var vol_data")){
					//process data
					System.out.println(lines[i]);
				}
			}
			
	}
	
}
