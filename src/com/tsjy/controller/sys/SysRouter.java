package com.tsjy.controller.sys;

import com.jfinal.config.Routes;

public class SysRouter extends Routes {
	@Override
	public void config() {
		add("/account", LoginController.class);
		add("/sys", SysController.class);
		add("/menu", MenuController.class);
		add("/user", UserController.class);
		add("/role", RoleController.class);
		add("/news", NewsController.class);
		add("/column", ColumnController.class);
		add("/setting", SettingController.class);
		add("/dept", DeptController.class);
		add("/action", ActionController.class);
	}
}
