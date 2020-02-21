package com.accp.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;
public class UnPoolConnectionFactory implements ConnectionFactory {

	private final static UnPoolConnectionFactory cf = new UnPoolConnectionFactory();

	private static Logger logger = Logger.getRootLogger();

	// 静态很重要
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();// 存放单个连接， 多线程情况下，保存线程安全，一个线程一个ThreadLocal

	public static String CLASS_NAME;
	public static String URL;
	public static String USER_NAME;
	public static String USER_PWD;

	/**
	 * 
	 * 加载java属性配置文件
	 */
	private UnPoolConnectionFactory() {
		Properties param = new Properties();
		try {
			param.load(UnPoolConnectionFactory.class.getResourceAsStream("db.properties"));
			URL = param.getProperty("db.url");
			CLASS_NAME = param.getProperty("db.classname");
			USER_NAME = param.getProperty("db.username");
			USER_PWD = param.getProperty("db.pwd");
		} catch (Exception ex) {
			logger.error("配置文件读取失败", ex);
		}

		try {
			Class.forName(CLASS_NAME);
		} catch (ClassNotFoundException e) {
			logger.error("驱动加载失败", e);
		}

	}

	/**
	 * 
	 * @title: getInstance
	 * @description: 单例获得实例
	 * @return
	 */
	public static UnPoolConnectionFactory getInstance() {
		return cf;
	}

	@Override
	public Connection getConnection() {
		try {
			Connection conn = tl.get();// key:线程id
			if (conn == null) {
				conn = DriverManager.getConnection(URL, USER_NAME, USER_PWD);
				conn.setAutoCommit(false);// 关闭jdbc自动提交事务【非常重要】
				tl.set(conn);// 非常重要
			}
			return conn;
		} catch (SQLException e) {
			logger.error("数据库连接获得失败", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void releaseConnection() {
		try {
			Connection conn = tl.get();
			if (conn != null && !conn.isClosed()) {
				conn.close();
				tl.set(null);// 非常重要
			}
		} catch (SQLException e) {
			logger.error("数据库连接关闭失败", e);
			throw new RuntimeException(e);
		}
	}

}
