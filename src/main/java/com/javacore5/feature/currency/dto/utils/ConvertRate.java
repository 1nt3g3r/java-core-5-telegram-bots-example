package com.javacore5.feature.currency.dto.utils;


import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.javacore5.feature.client.BankClient;
import com.javacore5.feature.model.Currency;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class ConvertRate {


    protected static JsonArray responseToJsonArray(String url) throws IOException, InterruptedException, URISyntaxException {
        BankClient bankClient = new BankClient();
        String responseString = bankClient.getRate(url);

        return JsonParser.parseString(responseString).getAsJsonArray();
    }

    public void convertResponseToCurrency
            (List<Currency> listCurrencies) throws InterruptedException, IOException, URISyntaxException {
    }


    public static List<Currency> allCurrencyToList() throws InterruptedException, IOException, URISyntaxException {
        List<Currency> listCurrencies = new ArrayList();

        PrivateBankConvertRate.convertResponseToCurrency(listCurrencies);
        new NationalBankConvertRate().convertResponseToCurrency(listCurrencies);
        new MonoBankConvertRate().convertResponseToCurrency(listCurrencies);
        return listCurrencies;
    }
}
