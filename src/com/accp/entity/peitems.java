package com.accp.entity;

import com.accp.annotation.Column;
import com.accp.annotation.Table;

@Table("peitems")

public class peitems {
	@Column(name = "itemid",isPrimaryKey = true,isnull = true,length = 10,type = "int",isIdentity = true)
	private Integer itemid;
	@Column(name = "itemname",length = 50,type = "varchar")
	private String itemname;
	@Column(name = "typeid",length = 10,type = "int")
	private Integer typeid;
	@Column(name = "necessary",length = 10,type = "int")
	private Integer necessary;
	@Column(name = "ref",length = 50,type = "varchar")
	private String ref;
	@Column(name = "price",length = 20,type = "float")
	private Float price;
	@Column(name = "info",length = 50,type = "varchar")
	private String info;

	public Integer getItemid() {
		return itemid;
	}

	public void setItemid(Integer itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname == null ? null : itemname.trim();
	}

	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	public Integer getNecessary() {
		return necessary;
	}

	public void setNecessary(Integer necessary) {
		this.necessary = necessary;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref == null ? null : ref.trim();
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info == null ? null : info.trim();
	}

	public peitems(Integer itemid, String itemname, Integer typeid, Integer necessary, String ref, Float price,
			String info) {
		super();
		this.itemid = itemid;
		this.itemname = itemname;
		this.typeid = typeid;
		this.necessary = necessary;
		this.ref = ref;
		this.price = price;
		this.info = info;
	}

	public peitems(String itemname, Integer typeid, Integer necessary, String ref, Float price, String info) {
		super();
		this.itemname = itemname;
		this.typeid = typeid;
		this.necessary = necessary;
		this.ref = ref;
		this.price = price;
		this.info = info;
	}

	public peitems() {
		super();
	}
	
}
