package com.tzz.controller;

import com.tzz.entity.BondsEntity;
import com.tzz.entity.FundsEntity;
import com.tzz.server.BondsService;
import com.tzz.util.VarianceCalculator;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bonds")
public class BondsController {

    @Autowired
    private BondsService bondsService;

    @GetMapping("/bonds_details/{bondCode}")
    @ResponseBody
    @ApiOperation("根据债券代码查询基金信息")
    public List<BondsEntity> getBondDetailsByBondCode(@PathVariable String bondCode){
        return bondsService.getBondsDetailsByBondsCode(bondCode);

    }

    @GetMapping("")
    @ApiOperation("查询债券信息-分页")
    public Page<BondsEntity> getBondPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Page<BondsEntity> bondsEntityPage = bondsService.getBondsPage(pageNumber, pageSize, date);
        if(!bondsEntityPage.isEmpty()){
            return bondsEntityPage;
        }
        return null;

    }

    @GetMapping("/bonds_price_change/{bondCode}")
    @ApiOperation("查询债券近一个月价格变化")
    public Double getBondsPriceChange(@PathVariable String bondCode,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                      @RequestParam Integer period){
        // 创建一个Calendar实例
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -period);
        Date preDate = calendar.getTime();
        System.out.printf(preDate.toString());
        List<BondsEntity> bondsList = bondsService.getBondInfoByPeriod(bondCode, preDate, date);

        if(!bondsList.isEmpty()) {
            Double todayPrice = bondsList.stream()
                    .filter(s->s.getClose()!=null)
                    .sorted(Comparator.comparing(BondsEntity::getDate).reversed())
                    .findFirst()
                    .get()
                    .getClose();
            Double prePrice  = bondsList.stream()
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

    @GetMapping("/bonds_price_stability/{bondCode}")
    @ApiOperation("查询债券近一个月价格稳定性")
    public Integer getBondsPriceStability(@PathVariable String bondCode,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                      @RequestParam Integer period){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -period);
        Date preDate = calendar.getTime();
        System.out.printf(preDate.toString());
        List<BondsEntity> bondsList = bondsService.getBondInfoByPeriod(bondCode, preDate, date);

        List<Double> bondsPriceList = bondsList.stream()
                .filter(s->s.getClose()!=null)
                .map(s->s.getClose())
                .collect(Collectors.toList());
        if(!bondsPriceList.isEmpty()){
            Double variance = VarianceCalculator.calculateVariance(bondsPriceList);
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
