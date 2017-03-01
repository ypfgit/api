/**
 * Copyright (c) 2011-2014, James Zhan 詹波 (jfinal@126.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */

package com;

import java.text.ParseException;

import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRenderFactory;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;

import com.baomidou.kisso.plugin.KissoJfinalPlugin;
import com.baomidou.kisso.plugin.SSOJfinalInterceptor;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.ext.plugin.shiro.SSOShiroInterceptor;
import com.jfinal.ext.plugin.shiro.ShiroMethod;
import com.jfinal.ext.plugin.shiro.ShiroPlugin;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.tx.TxByMethodRegex;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.plat.core.dto.Dto;
import com.plat.core.util.JsonHelper;
import com.plat.core.util.TSUtils;
import com.tsjy.controller.api.ApiRouter;
import com.tsjy.controller.site.IndexController;
import com.tsjy.controller.sys.SysRouter;
import com.tsjy.model.Setting;
import com.tsjy.model._MappingKit;

public class startConfig extends JFinalConfig {
	public static C3p0Plugin c3p0Plugin;

	/**
	 * 供Shiro插件使用。
	 */
	Routes routes;

	/**
	 * 如果生产环境配置文件存在，则优先加载该配置，否则加载开发环境配置文件
	 * 
	 * @param pro
	 *            生产环境配置文件
	 * @param dev
	 *            开发环境配置文件
	 */
	public void loadProp(String pro, String dev) {
		try {
			PropKit.use(pro);
		} catch (Exception e) {
			PropKit.use(dev);
		}
	}

	public void configConstant(Constants me) {
		loadProp("a_little_config_pro.txt", "a_little_config.txt");
		me.setDevMode(PropKit.getBoolean("devMode", false));
		me.setMainRenderFactory(new BeetlRenderFactory());
		GroupTemplate gt = BeetlRenderFactory.groupTemplate;
		gt.registerFunctionPackage("so", ShiroMethod.class);

		me.setEncoding("utf-8");
		me.setErrorView(401, "/error/401.html");
		me.setErrorView(403, "/error/403.html");
		me.setError404View("/error/404.html");
		me.setError500View("/error/500.html");
	}

	public void configRoute(Routes me) {
		// site
		me.add("/", IndexController.class);
		// sys
		me.add(new SysRouter());
		// api
		me.add(new ApiRouter());

		this.routes = me;
	}

	public void configPlugin(Plugins me) {

		c3p0Plugin = new C3p0Plugin(PropKit.get("jdbcUrl"),
				PropKit.get("user"), PropKit.get("password").trim());
		me.add(c3p0Plugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
		arp.setShowSql(true);// 这句话就是ShowSql
		arp.setTransactionLevel(2);
		me.add(arp);
		arp.setDialect(new MysqlDialect());
		// 配置属性名(字段名)大小写不敏感容器工厂
		arp.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		_MappingKit.mapping(arp);

		// 缓存插件
		// EhCachePlugin ecp = new EhCachePlugin();
		// me.add(ecp);

		me.add(new ShiroPlugin(routes));
		// kisso 初始化
		me.add(new KissoJfinalPlugin());
	}

	public void configInterceptor(Interceptors me) {
		// sso登录认证拦截
		me.add(new SSOJfinalInterceptor());
		// shiro权限认证拦截
		me.add(new SSOShiroInterceptor());
		// sql事务回滚拦截器
		me.add(new TxByMethodRegex("(.*save.*|.*update.*|.*delete.*)"));

	}

	public void configHandler(Handlers me) {
		me.add(new ContextPathHandler("path"));

	}

	@Override
	public void afterJFinalStart() {
		super.afterJFinalStart();
		// 读取参数表，设置全局参数
		GroupTemplate gt = BeetlRenderFactory.groupTemplate;

		Setting setting = Setting.dao.findFirst("select * from ts_setting");

		Dto dto = JsonHelper.parseSingleJson2Dto(setting.toJson());

		if (TSUtils.isNotEmpty(setting)) {
			gt.setSharedVars(dto);
		}
		// 初始化beetlsql插件
		JFinalBeetlSql.init(c3p0Plugin.getDataSource(), null);
	}

	public static void main(String[] args) throws ParseException {
		JFinal.start("WebRoot", 8980, "/", 5);
	}
}
