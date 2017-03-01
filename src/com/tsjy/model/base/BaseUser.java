package com.tsjy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseUser<M extends BaseUser<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setPassword(java.lang.String password) {
		set("password", password);
	}

	public java.lang.String getPassword() {
		return get("password");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setAccount(java.lang.String account) {
		set("account", account);
	}

	public java.lang.String getAccount() {
		return get("account");
	}

	public void setDeptId(java.lang.String deptId) {
		set("dept_id", deptId);
	}

	public java.lang.String getDeptId() {
		return get("dept_id");
	}

	public void setStatus(java.lang.String status) {
		set("status", status);
	}

	public java.lang.String getStatus() {
		return get("status");
	}

	public void setPhone(java.lang.String phone) {
		set("phone", phone);
	}

	public java.lang.String getPhone() {
		return get("phone");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

	public void setSex(java.lang.String sex) {
		set("sex", sex);
	}

	public java.lang.String getSex() {
		return get("sex");
	}

	public void setVCompany(java.lang.String vCompany) {
		set("v_company", vCompany);
	}

	public java.lang.String getVCompany() {
		return get("v_company");
	}

	public void setVCard(java.lang.String vCard) {
		set("v_card", vCard);
	}

	public java.lang.String getVCard() {
		return get("v_card");
	}

	public void setCardBegin(java.util.Date cardBegin) {
		set("card_begin", cardBegin);
	}

	public java.util.Date getCardBegin() {
		return get("card_begin");
	}

	public void setCardEnd(java.util.Date cardEnd) {
		set("card_end", cardEnd);
	}

	public java.util.Date getCardEnd() {
		return get("card_end");
	}

	public void setCreateDate(java.util.Date createDate) {
		set("create_date", createDate);
	}

	public java.util.Date getCreateDate() {
		return get("create_date");
	}

	public void setCardId(java.lang.String cardId) {
		set("card_id", cardId);
	}

	public java.lang.String getCardId() {
		return get("card_id");
	}

}
