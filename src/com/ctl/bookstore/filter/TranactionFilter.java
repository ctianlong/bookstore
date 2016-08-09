package com.ctl.bookstore.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ctl.bookstore.db.JDBCUtils;
import com.ctl.bookstore.web.ConnectionContext;


public class TranactionFilter implements Filter {

  
    public TranactionFilter() {
    }

	public void destroy() {
	}


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		Connection connection = null;
		
		try {
			//1. 获取连接
			connection = JDBCUtils.getConnection();
			//2. 开启事务
			connection.setAutoCommit(false);
			//3. 利用 ThreadLocal 把连接和当前线程绑定
			ConnectionContext.getInstance().Bind(connection);
			//4. 把请求转给目标 Servlet
			chain.doFilter(request, response);
			//5. 提交事务
			connection.commit();
		} catch (Exception e) {
			e.printStackTrace();
			//6. 回滚事务
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpServletRequest req = (HttpServletRequest) request;
			resp.sendRedirect(req.getContextPath() + "/bookstore/error.jsp");
			
		} finally {
			//7. 解除绑定
			ConnectionContext.getInstance().remove();
			//8. 关闭连接
			JDBCUtils.release(connection);
		}
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
