package com.tsjy.controller.sys;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.plat.core.dto.BaseDto;
import com.plat.core.util.IDHelper;
import com.tsjy.model.Dept;

public class DeptController extends Controller {

	@RequiresAuthentication
	public void index() {

		render("/view/sys/dept.html");
	}

	public void getTreeNodes() {

		List<Record> nodes = Db
				.find("select id ,name,parent_id,leaf from ts_dept where parent_id =? order by id",getPara("id"));
		
		for (Record record : nodes) {
			if(record.get("leaf").equals("1")){
				record.set("isParent", true);
			}else{
				record.set("isParent", false);
			}
		}
		
		
		renderJson(JsonKit.toJson(nodes).replace("isparent", "isParent"));
	}

	public void getTreeList() {

		List<Record> nodes = Db
				.find("select id ,name,parent_id from ts_dept  order by id");
		renderJson(nodes);
	}

	public void manageDatas() {

		int startIndex = getParaToInt("startIndex");
		int pageSize = getParaToInt("pageSize");

		int pageNumber = startIndex / pageSize + 1;

		Page<Dept> datas = Dept.dao.paginate(pageNumber, pageSize,
				"select t.*,c.name parent_name ", "from ts_dept t ,ts_dept c  where t.parent_id = c.id and t.id like ? and t.name like ? order by t.id ",
				getPara("treeid") + "%",getPara("queryname") + "%");

		renderJson(datas);
	}

	public void save(){
		
		Dept model = getModel(Dept.class, "",true);
		Db.update("update ts_dept  set leaf = 0 where parent_id= ?",model.getParentId());
		model.setId(IDHelper.getDeptId(model.getParentId()));
		model.setLeaf("1");
		model.save();

		renderJson(new BaseDto(true));
	}
	public void edit() {
		
		Dept model = getModel(Dept.class, "",true);
		model.update();

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

	/*@RequiresPermissions({ "menu:delete" })*/
	public void delete() {
		//删除目录下的文章
		//删除目录及其子目录
		//更新父目录状态
		
		String[] ids = getPara("ids").split(",");

		for (String id : ids) {
			Dept.dao.deleteById(id);
		}
		
		renderJson(new BaseDto(true, "删除成功"));
	}
}
