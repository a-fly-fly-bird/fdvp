package com.tzz.controller;

import com.tzz.entity.FundsEntity;
import com.tzz.entity.StocksEntity;
import com.tzz.server.StocksService;
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
@RequestMapping("/api/stocks")
public class StocksController {
    @Autowired
    private StocksService stocksService;

    @GetMapping("/stock_details/{stockCode}")
    @ResponseBody
    @ApiOperation("根据股票代码查询股票信息")
    public List<StocksEntity> getStockDetailsByStockCode(@PathVariable String stockCode){
        return stocksService.getStockDetailsByStockCode(stockCode);
    }

    @GetMapping("")
    @ApiOperation("查询股票信息-分页")
    public Page<StocksEntity> getStockPage(@RequestParam Integer pageNumber, @RequestParam Integer pageSize, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        System.out.printf(date.toString());
        Page<StocksEntity> stocksEntityPage = stocksService.getStocksPage(pageNumber, pageSize, date);
        if(!stocksEntityPage.isEmpty()){
            return stocksEntityPage;
        }
       return null;
    }


    @GetMapping("/stocks_price_change/{stockCode}")
    @ApiOperation("查询股票近一个月价格变化")
    public Double getStocksPriceChange(@PathVariable String stockCode,
                                      @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                      @RequestParam Integer period){
        // 创建一个Calendar实例
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -period);
        Date preDate = calendar.getTime();
        System.out.printf(preDate.toString());
        List<StocksEntity> stocksList = stocksService.getStocksInfoByPeriod(stockCode, preDate, date);

        if(!stocksList.isEmpty()) {
            Double todayPrice = stocksList.stream()
                    .filter(s->s.getClose()!=null)
                    .sorted(Comparator.comparing(StocksEntity::getDate).reversed())
                    .findFirst()
                    .get()
                    .getClose();
            Double prePrice  = stocksList.stream()
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


    @GetMapping("/stocks_price_stability/{stockCode}")
    @ApiOperation("查询股票近一个月价格稳定性")
    public Integer getStocksPriceStability(@PathVariable String stockCode,
                                          @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
                                          @RequestParam Integer period){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -period);
        Date preDate = calendar.getTime();
        System.out.printf(preDate.toString());
        List<StocksEntity> stocksList = stocksService.getStocksInfoByPeriod(stockCode, preDate, date);

        List<Double> stocksPriceList = stocksList.stream()
                .filter(s->s.getClose()!=null)
                .map(s->s.getClose())
                .collect(Collectors.toList());
        if(!stocksPriceList.isEmpty()){
            Double variance = VarianceCalculator.calculateVariance(stocksPriceList);
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
