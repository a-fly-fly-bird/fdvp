package com.tzz.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ApiModel(value = "用户交易表", description = "用户交易表")
@Table(name = "user_transaction", schema = "financial_system")
public class UserTransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "交易id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value = "股票/基金/债券Code")
    private String productCode;

    @ApiModelProperty(value = "产品种类")
    private ProductType productType;

    @ApiModelProperty(value = "交易数量")
    private Integer transactionAmount;

    @ApiModelProperty(value = "投资金额")
    private Double transactionPrice;

    @ApiModelProperty(value = "交易类型(买入/卖出)")
    private TransactionType transactionType;

    @ApiModelProperty(value = "交易日期")
    private Date transactionDate;

    @ApiModelProperty(value = "余额")
    private double balance;




}
