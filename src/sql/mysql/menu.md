queryAuthMenu
===
*用户所属角色的授权侧边菜单
*parentid 根节点ID  level 从根节点向下取几级
SELECT DISTINCT
	m.id,
	m.text,
	m.en_name,
	m.parent_id,
	m.collapsed,
	m.leaf,
	m.url,
	m.sort_no,
	m.icon,
	m.iconcss,
	m.remark,
	t. NAME module_name,
	t.en_name module_en_name,
	t.home_page module_home_page,
	t.collapsed module_collapsed
FROM
	ts_menu m,
	ts_role_auth r,
	ts_user_role u,
	ts_module t
WHERE
	m.id = r.menu_id
AND r.role_id = u.role_id
AND m.module_id = t.id
@if(!isEmpty(userid)){
	 AND u.user_id =#userid#
@}

@if(!isEmpty(parentid)){
	 and m.parent_id like #parentid+'%'#
	 @if(!isEmpty(level)){
	   and LENGTH(m.id)<=length(#text(parentid)#)+2*#level#
	 @}
@}

@if(!isEmpty(leaf)){
	and m.leaf = #leaf#
@}

@if(!isEmpty(roleid)){
	and r.role_id = #roleid#
@}

ORDER BY
	m.sort_no
	
	
queryMenu
===
*菜单管理
SELECT 
	m.id,
	m.text,
	m.en_name,
	m.parent_id,
	m.collapsed,
	m.leaf,
	m.url,
	m.sort_no,
	m.remark,
	m.type,
	m.icon,
	t.name module_name,
	t.en_name module_en_name,
	t.home_page module_home_page,
	t.collapsed module_collapsed
FROM
	ts_menu m,
	ts_module t
where m.module_id = t.id
ORDER BY
	m.sort_no
	
deleteMenuAndSon
===
*删除菜单及其子菜单
delete from ts_menu  where id like #menuid+'%'#

queryMenuCountByPid
===
*查询父菜单下的子菜单数据
select count(1) num from ts_menu m where m.parent_id=#parentid#
	
updateMenuLeaf
===
*更新菜单的叶子结点信息
update ts_menu set leaf = #leaf# where id = #id#

queryPermissionMenus
===
*用于角色管理里 显示授权树
SELECT
	m.id,
	m.parent_id,
	m.text,
	m.leaf,
	a.role_id,
	a.actions
FROM
	ts_menu m
LEFT JOIN ts_role_auth a ON m.id = a.menu_id
AND a.role_id = #roleid#
order by m.sort_no

queryActionMenus
===
*角色的授权侧边菜单,只取叶节点
SELECT
	m.id,
	m.parent_id,
	m.text,
	m.en_name,
	m.leaf,
	a.role_id,
	a.actions,
	g.actions names,
	g.codes codes
FROM
	ts_menu m,
	ts_role_auth a,
	(
		SELECT
			menu_id,
			GROUP_CONCAT(t. CODE)codes,
			GROUP_CONCAT(t.action)actions
		FROM
			ts_menu_action t
		group by t.menu_id
	)g
WHERE
	m.id = a.menu_id
AND m.id = g.menu_id
and a.role_id = #roleid#
and m.leaf = #leaf#

order by m.id

