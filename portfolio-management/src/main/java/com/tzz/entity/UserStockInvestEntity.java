package com.tzz.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ApiModel(value = "用户股票投资", description = "用户股票投资表")
@Table(name = "user_stock_invest", schema = "financial_system")
public class UserStockInvestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userid;

    @ApiModelProperty(value = "股票code")
    private String stockCode;

    @ApiModelProperty(value = "股票名称")
    private String stockName;

    @ApiModelProperty(value = "买入价格")
    private Double buyPrice;

    @ApiModelProperty(value = "持有数量")
    private Integer positionNumber;

    @ApiModelProperty(value = "购买日期")
    private Date buyDate;

    @Transient
    @ApiModelProperty(value = "股票当前价格")
    private Double currentPrice;

    @Transient
    @ApiModelProperty(value = "当前收益率")
    private Double rateOfReturn;


}
