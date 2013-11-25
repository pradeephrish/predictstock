package com.asu.nlp.model;
public class SentimentData{
		public SentimentData(String bullish, String bearish, String timestamp,
			String stock) {
		super();
		this.bullish = bullish;
		this.bearish = bearish;
		this.timestamp = timestamp;
		this.stock = stock;
	}
		
		public String toString() {
			return stock+" "+timestamp+" bullish:"+bullish+" "+"bearish:"+bearish;
		}
		
		
		String bullish;
		public String getBullish() {
			return bullish;
		}
		public void setBullish(String bullish) {
			this.bullish = bullish;
		}
		public String getBearish() {
			return bearish;
		}
		public void setBearish(String bearish) {
			this.bearish = bearish;
		}
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		public String getStock() {
			return stock;
		}
		public void setStock(String stock) {
			this.stock = stock;
		}
		String bearish;
		String timestamp;
		String stock;
	}