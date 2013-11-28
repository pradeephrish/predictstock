package com.asu.nlp.model;

public class PredictedResult {
	Double result;
	String modelName;
	
	public PredictedResult(Double result, String modelName) {
		super();
		this.result = result;
		this.modelName = modelName;
	}
	
	
	@Override
	public String toString() {
		return "PredictedResult [result=" + result + ", modelName=" + modelName
				+ "]";
	}


	public Double getResult() {
		return result;
	}
	public void setResult(Double result) {
		this.result = result;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	
}
