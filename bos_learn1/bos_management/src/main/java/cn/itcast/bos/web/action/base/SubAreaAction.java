package cn.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.web.action.common.BaseAction;






@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class SubAreaAction extends BaseAction<SubArea>{
	private File file;//fileFileName
	private String fileFileName;
	@Action(value = "area_batchImportSubarea")
	public String upLoadfile() throws IOException{
		String fileEname=fileFileName.substring(fileFileName.indexOf(".")+1);
		if(fileEname.equals("xls")){
		//加载excel(xls)文件,如果是slsx的对象是XSSFWorkbook类
		HSSFWorkbook hssfWorkbook=new HSSFWorkbook(new FileInputStream(file));
		//得到工作簿
		HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
		//创建一个集合，保存数据
		List<Area> list=new ArrayList<>();
		//遍历工作簿，得到每一行
		for (Row row : sheetAt) {
			//跳过第一行，第一行的数据不是我们需要的
			if(row.getRowNum()==0){
				continue;
			}
			//跳过空白行
			if(row.getCell(0)==null 
					|| StringUtils.isBlank(row.getCell(0).getStringCellValue())){
				continue;
			}
		//待填信息
			
		 }
		}
		return NONE;

	}
	public void setFile(File file) {
		this.file = file;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	
	
	
	
}
