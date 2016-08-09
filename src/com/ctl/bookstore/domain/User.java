package com.ctl.bookstore.domain;

import java.util.LinkedHashSet;
import java.util.Set;

public class User {
	
	private Integer userId;
	private String username;
	private int accountId;
	
	private Set<Trade> trades = new LinkedHashSet<Trade>();
	
	public User() {
	}
	
	public User(Integer userId, String username, int accountId) {
		super();
		this.userId = userId;
		this.username = username;
		this.accountId = accountId;
	}
	
	public void setTrades(Set<Trade> trades) {
		this.trades = trades;
	}
	
	public Set<Trade> getTrades() {
		return trades;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", accountId=" + accountId + "]";
	}

	
}
