package com.accp.dao;

import java.sql.*;
import org.apache.log4j.Logger;

import com.accp.factory.ConnectionFactory;
import com.accp.factory.UnPoolConnectionFactory;



@SuppressWarnings("all")
public abstract class BaseDao {

	private  ConnectionFactory cf = UnPoolConnectionFactory.getInstance();// 单例模式获得连接对象

	private static Logger logger = Logger.getRootLogger();

	/**
	 * 
	 * @title: bindingParam
	 * @description: 绑定参数
	 * @param ps
	 * @param params
	 * @throws SQLException
	 */
	private void bindingParam(PreparedStatement ps, Object... params) throws SQLException {
		if (params != null) {
			for (int i = 0; i < params.length; i++) {
				ps.setObject(i + 1, params[i]);
			}
		}
	}
	public int querypage(String sql,Object... params){
		logger.debug("执行SQL:" + sql);
		System.out.println("执行SQL:"+sql);
		ResultSet rs=null;
		Connection conn = cf.getConnection();//获得产品
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			bindingParam(ps, params);
		    rs=ps.executeQuery();
			while(rs.next()){
		return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
		finally{
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
		}

	/**
	 * 
	 * @title: update
	 * @description: jdbc更新
	 * @param sql
	 * @param params
	 * @return
	 */
	protected int executeUpdate(String sql, Object... params) {
		logger.debug("执行SQL:" + sql);
		System.out.println("执行SQL:"+sql);
		Connection conn = cf.getConnection();//获得产品
		PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement(sql);
			bindingParam(ps, params);
			return ps.executeUpdate();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 
	 * @title: executeQuery
	 * @description: jdbc查询
	 * @param ps
	 * @param sql
	 * @param params
	 * @return
	 */
	protected ResultSet executeQuery(String sql, Object... params) {
		logger.debug("执行SQL:" + sql);
		System.out.println("执行SQL:"+sql);
		try {
			PreparedStatement ps = cf.getConnection().prepareStatement(sql);
			bindingParam(ps, params);
			return ps.executeQuery();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @param rs 
	 * @title: close
	 * @description: 释放资源
	 */
	public void close() {
		cf.releaseConnection();//销毁产品
	}

	/**
	 * 
	 * @title: commit
	 * @description: 提交事务
	 */
	public void commit() {
		try {
			cf.getConnection().commit();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 
	 * @title: rollback
	 * @description: 回滚事务
	 */
	public void rollback() {
		try {
			cf.getConnection().rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
