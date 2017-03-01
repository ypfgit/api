package com.tsjy.controller.sys;

import java.io.File;

import org.apache.shiro.authz.annotation.RequiresAuthentication;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.upload.UploadFile;
import com.plat.core.dto.BaseDto;
import com.plat.core.util.TSConstants;
import com.plat.core.util.TSUtils;
import com.tsjy.model.Menu;
import com.tsjy.model.Notice;
import com.tsjy.model.User;

public class NewsController extends Controller {

	@RequiresAuthentication
	public void index() {
		render("/view/sys/news.html");
	}

	public void manageDatas() {

		int startIndex = getParaToInt("startIndex");
		int pageSize = getParaToInt("pageSize");

		int pageNumber = startIndex / pageSize + 1;

		Page<Notice> datas = Notice.dao
				.paginate(
						pageNumber,
						pageSize,
						"select t.*,m.name columnname ",
						"from ts_notice t,ts_column m where t.columnid = m.id order by createdate desc ");

		renderJson(datas);
	}

	public void getTreeMenuItems() {
		renderJson(Menu.dao.getMenuTreeList());
	}

	public void save() {
		Notice model = getModel(Notice.class, "", true);
		if (getPara("iscomment", "").equals("on")) {
			model.setIscomment("1");
		} else {
			model.setIscomment("0");
		}
		model.setCreatedate(TSUtils.getCurrentTimestamp());
		model.save();
	}

	public void upload() {

		UploadFile file = getFile();

		User user = getSessionAttr(TSConstants.S_USERINFO);

		String userid = user.getId().toString();
		String username = getPara("username");

		String filename = file.getFileName();
		String mypath = File.separator + "news" + File.separator
				+ TSUtils.getCurrentTimeAsNumber();
		String dirpath = file.getUploadPath() + mypath;

		String filepath = dirpath + File.separator + filename;
		File dir = new File(dirpath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File newFile = new File(filepath);
		file.getFile().renameTo(newFile);

		// 保存到数据库表
		/*com.tsjy.model.File model = new com.tsjy.model.File();
		model.setName(filename);
		model.setPath("upload" + mypath + File.separator + filename);
		model.setSize(getPara("size"));
		model.setOperateDate(TSUtils.getCurrentTimestamp());
		model.setType("news");
		// model.setFormId(Integer.parseInt(userid));
		model.setUserId(Integer.parseInt(userid));
		model.save();*/
		
		String re = "upload" + mypath + File.separator
				+ filename;
		re = re.replaceAll("\\\\", "/");

		renderJson(new BaseDto("path", re));
	}

	public void edit() {

		Notice model = getModel(Notice.class, "", true);
		if (getPara("iscomment", "").equals("on")) {
			model.setIscomment("1");
		} else {
			model.setIscomment("0");
		}
		model.update();

		renderNull();
	}

	public void getNewsContent() {
		String id = getPara("id");

		Notice model = Notice.dao.findFirst(
				"select t.content from ts_notice t where t.id = ?", id);
		renderJson(model);
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

	/* @RequiresPermissions({"menu:delete"}) */
	public void delete() {

		String[] ids = getPara("ids").split(",");

		for (String id : ids) {
			Notice.dao.deleteById(id);
		}

		renderJson(new BaseDto(true, "删除成功"));
	}
	
	public void hit(){
		
		Db.update("update ts_notice set hits = hits+1 where id = ?", getPara("id"));
		
		renderJson(new BaseDto(true));
	}
}
