package com.tsjy.controller.api;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionException;
import org.apache.shiro.subject.Subject;

import com.baomidou.kisso.MyToken;
import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.common.IpHelper;
import com.baomidou.kisso.common.shiro.SSOAuthToken;
import com.baomidou.kisso.common.util.HttpUtil;
import com.baomidou.kisso.web.waf.request.WafRequestWrapper;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.plat.core.dto.BaseDto;
import com.plat.core.dto.Dto;
import com.plat.core.util.TSConstants;
import com.plat.core.util.TSUtils;
import com.tsjy.model.User;

@Clear
public class AuthController extends Controller {
	private static final Log LOG = Log.getLog(AuthController.class);

	/**
	 * 获取授权信息至cookie
	 */
	public void login() {
		Dto result = new BaseDto(true);
		MyToken token = (MyToken) SSOHelper.getToken(getRequest());
		if (token != null) {
			renderJson(result);
			return;
		} else {
			/**
			 * 登录 生产环境需要过滤sql注入
			 */
			if (HttpUtil.isPost(getRequest())) {
				WafRequestWrapper req = new WafRequestWrapper(getRequest());
				String account = req.getParameter("account");
				String password = req.getParameter("password");
				User user = User.dao.login(account, password);

				if (TSUtils.isNotEmpty(user)) {
					MyToken mt = new MyToken();
					mt.setId(user.getId().longValue());
					mt.setUid(user.getAccount());
					mt.setIp(IpHelper.getIpAddr(getRequest()));

					// 记住密码，设置 cookie 时长 1 周 = 604800 秒 【动态设置 maxAge 实现记住密码功能】
					String rememberMe = req.getParameter("rememberMe");
					if ("on".equals(rememberMe)) {
						getRequest().setAttribute(SSOConfig.SSO_COOKIE_MAXAGE,
								604800);
					}

					SSOHelper.setSSOCookie(getRequest(), getResponse(), mt,
							false);

					Subject subject = SecurityUtils.getSubject();

					Session session = subject.getSession();

					if (session != null) {
						session.touch();
					}

					if (!subject.isAuthenticated()) {
						subject.login(new SSOAuthToken(mt));
					}

					if (TSUtils.isEmpty(session
							.getAttribute(TSConstants.S_USERINFO))) {
						subject.getSession().setAttribute(
								TSConstants.S_USERINFO, user);
					}
					result.setMsg("已下发Cookies至响应");

				} else {
					result.setSuccess(false);
					result.setMsg("用户名或密码有误");
				}
			} else {
				result.setSuccess(false);
				result.setMsg("登录方式有误");
			}

			renderJson(result);
		}
	}

	/**
	 * 退出登录
	 */
	public void logout() {
		Dto re = new BaseDto(true);
		SSOHelper.clearLogin(getRequest(), getResponse());
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.logout();
			this.removeSessionAttr(TSConstants.S_USERINFO);
		} catch (SessionException ise) {
			LOG.debug(
					"Encountered session exception during logout.  This can generally safely be ignored.",
					ise);
			renderJson(re);
		} catch (Exception e) {
			LOG.debug("登出发生错误", e);
			re.setMsg(e.getMessage());
			renderJson(re);
		}
		
		renderJson(re);
	}
}
