package com.tzz.entity;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "产品类型", description = "产品类型")
public enum ProductType {
    STOCK,
    FUND,
    BOND;
}
