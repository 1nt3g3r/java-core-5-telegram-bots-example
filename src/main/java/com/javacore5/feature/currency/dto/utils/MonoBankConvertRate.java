package com.javacore5.feature.currency.dto.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.javacore5.feature.model.Currency;
import com.javacore5.feature.model.MonoBankRate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;


public class MonoBankConvertRate extends ConvertRate {
    @Override
    public void convertResponseToCurrency(List<Currency> listCurrencies)
            throws InterruptedException, IOException, URISyntaxException {
        JsonArray jsonArray = responseToJsonArray
                ("https://api.monobank.ua/bank/currency");
        int count = 0;
        while (count < jsonArray.size() - 1) {
            JsonObject jsonObject = jsonArray.get(count++).getAsJsonObject();
            MonoBankRate mono = createMonoBankRateFromJson(jsonObject);
            if (mono.getCurrencyCodeA() == Currency.getCURRENCY_CODE_USD()
                    && mono.getCurrencyCodeB() == Currency.getCURRENCY_CODE_UHN()) {
                listCurrencies.add(setCurrency(mono, "USD", "MonoBank"));
            } else if (mono.getCurrencyCodeA() == Currency.getCURRENCY_CODE_EUR()
                    && mono.getCurrencyCodeB() == Currency.getCURRENCY_CODE_UHN()) {
                listCurrencies.add(setCurrency(mono, "EUR", "MonoBank"));
                return;
            }
        }
    }


    protected static Currency setCurrency(MonoBankRate bank, String currencyName, String bankName) {
        Currency currency = new Currency();
        currency.setBuy(bank.getBuy());
        currency.setSell(bank.getSell());
        currency.setCurrencyName(currencyName);
        currency.setBankName(bankName);
        return currency;
    }

    private static MonoBankRate createMonoBankRateFromJson(JsonObject jsonObject) {
        MonoBankRate mono = new MonoBankRate();
        mono.setCurrencyCodeA(jsonObject.get("currencyCodeA").getAsInt());
        mono.setCurrencyCodeB(jsonObject.get("currencyCodeB").getAsInt());
        mono.setDate(jsonObject.get("date").getAsInt());
        mono.setBuy(jsonObject.get("rateBuy").getAsBigDecimal());
        mono.setSell(jsonObject.get("rateSell").getAsBigDecimal());

        return mono;
    }

}
