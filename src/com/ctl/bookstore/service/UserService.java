package com.ctl.bookstore.service;

import java.util.Set;

import com.ctl.bookstore.dao.BookDAO;
import com.ctl.bookstore.dao.TradeDAO;
import com.ctl.bookstore.dao.TradeItemDAO;
import com.ctl.bookstore.dao.UserDAO;
import com.ctl.bookstore.dao.impl.BookDAOImpl;
import com.ctl.bookstore.dao.impl.TradeDAOImpl;
import com.ctl.bookstore.dao.impl.TradeItemDAOImpl;
import com.ctl.bookstore.dao.impl.UserDAOImpl;
import com.ctl.bookstore.domain.Trade;
import com.ctl.bookstore.domain.TradeItem;
import com.ctl.bookstore.domain.User;

public class UserService {
	
	private UserDAO userDAO = new UserDAOImpl();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	private BookDAO bookDAO = new BookDAOImpl();

	public User getUserByUsername(String username) {
		return userDAO.getUser(username);
	}

	public User getUserWithTrades(String username) {
		//调用 UserDAO 的方法获取 User 对象
		User user = userDAO.getUser(username);
		if(user == null){
			return null;
		}
		int userId = user.getUserId();
		//调用 TradeDAO 的方法获取 Trade 的集合，把其装配为 User 的属性
		Set<Trade> trades = tradeDAO.getTradesWithUserId(userId);
		//调用 TradeItemDAO 的方法获取每一个 Trade 中的 TradeItem 的集合，并把其装配为 Trade 的属性
		if(trades != null){
			for(Trade trade: trades){
				Set<TradeItem> items = tradeItemDAO.getTradeItemsWithTradeId(trade.getTradeId());
				if(items != null){
					for(TradeItem tradeItem: items){
						tradeItem.setBook(bookDAO.getBook(tradeItem.getBookId()));
					}
					trade.setItems(items);
				}
			}
			user.setTrades(trades);
		}
		return user;
	}
	
	

}
