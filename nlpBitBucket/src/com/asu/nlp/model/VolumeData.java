package com.asu.nlp.model;
public class VolumeData{
		public VolumeData(String timestamp, String volume_score,
				String volume_change,String stockSymbol) {
			super();
			this.timestamp = timestamp;
			this.volume_score = volume_score;
			this.volume_change = volume_change;
			this.stockSymbol=stockSymbol;
		}
		
		
		public String toString() {
			return stockSymbol+" "+timestamp+" volume_score:"+volume_score+" volume_change:"+volume_change;
		}
		
		String timestamp;
		private String stockSymbol;
		public String getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(String timestamp) {
			this.timestamp = timestamp;
		}
		public String getVolume_score() {
			return volume_score;
		}
		public void setVolume_score(String volume_score) {
			this.volume_score = volume_score;
		}
		public String getVolume_change() {
			return volume_change;
		}
		public void setVolume_change(String volume_change) {
			this.volume_change = volume_change;
		}
		public String getStockSymbol() {
			return stockSymbol;
		}
		public void setStockSymbol(String stockSymbol) {
			this.stockSymbol = stockSymbol;
		}
		String volume_score;
		String volume_change;
		
	}