package com.tzz.controller;

import com.tzz.entity.BondsEntity;
import com.tzz.entity.FundsEntity;
import com.tzz.entity.StocksEntity;
import com.tzz.server.FundsService;
import com.tzz.util.VarianceCalculator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/funds")
public class FundsController {
    @Autowired
    private FundsService fundsService;

    @GetMapping("/funds_details/{fundCode}")
    @ResponseBody
    @ApiOperation("根据基金代码查询基金信息")
    public List<FundsEntity> getFundDetailsByFundsCode(@PathVariable String fundCode){

        return fundsService.getFundsDetailsByFundsCode(fundCode);

    }

    @GetMapping("")
    @ApiOperation("查询基金信息-分页")
    public Page<FundsEntity> getFoundPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Page<FundsEntity> fundsEntityPage = fundsService.getFundsPage(pageNumber, pageSize, date);
        if(!fundsEntityPage.isEmpty()){
            return fundsEntityPage;
        }
        return null;

    }

    @GetMapping("/funds_price_change/{fundCode}")
    @ApiOperation("查询基金近一个月价格变化")
    public Double getFundsPriceChange(@PathVariable String fundCode,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                      @RequestParam Integer period){
        // 创建一个Calendar实例
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -period);
        Date preDate = calendar.getTime();
        System.out.printf(preDate.toString());
        List<FundsEntity> fundsList = fundsService.getFundInfoByPeriod(fundCode, preDate, date);

        if(!fundsList.isEmpty()) {
            Double todayPrice = fundsList.stream()
                    .filter(s->s.getClose()!=null)
                    .sorted(Comparator.comparing(FundsEntity::getDate).reversed())
                    .findFirst()
                    .get()
                    .getClose();
            Double prePrice  = fundsList.stream()
                    .filter(s->s.getClose()!=null)
                    .findFirst()
                    .get()
                    .getClose();

            return (todayPrice-prePrice)/prePrice *100;
        }
        else {
            return null;
        }
    }

    @GetMapping("/funds_price_stability/{fundCode}")
    @ApiOperation("查询基金近一个月价格稳定性")
    public Integer getFundsPriceStability(@PathVariable String fundCode,
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                          @RequestParam Integer period){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -period);
        Date preDate = calendar.getTime();
        System.out.printf(preDate.toString());
        List<FundsEntity> fundsList = fundsService.getFundInfoByPeriod(fundCode, preDate, date);

        List<Double> fundsPriceList = fundsList.stream()
                .filter(s->s.getClose()!=null)
                .map(s->s.getClose())
                .collect(Collectors.toList());
        if(!fundsPriceList.isEmpty()){
            Double variance = VarianceCalculator.calculateVariance(fundsPriceList);
            System.out.printf(variance.toString());
            if(variance<=1)
                return 1;
            if(variance>1 && variance<=5)
                return 2;
            if(variance>5 && variance<=10)
                return 3;
            if(variance>10)
                return 4;
        }
        return null;


    }
}
