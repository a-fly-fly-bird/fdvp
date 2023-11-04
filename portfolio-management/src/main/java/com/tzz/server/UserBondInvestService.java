package com.tzz.server;

import com.tzz.entity.BondsEntity;
import com.tzz.entity.UserBondInvestEntity;
import com.tzz.repo.UserBondInvestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserBondInvestService {
    @Autowired
    private UserBondInvestRepo userBondInvestRepo;

    @Autowired
    private  BondsService bondsService;


    // 根据用户id查询用户债券投资信息
    public List<UserBondInvestEntity> getUserBondInvestDetail(Integer userId, Date date){
        // 查询债券投资信息
        List<UserBondInvestEntity> userBondInvestList = userBondInvestRepo.findAllByUserid(userId);

        // 获取投资信息的债券code
        List<String> bondCodeList = new ArrayList<>();
        bondCodeList = userBondInvestList.stream()
                .map(s->s.getBondCode())
                .collect(Collectors.toList());
        // 查询股票当前价格
        List<BondsEntity> bondInfoList = bondsService.getBondsEntitiesByBondCodeAndDate(date, bondCodeList);
        Map<String, Double> currenctPriceMap = new HashMap<>();
        currenctPriceMap = bondInfoList.stream()
                .collect(Collectors
                        .toMap(BondsEntity::getBondCode, BondsEntity::getClose));

        // 设置当天的价格
        for (UserBondInvestEntity b: userBondInvestList) {
            b.setCurrentPrice(currenctPriceMap.get(b.getBondCode()));
            // 计算回报率
            b.setRateOfReturn( (currenctPriceMap.get(b.getBondCode())-b.getBuyPrice())/b.getBuyPrice() *100 );
        }
        userBondInvestList = userBondInvestList.stream()
                .filter(s->s.getPositionNumber()!=0)
                .collect(Collectors.toList());
        return userBondInvestList;
    }
}
