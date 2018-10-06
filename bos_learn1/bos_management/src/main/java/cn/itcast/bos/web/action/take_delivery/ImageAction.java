package cn.itcast.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;

import com.opensymphony.xwork2.ActionContext;

import cn.itcast.bos.web.action.common.BaseAction;
@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class ImageAction extends BaseAction<Object>{
	private File imgFile;
	private String imgFileFileName;
	private String imgFileContentType;
	//实现图片上传功能，将图片上传到指定的文件夹下面
	@Action(value="image_upload",results={@Result(name="success",type="json")})
    public String uploadImage() throws IOException{
		System.out.println(imgFileFileName);
		//图片上传需要绝对路径，而返回到json时只需要相对路径
		String realPath = ServletActionContext.getServletContext().getRealPath("/upload/");
		String contextPath = ServletActionContext.getRequest().getContextPath();
		String saveUrl=contextPath+"/upload/";
		String ext = imgFileFileName.substring(imgFileFileName.lastIndexOf("."));
		//利用uuid随机生成图片名
		UUID randomUUID = UUID.randomUUID();
		String fileName=randomUUID+ext;
		FileUtils.copyFile(imgFile, new File(realPath+"/"+fileName));
		Map<String,Object> map=new HashMap<>();
		map.put("error", 0);
		map.put("url", saveUrl+fileName);
		ActionContext.getContext().getValueStack().push(map);
		return SUCCESS;
	}
	//编写图片编辑功能
	@Action(value="image_manage",results={@Result(name="success",type="json")})
	public String manageImage(){
		//根目录路径，可以指定绝对路径，比如 /var/www/attached/
		String realPath = ServletActionContext.getServletContext().getRealPath("/")+"upload/";
		//根目录URL，可以指定绝对路径，比如 http://www.yoursite.com/attached/
		String rootUrl = ServletActionContext.getRequest().getContextPath()+"/upload/";
		//遍历目录取得的文件夹信息
		List<Map<String, Object>> fileList=new ArrayList<>();
		//获取当前文件夹
		File currentPathFile=new File(realPath);
		//图片扩展名管理
		String[] fileTypes = new String[]{"gif", "jpg", "jpeg", "png", "bmp"};
		if(currentPathFile.listFiles() != null) {
			for (File file : currentPathFile.listFiles()) {
				Map<String, Object> hash = new HashMap<String,Object>();
				String fileName = file.getName();
				if(file.isDirectory()) {
					hash.put("is_dir", true);
					hash.put("has_file", (file.listFiles() != null));
					hash.put("filesize", 0L);
					hash.put("is_photo", false);
					hash.put("filetype", "");
				} else if(file.isFile()){
					String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
					hash.put("is_dir", false);
					hash.put("has_file", false);
					hash.put("filesize", file.length());
					hash.put("is_photo", Arrays.<String>asList(fileTypes).contains(fileExt));
					hash.put("filetype", fileExt);
				}
				hash.put("filename", fileName);
				hash.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(file.lastModified()));
				fileList.add(hash);
			}
		}
		Map<String, Object> result=new HashMap<>();
		result.put("moveup_dir_path", "");
		result.put("current_dir_path", realPath);
		result.put("current_url", rootUrl);
		result.put("total_count", fileList.size());
		result.put("file_list", fileList);
        ActionContext.getContext().getValueStack().push(result);		
		return SUCCESS;
	}
	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}

	public void setImgFileFileName(String imgFileFileName) {
		this.imgFileFileName = imgFileFileName;
	}
	public void setImgFileContentType(String imgFileContentType) {
		this.imgFileContentType = imgFileContentType;
	}
	
}
