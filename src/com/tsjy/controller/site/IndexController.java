package com.tsjy.controller.site;

import java.util.List;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.tsjy.model.Notice;

@Clear
public class IndexController extends Controller {

	
	public void index() {
		
		this.createToken("loginToken");
		List<Record> wzyw = Db
				.find("select t.id,t.thumb, t.title,date_format(t.createdate,'%Y-%m-%d') createdate from ts_notice t,ts_column c where t.columnid = c.id and c.name='公安信息'  limit 0,5");
		List<Record> gwdt = Db
				.find("select t.id,t.thumb, t.title,date_format(t.createdate,'%Y-%m-%d') createdate from ts_notice t,ts_column c where t.columnid = c.id and c.name='政策法规'  limit 0,5");
		setAttr("wzyw", wzyw);
		setAttr("gwdt", gwdt);

		List<Record> wzgg = Db
				.find("select t.id, t.title,date_format(t.createdate,'%Y-%m-%d') createdate from ts_notice t,ts_column c where t.columnid = c.id and c.name='通知公告'  limit 0,5");
		setAttr("wzgg", wzgg);

		List<Record> slides = Db
				.find("select t.id, t.title,t.thumb,date_format(t.createdate,'%Y-%m-%d') createdate from ts_notice t,ts_column c where t.columnid = c.id and c.name='首页幻灯图'  limit 0,5");
		setAttr("slides1", slides);

		Record fwcn = Db
				.findFirst("select t.id, t.summary from ts_notice t,ts_column c where t.columnid = c.id and c.name='服务承诺'");
		setAttr("fwcn", fwcn);
		render("/view/site/index.html");
	}

	// 新闻列表页
	public void more() {
		List<Record> slides = Db
				.find("select t.id, t.title,t.thumb,date_format(t.createdate,'%Y-%m-%d') createdate from ts_notice t,ts_column c where t.columnid = c.id and c.name='首页幻灯图'  limit 0,5");
		setAttr("slides", slides);

		// 1 幻灯图 2 网站公告 3 服务承诺 4 网站要闻 5 国网动态
		setAttr("type", getPara());

		render("/view/site/news.html");
	}

	// 分页获取新闻列表
	public void getNews() {
		int pageSize = getParaToInt("pageSize");
		int pageNumber = getParaToInt("pageNumber");

		String type = getPara("type");
		String name = "";
		// 1首页 幻灯图 2 网站公告 3 服务承诺 4 网站要闻 5 国网动态
		if (type.equals("1")) {
			name = "首页幻灯图";
		} else if (type.equals("2")) {
			name = "网站公告";
		} else if (type.equals("3")) {
			name = "服务承诺";
		} else if (type.equals("4")) {
			name = "网站要闻";
		} else if (type.equals("5")) {
			name = "国网动态";
		} else {
			name = "网站公告";
		}

		Page<Record> datas = Db
				.paginate(
						pageNumber,
						pageSize,
						"select t.id, t.title,t.thumb,left(t.summary ,200 ) summary,date_format(t.createdate,'%Y-%m-%d') createdate,left(t.content ,200 ) summary1 ",
						"from ts_notice t,ts_column c where t.columnid = c.id and c.name=?  order by id ",
						name);

		renderJson(datas);
	}

	// 新闻详情页
	public void show() {

		List<Record> slides = Db
				.find("select t.id, t.title,t.thumb,date_format(t.createdate,'%Y-%m-%d') createdate from ts_notice t,ts_column c where t.columnid = c.id and c.name='首页幻灯图'  limit 0,5");
		setAttr("slides", slides);

		String id = getPara(0);
		// 1 幻灯图 2 网站公告 3 服务承诺 4 网站要闻 5 国网动态
		String type = getPara(1);

		Notice item = Notice.dao.findById(id);

		// 更新点击量
		item.setHits(item.getHits() + 1);
		item.update();

		setAttr("item", item);
		setAttr("type", type);

		render("/view/site/news-dtal.html");
	}
}
