package com.asu.nlp.predictvolatility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.asu.nlp.GrabStockPriceIndex;
import com.asu.nlp.model.StockValue;
import com.asu.nlp.model.VolatilityReturnModel;
import com.asu.nlp.model.VolumeData;
import com.asu.nlp.utils.Utils;

public class ComputeActualVolatility {

	
	public static List<VolatilityReturnModel> getActualVolatility(String stock){
		
		List<VolatilityReturnModel> volatilityModels = new ArrayList<VolatilityReturnModel>();
		
		Map<String, VolumeData> volumeMap = Utils.getVolumeDataMap(stock);

		List<StockValue> stockData = GrabStockPriceIndex.getStockData(stock);
		
		//note data is in reverse time order, we need  to predict t from t-1,  reverse both list
		//IMP
		Collections.reverse(stockData);
		
		if(!Utils.isAscendingOrder(stockData)){
			//System.out.println("Error: Descending order found, Please reverse data");
			return null;
		}
		
		Double stockValueAverage = Utils.getAverage(getStockValueAverage(stockData));
		
		//System.out.println("StockValueAverage " +stockValueAverage);
		
		for (int i = 0; i < stockData.size(); i++) {
			//take the log of values
			
			Double volatility = Double.valueOf(stockData.get(i).getClose()) - stockValueAverage;
			
			if(volatility<0)
			{
				volatility=Math.log(-1*volatility);
				volatility=(-1)*volatility; //negative in case stock price falls
			}else{
				volatility = Math.log(volatility);
			}
			VolumeData vData = volumeMap.get("\""+stockData.get(i).getStockDate()+"\"");
			VolatilityReturnModel volatilityReturnModel = new VolatilityReturnModel(volatility, null, stockData.get(i),vData);
			volatilityModels.add(volatilityReturnModel);
		}
		
		return volatilityModels;
	}
	
	public static double[] getStockValueAverage(List<StockValue> stockData){
		
		double stockInput[] = new double[stockData.size()];
		
		for (int i = 0; i < stockInput.length; i++) {
			stockInput[i]=Double.valueOf(stockData.get(i).getClose());  // using close value
		}
		return stockInput;
	}
	
	public static void main(String[] args) {
		List<VolatilityReturnModel> data = ComputeActualVolatility.getActualVolatility("GM");
		for (Iterator iterator = data.iterator(); iterator.hasNext();) {
			VolatilityReturnModel volatilityReturnModel = (VolatilityReturnModel) iterator.next();
			System.out.println(volatilityReturnModel.toString());
		}
	}
}