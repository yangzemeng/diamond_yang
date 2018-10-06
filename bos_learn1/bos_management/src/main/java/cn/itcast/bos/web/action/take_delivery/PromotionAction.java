package cn.itcast.bos.web.action.take_delivery;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.take_delivery.Promotion;
import cn.itcast.bos.service.take_delivery.Promotionservice;
import cn.itcast.bos.web.action.common.BaseAction;
@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class PromotionAction extends BaseAction<Promotion>{
   private File titleImgFile;
   private String titleImgFileFileName;
   @Autowired
   private Promotionservice promotionService;
	@Action(value="promotion_save",results={@Result(name="success",type="redirect",location="/pages/take_delivery/promotion.html")})
	public String save() throws IOException{
		//需要存储哪些数据，怎样进行存储
		String realPath = ServletActionContext.getServletContext().getRealPath("/upload/");
		String contextPath = ServletActionContext.getRequest().getContextPath();
		String saveUrl=contextPath+"/upload/";
		String ext = titleImgFileFileName.substring(titleImgFileFileName.lastIndexOf("."));
		//利用uuid随机生成图片名
		UUID randomUUID = UUID.randomUUID();
		String fileName=randomUUID+ext;
		//进行图片保存，绝对路径保存
		FileUtils.copyFile(titleImgFile, new File(realPath+"/"+fileName));
		model.setTitleImg(saveUrl+fileName);
		promotionService.save(model);
		return SUCCESS;
	}
	@Action(value="promotion_pageQuery",results={@Result(name="success",type="json")})
	public String pageQuery(){
		Pageable pageable=new PageRequest(page-1, rows);
		Page<Promotion> pagedata=promotionService.findAll(pageable);
		pushToValueStack(pagedata);
		return SUCCESS;
	}
	public void setTitleImgFile(File titleImgFile) {
		this.titleImgFile = titleImgFile;
	}
	public void setTitleImgFileFileName(String titleImgFileFileName) {
		this.titleImgFileFileName = titleImgFileFileName;
	}
	
	
}
