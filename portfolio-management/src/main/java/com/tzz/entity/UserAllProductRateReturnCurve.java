package com.tzz.entity;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "用户产品收益曲线", description = "用户产品收益曲线")
public class UserAllProductRateReturnCurve {


    @ApiModelProperty(value = "时间")
    private Date date;

    @ApiModelProperty(value = "股票收益率")
    private Double stockRateOfReturn;

    @ApiModelProperty(value = "基金收益率")
    private Double fundRateOfReturn;

    @ApiModelProperty(value = "债券收益率")
    private Double bondRateOfReturn;

}
