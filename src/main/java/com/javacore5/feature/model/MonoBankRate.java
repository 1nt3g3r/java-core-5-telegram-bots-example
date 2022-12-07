package com.javacore5.feature.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


public class MonoBankRate {
    @Getter
    @Setter
    private int currencyCodeA;
    @Getter
    @Setter
    private int currencyCodeB;
    @Setter
    private int date;
    @Getter
    @Setter
    private BigDecimal sell;
    @Getter
    @Setter
    private BigDecimal buy;

}
