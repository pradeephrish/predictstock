package com.asu.nlp.predictreturns;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.asu.nlp.GrabStockPriceIndex;
import com.asu.nlp.computeindex.ComputeReturnFeatures;
import com.asu.nlp.model.SentimentData;
import com.asu.nlp.model.SentimentReturnFeature;
import com.asu.nlp.model.StockReturnModel;
import com.asu.nlp.model.StockValue;
import com.asu.nlp.utils.Utils;

/*
 * 
 */

public class ComputeActualReturns {

	/*
	 * This returns actual returns, predicted returns will be null
	 */
	public static List<StockReturnModel> getActualReturns(String stock) {

		List<StockReturnModel> stockReturnModels = new ArrayList<StockReturnModel>();

		List<SentimentReturnFeature> senticments = ComputeReturnFeatures
				.getSentimentReturnFeatures(stock);
		List<StockValue> stockData = GrabStockPriceIndex.getStockData(stock);
		
		//note data is in reverse time order, we need  to predict t from t-1,  reverse both list
		//IMP
		Collections.reverse(senticments);
		Collections.reverse(stockData);
		
		if(!Utils.isAscendingOrder(senticments)&&!Utils.isAscendingOrder(stockData)){
			System.out.println("Error: Descending order found, Please reverse data");
			return null;
		}
		
		
		

		// size must be same

		// first entry - with no actual returns and predicted returns

		int i = 0, j = 0; // assuming both will start from same date- for now
							// data is like that

		if (stockData.size() != senticments.size()) {
			System.out
					.println("Note Sentiment and Stock Data is only computed for the days when stock market is open");
		}

		Double adjClose_t = null;
		Double adjClose_t_1 = null;
		Double actualReturns = null;
		i=0;
		j=0;
		for (; i < senticments.size() && j < stockData.size();) {

			Integer compare = Utils.compare(senticments.get(i).getDate(), stockData.get(j).getDate());
			//if equal
			if (compare == 0) {
				StockReturnModel stockReturnModel = new StockReturnModel(null,
						null, stockData.get(j), senticments.get(i));
				
				if(j!=0){
				adjClose_t = Double.parseDouble(stockData.get(j).getAdjClose());
				adjClose_t_1 = Double.parseDouble(stockData.get(j - 1)
						.getAdjClose()); // i-1
				actualReturns = Math.log(adjClose_t) - Math.log(adjClose_t_1);
				stockReturnModel.setActualReturns(actualReturns);
				}
				
				stockReturnModels.add(stockReturnModel);
				
				if(i < senticments.size())
					++i;
				
				if(j < stockData.size())
					++j;
			}else{
				if(compare == -1){ //first value is less
					
					if(i < senticments.size())
						++i;
					
				}else{    //else second value is big
					
					if(j < stockData.size())
						++j;
					
				}
			}
		}
		return stockReturnModels;

	}

	public static void main(String[] args) {
		List<StockReturnModel> aReturns = ComputeActualReturns
				.getActualReturns("GOOG");
		for (Iterator iterator = aReturns.iterator(); iterator.hasNext();) {
			StockReturnModel stockReturnModel = (StockReturnModel) iterator
					.next();
			System.out.println(stockReturnModel.toString());
		}
	}
}
