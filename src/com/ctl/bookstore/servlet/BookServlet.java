package com.ctl.bookstore.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ctl.bookstore.domain.Account;
import com.ctl.bookstore.domain.Book;
import com.ctl.bookstore.domain.ShoppingCart;
import com.ctl.bookstore.domain.ShoppingCartItem;
import com.ctl.bookstore.domain.User;
import com.ctl.bookstore.service.AccountService;
import com.ctl.bookstore.service.BookService;
import com.ctl.bookstore.service.UserService;
import com.ctl.bookstore.utils.ReflectionUtils;
import com.ctl.bookstore.web.BookStoreWebUtils;
import com.ctl.bookstore.web.CriteriaBook;
import com.ctl.bookstore.web.Page;
import com.google.gson.Gson;


@WebServlet("/servlet/bookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private BookService bookService = new BookService();
	private UserService userService = new UserService();
	private AccountService accountService = new AccountService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		
		try {
			ReflectionUtils.invokeMethod(this, methodName, new Class<?>[]{HttpServletRequest.class, HttpServletResponse.class}, new Object[]{request, response});
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
//		try {
//			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
//			method.setAccessible(true);
//			method.invoke(this, request, response);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	//返回书的列表
	private void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		
		int pageNo = 1;
		float minPrice = 0;
		float maxPrice = Integer.MAX_VALUE;
		
		try {
			pageNo = Integer.parseInt(pageNoStr);
		} catch (Exception e) {}
		try {
			minPrice = Float.parseFloat(minPriceStr);
		} catch (Exception e) {}
		try {
			maxPrice = Float.parseFloat(maxPriceStr);
		} catch (Exception e) {}

		CriteriaBook cb = new CriteriaBook(minPrice, maxPrice, pageNo);
		Page<Book> page = bookService.getPage(cb);
		
		if(page == null){
			String msg = "空空如也";
			request.setAttribute("msg", msg);
		} else {
			request.setAttribute("bookpage", page);			
		}
		request.getRequestDispatcher("/WEB-INF/bookstorepages/books.jsp").forward(request, response);	
	}
	
	
	private void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		Book book = null;
		try {
			id = Integer.parseInt(idStr);
		} catch (RuntimeException e) {}
		if(id > 0){
			book = bookService.getBook(id);			
		}
		
		request.setAttribute("book", book);
		request.getRequestDispatcher("/WEB-INF/bookstorepages/book.jsp").forward(request, response);
	}
	
	private void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取商品的 id
		String idStr = request.getParameter("id");
		int id = -1;
		
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {}
		
		String message = "添加失败";
		if(id > 0){
			// 获取购物车对象
			ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
			boolean flag = bookService.addToCart(id, sc);
			if(flag){
				Book book = bookService.getBook(id);
				message = "成功添加" + book.getTitle() + "到购物车";			
			}
		}
		request.setAttribute("message", message);
		getBooks(request, response);		
	}
	
	private void forwardPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = request.getParameter("page");
		request.getRequestDispatcher("/WEB-INF/bookstorepages/" + page).forward(request, response);
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		int id = -1;
		
		try {
			id = Integer.parseInt(idStr);
		} catch (Exception e) {}
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.deleteItemFromShoppingCart(sc, id);
		
		request.getRequestDispatcher("/WEB-INF/bookstorepages/cart.jsp").forward(request, response);
	}
	
	private void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.clearShoppingCart(sc);
		request.getRequestDispatcher("/WEB-INF/bookstorepages/cart.jsp").forward(request, response);
	}
	
	private void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idStr = request.getParameter("id");
		String quantityStr = request.getParameter("quantity");
		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		
		int id = -1;
		int quantity = -1;
		
		try {
			id = Integer.parseInt(idStr);
			quantity = Integer.parseInt(quantityStr);
		} catch (Exception e) {}
		
		if(id > 0 && quantity > 0){
			bookService.updateItemQuantity(sc, id, quantity);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("bookNumber", sc.getBookNumber());
		result.put("totalMoney", sc.getTotalMoney());
		
		Gson gson = new Gson();
		String jsonStr = gson.toJson(result);
		
		response.setContentType("text/javascript");
		response.getWriter().println(jsonStr);		
	}
	
	private void cash(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 简单验证: 验证表单域的值是否符合基本的规范: 是否为空, 是否可以转为 int 类型, 是否是一个 email. 不需要进行查询
		//数据库或调用任何的业务方法.
		String username = request.getParameter("username");
		String accountId = request.getParameter("accountId");
		
		String errors = validateFormField(username, accountId);
		//表单验证通过。 
		if("".equals(errors)){
			errors = validateUser(username, accountId);
			//用户名和账号验证通过
			if("".equals(errors)){
				errors = validateBookStoreNumber(request);
				//库存验证通过
				if("".equals(errors)){
					//验证余额
					errors = validateBalance(request, accountId);
				}
			}
		}
		//如果不通过
		if(!"".equals(errors)){
			request.setAttribute("errors", errors);
			request.getRequestDispatcher("/WEB-INF/bookstorepages/cash.jsp").forward(request, response);
			return;
		}
		
		//验证通过执行具体的逻辑操作
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		bookService.cash(username, accountId, sc);
		response.sendRedirect(request.getContextPath() + "/bookstore/success.jsp");
		
	}
	
	//验证表单域是否符合基本的规则: 是否为空. 
	public String validateFormField(String username, String accountId){
		StringBuffer errors = new StringBuffer("");
		if(username == null || "".equals(username.trim())){
			errors.append("用户姓名不能为空<br>");
		}
		if(accountId == null || "".equals(accountId.trim())){
			errors.append("用户账号不能为空");
		}
		return errors.toString();
	}
	
	//验证用户名和账号是否匹配
	public String validateUser(String username, String accountId){
		String errors = "";
		User user = userService.getUserByUsername(username);
		if(user == null){
			errors = "用户姓名不存在";
		}else{
			int accountId2 = user.getAccountId();
			if(!accountId.trim().equals(accountId2 + "")){
				errors = "用户姓名与账号不匹配";
			}
		}
		return errors;
	}
	
	//验证库存是否充足
	public String validateBookStoreNumber(HttpServletRequest request){
		StringBuffer errors = new StringBuffer("");
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		
		for(ShoppingCartItem sci: sc.getItems()){
			int quantity = sci.getQuantity();
			int storeNumber = bookService.getBook(sci.getBook().getId()).getStoreNumber();
			if(quantity > storeNumber){
				errors.append(sci.getBook().getTitle() + "库存不足<br>");
			}
		}
		
		return errors.toString();
	}
	
	//验证余额是否充足
	public String validateBalance(HttpServletRequest request, String accountId){
		String errors = "";
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		Account account = accountService.getAccountById(Integer.parseInt(accountId));
		if(sc.getTotalMoney() > account.getBalance()){
			errors = "余额不足";
		}
		return errors;
	}
	
}
