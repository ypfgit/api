package com.tsjy.controller.sys;

import java.io.File;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRenderFactory;

import com.jfinal.core.Controller;
import com.jfinal.kit.JsonKit;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.plat.core.dto.BaseDto;
import com.plat.core.dto.Dto;
import com.plat.core.util.IDHelper;
import com.plat.core.util.JsonHelper;
import com.plat.core.util.TSUtils;
import com.tsjy.model.Column;
import com.tsjy.model.Setting;

public class SettingController extends Controller {

	@RequiresAuthentication
	public void index() {
        
		render("/view/sys/setting.html");
	}
	
	public void info(){
		
		Setting setting = Setting.dao.findFirst("select * from ts_setting");
		
		if(TSUtils.isEmpty(setting)){
			renderJson("{}");
		}else{
			renderJson(setting);
		}
	}

	public void upload() {

		UploadFile file = getFile();

		String filename = file.getFileName();
		String mypath = File.separator + "site";
		String dirpath = file.getUploadPath() + mypath;

		String filepath = dirpath + File.separator + filename;
		File dir = new File(dirpath);
		if (!dir.exists()) {
			dir.mkdirs();
		}

		File newFile = new File(filepath);
		file.getFile().renameTo(newFile);
		String re = "upload" + mypath + File.separator
				+ filename;
		renderJson(new BaseDto("path", re.replaceAll("\\\\", "/")));
	}
	
	public void getTreeNodes() {

		List<Record> nodes = Db
				.find("select id ,name,parent_id,leaf from ts_column where parent_id =? order by id",getPara("id"));
		
		for (Record record : nodes) {
			if(record.get("leaf").equals("1")){
				record.set("isParent", false);
			}else{
				record.set("isParent", true);
			}
		}
		
		
		renderJson(JsonKit.toJson(nodes).replace("isparent", "isParent"));
	}

	public void getTreeList() {

		List<Record> nodes = Db
				.find("select id ,name,parent_id from ts_column  order by id");
		renderJson(nodes);
	}

	public void manageDatas() {

		int startIndex = getParaToInt("startIndex");
		int pageSize = getParaToInt("pageSize");

		int pageNumber = startIndex / pageSize + 1;

		Page<Column> datas = Column.dao.paginate(pageNumber, pageSize,
				"select t.*,c.name parent_name ", "from ts_column t ,ts_column c  where t.parent_id = c.id and t.id like ?  order by t.id ",
				getPara("treeid") + "%");

		renderJson(datas);
	}

	
	public void save(){
		
		Column model = getModel(Column.class, "",true);
		model.setId(IDHelper.getColumnID(model.getParentId()));
		model.setLeaf("1");
		// System.out.println(menu.toJson());
		model.save();
		
		renderJson(new BaseDto(true));
	}
	public void edit() {

		Column model = getModel(Column.class, "",true);
		// System.out.println(menu.toJson());
		model.update();

		renderJson(new BaseDto(true));
	}
	
	public void saveSetting() {
		Db.update("delete from ts_setting");
		Setting model = getModel(Setting.class, "", true);
		//model.set("logo", model.getLogo().replaceAll("\\", "/"));
		model.save();
		
		GroupTemplate gt = BeetlRenderFactory.groupTemplate;
			
		
		Setting setting = Setting.dao.findFirst("select * from ts_setting");
		
		Dto dto =JsonHelper.parseSingleJson2Dto(setting.toJson());
		
		
		if (TSUtils.isNotEmpty(setting)) {
			gt.setSharedVars(dto);
		}
		renderJson(new BaseDto());
	}


	/*@RequiresPermissions({ "menu:delete" })*/
	public void delete() {
		//删除目录下的文章
		//删除目录及其子目录
		//更新父目录状态
		
		String[] ids = getPara("ids").split(",");

		for (String id : ids) {
			Column.dao.deleteById(id);
		}
		
		renderJson(new BaseDto(true, "删除成功"));
	}
}
