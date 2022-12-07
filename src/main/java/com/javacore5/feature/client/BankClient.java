package com.javacore5.feature.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BankClient {

    private final static HttpClient client = HttpClient.newHttpClient();
    HttpRequest request;
    HttpResponse<String> response;

    public String getRate(String url) throws IOException, InterruptedException, URISyntaxException {
        URI uri = new URI(url);
         request = HttpRequest.newBuilder()
                .uri(uri)
                .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
