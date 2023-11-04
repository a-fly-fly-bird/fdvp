package com.tzz.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ApiModel(value = "用户基金投资", description = "用户基金表")
@Table(name = "user_fund_invest", schema = "financial_system")
public class UserFundInvestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userid;

    @ApiModelProperty(value = "基金code")
    private String fundCode;

    @ApiModelProperty(value = "基金名称")
    private String fundName;

    @ApiModelProperty(value = "买入价格")
    private Double buyPrice;

    @ApiModelProperty(value = "持有数量")
    private Integer positionNumber;

    @ApiModelProperty(value = "购买日期")
    private Date buyDate;

    @Transient
    @ApiModelProperty(value = "基金当前价格")
    private Double currentPrice;

    @Transient
    @ApiModelProperty(value = "基金收益率")
    private Double rateOfReturn;


}
