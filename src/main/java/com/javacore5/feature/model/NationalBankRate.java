package com.javacore5.feature.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class NationalBankRate {
    @Getter
    @Setter
    private int currencyDigitCode;
    @Setter
    private String name;
    @Getter
    @Setter
    private BigDecimal rate;
    @Getter
    @Setter
    private String currencySymbolCode;
    @Setter
    private String date;

}
