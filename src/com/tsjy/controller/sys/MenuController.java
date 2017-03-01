package com.tsjy.controller.sys;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.plat.core.dto.BaseDto;
import com.plat.core.util.IDHelper;
import com.plat.core.util.TSConstants;
import com.tsjy.model.Menu;
import com.tsjy.model.User;

public class MenuController extends Controller {

	@RequiresAuthentication
	public void index() {
		List menus = Menu.dao.getMenuTreeList();

		setAttr("menus", JsonKit.toJson(menus));
		render("/view/sys/menu.html");
	}

	public void getProductTreeNodes() {
		User user = getSessionAttr(TSConstants.S_USERINFO);

		String menuItems = Menu.dao.getProductTreeItems(
				user.getId().toString(), getPara("menuid"), 2);
		renderJson(menuItems);
	}

	public void manageDatas() {

		int startIndex = getParaToInt("startIndex");
		int pageSize = getParaToInt("pageSize");

		int pageNumber = startIndex / pageSize + 1;

		Page<Menu> menus = Menu.dao.paginate(pageNumber, pageSize, "select * ",
				"from ts_menu where id like ? and text like ? order by id ",
				getPara("menuid") + "%", getPara("queryname") + "%");

		renderJson(menus);
	}

	public void getTreeList() {

		List<Record> nodes = Db
				.find("select id ,text name,parent_id from ts_menu  order by id");
		renderJson(nodes);
	}

	public void getTreeMenuItems() {
		renderJson(Menu.dao.getMenuTreeList());
	}

	public void save() {
		Menu menu = getModel(Menu.class, "", true);
		menu.setId(IDHelper.getMenuID(menu.getParentId()));
		menu.setLeaf("1");
		menu.setModuleId(3);
		menu.save();
		renderJson(new BaseDto(true));
	}

	public void edit() {

		Menu menu = getModel(Menu.class, "", true);
		menu.update();

		renderJson(new BaseDto(true));
	}

	/*
	 * @RequiresPermissions({"menu:update"}) public void edit() { Menu menu =
	 * getModel(Menu.class,""); menu.update(); renderJson(new
	 * BaseDto(true,"修改成功")); }
	 */

	/*
	 * @RequiresPermissions({"menu:add"}) public void add() {
	 * renderJson(Menu.dao.getMenuTreeList()); }
	 */

	@RequiresPermissions({ "menu:delete" })
	public void delete() {

		Menu.dao.deleteMenu(getPara("id"), getPara("parentid"));
		Db.update("delete from ts_menu_action where menu_id like ?",
				getPara("id") + "%");
		renderJson(new BaseDto(true, "删除成功"));
	}
}
