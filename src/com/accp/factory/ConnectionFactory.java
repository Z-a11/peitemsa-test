package com.accp.factory;

import java.sql.Connection;

public interface ConnectionFactory {
	/**
	 * 
	 * @title: getConnection
	 * @description: 获得数据库连接
	 * @return
	 */
	public Connection getConnection();

	/**
	 * 
	 * @title: releaseConnection
	 * @description: 释放连接
	 */
	public void releaseConnection();
}
