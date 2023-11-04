package com.tzz.repo;

import com.tzz.entity.FundsEntity;
import com.tzz.entity.StocksEntity;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StocksRepo extends PagingAndSortingRepository<StocksEntity, Integer> {

    // 查询stocks表的所有数据，传入Pageable分页参数
    Page<StocksEntity> findALLByDate(Pageable pageable, Date date);

    // 根据股票带队吗查询股票历史信息
    List<StocksEntity> findALLByStockCodeOrderByDate(String stockCode);

    // 根据日期和股票代码查询股票信息
    @Query(value = "from StocksEntity  where  date=?1 and stockCode in ?2")
    List<StocksEntity> getStocksEntitiesByStockCodeAndDate(Date date, List<String> stockCodeList);

    @Query(value="select * from stocks where stocks.stock_code = ?1 and stocks.date = ?2", nativeQuery=true)
    StocksEntity findByStockCodeAndDate(String stockCode, String date);

    // 根据日期区间段和股票代码查询股票信息
    @Query(value = "select * from stocks where stocks.stock_code=?1 and stocks.date between ?2 and ?3", nativeQuery = true)
    List<StocksEntity> getStocksInfoByPeriod(String stockCode, Date date1, Date date2);



}
