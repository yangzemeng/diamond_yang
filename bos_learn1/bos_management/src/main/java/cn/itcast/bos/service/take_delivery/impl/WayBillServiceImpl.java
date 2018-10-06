package cn.itcast.bos.service.take_delivery.impl;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.index.query.QueryStringQueryBuilder.Operator;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.take_delivery.WayBillDao;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.bos.index.WayBillIndexDao;
import cn.itcast.bos.service.take_delivery.WayBillService;

@Service
@Transactional
public class WayBillServiceImpl implements WayBillService{
    @Autowired
    private WayBillDao wayBillDao;
    @Autowired
    private WayBillIndexDao wayBillIndexDao;
	@Override
	public void save(WayBill model) {
		//因为id存在唯一约束，因此在保存的时候要进行修改
	    //先通过运单号得到运单对象
		WayBill preWayBillNum = wayBillDao.findBywayBillNum(model.getWayBillNum());
	    if(preWayBillNum==null || preWayBillNum.getId()==null){
	    	//运单不存在时
	    	model.setSignStatus(1); //待发货
	    	wayBillDao.save(model);
	    	wayBillIndexDao.save(model);
	    }else{
	    	//运单存在，进行运单的修改
	    	try {
	    		if(preWayBillNum.getSignStatus()==1){
	    			Integer id = preWayBillNum.getId();
			    	BeanUtils.copyProperties(preWayBillNum,model);
			    	preWayBillNum.setId(id);
			    	preWayBillNum.setSignStatus(1);
			    	//wayBillDao.save(preWayBillNum);
			    	wayBillIndexDao.save(preWayBillNum);
	    		}else{
	    			throw new RuntimeException("订单已发货，无法修改");
	    		}
	    		
	    		
			} catch (Exception e) {
				// TODO: handle exception
				throw new RuntimeException(e.getMessage());
			}
	    	
	    }
		
	}
	@Override
	public Page<WayBill> findAll(WayBill wayBill,Pageable pageable) {
		//基于索引库进行查询，判断waybill中的输入框之中查询条件是否存在,无条件查询
		if(StringUtils.isBlank(wayBill.getWayBillNum())&& StringUtils.isBlank(wayBill.getSendAddress())
				&& StringUtils.isBlank(wayBill.getRecAddress())
				&& StringUtils.isBlank(wayBill.getSendProNum())
				&&(wayBill.getSignStatus()==null|| wayBill.getSignStatus()==0)){
			return wayBillDao.findAll(pageable);
		}else{
			
			//must条件必须成立and  should 条件可以成立 or
			BoolQueryBuilder query=new BoolQueryBuilder(); //布尔查询，多条件组合查询
			if(StringUtils.isNoneBlank(wayBill.getWayBillNum())){
				//运单号查询
				QueryBuilder tempQuery=new TermQueryBuilder("wayBillNum",wayBill.getWayBillNum());
				query.must(tempQuery);
			}
			if(StringUtils.isNoneBlank(wayBill.getSendAddress())){
				//送货地址模糊查询
				//情况一：输入“北”是查询词条的一部分，使用模糊匹配词条查询
				QueryBuilder wildcardQuery=new WildcardQueryBuilder("sendAddress","*"+ wayBill.getSendAddress()+"*");
				//情况二：输入“北京市海淀区”时进行多个词条的组合，进行分词后 每个词条匹配查询
				QueryBuilder queryStringQueryBuiler=new QueryStringQueryBuilder(wayBill.getSendAddress()).field("sendAddress").defaultOperator(Operator.AND);
				//两种情况取or关系
				BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
				boolQueryBuilder.should(queryStringQueryBuiler);
				boolQueryBuilder.should(wildcardQuery);
				query.must(boolQueryBuilder);
				
				
			}
			if(StringUtils.isNoneBlank(wayBill.getRecAddress())){
				//收货地址模糊查询
				QueryBuilder wildcardQuery=new WildcardQueryBuilder("recAddress","*"+ wayBill.getRecAddress()+"*");
				//情况二：输入“北京市海淀区”时进行多个词条的组合，进行分词后 每个词条匹配查询
				QueryBuilder queryStringQueryBuiler=new QueryStringQueryBuilder(wayBill.getRecAddress()).field("recAddress").defaultOperator(Operator.AND);
				//两种情况取or关系
				BoolQueryBuilder boolQueryBuilder=new BoolQueryBuilder();
				boolQueryBuilder.should(queryStringQueryBuiler);
				boolQueryBuilder.should(wildcardQuery);
				query.must(boolQueryBuilder);
				
			}
			if(StringUtils.isNoneBlank(wayBill.getSendProNum())){
				//速运类型等值查询
				QueryBuilder tempQuery=new TermQueryBuilder("sendProNum",wayBill.getSendProNum());
				query.must(tempQuery);
			}
			
			if(wayBill.getSignStatus()!=null && wayBill.getSignStatus() !=0){
				QueryBuilder tempQuery=new TermQueryBuilder("signStatus",wayBill.getSignStatus());
				query.must(tempQuery);
			}
			SearchQuery searchQuery=new NativeSearchQuery(query);
			searchQuery.setPageable(pageable);
			//System.out.println(pageable.toString());
			return wayBillIndexDao.search(searchQuery);
		}
		
		
		
	}
	@Override
	public WayBill findBywayBillNum(String wayBillNum) {
		// TODO Auto-generated method stub
		return wayBillDao.findBywayBillNum(wayBillNum);
	}
	@Override
	public void syncIndex() {
		List<WayBill> list = wayBillDao.findAll();
		wayBillIndexDao.save(list);
		System.out.println("同步索引库执行");
		
	}

}
