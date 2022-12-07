package com.javacore5.feature.currency.dto.utils;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.javacore5.feature.model.Currency;
import com.javacore5.feature.model.NationalBankRate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

public class NationalBankConvertRate extends ConvertRate {

    public void convertResponseToCurrency
            (List<Currency> listCurrencies)
            throws InterruptedException, IOException, URISyntaxException {
        JsonArray jsonArray = responseToJsonArray
                ("https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json");
        int count = 0;
        Currency currency = new Currency();
        while (count < jsonArray.size()) {
            JsonObject jsonObject = jsonArray.get(count++).getAsJsonObject();
            NationalBankRate nb = createNationalBankRateFromJson(jsonObject);
            if (nb.getCurrencyDigitCode() == Currency.getCURRENCY_CODE_USD()) {
                listCurrencies.add(setCurrency(nb, "NationalBank"));
            } else if (nb.getCurrencyDigitCode() == Currency.getCURRENCY_CODE_EUR()) {
                listCurrencies.add(setCurrency(nb, "NationalBank"));
            }
        }
    }

    protected static Currency setCurrency(NationalBankRate bank, String bankName) {
        Currency currency = new Currency();
        currency.setBuy(bank.getRate());
        currency.setCurrencyName(bank.getCurrencySymbolCode());
        currency.setBankName(bankName);
        return currency;
    }


    private static NationalBankRate createNationalBankRateFromJson(JsonObject jsonObject) {
        NationalBankRate nb = new NationalBankRate();
        nb.setCurrencyDigitCode(jsonObject.get("r030").getAsInt());
        nb.setName(jsonObject.get("txt").getAsString());
        nb.setRate(jsonObject.get("rate").getAsBigDecimal());
        nb.setCurrencySymbolCode(jsonObject.get("cc").getAsString());
        nb.setDate(jsonObject.get("exchangedate").getAsString());

        return nb;
    }

}

