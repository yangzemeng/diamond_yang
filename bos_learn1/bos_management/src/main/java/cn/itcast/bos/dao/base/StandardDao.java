package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cn.itcast.bos.domain.base.Standard;

@Repository
public interface StandardDao extends JpaRepository<Standard, Integer>{
   //List<Standard> findAll();
   //public List<Standard> findByName(String name);
  /* @Query(value="from Standard where name=?",nativeQuery=false)
   public List<Standard> quer3(String name);*/
}
