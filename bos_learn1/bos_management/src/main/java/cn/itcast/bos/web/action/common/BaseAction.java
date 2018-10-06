package cn.itcast.bos.web.action.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.util.ValueStack;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>{
     protected T model;
     public T getModel() {
 		// TODO Auto-generated method stub
 		return model;
     }
 	//利用构造器进行实例化
 	public BaseAction(){
 		 Type genericSuperclass = this.getClass().getGenericSuperclass();
 		ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
 		Class<T> modelClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
 		try {
			model=modelClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			System.out.println("模型驱动失败");
			e.printStackTrace();
		}
 	}
 	
 	protected int page;
 	protected int rows;
 	public void pushToValueStack(Page<T> pageData){
 		Map<String ,Object> map=new HashMap<>();
		map.put("total",pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		ValueStack valueStack = ActionContext.getContext().getValueStack();
		valueStack.push(map);
 	}
	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
