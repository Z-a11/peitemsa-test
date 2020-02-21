package com.accp.dao;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.accp.annotation.*;
import com.accp.annotation.Table;
public class CommonDao extends BaseDao{
	
	public <T> List<T> queryAll(Class<T> cls) {
		ResultSet rs = null;
		List<T> data;
		String tblName = null;
		try {
			if (cls.isAnnotationPresent(Table.class)) {
				// 获得指定表元注解
				Table tblAn = cls.getDeclaredAnnotation(Table.class);
				tblName = tblAn.value();
			}
			rs = super.executeQuery("select * from " + tblName);
			data = new ArrayList<T>();
			while (rs.next()) {
				Object obj = cls.getDeclaredConstructor().newInstance();// 无参实例化
				// 属性赋值
				Field[] fs = cls.getDeclaredFields();
				for (Field field : fs) {
					field.setAccessible(true);
					// 获得指定列元注解
					if (field.isAnnotationPresent(Column.class)) {
						Column colAn = field.getDeclaredAnnotation(Column.class);
						field.set(obj, rs.getObject(colAn.name()));
					}

				}
				data.add((T) obj);
			}
			return data;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				rs.close();
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
	public <T> int deleteById(Class<T> cls,Object id) {
		 String tal=null;//表名
		try {
			String sql="delete from %s where %s=?";
			if (cls.isAnnotationPresent(Table.class)) {
				Table t = cls.getDeclaredAnnotation(Table.class);
				tal = t.value();
			}
			Field[] fs=cls.getDeclaredFields();
			// 获得指定列元注解
			for (Field f : fs) {
				if (f.isAnnotationPresent(Column.class) && f.getDeclaredAnnotation(Column.class).isPrimaryKey()) {
					sql=String.format(sql, tal,f.getDeclaredAnnotation(Column.class).name());
				}
			}
			return super.executeUpdate(sql,id);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public <T> int updateById(Object obj) {//修改
		 Object tal=null;//表名
		 Object id=null;//id值
		 String idname = null;//id名
		 Class cls=obj.getClass();
		try {
			// 获得表的元注解
			if (cls.isAnnotationPresent(Table.class)) {
				Table t = (Table) cls.getDeclaredAnnotation(Table.class);
				tal = t.value();
			}
			Field[] fs=cls.getDeclaredFields();
			System.out.println(fs);
			String sql="UPDATE ";
			sql+=tal+" set";
			for (int i=0;i<fs.length;i++) {
				fs[i].setAccessible(true);
				if (fs[i].getDeclaredAnnotation(Column.class).isPrimaryKey()==true) {
					id=fs[i].get(obj).toString();
					idname=fs[i].getDeclaredAnnotation(Column.class).name();
				}
				else if(!fs[i].getDeclaredAnnotation(Column.class).isPrimaryKey()) {
					if(fs[i].getDeclaredAnnotation(Column.class).type().equals("varchar")) {
					sql+=" "+fs[i].getDeclaredAnnotation(Column.class).name()+"= '"+fs[i].get(obj).toString()+"' ";
					}
					else {
						sql+=" "+fs[i].getDeclaredAnnotation(Column.class).name()+"="+fs[i].get(obj).toString()+" ";
					}
				}
				if(i!=fs.length-1&&!fs[i].getDeclaredAnnotation(Column.class).isPrimaryKey()) {
					sql+=",";
				}
			}
			sql+="where "+idname+"="+id;
			System.out.println(sql);
			return super.executeUpdate(sql);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public  int insert(Object objs) {//新增
		Object tal=null;//表名
		Class cls=objs.getClass();
		try {
			// 获得表的元注解
			if (cls.isAnnotationPresent(Table.class)) {
			 Table t = (Table) cls.getDeclaredAnnotation(Table.class);
			 tal = t.value();
			}
			Field[] fs=cls.getDeclaredFields();
			String sql="INSERT INTO ";
			sql+=tal+" (";
			for (int i =1; i < fs.length; i++) {
				fs[i].setAccessible(true);
				if(fs[i].getDeclaredAnnotation(Column.class).isnull()==false&&fs[i].getDeclaredAnnotation(Column.class).isPrimaryKey()==false) {
					sql+=" "+fs[i].getDeclaredAnnotation(Column.class).name();
				}
				if(i!=fs.length-1) {
					sql+=",";
				}
			}
			sql+=") values(";
			for (int i =1; i < fs.length; i++) {
				fs[i].setAccessible(true);
				if (fs[i].getDeclaredAnnotation(Column.class).type().equals("varchar")) {// 判断该列定义的是否为“varchar” 该类型需要“'”(单引号)
					sql+=" '"+fs[i].get(objs)+"'";
				} else {
					sql+=" "+fs[i].get(objs);
				}
				if(i!= fs.length-1) {
					sql+=",";
				}
			}
			sql+=")";
			return super.executeUpdate(sql);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	public <T> List<T> query(Class<T> cls, Object obj, Boolean judge,String lname) {// judge 为true时模糊查询反之不   //暂时+列名
		ResultSet rs = null;
		List<T> data;
		try {
			String sql = "select * from " + cls.getSimpleName();
			Field[] fi = cls.getDeclaredFields();
			if (obj != null) {
				sql += " where 1=1";
					if (obj instanceof Integer) {
						System.out.println("int类型");
						if ((int) obj != 0) {
							sql += " AND " + lname + " LIKE '%" + obj + "%'";
						}
					} else if (obj instanceof String) {
						System.out.println("String");
						if (obj != null) {
							sql += " AND " + lname + " LIKE '%" + obj + "%'";
						}
					}
				}
			if (!judge) {
				sql = sql.replace("LIKE", "=");
				sql = sql.replace("%", "");
			}
			System.out.println(sql);
			rs = super.executeQuery(sql);
			data = new ArrayList<T>();
			while (rs.next()) {
				// 通用性无参构建对象
				T objt = cls.newInstance();
				// 循环给属性赋值
				for (Field f : cls.getDeclaredFields()) {
					f.setAccessible(true);
					f.set(objt, rs.getObject(f.getName()));
				}
				data.add(objt);
			}
			return data;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				rs.close();
			} catch (SQLException ex) {
				throw new RuntimeException(ex);
			}
		}
	}
}
