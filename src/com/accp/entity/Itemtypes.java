package com.accp.entity;

import com.accp.annotation.Table;
import com.accp.annotation.*;

@Table("itemtypes")
public class Itemtypes {
	@Column(name = "typeid",isPrimaryKey = true,isnull = true,length = 10,type = "int",isIdentity = true)
    private Integer typeid;
	@Column(name = "typename",length = 50,type="varchar")
    private String typename;

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename == null ? null : typename.trim();
    }

	public Itemtypes(Integer typeid, String typename) {
		super();
		this.typeid = typeid;
		this.typename = typename;
	}

	public Itemtypes() {
		super();
	}
}
