package com.asu.nlp.model;

public class StockReturnModel {
	Double actualReturns;
	Double predictedReturns; //while computing actual returns, this will be null
	StockValue stockDetails;
	SentimentReturnFeature sentimentReturnFeature;
	
	public StockReturnModel(Double actualReturns, Double predictedReturns,
			StockValue stockDetails,
			SentimentReturnFeature sentimentReturnFeature) {
		super();
		this.actualReturns = actualReturns;
		this.predictedReturns = predictedReturns;
		this.stockDetails = stockDetails;
		this.sentimentReturnFeature = sentimentReturnFeature;
	}
	
	@Override
	public String toString() {
		return "StockReturnModel [actualReturns=" + actualReturns
				+ ", predictedReturns=" + predictedReturns + ", stockDetails="
				+ stockDetails + ", sentimentReturnFeature="
				+ sentimentReturnFeature + "]";
	}

	public Double getActualReturns() {
		return actualReturns;
	}
	public void setActualReturns(Double actualReturns) {
		this.actualReturns = actualReturns;
	}
	public Double getPredictedReturns() {
		return predictedReturns;
	}
	public void setPredictedReturns(Double predictedReturns) {
		this.predictedReturns = predictedReturns;
	}
	public StockValue getStockDetails() {
		return stockDetails;
	}
	public void setStockDetails(StockValue stockDetails) {
		this.stockDetails = stockDetails;
	}
	public SentimentReturnFeature getSentimentReturnFeature() {
		return sentimentReturnFeature;
	}
	public void setSentimentReturnFeature(
			SentimentReturnFeature sentimentReturnFeature) {
		this.sentimentReturnFeature = sentimentReturnFeature;
	}
	
	
}
