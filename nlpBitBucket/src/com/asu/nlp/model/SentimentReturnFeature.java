package com.asu.nlp.model;

public class SentimentReturnFeature {
	private Double bind; //Bullishness Index
	private Double TIS; //Twitter Investment Sentiment
	private String date; //date is kept as string to compare fast using map
	
	public SentimentReturnFeature(Double bind, Double tIS, String date) {
		super();
		this.bind = bind;
		TIS = tIS;
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "SentimentReturnFeature [bind=" + bind + ", TIS=" + TIS
				+ ", date=" + date + "]";
	}

	public Double getBind() {
		return bind;
	}
	public void setBind(Double bind) {
		this.bind = bind;
	}
	public Double getTIS() {
		return TIS;
	}
	public void setTIS(Double tIS) {
		TIS = tIS;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
