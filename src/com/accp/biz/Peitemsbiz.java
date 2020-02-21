package com.accp.biz;

import java.util.List;

import com.accp.dao.CommonDao;
import com.accp.entity.peitems;

public class Peitemsbiz {
	private CommonDao dao=new CommonDao();
	  public List<peitems> queryAll(){//查询Peitems所有
		  try {
			  return dao.queryAll(peitems.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		 finally {
			 dao.close();	
		}
		  
	  }
	  public void add(peitems temp) {//新增
			try {
				dao.insert(temp);
				if("sa".equals(temp.getItemname())) {
					throw new RuntimeException("用户名重复");
				}
				dao.commit();
			} catch (Exception e) {
				dao.rollback();
				throw new RuntimeException(e);
			} finally {
				dao.close();
			}
		}
	  public void dele(Object id) {//删除
			try {
				dao.deleteById(peitems.class,id);
				dao.commit();
			} catch (Exception e) {
				dao.rollback();
				throw new RuntimeException(e);
			} finally {
				dao.close();
			}
		}
	  public void update(peitems temp) {//修改
			try {
				dao.updateById(temp);
				dao.commit();
			} catch (Exception e) {
				dao.rollback();
				throw new RuntimeException(e);
			} finally {
				dao.close();
			}
		}
	  public List<peitems> queryid(Object id) {//根据id查找
			try {
			 return dao.query(peitems.class,id,false,"itemid");
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				dao.close();
			}
		}
	  public List<peitems> querytypeid(Object typeid) {//根据项目类别id查找
			try {
			 return dao.query(peitems.class,typeid,false,"typeid");
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				dao.close();
			}
		}
}
