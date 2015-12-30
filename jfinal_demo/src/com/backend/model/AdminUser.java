package com.backend.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
/**
 * 用户登录注册
 * @author dell
 *
 */

public class AdminUser extends Model<AdminUser> {
	public static final AdminUser dao = new AdminUser();
	//判断该用户是否存在  用于登录使用
	public Record isAdminUser(String userName){
		return Db.findFirst("select * from adminuser where userName=?",userName);
	} 
	//修改
	public boolean updateUser(Record record){
		
		 return Db.update("adminuser", record);
	} 
}
