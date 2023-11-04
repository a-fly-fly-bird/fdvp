package com.tzz.entity;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "交易类型", description = "交易类型")
public enum TransactionType {
    SELL,
    BUY;
}
