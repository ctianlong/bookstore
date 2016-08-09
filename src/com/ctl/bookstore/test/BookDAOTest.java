package com.ctl.bookstore.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ctl.bookstore.dao.BookDAO;
import com.ctl.bookstore.dao.impl.BookDAOImpl;
import com.ctl.bookstore.domain.Book;
import com.ctl.bookstore.web.CriteriaBook;
import com.ctl.bookstore.web.Page;

public class BookDAOTest {
	
	private BookDAO bookDAO = new BookDAOImpl();

	@Test
	public void testGetBook() {
		System.out.println(bookDAO.getBook(3));
	}

	@Test
	public void testGetPage() {
		CriteriaBook  cb = new CriteriaBook(50, 55, 2);
		Page<Book> page = bookDAO.getPage(cb);
		
		System.out.println("pageNo: " + page.getPageNo());
		System.out.println("totalPageNumber: " + page.getTotalPageNumber());
		System.out.println("list: " + page.getList());
		System.out.println("prevPage: " + page.getPrePage());
		System.out.println("nextPage: " + page.getNextPage());
	}

	@Test
	public void testGetTotalBookNumber() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPageList() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetStoreNumber() {
		int storeNumber = bookDAO.getStoreNumber(2);
		System.out.println(storeNumber); 
	}

}
