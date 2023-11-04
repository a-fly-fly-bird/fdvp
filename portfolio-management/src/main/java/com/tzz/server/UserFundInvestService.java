package com.tzz.server;

import com.tzz.entity.FundsEntity;
import com.tzz.entity.UserFundInvestEntity;
import com.tzz.repo.UserFundInvestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserFundInvestService {

    @Autowired
    private UserFundInvestRepo userFondInvestRepo;

    @Autowired
    private  FundsService fundsService;

    // 根据用户id查询用户基金投资信息
    public List<UserFundInvestEntity> getUserFundInvestDetail(Integer userId, Date date){
        // 查询基金投资信息
        List<UserFundInvestEntity> userFundInvestList = userFondInvestRepo.findAllByUserid(userId);

        // 获取投资信息的股票code
        List<String> fundCodeList = new ArrayList<>();
        fundCodeList = userFundInvestList.stream()
                .map(s->s.getFundCode())
                .collect(Collectors.toList());
        // 查询股票当前价格
        List<FundsEntity> fundInfoList = fundsService.getFundsEntitiesByFundCodeAndDate(date, fundCodeList);
        Map<String, Double> currenctPriceMap = new HashMap<>();
        currenctPriceMap = fundInfoList.stream()
                .collect(Collectors
                        .toMap(FundsEntity::getFundCode, FundsEntity::getClose));

        // 设置当天的价格
        for (UserFundInvestEntity f: userFundInvestList) {
            f.setCurrentPrice(currenctPriceMap.get(f.getFundCode()));
            // 计算回报率
            f.setRateOfReturn( (currenctPriceMap.get(f.getFundCode())-f.getBuyPrice())/f.getBuyPrice() *100 );
        }

        userFundInvestList = userFundInvestList.stream()
                .filter(s->s.getPositionNumber()!=0)
                .collect(Collectors.toList());
        return userFundInvestList;
    }
}
