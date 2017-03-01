package com.tsjy.controller.sys;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class LoginValidator extends Validator {

	@Override
	protected void validate(Controller c) {
		validateToken("loginToken", "msg", "验证超时，请重新输入");
		validateRequired("account", "msg", "用户名不能为空");
		validateRequired("password", "msg", "密码不能为空");
	}

	@Override
	protected void handleError(Controller c) {
		
		c.keepPara("account");
		c.createToken("loginToken");
		c.setAttr("success", false);
		c.renderJson();
	}

}
