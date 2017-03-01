package com.plat.core.util;



/**
* 类描述： 提供字符串的一些处理
* 创建者： LiangChen
* 项目名称： TSPlat
* 创建时间： 2013-9-17 下午09:24:07
* 版本号： v1.0
*/
public class StringUtils {
	
	/**
	 * 判断输入的字符串是否为有效输入(不为null和空字符串)
	 * @param string
	 * @return boolean
	 */
	public boolean isNotNullOrEmpty(String string){
		if (string==null||string.trim().length()==0) {
			return false;
		}
		return true;
	}
}
