package com.ctl.bookstore.service;

import com.ctl.bookstore.dao.AccountDAO;
import com.ctl.bookstore.dao.impl.AccountDAOImpl;
import com.ctl.bookstore.domain.Account;

public class AccountService {
	
	private AccountDAO accountDAO = new AccountDAOImpl();

	public Account getAccountById(int accountId) {
		return accountDAO.get(accountId);
	}
	
	

}
