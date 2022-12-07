package com.javacore5.feature.currency.dto.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.javacore5.feature.model.Currency;
import com.javacore5.feature.model.PrivateBankRate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class PrivateBankConvertRate {

    public static void convertResponseToCurrency
            (List<Currency> listCurrencies) throws InterruptedException, IOException, URISyntaxException {
        JsonArray jsonArray = ConvertRate.responseToJsonArray
                ("https://api.privatbank.ua/p24api/pubinfo?exchange&coursid=5");
        int count = 0;
        Currency currency = new Currency();
        while (count < jsonArray.size()) {
            JsonObject jsonObject = jsonArray.get(count++).getAsJsonObject();
            PrivateBankRate privat = createPrivateBankRateFromJson(jsonObject);
            listCurrencies.add(setCurrency(privat, "PrivatBank"));

    }
}

    protected static Currency setCurrency(PrivateBankRate bank, String bankName) {
        Currency currency = new Currency();
        currency.setBuy(bank.getBuy());
        currency.setSell(bank.getSell());
        currency.setCurrencyName(bank.getCurrency());
        currency.setBankName(bankName);
        return currency;
    }

    private static PrivateBankRate createPrivateBankRateFromJson(JsonObject jsonObject) {
        PrivateBankRate privat = new PrivateBankRate();
        privat.setCurrency(jsonObject.get("ccy").getAsString());
        privat.setBaseCurrency(jsonObject.get("base_ccy").getAsString());
        privat.setBuy(jsonObject.get("buy").getAsBigDecimal());
        privat.setSell(jsonObject.get("sale").getAsBigDecimal());


        return privat;
    }
    }
