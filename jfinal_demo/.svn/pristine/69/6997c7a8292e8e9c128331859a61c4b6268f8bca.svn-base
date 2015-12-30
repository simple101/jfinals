package com.backend.action;


import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.util.StringUtils;
import com.backend.model.AdminUser;
import com.common.Config;
import com.jfinal.core.ActionKey;
import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import com.util.MD5Util;
import com.util.SendSMS;

import freemarker.template.utility.StringUtil;



public class AdminAction extends Controller{
	public void index(){
		render("user.html");
	}
	
	@ActionKey("/upload")
	public void upload(){
		render("upload.html");
	}
	
	
	public void validate(){
		String mobile = getPara("mobile");
		String code = getPara("valiCode");
		Map<String,Object> Info = new HashMap<String,Object>();
		
		if(!StringUtils.isEmpty(mobile)&&!StringUtils.isEmpty(code)){
			Object viCode = getSession().getAttribute(mobile);
			if(viCode!=null&&viCode.toString().equals(code)){
				Info.put("status", 0);
				Info.put("msg", "成功");
				getSession().removeAttribute(mobile);
			}else{
				Info.put("status", 1);
				Info.put("msg", "失败");
			}
		}else{
			Info.put("msg", "手机号或短信验证码不能为空");
		}
		renderJson(Info);
	}
	//登录
	public void login(){
		try{
			String userName = getPara("userName");
			String password = getPara("password");
			Map<String,Object> loginInfo = new HashMap<String,Object>();
			Record user= AdminUser.dao.isAdminUser(userName);
			if(user!=null){
				if(user.getStr("password").equals(MD5Util.md5(password))){
					getSession().setAttribute("userName", userName);
					loginInfo.put("userName", userName);
					loginInfo.put("status", 0);
				} else{
					loginInfo.put("status", 1);
					loginInfo.put("msg", "密码错误");
				}
			}else{
				loginInfo.put("status", 1);
				loginInfo.put("msg", "用户名不存在");
			}
			renderJson(loginInfo);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	//注册
	public void regist(){
	try{
			String mobile = getPara("mobile");
			String password = getPara("password");
			String code = getPara("valiCode");
			Map<String,Object> registInfo = new HashMap<String,Object>();
			if(mobile!=null&&code!=null){
				Object viCode = getSession().getAttribute(mobile);
				if(viCode!=null&&viCode.toString().equals(code)){
					if(!StringUtils.isEmpty(password)){
						AdminUser.dao.set("userName", mobile).set("password", MD5Util.md5(password)).save();
						registInfo.put("status", 0);
						registInfo.put("msg", "注册成功");
						getSession().removeAttribute(mobile);
					}else{
						registInfo.put("status", 1);
						registInfo.put("msg", "密码不能为空");
					}
				}else{
					registInfo.put("status", 1);
					registInfo.put("msg", "短信验证码错误");
				}
			}else{
				registInfo.put("status", 1);
				registInfo.put("msg", "手机号或短信验证码不能为空");
			}
			renderJson(registInfo);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	//短信接口
	public void sendmsg(){
		String mobile = getPara("mobile");
		Map<String,Object> Info = new HashMap<String,Object>();
		try{
			if(!StringUtils.isEmpty(mobile)){
			 String content = SendSMS.sendmsg(mobile);
			 getSession().setAttribute(mobile, content);
			 System.out.println(getSession().getAttribute(mobile)+"=======session取到");
			 Info.put("status", 0);
			 Info.put("msg", "成功");
			}else{
				Info.put("status", 1);
				Info.put("msg", "手机号不能为空");
			}
			renderJson(Info);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//关于
	public void relation(){
		try{
	    Map<String,Object> relationMap = new HashMap<String,Object>();
		String relation = new Config().get();
		relationMap.put("status", 0);
		relationMap.put("relation", relation);
		renderJson(relationMap);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	//找回密码
	public void retrieve(){
		try{
			String mobile = getPara("mobile");
			String password = getPara("password");
			String code = getPara("valiCode");
			Map<String,Object> registInfo = new HashMap<String,Object>();
			if(mobile!=null&&code!=null){
				Object viCode = getSession().getAttribute(mobile);
				if(viCode!=null&&viCode.toString().equals(code)){
					if(!StringUtils.isEmpty(password)){
						Record user= AdminUser.dao.isAdminUser(mobile);
						user.set("password", password);
						AdminUser.dao.updateUser(user);
						registInfo.put("status", 0);
						registInfo.put("msg", "密码修改成功");
					}else{
						registInfo.put("status", 1);
						registInfo.put("msg", "密码不能为空");
					}
				}else{
					registInfo.put("status", 1);
					registInfo.put("msg", "短信验证码错误");
				}
			}else{
				registInfo.put("status", 1);
				registInfo.put("msg", "手机号或短信验证码不能为空");
			}
			renderJson(registInfo);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	//文件上传
	public void uploadFile(){
		String filePath = getPara("filePath");
		
		Map<String,Object> registInfo = new HashMap<String,Object>();
		
		if(filePath.trim().length()!=0){
			FileItemFactory factory = new DiskFileItemFactory(); 
			ServletFileUpload upload = new ServletFileUpload(factory); 
			
			
		}else{
			registInfo.put("status", 1);
			registInfo.put("msg", "上传失败");
		}
		renderJson(registInfo);
	}

}
