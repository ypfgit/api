package com.tsjy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseParam<M extends BaseParam<M>> extends Model<M> implements IBean {

	public void setParamid(java.lang.String paramid) {
		set("paramid", paramid);
	}

	public java.lang.String getParamid() {
		return get("paramid");
	}

	public void setParamkey(java.lang.String paramkey) {
		set("paramkey", paramkey);
	}

	public java.lang.String getParamkey() {
		return get("paramkey");
	}

	public void setParamvalue(java.lang.String paramvalue) {
		set("paramvalue", paramvalue);
	}

	public java.lang.String getParamvalue() {
		return get("paramvalue");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

}
