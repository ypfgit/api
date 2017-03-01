package com.jfinal.ext.plugin.shiro;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.baomidou.kisso.MyToken;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.common.shiro.SSOAuthToken;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.plat.core.dto.BaseDto;
import com.plat.core.util.TSConstants;
import com.plat.core.util.TSUtils;
import com.tsjy.model.User;

public class SSOShiroInterceptor implements Interceptor {
	private static final Logger logger = Logger
			.getLogger("SSOShiroInterceptor");

	public void intercept(Invocation inv) {
		HttpServletRequest request = inv.getController().getRequest();
		HttpServletResponse response = inv.getController().getResponse();
		MyToken token = SSOHelper.attrToken(request);

		if (token == null) {
			logger.fine("logout. request url:" + request.getRequestURL());
			try {
				SSOHelper.clearRedirectLogin(request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {

			AuthzHandler ah = ShiroKit.getAuthzHandler(inv.getActionKey());
			try {
				if (ah != null) {
					/**
					 * shiro 会话管理
					 */
					Subject subject = SecurityUtils.getSubject();

					Session session = subject.getSession(false);
					if (session != null) {
						session.touch();
					}

					/**
					 * shiro 登录认证
					 */
					if (!subject.isAuthenticated()) {
						subject.login(new SSOAuthToken(token));
						logger.fine(" shiro login success. ");
					}else{

						String account = token.getUid();

						Object obj =session.getAttribute(
								TSConstants.S_USERINFO);
						if (TSUtils.isEmpty(obj)) {
							User user = User.dao.findByAccount(account);
							subject.getSession().setAttribute(TSConstants.S_USERINFO, user);
						}
					}

					ah.assertAuthorized();

					// 执行正常逻辑
					inv.invoke();
				}else{
					inv.invoke();
				}
			} catch (UnauthenticatedException lae) {
				// RequiresGuest，RequiresAuthentication，RequiresUser，未满足时，抛出未经授权的异常。
				// 如果没有进行身份验证，返回HTTP401状态码,或者跳转到默认登录页面
				/*
				 * if (StrKit.notBlank(ShiroKit.getLoginUrl())) { //
				 * 保存登录前的页面信息,只保存GET请求。其他请求不处理。 if
				 * (inv.getController().getRequest().getMethod()
				 * .equalsIgnoreCase("GET")) {
				 * inv.getController().setSessionAttr(
				 * ShiroKit.getSavedRequestKey(), inv.getActionKey()); }
				 * inv.getController().redirect(ShiroKit.getLoginUrl()); } else
				 * {
				 */
				inv.getController().renderError(401);
				// }
				return;

			} catch (AuthorizationException ae) {
				
				if ( "XMLHttpRequest".equals(request.getHeader("X-Requested-With")) ) {
					
					inv.getController().renderJson(new BaseDto(false, "您没有权限进行此操作!"));
				}else{
					inv.getController().renderError(403);
				}
				// RequiresRoles，RequiresPermissions授权异常
				// 如果没有权限访问对应的资源，返回HTTP状态码403，或者调转到为授权页面
				/*if (StrKit.notBlank(ShiroKit.getUnauthorizedUrl())) {
					inv.getController().redirect(ShiroKit.getUnauthorizedUrl());
				} else {
					inv.getController().renderError(403);
				}*/
				return;
			}
		}

	}

}
