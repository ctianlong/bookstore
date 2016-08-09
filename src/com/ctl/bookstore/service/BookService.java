package com.ctl.bookstore.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.ctl.bookstore.dao.AccountDAO;
import com.ctl.bookstore.dao.BookDAO;
import com.ctl.bookstore.dao.TradeDAO;
import com.ctl.bookstore.dao.TradeItemDAO;
import com.ctl.bookstore.dao.UserDAO;
import com.ctl.bookstore.dao.impl.AccountDAOImpl;
import com.ctl.bookstore.dao.impl.BookDAOImpl;
import com.ctl.bookstore.dao.impl.TradeDAOImpl;
import com.ctl.bookstore.dao.impl.TradeItemDAOImpl;
import com.ctl.bookstore.dao.impl.UserDAOImpl;
import com.ctl.bookstore.domain.Book;
import com.ctl.bookstore.domain.ShoppingCart;
import com.ctl.bookstore.domain.ShoppingCartItem;
import com.ctl.bookstore.domain.Trade;
import com.ctl.bookstore.domain.TradeItem;
import com.ctl.bookstore.web.CriteriaBook;
import com.ctl.bookstore.web.Page;

public class BookService {
	
	private BookDAO bookDAO = new BookDAOImpl();
	
	public Page<Book> getPage(CriteriaBook cb){
		return bookDAO.getPage(cb);
	}

	public Book getBook(int id) {
		return bookDAO.getBook(id);
	}

	public boolean addToCart(int id, ShoppingCart sc) {
		Book book = bookDAO.getBook(id);
		if(book != null){
			sc.addBook(book);
			return true;
		}
		return false;
	}

	public void deleteItemFromShoppingCart(ShoppingCart sc, int id) {
		sc.removeItem(id);
	}

	public void clearShoppingCart(ShoppingCart sc) {
		sc.clear();
	}

	public void updateItemQuantity(ShoppingCart sc, int id, int quantity) {
		sc.updateItemQuantity(id, quantity);
	}
	
	private AccountDAO accountDAO = new AccountDAOImpl();
	private TradeDAO tradeDAO = new TradeDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private TradeItemDAO tradeItemDAO = new TradeItemDAOImpl();
	
	//业务方法
	public void cash(String username, String accountId, ShoppingCart sc) {
		//1. 更新 mybooks 数据表相关记录的 salesamount 和 storenumber
		bookDAO.batchUpdateStoreNumberAndSalesAmount(sc.getItems());
		//2. 更新 account 数据表的 balance
		accountDAO.updateBalance(Integer.parseInt(accountId), sc.getTotalMoney());
		//3. 向 trade 数据表插入一条记录
		Trade trade = new Trade();
		trade.setUserId(userDAO.getUser(username).getUserId());
		trade.setTradeTime(new Timestamp(new Date().getTime()));
		tradeDAO.insert(trade);
		//4. 向 tradeitem 数据表插入 n 条记录
		Collection<TradeItem> items = new ArrayList<>();
		for(ShoppingCartItem sci: sc.getItems()){
			TradeItem tradeItem = new TradeItem(sci.getQuantity(), sci.getBook().getId(), trade.getTradeId());
			items.add(tradeItem);
		}
		tradeItemDAO.batchSave(items);
		//5. 清空购物车
		sc.clear();
	}


}
