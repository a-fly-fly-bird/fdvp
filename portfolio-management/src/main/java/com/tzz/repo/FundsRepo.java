package com.tzz.repo;

import com.tzz.entity.BondsEntity;
import com.tzz.entity.FundsEntity;
import com.tzz.entity.StocksEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.Date;
import java.util.List;

@Repository
public interface FundsRepo extends PagingAndSortingRepository<FundsEntity, Integer> {

    // 查询Funds表的所有数据，传入Pageable分页参数
    Page<FundsEntity> findALLByDate(Pageable pageable, Date date);

    // 根据基金代码查询基金历史记录
    List<FundsEntity> findALLByFundCodeOrderByDate(String fundCode);

    // 根据日期和基金代码查询基金信息
    @Query(value = "from FundsEntity  where  date=?1 and fundCode in ?2")
    List<FundsEntity> getFundsEntitiesByFundCodeAndDate(Date date, List<String> fundCodeList);

    @Query(value="select * from funds where funds.fund_code = ?1 and funds.date = ?2", nativeQuery=true)
    FundsEntity findByFundCodeAndDate(String fundCode, String date);

    // 根据日期区间段和基金代码查询基金信息
    @Query(value = "select * from funds where funds.fund_code=?1 and funds.date between ?2 and ?3", nativeQuery = true)
    List<FundsEntity> getFundsInfoByPeriod(String fundCode, Date date1, Date date2);

}
