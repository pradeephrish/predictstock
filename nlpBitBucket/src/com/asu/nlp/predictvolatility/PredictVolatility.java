package com.asu.nlp.predictvolatility;

import java.util.List;

import org.apache.commons.math3.stat.regression.SimpleRegression;

import com.asu.nlp.model.StockReturnModel;
import com.asu.nlp.model.VolatilityReturnModel;
import com.asu.nlp.predictreturns.ComputeActualReturns;
import com.asu.nlp.utils.Utils;

public class PredictVolatility {
	
	//ln (ˆσt) = f(ln (σt−1)) (baseline)
	private void predictVolatilityUsingBaseLine(List<VolatilityReturnModel> input,String stock){
		double data[][] = new double[input.size()-1][2];
		if(!Utils.isAscendingOrder(input)){
			System.out.println("Error: Descending order found, Please reverse data");
			return;
		}
		for (int i = 0,index=1; i < input.size()-1; i++,index++) {
			data[i][0]= input.get(index).getActualVolatility(); // y value at t
			data[i][1]=input.get(index-1).getActualVolatility(); // x value at t-1
		}
		//data is ready
		SimpleRegression regression = Utils.executeBaseLineModel(data);
		
		System.out.println("M1\t\t"+stock +"\t\t"+Math.sqrt(regression.getMeanSquareError()*100));
	}
	
	public void runBaseLine(String stock){
		List<VolatilityReturnModel> vReturns = ComputeActualVolatility.getActualVolatility(stock);
		this.predictVolatilityUsingBaseLine(vReturns, stock);
	}
	
	
	
	
	
	public void test(){
		System.out.println("Baseline - M1");
		System.out.println();
		System.out.println("Model\t\tStock\t\tRMSE(%)");
		this.runBaseLine("GOOG");
		this.runBaseLine("AAPL");
		this.runBaseLine("GM");
		this.runBaseLine("TWTR");
	}
	
	
	public static void main(String[] args) {
		PredictVolatility predictVolatility = new PredictVolatility();
		predictVolatility.test();
	}
}
