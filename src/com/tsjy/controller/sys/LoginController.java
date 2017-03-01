package com.tsjy.controller.sys;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
import com.jfinal.aop.Before;
import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.plat.core.dto.BaseDto;
import com.plat.core.dto.Dto;
import com.plat.core.shiro.CaptchaRender;
import com.plat.core.util.TSConstants;
import com.plat.core.util.TSUtils;
import com.tsjy.model.User;

public class LoginController extends Controller {

	private static final Log LOG = Log.getLog(LoginController.class);

	private static final int DEFAULT_CAPTCHA_LEN = 4;
	private static final String LOGIN_URL = "/";

	@Clear
	public void login() {
		this.createToken("loginToken");
		this.render("/view/sys/login.html");
	}

	/**
	 * @Title: img
	 * @Description: 图形验证码
	 * @since V1.0.0
	 */
	@Clear
	public void img() {
		CaptchaRender img = new CaptchaRender(DEFAULT_CAPTCHA_LEN);
		this.setSessionAttr(CaptchaRender.DEFAULT_CAPTCHA_MD5_CODE_KEY,
				img.getMd5RandonCode());
		render(img);
	}

	@Clear
	@Before(LoginValidator.class)
	public void doLogin() {
		Dto result = new BaseDto(true);

		/**
		 * 登录 生产环境需要过滤sql注入
		 */
		if (HttpUtil.isPost(getRequest())) {
			WafRequestWrapper req = new WafRequestWrapper(getRequest());
			String account = req.getParameter("account");
			String password = req.getParameter("password");

			User user = User.dao.findByAccount(account);

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
				SSOHelper.setSSOCookie(getRequest(), getResponse(), mt, false);

				Subject subject = SecurityUtils.getSubject();

				Session session = subject.getSession();

				if (session != null) {
					session.touch();
				}

				if (!subject.isAuthenticated()) {
					subject.login(new SSOAuthToken(mt));
				}

				// fix 用户锁定、用户更改密码之类的情况

				if (TSUtils.isEmpty(session
						.getAttribute(TSConstants.S_USERINFO))) {
					subject.getSession().setAttribute(TSConstants.S_USERINFO,
							user);
				}
			} else {
				result.setSuccess(false);
				result.setMsg("用户名或密码有误");
			}
		} else {
			result.setSuccess(false);
			result.setMsg("登录方式有误");
		}

		if (result.getSuccess()) {
			String ret = getPara("ReturnURL", "");
			if (TSUtils.isEmpty(ret)) {
				redirect("/");
			} else {
				redirect(ret);
			}

		} else {
			setAttr("success", false);
			setAttr("msg", result.getMsg());
			render("/view/sys/login.html");
		}

		// renderJson(result);
	}

	@Clear
	@Before(LoginValidator.class)
	public void ajaxLogin() {
		Dto result = new BaseDto(true);

		/**
		 * 登录 生产环境需要过滤sql注入
		 */
		if (HttpUtil.isPost(getRequest())) {
			WafRequestWrapper req = new WafRequestWrapper(getRequest());
			String account = req.getParameter("account");
			String password = req.getParameter("password");

			User user = User.dao.findByAccount(account);

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

				SSOHelper.setSSOCookie(getRequest(), getResponse(), mt, false);

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
					subject.getSession().setAttribute(TSConstants.S_USERINFO,
							user);
				}

			} else {
				result.setSuccess(false);
				result.setMsg("用户名或密码有误");
			}
		} else {
			result.setSuccess(false);
			result.setMsg("登录方式有误");
		}

		if (result.getSuccess()) {
			String ret = getPara("ReturnURL", "");
			if (TSUtils.isEmpty(ret)) {
				result.put("url", "/");
			} else {
				result.put("url", ret);
			}
		}

		renderJson(result);
	}

	@RequiresAuthentication
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		try {
			currentUser.logout();
			this.removeSessionAttr("user");
			this.redirect(LOGIN_URL);
		} catch (SessionException ise) {
			LOG.debug(
					"Encountered session exception during logout.  This can generally safely be ignored.",
					ise);
		} catch (Exception e) {
			LOG.debug("登出发生错误", e);
		}
	}

	public void changePassword() {
		Dto dto = new BaseDto(true);
		String id = getPara("id");
		String old = getPara("p");
		String now = getPara("p1");

		Record re = Db.findFirst(
				"select * from ts_user where password=? and id = ?", old, id);
		if (TSUtils.isNotEmpty(re)) {
			Db.update("update ts_user set password = ? where id = ?", now, id);
		} else {
			dto.setSuccess(false);
			dto.setMsg("密码不正确");
		}

		renderJson(dto);
	}
}
