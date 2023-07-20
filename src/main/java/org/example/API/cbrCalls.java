package org.example.API;

import org.example.DTO.CurrencyInfo;
import org.example.Exceptions.CurrencyNotFound;
import org.example.Exceptions.FutureDayException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class cbrCalls {
    private static final String BaseURL = "https://www.cbr.ru/scripts/";

    public static CurrencyInfo getInfo(String currencyNameISO, LocalDate targetDate) {
        if (targetDate.isAfter(LocalDate.now())) {
            throw new FutureDayException();
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String formattedDate = targetDate.format(formatter);
        String apiUrlWithDate = BaseURL + "XML_daily.asp/?date_req=" + formattedDate;

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(apiUrlWithDate)).GET().build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String xmlResponse = response.body();

            CurrencyInfo currencyInfo = Parser.findCurrencyIdByName(xmlResponse, currencyNameISO);

            if (currencyInfo != null) {
                return currencyInfo;
            } else {
                throw new CurrencyNotFound();
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        throw new CurrencyNotFound();

    }
}
