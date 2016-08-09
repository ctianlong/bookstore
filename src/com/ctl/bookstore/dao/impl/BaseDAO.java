package com.ctl.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.ctl.bookstore.dao.Dao;
import com.ctl.bookstore.db.JDBCUtils;
import com.ctl.bookstore.utils.ReflectionUtils;
import com.ctl.bookstore.web.ConnectionContext;

public class BaseDAO<T> implements Dao<T> {
	
	private QueryRunner queryRunner = new QueryRunner();
	
	private Class<T> clazz;
	
	public BaseDAO() {
		clazz = ReflectionUtils.getSuperGenericType(getClass());
	}

	@Override
	public long insert(String sql, Object... args) {
		long id = 0;
		Connection conn = null;
		PreparedStatement pStatement = null;
		ResultSet resultSet = null;
		try {
			conn = ConnectionContext.getInstance().get();
			pStatement = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			if(args != null){
				for(int i = 0; i < args.length; i++){
					pStatement.setObject(i + 1, args[i]);
				}
			}
			pStatement.executeUpdate();
			//获取生成的主键值
			resultSet = pStatement.getGeneratedKeys();
			while(resultSet.next()){
				id = resultSet.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtils.release(null, pStatement, resultSet);
		}
		return id;
	}

	@Override
	public void update(String sql, Object... args) {
		Connection conn = null;
		try {
			conn = ConnectionContext.getInstance().get();
			queryRunner.update(conn, sql, args);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public T query(String sql, Object... args) {
		Connection conn = null;
		try {
			conn = ConnectionContext.getInstance().get();
			return queryRunner.query(conn, sql, new BeanHandler<>(clazz), args);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<T> queryForList(String sql, Object... args) {
		Connection conn = null;
		try {
			conn = ConnectionContext.getInstance().get();
			return queryRunner.query(conn, sql, new BeanListHandler<>(clazz), args);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <V> V getSingleVal(String sql, Object... args) {
		Connection conn = null;
		try {
			conn = ConnectionContext.getInstance().get();
			return (V) queryRunner.query(conn, sql, new ScalarHandler(), args);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void batch(String sql, Object[]... params) {
		Connection conn = null;
		try {
			conn = ConnectionContext.getInstance().get();
			queryRunner.batch(conn, sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
