package com.tsjy.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("ts_column", "id", Column.class);
		arp.addMapping("ts_dept", "id", Dept.class);
		arp.addMapping("ts_file", "id", File.class);
		arp.addMapping("ts_icon", "id", Icon.class);
		arp.addMapping("ts_menu", "id", Menu.class);
		arp.addMapping("ts_menu_action", "id", MenuAction.class);
		arp.addMapping("ts_module", "id", Module.class);
		arp.addMapping("ts_notice", "id", Notice.class);
		arp.addMapping("ts_param", "paramid", Param.class);
		arp.addMapping("ts_role", "id", Role.class);
		// Composite Primary Key order: menu_id,role_id
		arp.addMapping("ts_role_auth", "menu_id,role_id", RoleAuth.class);
		arp.addMapping("ts_setting", "id", Setting.class);
		arp.addMapping("ts_user", "id", User.class);
		// Composite Primary Key order: role_id,user_id
		arp.addMapping("ts_user_role", "role_id,user_id", UserRole.class);
	}
}

