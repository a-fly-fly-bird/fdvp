package com.tzz.controller;

import com.tzz.entity.*;
import com.tzz.server.UserBondInvestService;
import com.tzz.server.UserFundInvestService;
import com.tzz.server.UserStockInvestService;
import com.tzz.server.UserTransactionService;
import com.tzz.server.UserTotalInvestService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user/invest")
public class UserInvestController {

    @Autowired
    private UserStockInvestService userStockInvestService;

    @Autowired
    private UserFundInvestService userFundInvestService;

    @Autowired
    private UserBondInvestService userBondInvestService;

    @Autowired
    private UserTransactionService userTransactionService;

    @Autowired
    private UserTotalInvestService userTotalInvestService;

    @GetMapping("/stock/{userId}")
    @ApiOperation("根据用户id查询用户的股票投资情况")
    public List<UserStockInvestEntity> getUserStockInvestDetail(@PathVariable Integer userId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){

        List<UserStockInvestEntity> userStockInvestEntityList = userStockInvestService.getUserStockInvestDetail(userId, date);

        if(!userStockInvestEntityList.isEmpty()){
            return userStockInvestEntityList;
        }
        else
            return null;
    }


    @GetMapping("/fund/{userId}")
    @ApiOperation("根据用户id查询用户的基金投资情况")
    public List<UserFundInvestEntity> getUserFundInvestDetail(@PathVariable Integer userId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){

        List<UserFundInvestEntity> userFondInvestEntityList = userFundInvestService.getUserFundInvestDetail(userId, date);

        if(!userFondInvestEntityList.isEmpty()){
            return userFondInvestEntityList;
        }
        else
            return null;
    }

    @GetMapping("/bond/{userId}")
    @ApiOperation("根据用户id查询用户的债券投资情况")
    public List<UserBondInvestEntity> getUserBondInvestDetail(@PathVariable Integer userId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){

        List<UserBondInvestEntity> userBondInvestEntityList = userBondInvestService.getUserBondInvestDetail(userId, date);

        if(!userBondInvestEntityList.isEmpty()){
            return userBondInvestEntityList;
        }
        else
            return null;
    }

    @GetMapping("/total/{userId}")
    @ApiOperation("根据用户id查询用户的总体投资情况")
    public List<UserTotalInvest> getUserTotalInvestDetail(@PathVariable Integer userId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        List<UserTotalInvest> userTotalInvestList = userTotalInvestService.getUserTotalInvestDetail(userId, date);
        if(!userTotalInvestList.isEmpty()){
            return userTotalInvestList;
        }
        else
            return null;
    }

    @GetMapping("/reveneue_curve/{userId}")
    @ApiOperation("根据用户id查询用户的总体收益曲线")
    public List<UserAllProductRateReturnCurve> getUserInvestReveneueCurve(@PathVariable Integer userId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        // 根据具体情况设计类，都是根据现有数据查询，不涉及数据库表
        List<UserAllProductRateReturnCurve> userAllProductRateReturnCurveList = userTotalInvestService.getAllProductRateOfReturnCurve(userId, date);
        if(!userAllProductRateReturnCurveList.isEmpty()){
            return userAllProductRateReturnCurveList;
        }
        else {
            return null;
        }

    }

    @GetMapping("/total_rate_return/{userId}")
    @ApiOperation("用户投资总体回报率")
    public Double getUserTotalRateOfReturn(@PathVariable Integer userId, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){

        return userTotalInvestService.getUserTotalRateOfReturn(userId, date);
    }

    @PostMapping("/buyStock")
    @ApiOperation("用户买入股票")
    public UserTransactionEntity buyStock(@RequestBody UserTransactionEntity userTransactionEntity){
        return userTransactionService.addTransaction(userTransactionEntity);

    }

    @PostMapping("/sellStock")
    @ApiOperation("用户出售股票")
    public UserTransactionEntity sellStock(@RequestBody UserTransactionEntity userTransactionEntity){
        return userTransactionService.sellTransaction(userTransactionEntity);

    }

    @PostMapping("/buyFund")
    @ApiOperation("用户买入基金")
    public UserTransactionEntity buyFund(@RequestBody UserTransactionEntity userTransactionEntity){
        return userTransactionService.addTransaction(userTransactionEntity);
    }

    @PostMapping("/sellFund")
    @ApiOperation("用户出售基金")
    public UserTransactionEntity sellFund(@RequestBody UserTransactionEntity userTransactionEntity){
        return userTransactionService.sellTransaction(userTransactionEntity);

    }

    @PostMapping("/buyBond")
    @ApiOperation("用户买入债券")
    public UserTransactionEntity bugBond(@RequestBody UserTransactionEntity userTransactionEntity){
        return userTransactionService.addTransaction(userTransactionEntity);
    }

    @PostMapping("/sellBond")
    @ApiOperation("用户出售债券")
    public UserTransactionEntity sellBond(@RequestBody UserTransactionEntity userTransactionEntity){
        return userTransactionService.sellTransaction(userTransactionEntity);
    }


    @GetMapping("/userTransaction/{userId}")
    @ApiOperation("根据用户id查询用户的金融交易情况")
    public List<UserTransactionEntity> getUserTransactionDetail(@PathVariable Integer userId){
        List<UserTransactionEntity> userTransactionList = userTransactionService.getUserTransactionDetail(userId);
        if(!userTransactionList.isEmpty()){
            return userTransactionList;
        }
        else
            return null;
    }








}
