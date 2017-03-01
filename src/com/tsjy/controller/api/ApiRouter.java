package com.tsjy.controller.api;

import com.jfinal.config.Routes;

public class ApiRouter extends Routes {
	@Override
	public void config() {
		// ------------api路由------------------//
		add("/api/auth", AuthController.class);
		add("/api/test", TestController.class);
	}
}
