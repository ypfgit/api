	  
	  
queryUser
===
SELECT
u.*
FROM
ts_user u

WHERE
 u.account=#account# and u.password= #password#
	

queryUserInfo
===
select u.*,r.role_name,r.role_id
from ts_user u ,v_user_role r  where u.id = r.user_id 
@if(!isEmpty(account)){
	 and u.account=#account# 
@}
@if(!isEmpty(id)){
	 and u.id=#id# 
@}
@if(!isEmpty(password)){
	 and u.password=#password# 
@}

