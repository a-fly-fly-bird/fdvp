package com.tzz.server;


import com.tzz.entity.StocksEntity;
import com.tzz.entity.UserStockInvestEntity;
import com.tzz.repo.UserStockInvestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserStockInvestService {

    @Autowired
    private UserStockInvestRepo userStockInvestRepo;

    @Autowired
    private StocksService stocksService;


    // 根据用户id查询用户股票投资信息
    public List<UserStockInvestEntity> getUserStockInvestDetail(Integer userId, Date date){
        // 查询投资信息
        List<UserStockInvestEntity> userStockInvestList = userStockInvestRepo.findAllByUserid(userId);

        // 获取投资信息的股票code
        List<String> stockCodeList = new ArrayList<>();
        stockCodeList = userStockInvestList.stream()
                .map(s->s.getStockCode())
                .collect(Collectors.toList());
        // 查询股票当前价格
        List<StocksEntity> stockInfoList = stocksService.getStockInfoByStockCodeAndDate(date, stockCodeList);
        Map<String, Double> currenctPriceMap = new HashMap<>();
        currenctPriceMap = stockInfoList.stream()
                .collect(Collectors
                        .toMap(StocksEntity::getStockCode, StocksEntity::getClose));

        // 设置当天的价格
        for (UserStockInvestEntity s: userStockInvestList) {
            s.setCurrentPrice(currenctPriceMap.get(s.getStockCode()));
            // 计算回报率
            s.setRateOfReturn( (currenctPriceMap.get(s.getStockCode())-s.getBuyPrice())/s.getBuyPrice() *100 );
        }
        userStockInvestList = userStockInvestList.stream()
                .filter(s->s.getPositionNumber()!=0)
                .collect(Collectors.toList());
        return userStockInvestList;
    }
}
