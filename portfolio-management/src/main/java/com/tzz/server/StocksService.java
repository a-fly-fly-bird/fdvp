package com.tzz.server;

import com.tzz.entity.FundsEntity;
import com.tzz.entity.StocksEntity;
import com.tzz.repo.StocksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StocksService {
    @Autowired
    private StocksRepo stocksRepo;


    // 根据股票代码查询股票的历史信息
    public List<StocksEntity> getStockDetailsByStockCode(String stockCode) {
        if(!stocksRepo.findALLByStockCodeOrderByDate(stockCode).isEmpty()){
            System.out.println("查询成功");
            return stocksRepo.findALLByStockCodeOrderByDate(stockCode);
        }
        System.out.println("查询失败");
        return null;
    }

    // 查询所有股票数据
    public Page<StocksEntity> getStocksPage(Integer pageNumber, Integer pageSize, Date date) {
        Pageable pageRequest = BuildPageRequest(pageNumber, pageSize);
        // 从数据库查询所数据
        Page<StocksEntity> stocksPage = stocksRepo.findALLByDate(pageRequest, date);

        return stocksPage;
    }

    public static Pageable BuildPageRequest(Integer pageNumber, Integer pageSize) {
        return PageRequest.of(pageNumber-1, pageSize);
    }

    // 根据日期和股票代码查询股票信息
    public List<StocksEntity>  getStockInfoByStockCodeAndDate(Date date, List<String> stockCodeList) {

        return stocksRepo.getStocksEntitiesByStockCodeAndDate(date, stockCodeList);
    }

    // 根据日期区间段和基金代码查询基金信息
    public List<StocksEntity> getStocksInfoByPeriod(String stockCode, Date date1, Date date2){
        return stocksRepo.getStocksInfoByPeriod(stockCode, date1, date2);
    }



}
