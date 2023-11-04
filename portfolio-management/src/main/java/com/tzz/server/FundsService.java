package com.tzz.server;

import com.tzz.entity.BondsEntity;
import com.tzz.entity.FundsEntity;
import com.tzz.entity.StocksEntity;
import com.tzz.repo.FundsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class FundsService {

    @Autowired
    private FundsRepo fundsRepo;

   // 根据基金代码查询基金的历史数据
    public List<FundsEntity> getFundsDetailsByFundsCode(String fundCode) {
        if(!fundsRepo.findALLByFundCodeOrderByDate(fundCode).isEmpty()){
            System.out.println("查询成功");
            return fundsRepo.findALLByFundCodeOrderByDate(fundCode);
        }
        System.out.println("查询失败");
        return null;
    }

    // 查询当日所有基金数据
    public Page<FundsEntity> getFundsPage(Integer pageNumber, Integer pageSize, Date date) {
        Pageable pageRequest = BuildPageRequest(pageNumber, pageSize);
        // 从数据库查询所数据
        Page<FundsEntity> fundsPage = fundsRepo.findALLByDate(pageRequest, date);

        return fundsPage;
    }

    public static Pageable BuildPageRequest(Integer pageNumber, Integer pageSize) {
        return PageRequest.of(pageNumber-1, pageSize);
    }

    // 根据日期和基金代码查询基金信息
    public List<FundsEntity>  getFundsEntitiesByFundCodeAndDate(Date date,  List<String> fundCodeList) {

        return fundsRepo.getFundsEntitiesByFundCodeAndDate(date, fundCodeList);
    }

    // 根据日期区间段和基金代码查询基金信息
    public List<FundsEntity> getFundInfoByPeriod(String fundCode, Date date1, Date date2){
        return fundsRepo.getFundsInfoByPeriod(fundCode, date1, date2);
    }

}
