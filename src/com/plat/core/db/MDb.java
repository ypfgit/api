package com.plat.core.db;

import java.util.List;
import java.util.Map;

import org.beetl.sql.core.SQLManager;
import org.beetl.sql.core.SQLResult;
import org.beetl.sql.ext.jfinal.JFinalBeetlSql;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;

/**
 * 在Db的基础上封装
 */
public class MDb {
	public static SQLManager sqlManager = JFinalBeetlSql.dao();

	public static int update(String sqlId, Map<String, Object> map) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, map);

		return Db.update(sqlResult.jdbcSql, sqlResult.jdbcPara.toArray());
	}

	public static int update(String sqlId) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, null);

		return Db.update(sqlResult.jdbcSql);
	}

	/**
	 * @see #find(String, String, Object...)
	 */
	public static List<Record> find(String sqlId, Map<String, Object> paras) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, paras);

		return Db.find(sqlResult.jdbcSql, sqlResult.jdbcPara.toArray());
	}

	/**
	 * @see #find(String, String, Object...)
	 * @param sql
	 *            the sql statement
	 */
	public static List<Record> find(String sqlId) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, null);

		return Db.find(sqlResult.jdbcSql);
	}

	/**
	 * Find first record. I recommend add "limit 1" in your sql.
	 * 
	 * @param sql
	 *            an SQL statement that may contain one or more '?' IN parameter
	 *            placeholders
	 * @param paras
	 *            the parameters of sql
	 * @return the Record object
	 */
	public static Record findFirst(String sqlId, Map<String, Object> paras) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, paras);

		return Db.findFirst(sqlResult.jdbcSql, sqlResult.jdbcPara.toArray());
	}

	/**
	 * @see #findFirst(String, Object...)
	 * @param sql
	 *            an SQL statement
	 */
	public static Record findFirst(String sqlId) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, null);

		return Db.findFirst(sqlResult.jdbcSql);
	}
	
	
	
	public static Model findFirst(Model dao, String sqlId, Map<String, Object> paras) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, paras);

		return dao.findFirst(sqlResult.jdbcSql, sqlResult.jdbcPara.toArray());
	}

	/**
	 * @see #findFirst(String, Object...)
	 * @param sql
	 *            an SQL statement
	 * @return 
	 */
	public static Model findFirst(Model dao, String sqlId) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, null);

		return  dao.findFirst(sqlId);
		
	}

	/**
	 * @param <M>
	 * @param <T>
	 * @return
	 * @see #find(String, String, Object...)
	 */
	public static  <M> List<M> find(Model dao, String sqlId,
			Map<String, Object> paras) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, paras);

		return dao.find(sqlResult.jdbcSql, sqlResult.jdbcPara.toArray());
	}

	public static List<? extends Model> find(Model dao, String sqlId) {

		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, null);

		return dao.find(sqlResult.jdbcSql);
	}

	public static String queryStr(String sqlId, Map<String, Object> paras) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, paras);
		return Db.queryStr(sqlResult.jdbcSql, sqlResult.jdbcPara.toArray());
	}

	public static String queryStr(String sqlId) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, null);
		return Db.queryStr(sqlResult.jdbcSql, sqlResult.jdbcPara.toArray());
	}

	public static Integer queryInt(String sqlId, Map<String, Object> paras) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, paras);
		return Db.queryInt(sqlResult.jdbcSql, sqlResult.jdbcPara.toArray());
	}

	public static Integer queryInt(String sqlId) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, null);
		return Db.queryInt(sqlResult.jdbcSql, sqlResult.jdbcPara.toArray());
	}

	public static <M> Page<M> paginate(Model dao, String sqlId,
			int pageNumber, int pageSize, Map<String, Object> paras) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, paras);

		int fromIndex = sqlResult.jdbcSql.lastIndexOf("from");
		
		
		if(fromIndex<0){
			fromIndex = sqlResult.jdbcSql.lastIndexOf("FROM");
		}

		String select = sqlResult.jdbcSql.substring(0, fromIndex);
		String where = sqlResult.jdbcSql.substring(fromIndex);

		return dao.paginate(pageNumber, pageSize, select, where,
				sqlResult.jdbcPara.toArray());
	}
	
	
	public static Page<Record> paginate(String sqlId,
			int pageNumber, int pageSize, Map<String, Object> paras) {
		SQLResult sqlResult = sqlManager.getSQLResult(sqlId, paras);

		int fromIndex = sqlResult.jdbcSql.lastIndexOf("from");
		if(fromIndex<0){
			fromIndex = sqlResult.jdbcSql.lastIndexOf("FROM");
		}
		
		String select = sqlResult.jdbcSql.substring(0, fromIndex);
		String where = sqlResult.jdbcSql.substring(fromIndex);

		return Db.paginate(pageNumber, pageSize, select, where,
				sqlResult.jdbcPara.toArray());
	}
}
