package com.plat.core.shiro;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;

import com.baomidou.kisso.MyToken;
import com.baomidou.kisso.common.shiro.SSOAuthToken;
import com.plat.core.util.TSConstants;
import com.plat.core.util.TSUtils;
import com.tsjy.model.Role;
import com.tsjy.model.RoleAuth;
import com.tsjy.model.User;

public class ShiroRealm extends AuthorizingRealm {
	/**
	 * 认证回调函数,登录时调用. 保存用户的可操作菜单
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken)
			throws AuthenticationException {

		MyToken token = (MyToken) authenticationToken.getPrincipal();

		String account = token.getUid();

		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
		if(session!=null){
			session.touch();
		}
		// fix 用户锁定、用户更改密码之类的情况

		Object obj =session.getAttribute(
				TSConstants.S_USERINFO);
		if (TSUtils.isEmpty(obj)) {
			User user = User.dao.findByAccount(account);
			subject.getSession().setAttribute(TSConstants.S_USERINFO, user);
		}

		return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(),
				authenticationToken.getCredentials(), getName());

	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用. 保存用戶的角色 保存用戶的動作權限
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection printcipal) {

		MyToken token = (MyToken) printcipal.getPrimaryPrincipal();

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

		List<Role> roles = Role.dao.getAuthRoleList(token.getId().toString());

		if (roles != null && roles.size() > 0) {

			Set<String> r = new HashSet<String>();
			for (Role role : roles) {
				r.add(role.getStr("name"));
			}
			info.addRoles(r);
		}

		List<RoleAuth> permissionList = RoleAuth.dao.getAuthActionList(token
				.getId().toString());
		if (permissionList != null && permissionList.size() > 0) {

			Set<String> pers = new HashSet<String>();
			for (RoleAuth p : permissionList) {
				if (TSUtils.isNotEmpty(p.getStr("actions"))) {
					String[] actions = p.getStr("actions").split(",");
					for (String item : actions) {
						pers.add(item);
					}
				}
			}
			info.setStringPermissions(pers);
		}

		// ui权限集
		/*List<Record> uiList = RoleAuth.dao.getAuthUiList(token.getId()
				.toString());
		Dto uiDto = new BaseDto();
		if (uiList != null && uiList.size() > 0) {

			for (Record r : uiList) {
				uiDto.put(r.getStr("en_name"), r.getStr("ui"));
			}
		}*/

		// 将权限集合保存在session中，方便其它地方使用
		/*SecurityUtils.getSubject().getSession()
				.setAttribute(TSConstants.SESSION_AUTHUI, uiDto);*/

		return info;
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}

	@Override
	public Class getAuthenticationTokenClass() {
		return SSOAuthToken.class;
	}

	@Override
	public boolean supports(AuthenticationToken token) {
		return token != null
				&& SSOAuthToken.class.isAssignableFrom(token.getClass());
	}
}
