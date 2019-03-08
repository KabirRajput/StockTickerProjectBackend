package com.legend.stock.dbservice.model;

import java.util.List;

public class Quotes {

	private String username;
	private List<String> quotes;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public List<String> getQuotes() {
		return quotes;
	}
	public void setQuotes(List<String> quotes) {
		this.quotes = quotes;
	}
	public Quotes(String username, List<String> quotes) {
		super();
		this.username = username;
		this.quotes = quotes;
	}
	public Quotes() {
		super();
	}
	
	@Override
	public String toString() {
		return "Quotes [username=" + username + ", quotes=" + quotes + "]";
	}
	
}
