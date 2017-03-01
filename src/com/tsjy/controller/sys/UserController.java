package com.tsjy.controller.sys;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.plat.core.dto.BaseDto;
import com.plat.core.dto.Dto;
import com.plat.core.util.TSUtils;
import com.tsjy.model.Dept;
import com.tsjy.model.Role;
import com.tsjy.model.User;
import com.tsjy.model.UserRole;

public class UserController extends Controller {

	@RequiresAuthentication
	public void index() {
		
		List<Record> cards = Db
				.find("select id ,name ,fee,remark from ts_finance_card");
		setAttr("cards", cards);
		
		setAttr("roles", Role.dao.find("select id,name from ts_role"));
		render("/view/sys/user.html");
	}

	public void getCatTree() {
		List<Dept> data = Dept.dao
				.find("select id,name,parent_id from ts_category");
		renderJson(data);
	}

	public void manageDatas() {

		int startIndex = getParaToInt("startIndex");
		int pageSize = getParaToInt("pageSize");

		int pageNumber = startIndex / pageSize + 1;

		Page<User> datas = User.dao
				.paginate(
						pageNumber,
						pageSize,
						"select u.*,r.role_name,r.role_id,d.name dept_name",
						"from ts_user u ,v_user_role r ,ts_dept d where u.id = r.user_id and u.dept_id = d.id and d.id like ?"
						+ " and u.name like ?",
						getPara("deptid") + "%",getPara("queryname") + "%");

		renderJson(datas);
	}

	public void save() {

		Dto re = new BaseDto(true);

		Record record = Db.findFirst(
				"select count(1) num from ts_user where login_name = ?",
				getPara("login_name"));
		if (record.getLong("num") == 0) {

			// 用户信息
			User model = getModel(User.class, "", true);
			model.setVCompany("1");
			model.setCreateDate(TSUtils.getCurrentTimestamp());
			model.save();

			// 角色、分类信息
			new UserRole().set("user_id", model.get("id"))
					.set("role_id", getPara("role_id")).save();
			re.put("id", model.get("id"));
		} else {
			re.setSuccess(false);
			re.setMsg("用户名已存在");
		}

		renderJson(re);
	}

	public void edit() {

		Dto re = new BaseDto(true);

		User model = getModel(User.class, "", true);

		model.update();

		Db.update("delete from ts_user_role where user_id = ?", getPara("id"));

		new UserRole().set("user_id", getPara("id"))
				.set("role_id", getPara("role_id")).save();

		renderJson(re);
	}
	
	public void review(){
		
		String id = getPara("id");
		
		User model = getModel(User.class, "", true);

		model.update();
		
		renderJson(new BaseDto(true));
	}

	public void delete() {
		String[] ids = getPara("ids").split(",");
		
		//fix
		for (String id : ids) {
			User.dao.deleteById(id);
			Db.update("delete from ts_user_role where user_id = ?", id);
		}

		renderNull();
	}

	public void companyImg() {
		List<Record> list = Db
				.find("select id ,name,path from ts_file where type='company' and form_id=?",
						getPara("userid"));

		renderJson(list);
	}
	
	public void deleteImg(){
		
		Db.update("delete from ts_file where id=?", getPara("id"));
		renderJson(new BaseDto());
	}
}
