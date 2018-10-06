package cn.itcast.bos.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.bos.domain.take_delivery.WayBill;

public interface WayBillDao extends JpaRepository<WayBill, Integer>,JpaSpecificationExecutor<WayBill>{

	WayBill findBywayBillNum(String wayBillNum);

}
