package com.asu.nlp.predictvolatility;

import java.util.List;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import org.apache.commons.math3.stat.regression.SimpleRegression;

import com.asu.nlp.model.StockReturnModel;
import com.asu.nlp.model.StockValue;
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
	
	
	//ln (ˆσt) = f(ln (σt−1)) (baseline)
		private void predictUsingMultiLinear1(List<VolatilityReturnModel> input,String stock){
			double y[] = new double[input.size()-1];
			double x[][] = new double[input.size()-1][2];
			if(!Utils.isAscendingOrder(input)){
				System.out.println("Error: Descending order found, Please reverse data");
				return;
			}
			for (int i = 0,index=2; i < input.size()-2; i++,index++) {  //note index=2
				y[i]=input.get(index).getActualVolatility(); // y value at t;   volatitlity is actually log
				x[i][0]=Math.log(Double.valueOf(input.get(index-1).getVolumeData().getVolume_score())); // x1 value at t-1 
				x[i][1]=Math.log(Double.valueOf(input.get(index-2).getVolumeData().getVolume_score()));
			}
			//data is ready
			OLSMultipleLinearRegression regression = (OLSMultipleLinearRegression) Utils.executeMultiLiniearRegression(y, x);
			//print error
//			double[] beta = regression.estimateRegressionParameters();       

//			double[] residuals = regression.estimateResiduals();

//			double[][] parametersVariance = regression.estimateRegressionParametersVariance();

			double regressandVariance = regression.estimateRegressandVariance();

			double rSquared = regression.calculateRSquared();

			double sigma = regression.estimateRegressionStandardError();
			
			//check really root mean square error rSquared ?
			System.out.println("M2\t\t"+stock +"\t\t"+Math.sqrt(rSquared*100));
		}
		
		//ln (ˆσt) = f(ln (σt−1),ln (nt−1),ln (MAt−1))
		private void predictUsingMultiLinear2(List<VolatilityReturnModel> input,String stock){
					double y[] = new double[input.size()-1];
					double x[][] = new double[input.size()-1][3];
					if(!Utils.isAscendingOrder(input)){
						System.out.println("Error: Descending order found, Please reverse data");
						return;
					}
					for (int i = 0,index=3; i < input.size()-3; i++,index++) {  //note index=2
						y[i]=input.get(index).getActualVolatility(); // y value at t;   volatitlity is actually log
						x[i][0]=Math.log(Double.valueOf(input.get(index-1).getVolumeData().getVolume_score())); // x1 value at t-1 
						x[i][1]=Math.log(Double.valueOf(input.get(index-2).getVolumeData().getVolume_score()));
						x[i][2]=Math.log(Double.valueOf(input.get(index-3).getVolumeData().getVolume_score()));
					}
					//data is ready
					OLSMultipleLinearRegression regression = (OLSMultipleLinearRegression) Utils.executeMultiLiniearRegression(y, x);
					//print error
//					double[] beta = regression.estimateRegressionParameters();       

//					double[] residuals = regression.estimateResiduals();

//					double[][] parametersVariance = regression.estimateRegressionParametersVariance();

					double regressandVariance = regression.estimateRegressandVariance();

					double rSquared = regression.calculateRSquared();

					double sigma = regression.estimateRegressionStandardError();
					
					//check really root mean square error rSquared ?
					System.out.println("M3\t\t"+stock +"\t\t"+Math.sqrt(rSquared*100));
				}
	
	
	//ln (ˆσt) = f(ln (σt−1)) (baseline)
	public void runBaseLine(String stock){
		List<VolatilityReturnModel> vReturns = ComputeActualVolatility.getActualVolatility(stock);
		this.predictVolatilityUsingBaseLine(vReturns, stock);
	}
	
	//ln (ˆσt) = f(ln (σt−1),ln (nt−1),ln (nt−2))
	public void runMutlilinearModel1(String stock){
		List<VolatilityReturnModel> vReturns = ComputeActualVolatility.getActualVolatility(stock);
		this.predictUsingMultiLinear1(vReturns, stock);
	}
	
	
	//ln (ˆσt) = f(ln (σt−1),ln (nt−1),ln (nt−2))
		public void runMutlilinearModel2(String stock){
			List<VolatilityReturnModel> vReturns = ComputeActualVolatility.getActualVolatility(stock);
			this.predictUsingMultiLinear2(vReturns, stock);
		}
	
	
	public void test(){
		System.out.println("Volatility Baseline - M1");
		System.out.println();
		System.out.println("Model\t\tStock\t\tRMSE(%)");
		this.runBaseLine("GOOG");
		this.runBaseLine("AAPL");
		this.runBaseLine("GM");
		this.runBaseLine("TWTR");
		System.out.println();
		System.out.println("Volatility Multilinear - M2");
		System.out.println();
		System.out.println("Model\t\tStock\t\tRMSE(%)");
		this.runMutlilinearModel1("GOOG");
		this.runMutlilinearModel1("AAPL");
		this.runMutlilinearModel1("GM");
		this.runMutlilinearModel1("TWTR");
		System.out.println();
		System.out.println("Volatility Multilinear - M3");
		System.out.println();
		System.out.println("Model\t\tStock\t\tRMSE(%)");
		this.runMutlilinearModel2("GOOG");
		this.runMutlilinearModel2("AAPL");
		this.runMutlilinearModel2("GM");
		this.runMutlilinearModel2("TWTR");
	}
	
	
	public static void main(String[] args) {
		PredictVolatility predictVolatility = new PredictVolatility();
		predictVolatility.test();
	}
}
