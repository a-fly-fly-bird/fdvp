package com.tzz.entity;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user", schema = "financial_system", catalog = "")
@ApiModel(value = "User对象", description = "用户表")
@Data
public class UserEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "id")
    private Integer id;

    @ApiModelProperty("用户名")
    private String name;
    @ApiModelProperty("用户密码")
    private String passWord;
    @ApiModelProperty("余额")
    private double balance = 1000000;

}
