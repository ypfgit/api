package com.tsjy.controller.sys;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.plugin.auth.SessionKit;
import com.plat.core.util.TSConstants;
import com.plat.core.util.TSUtils;
import com.tsjy.model.User;

/**
 * 类描述： 验证用户是否已经登陆 创建者： LiangChen 项目名称： TSPlat 创建时间： 2013-9-17 下午04:19:01 版本号：
 * v1.0
 */
public class LoginInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation ai) {
		Subject cuurenUser = SecurityUtils.getSubject();
		// 如果当前用户未登录且访问的不是login方法
		if (!cuurenUser.isAuthenticated()
				&& !ai.getMethodName().endsWith("login")) {
			ai.getController().redirect("/account/login");
			return;
		}

		ai.invoke();
	}

}
