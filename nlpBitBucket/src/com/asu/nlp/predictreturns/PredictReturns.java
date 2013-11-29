package com.asu.nlp.predictreturns;

import java.util.List;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
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
	
	//Rˆt = f(Rt−1,ln (TISt−1))  - M2
	private void predictUsingMultiLinear1(List<StockReturnModel> input,String stock)
	{
		double y[] = new double[input.size()-2];
		double x[][] = new double[input.size()-2][2];
		if(!Utils.isAscendingOrder(input)){
			System.out.println("Error: Descending order found, Please reverse data");
			return;
		}
		
		for (int i = 2,j=0; i < input.size(); i++,j++) {  //first entry(0th entry) is null
			y[j]=input.get(i).getActualReturns();  // y value at t
			x[j][0]=input.get(i-1).getActualReturns(); //x1 value at t-1 
			x[j][1]=Math.log(input.get(i-1).getSentimentReturnFeature().getTIS()); //x2 value at t-1		
		}
		
		
		OLSMultipleLinearRegression regression = (OLSMultipleLinearRegression) Utils.executeMultiLiniearRegression(y, x);
		//print error
//		double[] beta = regression.estimateRegressionParameters();       

//		double[] residuals = regression.estimateResiduals();

//		double[][] parametersVariance = regression.estimateRegressionParametersVariance();

		double regressandVariance = regression.estimateRegressandVariance();

		double rSquared = regression.calculateRSquared();

		double sigma = regression.estimateRegressionStandardError();
		
		//check really root mean square error rSquared ?
		System.out.println("M2\t\t"+stock +"\t\t"+Math.sqrt(rSquared*100));
	}
	
	public void runBaseLine(String stock){
		List<StockReturnModel> aReturns = ComputeActualReturns.getActualReturns(stock);
		this.predictReturnsUsingBaseLine(aReturns,stock);
	}
	
	
     	//Rˆt = f(bindt-1,ln (TISt−1))  - M3
		private void predictUsingMultiLinear2(List<StockReturnModel> input,String stock)
		{
			double y[] = new double[input.size()-2];
			double x[][] = new double[input.size()-2][2]; //increase value when evaluating
			if(!Utils.isAscendingOrder(input)){
				System.out.println("Error: Descending order found, Please reverse data");
				return;
			}
			
			for (int i = 2,j=0; i < input.size(); i++,j++) {  //first entry(0th entry) is null
				y[j]=input.get(i).getActualReturns();  // y value at t
				x[j][0]=input.get(i-1).getSentimentReturnFeature().getBind(); //x1 value at t-1 
				x[j][1]=Math.log(input.get(i-1).getSentimentReturnFeature().getTIS()); //x2 value at t-1
//				x[j][2]=input.get(i-1).getActualReturns(); //x2 value at t-1
			}
			
			
			OLSMultipleLinearRegression regression = (OLSMultipleLinearRegression) Utils.executeMultiLiniearRegression(y, x);
			//print error
//			double[] beta = regression.estimateRegressionParameters();       

//			double[] residuals = regression.estimateResiduals();

//			double[][] parametersVariance = regression.estimateRegressionParametersVariance();

			double regressandVariance = regression.estimateRegressandVariance();

			double rSquared = regression.calculateRSquared();

			double sigma = regression.estimateRegressionStandardError();
			
			//check really root mean square error rSquared ?
			System.out.println("M3\t\t"+stock +"\t\t"+Math.sqrt(rSquared*100));
		}
	
	
	
	//Rˆt = f(Rt−1,ln (TISt−1))  - M2
	public void runMutlilinearModel1(String stock){
		List<StockReturnModel> aReturns = ComputeActualReturns.getActualReturns(stock);
		this.predictUsingMultiLinear1(aReturns, stock);
	}
	
	//Rˆt = f(bindt−1,ln (TISt−1))  - M2
		public void runMutlilinearModel2(String stock){
			List<StockReturnModel> aReturns = ComputeActualReturns.getActualReturns(stock);
			this.predictUsingMultiLinear2(aReturns, stock);
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
		this.runMutlilinearModel1("GOOG");
		this.runMutlilinearModel1("AAPL");
		this.runMutlilinearModel1("GM");
		this.runMutlilinearModel1("TWTR");
		System.out.println("Multilinear - M3");
		this.runMutlilinearModel2("GOOG");
		this.runMutlilinearModel2("AAPL");
		this.runMutlilinearModel2("GM");
		this.runMutlilinearModel2("TWTR");
	}
	
	public static void main(String[] args) {
		PredictReturns predictReturns = new PredictReturns();
		predictReturns.test();
	}
	
	
}
