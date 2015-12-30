package com.backend.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;

import com.jspsmart.upload.Request;
import com.jspsmart.upload.SmartUpload;
import com.util.MD5Util;

public class UploadServlet extends HttpServlet {

	private String uploadPath = "C:\\upload\\"; // 上传文件的目录
	private String tempPath = "C:\\upload\\tmp\\"; // 临时文件目录

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			 	response.setContentType("text/html;charset=UTF-8");
		        PrintWriter out = response.getWriter();
		        out.flush();
		
		        String param="";
				String savePath="";
				String md5Val="";
				String tempPath = "C:\\upload\\";
				
				response.setContentType("text/html;charset=UTF-8");
			        File file = new File(tempPath);
			        if(!file.exists()){
			            file.mkdir();
			        }
			        DiskFileItemFactory factory = new DiskFileItemFactory();
			        // Create a new file upload handler
			        ServletFileUpload upload = new ServletFileUpload(factory);
			        List items = null;
			        try {
			            items = upload.parseRequest(request);
			        } catch (FileUploadException e) {
			            e.printStackTrace();
			        }
			        byte data[] = new byte[1024];
			        int i = 0;
//			        Affix affix = null;
//			        User user = new User();
			        if (items != null)
			            for (Iterator iterator = items.iterator(); iterator.hasNext();) {
			                FileItem item = (FileItem) iterator.next();
			                
			                if (item.isFormField()) {
			                    if ("param".equals(item.getFieldName())) {
			                    	param=new String(item.getString("UTF-8"));
			                    	
			                    	savePath=param.split("##")[0];
			                    	savePath=savePath.replace(",", File.separator);
			                    	tempPath=tempPath+savePath+File.separator;
			                    	
			                    	md5Val=param.split("##")[1].toUpperCase();
			                    	
			                    	
			                    	System.out.println(tempPath+"\t"+md5Val);
			                    }
			                   
			                } else {
			                	String filePaht=item.getName().toString().toUpperCase();
			            		String md5 = MD5Util.getFileMD5String(new File(filePaht)).toUpperCase();
			                	if(md5.equals(md5Val)){
			                		 if (!new File(tempPath).isDirectory()) {
											new File(tempPath).mkdirs(); //选定上传的目录此处为当前目录，没有则创建
										}
			                		String fileName = item.getName().substring(
				                            item.getName().lastIndexOf(File.separator) + 1,
				                            item.getName().length());
				                    InputStream inputStream = item.getInputStream();
				                    // InputStream inputStream = request.getInputStream();
				                    OutputStream outputStream = new FileOutputStream(tempPath
				                            + fileName);
				                    while ((i = inputStream.read(data)) != -1) {
				                        outputStream.write(data, 0, i);
				                    }
				                    inputStream.close();
				                    outputStream.close();
				                    out.println("<script>");
							        out.println("alert('上传成功！');");
							        out.println("history.back();");
							        out.println("</script>");
			                	}else{
			                		
				                    out.println("<script>");
							        out.println("alert('上传失败，MD5值和文件不匹配！');");
							        out.println("history.back();");
							        out.println("</script>");
			                	}
			                	
			                    
			                }
			            }
			       
			        
			       
			        

//			        response.sendRedirect("/upload");
	    }


	
	//该方法为备用方法，无法获取前台组件参数
	public void doPostssss(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		File tmpDir = new File(tempPath); // 初始化上传文件的临时存放目录,必须是绝对路径
		try {
			 if (ServletFileUpload.isMultipartContent(request)) {
					DiskFileItemFactory factory = new DiskFileItemFactory();
					// 指定在内存中缓存数据大小,单位为byte,这里设为10Mb
					factory.setSizeThreshold(10 * 1024 * 1024);
					// 设置一旦文件大小超过getSizeThreshold()的值时数据存放在硬盘的目录
					factory.setRepository(tmpDir);
					ServletFileUpload sfu = new ServletFileUpload(factory);
					// 指定单个上传文件的最大尺寸,单位:字节，这里设为50Mb
					sfu.setFileSizeMax(50 * 1024 * 1024);
					// 指定一次上传多个文件的总尺寸,单位:字节，这里设为100Mb
					sfu.setSizeMax(100 * 1024 * 1024);
					sfu.setHeaderEncoding("UTF-8"); // 设置编码
					
					 
					FileItemIterator fii = sfu.getItemIterator(request);//
					// 解析request请求
					if (!new File(uploadPath).isDirectory()) {
						new File(uploadPath).mkdirs(); // 选定上传的目录此处为当前目录，没有则创建
					}

					int index = 0;
					while (fii.hasNext()) {
						FileItemStream fis = fii.next();// 从集合中获得一个文件流
						if (!fis.isFormField() && fis.getName().length() > 0) {//
							// 过滤掉表单中非文件域
							// String fileName =fis.getName().substring(fis.getName().lastIndexOf("."));//
							// 获得上传文件的文件名
							// fileName = sdf.format(new Date())+"-"+index+fileName;
							
							File file = new File(fis.getName());
							BufferedInputStream in = new BufferedInputStream(fis
									.openStream());
							BufferedOutputStream bout = new BufferedOutputStream(
									new FileOutputStream(new File(uploadPath + "//"
											+ file.getName())));
							Streams.copy(in, bout, true); // 开始把文件写到你指定的上传文件夹
							index++;
						}
					}
					
					response.sendRedirect("/upload");
				}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
