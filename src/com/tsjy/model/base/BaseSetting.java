package com.tsjy.model.base;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.IBean;

/**
 * Generated by JFinal, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseSetting<M extends BaseSetting<M>> extends Model<M> implements IBean {

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setKeywords(java.lang.String keywords) {
		set("keywords", keywords);
	}

	public java.lang.String getKeywords() {
		return get("keywords");
	}

	public void setDescription(java.lang.String description) {
		set("description", description);
	}

	public java.lang.String getDescription() {
		return get("description");
	}

	public void setSiteurl(java.lang.String siteurl) {
		set("siteurl", siteurl);
	}

	public java.lang.String getSiteurl() {
		return get("siteurl");
	}

	public void setIcp(java.lang.String icp) {
		set("icp", icp);
	}

	public java.lang.String getIcp() {
		return get("icp");
	}

	public void setLogo(java.lang.String logo) {
		set("logo", logo);
	}

	public java.lang.String getLogo() {
		return get("logo");
	}

	public void setBanner1(java.lang.String banner1) {
		set("banner1", banner1);
	}

	public java.lang.String getBanner1() {
		return get("banner1");
	}

	public void setBanner2(java.lang.String banner2) {
		set("banner2", banner2);
	}

	public java.lang.String getBanner2() {
		return get("banner2");
	}

	public void setBanner3(java.lang.String banner3) {
		set("banner3", banner3);
	}

	public java.lang.String getBanner3() {
		return get("banner3");
	}

	public void setBanner4(java.lang.String banner4) {
		set("banner4", banner4);
	}

	public java.lang.String getBanner4() {
		return get("banner4");
	}

	public void setBanner5(java.lang.String banner5) {
		set("banner5", banner5);
	}

	public java.lang.String getBanner5() {
		return get("banner5");
	}

	public void setPromise(java.lang.String promise) {
		set("promise", promise);
	}

	public java.lang.String getPromise() {
		return get("promise");
	}

	public void setAgreement(java.lang.String agreement) {
		set("agreement", agreement);
	}

	public java.lang.String getAgreement() {
		return get("agreement");
	}

	public void setContact(java.lang.String contact) {
		set("contact", contact);
	}

	public java.lang.String getContact() {
		return get("contact");
	}

}
