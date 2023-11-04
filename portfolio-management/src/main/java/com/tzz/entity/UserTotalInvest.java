package com.tzz.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "用户总体投资情况", description = "用户总体投资情况")
public class UserTotalInvest {

    @ApiModelProperty(value = "用户id")
    private  Integer userId;

    @ApiModelProperty(value = "产品名称(股票/基金/债券)")
    private ProductType productName;

    @ApiModelProperty(value = "该项投资在总投资中的占比")
    private Double proportion;

    @ApiModelProperty(value = "该项投资的回报率")
    private Double returnOfRate;

    @ApiModelProperty(value = "该项投资的投资总额")
    private Double sumMoney;

}
