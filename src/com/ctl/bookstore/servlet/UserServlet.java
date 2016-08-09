package com.ctl.bookstore.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ctl.bookstore.domain.User;
import com.ctl.bookstore.service.UserService;
import com.ctl.bookstore.utils.ReflectionUtils;


@WebServlet("/servlet/userServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private UserService userService = new UserService();

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String methodName = request.getParameter("method");
		try {
			ReflectionUtils.invokeMethod(this, methodName, new Class<?>[]{HttpServletRequest.class, HttpServletResponse.class},new Object[]{request, response} );
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void getUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		User user = userService.getUserWithTrades(username);
		if(user == null){
			request.setAttribute("message", "用户不存在");
		}else{
			request.setAttribute("user", user);
		}
		
		request.getRequestDispatcher("/bookstore/users.jsp").forward(request, response);
	}

}
