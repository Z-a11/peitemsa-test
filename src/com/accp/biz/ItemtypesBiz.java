package com.accp.biz;

import java.util.List;

import com.accp.dao.CommonDao;
import com.accp.entity.Itemtypes;

public class ItemtypesBiz {
	private CommonDao dao=new CommonDao();
	   public List<Itemtypes> queryAll(){//查询Itemtypes所有
			  try {
				  return dao.queryAll(Itemtypes.class);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			 finally {
				 dao.close();	
			}
			  
		  }
}
