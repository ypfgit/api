deleteRoleAuth
===
delete from ts_role_auth where role_id = #roleid# 
@if(!isEmpty(menuids)){
	 and menu_id not in (#text(menuids)#)
@}


insertRoleAuth
===
INSERT INTO ts_role_auth(role_id, menu_id) 
SELECT #roleid#, #menuid# FROM DUAL 
WHERE NOT EXISTS(SELECT role_id FROM ts_role_auth WHERE role_id = #roleid# and menu_id=#menuid#)