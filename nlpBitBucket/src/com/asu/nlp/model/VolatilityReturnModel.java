package com.asu.nlp.model;

import com.asu.nlp.interfaces.DateInterface;

public class VolatilityReturnModel implements DateInterface {
	Double actualVolatility;
	PredictedResult predictedResult;
	StockValue stockValue;
	
	
	
	public VolatilityReturnModel(Double actualVolatility,
			PredictedResult predictedResult, StockValue stockValue) {
		super();
		this.actualVolatility = actualVolatility;
		this.predictedResult = predictedResult;
		this.stockValue = stockValue;
	}

	@Override
	public String getStockDate() {
		// TODO Auto-generated method stub
		return stockValue.getStockDate();
	}

	public Double getActualVolatility() {
		return actualVolatility;
	}

	public void setActualVolatility(Double actualVolatility) {
		this.actualVolatility = actualVolatility;
	}

	public PredictedResult getPredictedResult() {
		return predictedResult;
	}

	public void setPredictedResult(PredictedResult predictedResult) {
		this.predictedResult = predictedResult;
	}

	public StockValue getStockValue() {
		return stockValue;
	}

	public void setStockValue(StockValue stockValue) {
		this.stockValue = stockValue;
	}

	@Override
	public String toString() {
		return "VolatilityReturnModel [actualVolatility=" + actualVolatility
				+ ", predictedResult=" + predictedResult + ", stockValue="
				+ stockValue + "]";
	}
	
	
	
}
