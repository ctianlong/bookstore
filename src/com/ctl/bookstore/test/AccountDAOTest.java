package com.ctl.bookstore.test;

import org.junit.Test;

import com.ctl.bookstore.dao.AccountDAO;
import com.ctl.bookstore.dao.impl.AccountDAOImpl;
import com.ctl.bookstore.domain.Account;

public class AccountDAOTest {
	
	private AccountDAO accountDao = new AccountDAOImpl();

	@Test
	public void testGet() {
		Account account = accountDao.get(1);
		System.out.println(account);
	}

	@Test
	public void testUpdateBalance() {
		accountDao.updateBalance(1, 40);
		System.out.println(accountDao.get(1).getBalance());
	}

}
