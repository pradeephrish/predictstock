package com.asu.nlp;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

import com.asu.nlp.model.StockValue;


public class GrabStockPriceIndex {
	//no need to fetch, saved csv in data/stockindex
	
	public static List <StockValue> getStockData(String stockQuote){
		String path = "resources//data//stockindex//"+stockQuote+".csv"; 
		List<StockValue> stockList = new ArrayList<StockValue>();
		try {
			CSVReader csvReader = new CSVReader(new FileReader(new File(path)));
			List<String[]> list = csvReader.readAll();
			for (int i = 1; i < list.size(); i++) { //skip first element
				String[] element = list.get(i);
				StockValue stockIndex = new StockValue(element[0], element[1], element[2], element[2], element[3], element[4], element[5]);
				stockList.add(stockIndex);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return stockList;
	}
	
	public static void main(String[] args) {
		GrabStockPriceIndex grabStockPriceIndex  = new GrabStockPriceIndex();
		List<StockValue> data = grabStockPriceIndex.getStockData("GOOG");
		for (int i = 0; i < data.size(); i++) {
			System.out.println(data.get(i).toString());
		}
	}
}
