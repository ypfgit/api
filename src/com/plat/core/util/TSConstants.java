package com.plat.core.util;


/**
 * 类描述： 系统常量 创建者： LiangChen 项目名称： TSPlat 创建时间： 2013-9-17 下午09:24:22 版本号： v1.0
 */
public class TSConstants {


	public static String SESSION_USER = "S_USER";
	
	//当前用户的ui权限集
	public static String SESSION_AUTHUI="S_AUTHUI";
	
	/**
	 * 是否打印sql默认为false,即默认不打印执行的sql语句
	 */
	public static boolean PRINT_SQL = true;
	/**
	 * sql前缀,分页时候需要用到,需要知道返回数据集,默认为 'select *'
	 */
	public static final String SQL_HEADER = "select *";

	/**
	 * 分页参数.每页显示数据条数
	 */
	public static final int PAGE_SIZE = 20;

	/**
	 * 验证码cookie的key
	 */
	public static final String C_RANDOM = "c_random";

	/**
	 * 用户类型为管理员
	 */
	public static final String TYPE_ADMIN = "admin";
	/**
	 * 用户类型为开发人员
	 */
	public static final String TYPE_DEV = "dev";
	/**
	 * 用户类型为普通用户
	 */
	public static final String TYPE_COM = "com";

	/**
	 * 存储用户类型
	 */
	public static String type;

	/**
	 * 登陆用户名长度为1-10,限制输入字符为数字和字母
	 */
	public static final String LOGIN_USERNAME_REGEX = "^([0-9a-zA-Z]{1,10})$";
	/**
	 * 登陆密码长度为6-16,不限制输入字符
	 */
	public static final String LOGIN_PASSWORD_REGEX = "^(.{1,16})$";

	/**
	 * 验证码长度为6位,限制输入为数字和字母
	 */
	public static final String LOGIN_RANDOMCODE_REGEX = "^([0-9a-zA-Z]{6})$";
	
	/**
	 * 字符串组成类型<br>
	 * number:数字字符串
	 */
	public static final String S_STYLE_N = "number";

	/**
	 * 字符串组成类型<br>
	 * letter:字母字符串
	 */
	public static final String S_STYLE_L = "letter";

	/**
	 * 字符串组成类型<br>
	 * numberletter:数字字母混合字符串
	 */
	public static final String S_STYLE_NL = "numberletter";
	
	/**
	 * 异常信息统一头信息<br>
	 * 非常遗憾的通知您,程序发生了异常
	 */
	public static final String Exception_Head = "\nOH,MY GOD! SOME ERRORS OCCURED! " + "AS FOLLOWS.\n";
	
	
	/**
	 * 树节点类型<br>
	 * 1:叶子节点
	 */
	public static final String LEAF_Y = "1"; 
	
	/**
	 * 树节点类型<br>
	 * 0:树枝节点
	 */
	public static final String LEAF_N = "0";
	
	/**
	 * XML文档风格<br>
	 * 0:节点属性值方式
	 */
	public static final String XML_Attribute = "0";

	/**
	 * XML文档风格<br>
	 * 1:节点元素值方式
	 */
	public static final String XML_Node = "1";
	
	/**
	 * Ext表格加载模式<br>
	 * \n:非翻页排序加载模式
	 */
	public static final String EXT_GRID_FIRSTLOAD = "first";

	/**
	 * Excel模板数据类型<br>
	 * number:数字类型
	 */
	public static final String ExcelTPL_DataType_Number = "number";

	/**
	 * Excel模板数据类型<br>
	 * number:文本类型
	 */
	public static final String ExcelTPL_DataType_Label = "label";
	public static final String S_USERINFO = "S_USER";
	
	
	/**
	 * Excel模板数据类型<br>
	 * number:文本类型
	 */
	public static String SMS_PLAT_URL = "";
	

}
