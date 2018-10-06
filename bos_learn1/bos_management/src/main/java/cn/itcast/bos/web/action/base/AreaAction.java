package cn.itcast.bos.web.action.base;


import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.dom4j.CDATA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Area;

import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.web.action.common.BaseAction;
@Controller
@Namespace("/")
@ParentPackage("json-default")
@Scope("prototype")
public class AreaAction extends BaseAction<Area>{
  /*  private int page;
    private int rows;*/
	
	@Autowired
	private AreaService areaService;
	private File file;//fileFileName
	private String fileFileName;
	@Action(value = "area_batchImport")
	public String ocUpload() throws IOException{
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
			
			model.setId(row.getCell(0).getStringCellValue());
			model.setProvince(row.getCell(1).getStringCellValue());
			model.setCity(row.getCell(2).getStringCellValue());
			model.setDistrict(row.getCell(3).getStringCellValue());
			model.setPostcode(row.getCell(4).getStringCellValue());
			
			//利用Pinyin4j生成城市编码和简码
			//得到省市区
			String province = model.getProvince();
			String city = model.getCity();
			String district = model.getDistrict();
			 province= province.substring(0, province.length()-1);
			 city=city.substring(0, city.length()-1);
			 district=district.substring(0, district.length()-1);
			 String[] headByString = PinYin4jUtils.getHeadByString(province+city+district);
			 StringBuffer sBuffer=new StringBuffer();
			 for (String string : headByString) {
				sBuffer.append(string);
			}
			 //得到汉字的首字母大写
			 model.setShortcode(sBuffer.toString());
			 //将汉字转换成拼音
			 model.setCitycode(PinYin4jUtils.hanziToPinyin(city,""));
			 list.add(model);
			 areaService.save(list);
		}
		//调用业务层，进行数据保存
		
		System.out.println(fileEname);
		}
		return NONE;
		
	}
	@Action(value = "area_pageQuery", results = { @Result(name = "success", type = "json") })
	public String pageQuery() {
		Pageable pageable=new PageRequest(page-1, rows);
		Specification<Area> specification=new Specification<Area>() {
            
			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> list=new ArrayList<>();
				if(StringUtils.isNotBlank(model.getProvince())){
					Predicate p1 = cb.like(root.get("province").as(String.class),"%"+model.getProvince()+"%");
				list.add(p1);
				}
				if(StringUtils.isNotBlank(model.getCity())){
					Predicate p2 = cb.like(root.get("city").as(String.class),"%"+model.getCity()+"%");
				list.add(p2);
				}
				if(StringUtils.isNotBlank(model.getDistrict())){
					Predicate p3 = cb.like(root.get("district").as(String.class),"%"+model.getDistrict()+"%");
				list.add(p3);
				}
				return cb.and(list.toArray(new Predicate[list.size()]));
			}
			
		};
		Page<Area> areaData = areaService.findData(specification, pageable);
		/*Map<String, Object> result = new HashMap<>();
		result.put("total", courierData.getTotalElements());
		result.put("rows", courierData.getContent());
		ActionContext.getContext().getValueStack().push(result);*/
		pushToValueStack(areaData);
		return SUCCESS;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}
	
	/*public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}*/
	
 
}
