package com.tsjy.controller.sys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.plat.core.db.MDb;
import com.plat.core.dto.BaseDto;
import com.plat.core.dto.Dto;
import com.tsjy.model.Menu;
import com.tsjy.model.Role;
import com.tsjy.model.RoleAuth;

public class RoleController extends Controller {

	@RequiresAuthentication
	public void index() {

		render("/view/sys/role.html");
	}

	public void manageDatas() {

		int startIndex = getParaToInt("startIndex");
		int pageSize = getParaToInt("pageSize");

		int pageNumber = startIndex / pageSize + 1;

		Page<Role> datas = Role.dao.paginate(pageNumber, pageSize, "select * ",
				"from ts_role where name like ? order by id ",getPara("queryname") + "%");

		renderJson(datas);
	}

	public void edit() {

		Role model = getModel(Role.class, "");
		model.update();

		renderNull();
	}

	/*
	 * @RequiresPermissions({"menu:update"}) public void edit() { Menu menu =
	 * getModel(Menu.class,""); menu.update(); renderJson(new
	 * BaseDto(true,"修改成功")); }
	 */

	/*
	 * @RequiresPermissions({"menu:add"})
	 */
	public void add() {
		getModel(Role.class, "").save();
		renderNull();
	}

	/* @RequiresPermissions({ "menu:delete" }) */
	public void delete() {

		Role.dao.deleteById(getPara(0));
		Db.update("delete from ts_user_role where role_id=?", getPara("id"));
		Db.update("delete from ts_role_auth where role_id=?", getPara("id"));

		renderJson(new BaseDto(true, "删除成功"));
	}

	public void getRoleMenuTreeDatas() {
		List list = Menu.dao.getRoleMenuTreeList(getPara("id"));

		renderJson(JsonKit.toJson(list));
	}

	public void permission() {

		List list = Menu.dao.getRoleMenuTreeList(getPara(0));

		setAttr("menus", JsonKit.toJson(list));
		setAttr("pid", getPara(0));

		render("/view/sys/role-permission.html");
	}

	public void savePermission() {
		
		String roleid = getPara("id");
		String menuids = getPara("menuids");
		Dto dto = new BaseDto();
		dto.put("roleid", roleid);
		dto.put("menuids", menuids);

		MDb.update("role.deleteRoleAuth", dto);

		String[] menuAttr = menuids.split(",");
		if (menuAttr.length > 0) {
			for (String menuid : menuAttr) {
				dto.put("menuid", menuid);
				MDb.update("role.insertRoleAuth", dto);
			}
		}

		renderNull();
	}

	public void action() {

		Dto dto = new BaseDto();
		dto.put("leaf", "1");
		dto.put("roleid", getPara(0));
		List list = MDb.find("menu.queryActionMenus", dto);
		Role role = Role.dao.findById(getPara(0));

		setAttr("role", role);
		setAttr("menus", list);

		render("/view/sys/role-action.html");
	}

	public void saveAction() {
		HttpServletRequest request = getRequest();
		Dto dto = new BaseDto();
		dto.put("leaf", "1");
		dto.put("roleid", getPara("id"));
		List<Record> list = MDb.find("menu.queryActionMenus", dto);
		for (Record record : list) {
			String[] actions = getParaValues(record.getStr("en_name"));
			String actionStr = "";
			if (actions == null) {
				new RoleAuth().set("role_id", getPara("id"))
						.set("menu_id", record.get("id"))
						.set("actions", "").update();
			} else if (actions != null && actions.length > 0) {
				for (String action : actions) {
					actionStr += record.getStr("en_name") + ":" + action + ",";
				}
				actionStr = actionStr.substring(0, actionStr.length() - 1);
				new RoleAuth().set("role_id", getPara("id"))
						.set("menu_id", record.get("id"))
						.set("actions", actionStr).update();
			}
		}

		renderNull();
	}

}
