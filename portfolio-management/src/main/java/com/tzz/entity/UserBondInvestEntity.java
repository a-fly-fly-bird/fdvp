package com.tzz.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@ApiModel(value = "用户债券投资", description = "债券基金表")
@Table(name = "user_bond_invest", schema = "financial_system")
public class UserBondInvestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty(value = "用户id")
    private Integer userid;

    @ApiModelProperty(value = "债券code")
    private String bondCode;

    @ApiModelProperty(value = "债券名称")
    private String bondName;

    @ApiModelProperty(value = "买入价格")
    private Double buyPrice;

    @ApiModelProperty(value = "持有数量")
    private Integer positionNumber;

    @ApiModelProperty(value = "购买日期")
    private Date buyDate;

    @Transient
    @ApiModelProperty(value = "债券当前价格")
    private Double currentPrice;

    @Transient
    @ApiModelProperty(value = "债券收益率")
    private Double rateOfReturn;

}
