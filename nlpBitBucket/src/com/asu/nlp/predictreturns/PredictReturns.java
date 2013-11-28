package com.asu.nlp.predictreturns;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import com.asu.nlp.model.StockReturnModel;
import com.asu.nlp.utils.Utils;


/*
 * Predict Returns
 */
public class PredictReturns {
	
	
	//executes Linear Model
    // Rˆt = f(Rt−1) (baseline)  -  baseline model for predicting returns
	
	//Note the order of input should ascending , since we are predict for t using t-1
	
	private void predictReturnsUsingBaseLine(List<StockReturnModel> input,String stock){
		double data[][] = new double[input.size()-2][2];
		if(!Utils.isAscendingOrder(input)){
			System.out.println("Error: Descending order found, Please reverse data");
			return;
		}
		
	    // Rˆt = f(Rt−1) (baseline)  -  baseline model for predicting returns
		
		//i will point to t
		//i-1 will point to t-1
		for (int i = 2,j=0; i < input.size(); i++,j++) {  //first entry(0th entry) is null
			data[j][0]=input.get(i).getActualReturns(); //y value at t 
			data[j][1]=input.get(i-1).getActualReturns(); //x value at t-1		
		}
		//data is ready
		SimpleRegression regression = Utils.executeBaseLineModel(data);
		
//		System.out.println("Predict Returns : Base Line Mean Square Error");
//		System.out.println("M1 "+stock +" "+regression.getMeanSquareError()*100);
		System.out.println("M1\t\t"+stock +"\t\t"+Math.sqrt(regression.getMeanSquareError()*100));
	}
	
	public void runBaseLine(String stock){
		List<StockReturnModel> aReturns = ComputeActualReturns
				.getActualReturns(stock);
		this.predictReturnsUsingBaseLine(aReturns,stock);
	}
	
	
	
	public void test(){
		System.out.println("Baseline - M1");
		System.out.println();
		System.out.println("Model\t\tStock\t\tRMSE(%)");
		this.runBaseLine("GOOG");
		this.runBaseLine("AAPL");
		this.runBaseLine("GM");
		this.runBaseLine("TWTR");
		System.out.println();
		System.out.println("Multilinear - M2");
		
	}
	
	public static void main(String[] args) {
		PredictReturns predictReturns = new PredictReturns();
		predictReturns.test();
	}
	
	
}
