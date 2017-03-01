package com.tsjy.controller.sys;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.plat.core.util.TSConstants;
import com.tsjy.model.Menu;
import com.tsjy.model.User;

public class SysController extends Controller {

	@RequiresAuthentication
	public void index() {
		User user = getSessionAttr(TSConstants.S_USERINFO);

		List menuItems = Menu.dao.getSideMenuItems(user.getId().toString(),
				"01", 3);

		setAttr("menuitems", menuItems);
		setAttr("menuitemsjson", JsonKit.toJson(menuItems));
		render("/view/sys/index.html");
	}

	@RequiresAuthentication
	public void getProductTreeNodes() {
		User user = getSessionAttr(TSConstants.S_USERINFO);

		String menuItems = Menu.dao.getProductTreeItems(
				user.getId().toString(), getPara("menuid"), 2);
		renderJson(menuItems);
	}
}
