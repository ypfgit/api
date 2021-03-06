package com.tsjy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseMenu<M extends BaseMenu<M>> extends Model<M> implements IBean {

	public void setId(java.lang.String id) {
		set("id", id);
	}

	public java.lang.String getId() {
		return get("id");
	}

	public void setText(java.lang.String text) {
		set("text", text);
	}

	public java.lang.String getText() {
		return get("text");
	}

	public void setParentId(java.lang.String parentId) {
		set("parent_id", parentId);
	}

	public java.lang.String getParentId() {
		return get("parent_id");
	}

	public void setIconcls(java.lang.String iconcls) {
		set("iconcls", iconcls);
	}

	public java.lang.String getIconcls() {
		return get("iconcls");
	}

	public void setCollapsed(java.lang.String collapsed) {
		set("collapsed", collapsed);
	}

	public java.lang.String getCollapsed() {
		return get("collapsed");
	}

	public void setUrl(java.lang.String url) {
		set("url", url);
	}

	public java.lang.String getUrl() {
		return get("url");
	}

	public void setLeaf(java.lang.String leaf) {
		set("leaf", leaf);
	}

	public java.lang.String getLeaf() {
		return get("leaf");
	}

	public void setSortNo(java.lang.Integer sortNo) {
		set("sort_no", sortNo);
	}

	public java.lang.Integer getSortNo() {
		return get("sort_no");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

	public void setIcon(java.lang.String icon) {
		set("icon", icon);
	}

	public java.lang.String getIcon() {
		return get("icon");
	}

	public void setType(java.lang.String type) {
		set("type", type);
	}

	public java.lang.String getType() {
		return get("type");
	}

	public void setEnName(java.lang.String enName) {
		set("en_name", enName);
	}

	public java.lang.String getEnName() {
		return get("en_name");
	}

	public void setModuleId(java.lang.Integer moduleId) {
		set("module_id", moduleId);
	}

	public java.lang.Integer getModuleId() {
		return get("module_id");
	}

	public void setIconcss(java.lang.String iconcss) {
		set("iconcss", iconcss);
	}

	public java.lang.String getIconcss() {
		return get("iconcss");
	}

}
