package cn.itcast.bos.index;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import cn.itcast.bos.domain.take_delivery.WayBill;

public interface WayBillIndexDao extends ElasticsearchRepository<WayBill, Integer>{

}
