package com.tzz.server;

import com.tzz.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserTotalInvestService {

    @Autowired
    private UserStockInvestService userStockInvestService;

    @Autowired
    private UserFundInvestService userFundInvestService;

    @Autowired
    private UserBondInvestService userBondInvestService;

    @Autowired
    private StocksService stocksService;

    @Autowired
    private FundsService fundsService;

    @Autowired
    private BondsService bondsService;


    public List<UserTotalInvest> getUserTotalInvestDetail(@PathVariable Integer userId,
                                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){

        List<UserTotalInvest> userTotalInvestList = new ArrayList<>();

        // 股票投资情况
        List<UserStockInvestEntity> userStockInvestList = userStockInvestService.getUserStockInvestDetail(userId, date);
        // 基金投资情况
        List<UserFundInvestEntity> userFundInvestList = userFundInvestService.getUserFundInvestDetail(userId, date);
        // 债券投资情况
        List<UserBondInvestEntity> userBondInvestList = userBondInvestService.getUserBondInvestDetail(userId, date);

        // 计算股票的投资金额
        Double userStockInvestMoney = 0.0;
        for (UserStockInvestEntity s: userStockInvestList) {
            userStockInvestMoney += s.getBuyPrice()*s.getPositionNumber();
        }
        // 计算基金的投资金额
        Double userFundInvestMoney = 0.0;
        for (UserFundInvestEntity f: userFundInvestList) {
            userFundInvestMoney += f.getBuyPrice()*f.getPositionNumber();
        }
        // 计算债券的投资金额
        Double userBondInvestMoney = 0.0;
        for (UserBondInvestEntity b: userBondInvestList) {
            userBondInvestMoney += b.getBuyPrice()*b.getPositionNumber();
        }


       Double userStockRateOfReturn = 0.0;
        // 计算股票的总体回报率
        if(!userStockInvestMoney.equals(0.0)){
            for (UserStockInvestEntity s: userStockInvestList) {
                userStockRateOfReturn += (s.getBuyPrice()*s.getPositionNumber())/userStockInvestMoney *s.getRateOfReturn();
            }
        }
        Double userFundRateOfReturn = 0.0;
        // 计算基金的总体回报率
        if(!userFundInvestMoney.equals(0.0)){
            for (UserFundInvestEntity f: userFundInvestList) {
                userFundRateOfReturn += (f.getBuyPrice()*f.getPositionNumber())/userFundInvestMoney *f.getRateOfReturn();
            }
        }

        Double userBondRateOfReturn = 0.0;
        // 计算债券的总体回报率
        if(!userBondInvestMoney.equals(0.0)){
            for (UserBondInvestEntity b: userBondInvestList) {
                userBondRateOfReturn += (b.getBuyPrice()*b.getPositionNumber())/userBondInvestMoney *b.getRateOfReturn();
            }
        }

        // 总投资资金
        Double userTotalMoney = userStockInvestMoney+userFundInvestMoney+userBondInvestMoney;

        // 股票投资占比
        Double userStockRate = 0.0;
        userStockRate = userStockInvestMoney/userTotalMoney*100;

        // 基金投资占比
        Double userBondRate = 0.0;
        userBondRate = userBondInvestMoney/userTotalMoney*100;

        // 债券投资占比
        Double userFundRate = 0.0;
        userFundRate = userFundInvestMoney/userTotalMoney*100;

        UserTotalInvest userTotalInvestStock = new UserTotalInvest();
        userTotalInvestStock.setUserId(userId);
        userTotalInvestStock.setProportion(userStockRate);
        userTotalInvestStock.setProductName(ProductType.STOCK);
        userTotalInvestStock.setReturnOfRate(userStockRateOfReturn);
        userTotalInvestStock.setSumMoney(userStockInvestMoney);
        userTotalInvestList.add(userTotalInvestStock);

        UserTotalInvest userTotalInvestFund = new UserTotalInvest();
        userTotalInvestFund.setUserId(userId);
        userTotalInvestFund.setProportion(userFundRate);
        userTotalInvestFund.setProductName(ProductType.FUND);
        userTotalInvestFund.setReturnOfRate(userFundRateOfReturn);
        userTotalInvestFund.setSumMoney(userFundInvestMoney);
        userTotalInvestList.add(userTotalInvestFund);

        UserTotalInvest userTotalInvestBond = new UserTotalInvest();
        userTotalInvestBond.setUserId(userId);
        userTotalInvestBond.setProportion(userBondRate);
        userTotalInvestBond.setProductName(ProductType.BOND);
        userTotalInvestBond.setReturnOfRate(userBondRateOfReturn);
        userTotalInvestBond.setSumMoney(userBondInvestMoney);
        userTotalInvestList.add(userTotalInvestBond);


        return userTotalInvestList;
    }


    // 查询用户总体收益率
    public Double getUserTotalRateOfReturn(@PathVariable Integer userId,
                                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<UserTotalInvest> userTotalInvestList = getUserTotalInvestDetail(userId, date);

        Double totalInvestMoney =0.0;
        Double totalRateofReturn = 0.0;
        if(!userTotalInvestList.isEmpty()){
            for (UserTotalInvest e:userTotalInvestList) {
                totalInvestMoney +=e.getSumMoney();
            }

            for (UserTotalInvest e:userTotalInvestList) {
                totalRateofReturn += (e.getSumMoney()/totalInvestMoney) * e.getReturnOfRate();
            }
        }

        return totalRateofReturn;

    }

    // 查询用户(股票/基金/债券)收益曲线
    public List<UserAllProductRateReturnCurve> getAllProductRateOfReturnCurve(@PathVariable Integer userId,
                                                                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        List<UserAllProductRateReturnCurve> userAllProductRateReturnCurveList = new ArrayList<>();

        // 查询用户投资股票代码
        List<UserStockInvestEntity> userStockInvestList = userStockInvestService.getUserStockInvestDetail(userId, date);
        List<String> stockCode = userStockInvestList.stream().
                map(UserStockInvestEntity::getStockCode)
                .collect(Collectors.toList());
        // 计算股票购入价
        Map<String, Double> stockBuyPrice = new HashMap<>();
        for (UserStockInvestEntity usi: userStockInvestList) {
            stockBuyPrice.put(usi.getStockCode(), usi.getBuyPrice());
        }
        // 计算股票的投资金额
        Double userStockInvestMoney = 0.0;
        for (UserStockInvestEntity s: userStockInvestList) {
            userStockInvestMoney += s.getBuyPrice()*s.getPositionNumber();
        }
        // 存储每个股票回报率的占比权重
        Map<String, Double> stockRateWeight = new HashMap<>();
        // 计算股票的总体回报率权重
        if(!userStockInvestMoney.equals(0.0)){
            for (UserStockInvestEntity s: userStockInvestList) {
                stockRateWeight.put(s.getStockCode(), (s.getBuyPrice()*s.getPositionNumber())/userStockInvestMoney);
            }
        }


      //########################################
        // 查询用户投资基金代码
        List<UserFundInvestEntity> userFundInvestList = userFundInvestService.getUserFundInvestDetail(userId, date);
        List<String> fundCode = userFundInvestList.stream().
                map(UserFundInvestEntity::getFundCode)
                .collect(Collectors.toList());
        // 计算基金购入价
        Map<String, Double> fundBuyPrice = new HashMap<>();
        for (UserFundInvestEntity ufi: userFundInvestList) {
            fundBuyPrice.put(ufi.getFundCode(), ufi.getBuyPrice());
        }
        // 计算基金的投资金额
        Double userFundInvestMoney = 0.0;
        for (UserFundInvestEntity f: userFundInvestList) {
            userFundInvestMoney += f.getBuyPrice()*f.getPositionNumber();
        }
        // 存储每个基金回报率的占比权重
        Map<String, Double> fundRateWeight = new HashMap<>();
        // 计算基金的总体回报率权重
        if(!userFundInvestMoney.equals(0.0)){
            for (UserFundInvestEntity f: userFundInvestList) {
                fundRateWeight.put(f.getFundCode(), (f.getBuyPrice()*f.getPositionNumber())/userFundInvestMoney);
            }
        }

        //########################################
        // 查询用户投资债券代码
        List<UserBondInvestEntity> userBondInvestList = userBondInvestService.getUserBondInvestDetail(userId, date);
        List<String> bondCode = userBondInvestList.stream().
                map(UserBondInvestEntity::getBondCode)
                .collect(Collectors.toList());
        // 计算债券购入价
        Map<String, Double> bondBuyPrice = new HashMap<>();
        for (UserBondInvestEntity ubi: userBondInvestList) {
            bondBuyPrice.put(ubi.getBondCode(), ubi.getBuyPrice());
        }
        // 计算债券的投资金额
        Double userBondInvestMoney = 0.0;
        for (UserBondInvestEntity b: userBondInvestList) {
            userBondInvestMoney += b.getBuyPrice()*b.getPositionNumber();
        }
        // 存储每个债券回报率的占比权重
        Map<String, Double> bondRateWeight = new HashMap<>();
        // 计算债券的总体回报率权重
        if(!userBondInvestMoney.equals(0.0)){
            for (UserBondInvestEntity b: userBondInvestList) {
                bondRateWeight.put(b.getBondCode(), (b.getBuyPrice()*b.getPositionNumber())/userBondInvestMoney);
            }
        }



        // 查询某段时间内的股票信息
        int timePeriod = 10;


        Calendar c = Calendar.getInstance();

        for(int i=timePeriod; i>=0; i--){
            c.setTime(date);
            c.add(Calendar.DATE, -i);
            Date newDate = c.getTime();
            System.out.printf(newDate.toString());
            List<StocksEntity> stockList = stocksService.getStockInfoByStockCodeAndDate(newDate, stockCode);
            List<FundsEntity> fundList = fundsService.getFundsEntitiesByFundCodeAndDate(newDate, fundCode);
            List<BondsEntity> bondList = bondsService.getBondsEntitiesByBondCodeAndDate(newDate, bondCode);
            UserAllProductRateReturnCurve ar =new UserAllProductRateReturnCurve();
            ar.setDate(newDate);
            if(!stockList.isEmpty()){
                Double tempStockRate = 0.0;
                for (StocksEntity s: stockList) {
                    tempStockRate+= (s.getClose()-stockBuyPrice.get(s.getStockCode()))/userStockInvestMoney*stockRateWeight.get(s.getStockCode());
                }
                ar.setStockRateOfReturn(tempStockRate*100);
            }

            if(!fundList.isEmpty()){
                Double tempFundRate = 0.0;
                for (FundsEntity f: fundList) {
                    tempFundRate+= (f.getClose()-fundBuyPrice.get(f.getFundCode()))/userFundInvestMoney*fundRateWeight.get(f.getFundCode());
                }
                ar.setFundRateOfReturn(tempFundRate*100);
            }
            if(!bondList.isEmpty()){
                Double tempBondRate = 0.0;
                Boolean computeTag = false;
                for (BondsEntity b: bondList) {
                    if(b.getClose()!=null){
                        tempBondRate+= (b.getClose()-bondBuyPrice.get(b.getBondCode()))/userBondInvestMoney*bondRateWeight.get(b.getBondCode());
                        computeTag = true;
                    }
                }
                if(computeTag){
                    ar.setBondRateOfReturn(tempBondRate *100);
                }


            }

            userAllProductRateReturnCurveList.add(ar);

        }

        return userAllProductRateReturnCurveList;
    }

}
