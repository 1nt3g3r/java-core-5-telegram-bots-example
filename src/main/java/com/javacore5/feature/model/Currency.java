package com.javacore5.feature.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class Currency {
    @Getter
    @Setter
    BigDecimal Sell;
    @Getter
    @Setter
    BigDecimal Buy;
    @Getter
    @Setter
    String currencyName;
    @Getter
    @Setter
    String bankName;
    @Getter
    private static final int CURRENCY_CODE_USD = 840;
    @Getter
    private static final int CURRENCY_CODE_UHN = 980;
    @Getter
    private static final int CURRENCY_CODE_EUR = 978;


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Currency{");
        sb.append("Sell=").append(Sell);
        sb.append(", Buy=").append(Buy);
        sb.append(", currencyName='").append(currencyName).append('\'');
        sb.append(", bankName='").append(bankName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
