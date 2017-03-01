package com.plat.core.util;

import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;

public class IDHelper {

	private static final Log log =  Log.getLog(IDHelper.class);
	public static int getModuleID() {

		return Db.queryBigDecimal("select ts_module_seq.nextval from dual")
				.intValue();
	}
	
	public static int getUserID() {

		return Db.queryBigDecimal("select ts_user_seq.nextval from dual")
				.intValue();
	}
	public static int getActionID() {

		return Db.queryBigDecimal("select ts_action_seq.nextval from dual")
				.intValue();
	}

	public static String getMenuID(String pParentID) {
		String sql = "SELECT MAX(id)" + "  FROM ts_menu"
				+ " WHERE parent_id = ?";
		String maxSubMenuId = Db.queryStr(sql, pParentID);
		String menuId = null;
		if (TSUtils.isEmpty(maxSubMenuId)) {
			menuId = "01";
		} else {
			int length = maxSubMenuId.length();
			String temp = maxSubMenuId.substring(length - 2, length);
			int intMenuId = Integer.valueOf(temp).intValue() + 1;
			if (intMenuId > 0 && intMenuId < 10) {
				menuId = "0" + String.valueOf(intMenuId);
			} else if (10 <= intMenuId && intMenuId <= 99) {
				menuId = String.valueOf(intMenuId);
			} else if (intMenuId > 99) {
				log.error(TSConstants.Exception_Head
						+ "生成菜单编号越界了.同级兄弟节点编号为[01-99]\n请和您的系统管理员联系!");
			} else {
				log.error(TSConstants.Exception_Head + "生成菜单编号发生未知错误,请和开发人员联系!");
			}
		}
		return pParentID + menuId;
	}
	
	public static String getColumnID(String pParentID) {
		String sql = "SELECT MAX(id)" + "  FROM ts_column"
				+ " WHERE parent_id = ?";
		String maxSubMenuId = Db.queryStr(sql, pParentID);
		String menuId = null;
		if (TSUtils.isEmpty(maxSubMenuId)) {
			menuId = "01";
		} else {
			int length = maxSubMenuId.length();
			String temp = maxSubMenuId.substring(length - 2, length);
			int intMenuId = Integer.valueOf(temp).intValue() + 1;
			if (intMenuId > 0 && intMenuId < 10) {
				menuId = "0" + String.valueOf(intMenuId);
			} else if (10 <= intMenuId && intMenuId <= 99) {
				menuId = String.valueOf(intMenuId);
			} else if (intMenuId > 99) {
				log.error(TSConstants.Exception_Head
						+ "生成菜单编号越界了.同级兄弟节点编号为[01-99]\n请和您的系统管理员联系!");
			} else {
				log.error(TSConstants.Exception_Head + "生成菜单编号发生未知错误,请和开发人员联系!");
			}
		}
		return pParentID + menuId;
	}
	public static String getAreaID(String pParentID) {
		String sql = "SELECT MAX(id)" + "  FROM ts_area"
				+ " WHERE parent_id = ?";
		String maxSubMenuId = Db.queryStr(sql, pParentID);
		String menuId = null;
		if (TSUtils.isEmpty(maxSubMenuId)) {
			menuId = "01";
		} else {
			int length = maxSubMenuId.length();
			String temp = maxSubMenuId.substring(length - 2, length);
			int intMenuId = Integer.valueOf(temp).intValue() + 1;
			if (intMenuId > 0 && intMenuId < 10) {
				menuId = "0" + String.valueOf(intMenuId);
			} else if (10 <= intMenuId && intMenuId <= 99) {
				menuId = String.valueOf(intMenuId);
			} else if (intMenuId > 99) {
				log.error(TSConstants.Exception_Head
						+ "生成菜单编号越界了.同级兄弟节点编号为[01-99]\n请和您的系统管理员联系!");
			} else {
				log.error(TSConstants.Exception_Head + "生成菜单编号发生未知错误,请和开发人员联系!");
			}
		}
		return pParentID + menuId;
	}
	public static String getCatID(String pParentID) {
		String sql = "SELECT MAX(id)" + "  FROM ts_category"
				+ " WHERE parent_id = ?";
		String maxSubMenuId = Db.queryStr(sql, pParentID);
		String menuId = null;
		if (TSUtils.isEmpty(maxSubMenuId)) {
			menuId = "01";
		} else {
			int length = maxSubMenuId.length();
			String temp = maxSubMenuId.substring(length - 2, length);
			int intMenuId = Integer.valueOf(temp).intValue() + 1;
			if (intMenuId > 0 && intMenuId < 10) {
				menuId = "0" + String.valueOf(intMenuId);
			} else if (10 <= intMenuId && intMenuId <= 99) {
				menuId = String.valueOf(intMenuId);
			} else if (intMenuId > 99) {
				log.error(TSConstants.Exception_Head
						+ "生成菜单编号越界了.同级兄弟节点编号为[01-99]\n请和您的系统管理员联系!");
			} else {
				log.error(TSConstants.Exception_Head + "生成菜单编号发生未知错误,请和开发人员联系!");
			}
		}
		return pParentID + menuId;
	}

	/**
	 * 部门编号ID生成器(自定义)
	 * 
	 * @param pParentid
	 *            菜单编号的参考编号
	 * @return
	 */
	public static String getDeptId(String pParentid) {
		String sql = "SELECT MAX(id)" + "  FROM ts_dept"
				+ " WHERE parent_id = ?";
		String maxSubDeptId = Db.queryStr(sql, pParentid);
		String deptid = null;
		if (TSUtils.isEmpty(maxSubDeptId)) {
			deptid = "001";
		} else {
			int length = maxSubDeptId.length();
			String temp = maxSubDeptId.substring(length - 3, length);
			int intDeptId = Integer.valueOf(temp).intValue() + 1;
			if (intDeptId > 0 && intDeptId < 10) {
				deptid = "00" + String.valueOf(intDeptId);
			} else if (10 <= intDeptId && intDeptId <= 99) {
				deptid = "0" + String.valueOf(intDeptId);
			} else if (100 <= intDeptId && intDeptId <= 999) {
				deptid = String.valueOf(intDeptId);
			} else if (intDeptId > 999) {
				log.error(TSConstants.Exception_Head
						+ "生成部门编号越界了.同级兄弟节点编号为[001-999]\n请和您的系统管理员联系!");
			} else {
				log.error(TSConstants.Exception_Head + "生成部门编号发生未知错误,请和开发人员联系!");
			}
		}
		return pParentid + deptid;
	}
}
