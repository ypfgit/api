package com.baomidou.kisso.plugin;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.kisso.MyToken;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.kisso.web.interceptor.KissoAbstractInterceptor;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.plat.core.dto.BaseDto;

/**
 * 登录权限验证
 * <p>
 * kisso jfinal 拦截器，Controller 方法调用前处理。
 * </p>
 */
public class SSOJfinalInterceptor extends KissoAbstractInterceptor implements
		Interceptor {

	private static final Logger logger = Logger
			.getLogger("SSOJfinalInterceptor");

	public void intercept(Invocation inv) {
		/**
		 * 正常执行
		 */

		HttpServletRequest request = inv.getController().getRequest();
		HttpServletResponse response = inv.getController().getResponse();
		MyToken token = SSOHelper.getToken(request);
		if (token == null) {
			if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
				/*
				 * Handler 处理 AJAX 请求
				 */
				
				getHandlerInterceptor().preTokenIsNullAjax(request, response);
			} else if ("APP".equals(request.getHeader("PLATFORM"))) {
				/*
				 * Handler 处理 APP接口调用 请求
				 * 没有修改kisso核心代码，直接使用Ajax的认证判断方式，如果未认证，返回401状态码
				 */

				getHandlerInterceptor().preTokenIsNullAjax(request, response);
				logger.info("request from APP invoke");
			} else if ("REST".equals(request.getHeader("PLATFORM"))) {
				/*
				 * Handler 处理rest接口调用 请求
				 */

				inv.getController().renderJson(new BaseDto(false, "登录后才能调用"));
				logger.info("request from REST invoke");
			} else {
				try {
					logger.fine("logout. request url:"
							+ request.getRequestURL());

					/* 清理当前登录状态 */
					SSOHelper.clearLogin(request, response);
					SSOConfig config = SSOConfig.getInstance();

					/* redirect login page */
					String loginUrl = config.getLoginUrl();
					if ("".equals(loginUrl)) {
						inv.getController().renderText(
								"sso.properties Must include: sso.login.url");
					} else {
						String retUrl = HttpUtil.getQueryString(request,
								config.getEncoding());
						logger.fine("loginAgain redirect pageUrl.." + retUrl);
						inv.getController().redirect(
								HttpUtil.encodeRetURL(loginUrl,
										config.getParamReturl(), retUrl));
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			/*
			 * 正常请求，request 设置 token 减少二次解密
			 */
			
			request.setAttribute(SSOConfig.SSO_TOKEN_ATTR, token);
			inv.invoke();
		}
	}

}
