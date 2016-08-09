package com.ctl.bookstore.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ctl.bookstore.dao.BookDAO;
import com.ctl.bookstore.domain.Book;
import com.ctl.bookstore.domain.ShoppingCartItem;
import com.ctl.bookstore.web.CriteriaBook;
import com.ctl.bookstore.web.Page;

public class BookDAOImpl extends BaseDAO<Book> implements BookDAO {

	@Override
	public Book getBook(int id) {
		String sql = "SELECT id, author, title, price, publishingDate, salesAmount, storeNumber, remark "
				+ "FROM mybooks WHERE id = ?";
		return query(sql, id);
	}

	@Override
	public Page<Book> getPage(CriteriaBook cb) {
		if(getTotalBookNumber(cb) == 0){
			return null;
		}
		Page<Book> page = new Page<Book>(cb.getPageNo());
		page.setTotalItemNumber(getTotalBookNumber(cb));
		if(cb.getPageNo() != page.getPageNo()){
			cb.setPageNo(page.getPageNo());			
		}
		page.setList(getPageList(cb, Page.getPageSize()));
		return page;
	}

	@Override
	public long getTotalBookNumber(CriteriaBook cb) {
		String sql = "SELECT count(id) FROM mybooks WHERE price >= ? AND price <= ?";
		return getSingleVal(sql, cb.getMinPrice(), cb.getMaxPrice());
	}

	@Override
	public List<Book> getPageList(CriteriaBook cb, int pageSize) {
		String sql = "SELECT id, author, title, price, publishingDate, salesAmount, storeNumber, remark FROM mybooks WHERE price >= ? AND price <= ? "
				+ "LIMIT ?, ?";
		return queryForList(sql, cb.getMinPrice(), cb.getMaxPrice(), (cb.getPageNo() - 1) * pageSize, pageSize);
	}

	@Override
	public int getStoreNumber(Integer id) {
		String sql = "SELECT storeNumber FROM mybooks WHERE id = ?";
		return getSingleVal(sql, id);
	}

	@Override
	public void batchUpdateStoreNumberAndSalesAmount(Collection<ShoppingCartItem> items) {
		String sql = "UPDATE mybooks SET salesAmount = salesAmount + ?, "
				+ "storeNumber = storeNumber - ? WHERE id = ?";
		List<ShoppingCartItem> scis = new ArrayList<>(items);
		int len = scis.size();
		Object[][] params = new Object[len][3];
		for(int i = 0; i < len; i++){
			params[i][0] = scis.get(i).getQuantity();
			params[i][1] = params[i][0];
			params[i][2] = scis.get(i).getBook().getId();
		}
		batch(sql, params);
	}


}
