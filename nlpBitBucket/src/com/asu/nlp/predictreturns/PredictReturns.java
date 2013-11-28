package com.asu.nlp.predictreturns;

import java.util.Iterator;
import java.util.List;

import com.asu.nlp.model.StockReturnModel;
import com.asu.nlp.utils.Utils;


/*
 * Predict Returns
 */
public class PredictReturns {
	
	
	//executes Linear Model
    // Rˆt = f(Rt−1) (baseline)  -  baseline model for predicting returns
	
	//Note the order of input should ascending , since we are predict for t using t-1
	
	public void predictReturnsUsingBaseLine(List<StockReturnModel> input){
		double data[][] = new double[input.size()][];
		
		if(!Utils.isAscendingOrder(input)){
			System.out.println("Error: Descending order found, Please reverse data");
			return;
		}
		
	    // Rˆt = f(Rt−1) (baseline)  -  baseline model for predicting returns
		
		//i will point to t
		//i-1 will point to t-1
		for (int i = 1; i < input.size(); i++) {
//			data[i][0]
		}
	}
	
	
	
	
	
}
