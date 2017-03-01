package com.tsjy.controller.sys;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.plat.core.dto.BaseDto;
import com.plat.core.dto.Dto;
import com.plat.core.util.TSUtils;
import com.tsjy.model.Menu;
import com.tsjy.model.MenuAction;

public class ActionController extends Controller {

	@RequiresAuthentication
	public void index() {

		List menus = Menu.dao.getMenuTreeList();

		setAttr("menus", JsonKit.toJson(menus));
		render("/view/sys/action.html");
	}

	public void manageDatas() {

		int startIndex = getParaToInt("startIndex");
		int pageSize = getParaToInt("pageSize");

		int pageNumber = startIndex / pageSize + 1;

		Page<MenuAction> datas = MenuAction.dao.paginate(pageNumber, pageSize,
				"select * ",
				"from ts_menu_action where menu_id=? order by id ",
				getPara("menuid"));

		renderJson(datas);
	}

	public void edit() {

		MenuAction model = getModel(MenuAction.class, "",true);
		model.update();

		renderJson(new BaseDto(true));
	}

	/*
	 * @RequiresPermissions({"menu:add"})
	 */
	public void add() {
		Dto re = new BaseDto(true, "添加成功");
		MenuAction model = MenuAction.dao.findFirst(
				"select * from ts_menu_action where menu_id=? and code=?",
				getPara("menuid"), getPara("code"));
		if (TSUtils.isEmpty(model)) {
			getModel(MenuAction.class, "").save();
		} else {
			re.setMsg("添加失败,标识已存在");
			re.setSuccess(false);
		}

		renderJson(re);
	}

	/*@RequiresPermissions({ "menu:delete" })*/
	public void delete() {
		MenuAction.dao.deleteById(getPara("id"));
		renderJson(new BaseDto(true, "删除成功"));
	}

	public void genDefault() {
		
		Db.update("delete from ts_menu_action where menu_id=?", getPara("menuid"));
		
		String[] actions = { "添加", "编辑", "删除" };
		String[] codes = { "add", "edit", "delete" };
		for (int i = 0; i < actions.length; i++) {
			new MenuAction().set("action", actions[i]).set("code", codes[i])
					.set("menu_id", getPara("menuid")).save();
		}
		
		renderNull();
	}
}
