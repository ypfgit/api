package com.tsjy.model;

import java.util.Iterator;
import java.util.List;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.plat.core.db.MDb;
import com.plat.core.dto.BaseDto;
import com.plat.core.dto.Dto;
import com.plat.core.util.TSUtils;
import com.tsjy.model.base.BaseUser;

/**
 * 类描述： 用户实体模型 创建者： LiangChen 项目名称： TSPlat 创建时间： 2013-9-16 下午12:43:57 版本号： v1.0
 */

@SuppressWarnings("serial")
public class User extends BaseUser<User> {
	public static final User dao = new User();

	public void setDept_name(String dept_name) {
		set("dept_name", dept_name);
	}

	public String getDept_name() {
		return getStr("dept_name");
	}

	public User login(String account, String password) {

		Dto dto = new BaseDto();
		dto.put("account", account);
		dto.put("password", password);

		return (User) MDb.findFirst(dao, "user.queryUserInfo", dto);

	}

	/**
	 * 查询指定部门是否存在
	 * 
	 * @param depts
	 * @param deptname
	 * @return String
	 */
	private String checkDept(List depts, String deptname) {
		Iterator deptritr = depts.iterator();
		while (deptritr.hasNext()) {
			Dept dept = (Dept) deptritr.next();
			if (dept.getStr("name").equals(deptname)) {
				return dept.getStr("id");
			}
		}

		return null;
	}

	/**
	 * 查询指定账户是否存在
	 * 
	 * @param users
	 * @param account
	 * @return String
	 */
	private String checkUser(List users, String account) {
		Iterator userritr = users.iterator();
		while (userritr.hasNext()) {
			User user = (User) userritr.next();
			if (user.getStr("account").equals(account)) {
				return user.getBigDecimal("id").toString();
			}
		}

		return null;
	}

	/**
	 * 查询指定工种是否存在
	 * 
	 * @param jobs
	 * @param jobname
	 * @return String
	 */
	private String checkJob(List jobs, String jobname) {
		Iterator jobritr = jobs.iterator();
		while (jobritr.hasNext()) {
			Record record = (Record) jobritr.next();
			if (record.getStr("name").equals(jobname)) {
				return record.getStr("id");
			}
		}

		return null;
	}

	public List<Record> getUserSelectDatas() {

		return Db.find("select id as value,name as text from ts_user");
	}

	public User findByAccount(String account) {
		return (User) MDb.findFirst(dao, "user.queryUserInfo", new BaseDto(
				"account", account));
	}

}
