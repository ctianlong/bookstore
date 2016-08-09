package com.ctl.bookstore.domain;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.LinkedHashSet;
import java.util.Set;

public class Trade {
	
	//Trade 对象对应的 id
	private Integer tradeId;
		
	@Override
	public String toString() {
		return "Trade [tradeId=" + tradeId + ", tradeTime=" + tradeTime + ", userId=" + userId + "]";
	}

	//交易的时间
	private Timestamp tradeTime;

	public Timestamp getTradeTime() {
		return tradeTime;
	}
	
	public String getTime(){
		DateFormat df = DateFormat.getDateTimeInstance();
		String time = df.format(tradeTime);
		return time;
	}

	public void setTradeTime(Timestamp tradeTime) {
		this.tradeTime = tradeTime;
	}

	//Trade 关联的多个 TradeItem
	private Set<TradeItem> items = new LinkedHashSet<TradeItem>();
	
	//和 Trade 关联的 User 的 userId
	private Integer userId;
	
	//得到本次交易总价格
	public float getTradeMoney(){
		float tradeMoney = 0;
		for(TradeItem item: items){
			tradeMoney += item.getItemMoney();
		}
		return tradeMoney;
	}

	public Integer getTradeId() {
		return tradeId;
	}

	public void setTradeId(Integer tradeId) {
		this.tradeId = tradeId;
	}



	public Set<TradeItem> getItems() {
		return items;
	}

	public void setItems(Set<TradeItem> items) {
		this.items = items;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Trade() {
	}

}
