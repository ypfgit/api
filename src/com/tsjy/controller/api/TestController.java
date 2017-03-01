package com.tsjy.controller.api;

import java.util.List;

import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.plat.core.dto.BaseDto;
import com.plat.core.dto.Dto;

public class TestController extends Controller {
	
	public void t1(){
		List<Record> data = Db.find("select * from ts_user");
		
		Dto re = new BaseDto(true);
		re.put("data", data);
		renderJson(re);
	}
}
