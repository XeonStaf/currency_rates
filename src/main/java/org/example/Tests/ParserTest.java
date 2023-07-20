package org.example.Tests;

import org.example.API.Parser;
import org.example.DTO.CurrencyInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ParserTest {

    private final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<ValCurs Date=\"20.07.2023\" name=\"Foreign Currency Market\">\n" +
            "    <Valute ID=\"R01010\">\n" +
            "        <NumCode>036</NumCode>\n" +
            "        <CharCode>AUD</CharCode>\n" +
            "        <Nominal>1</Nominal>\n" +
            "        <Name>Австралийский доллар</Name>\n" +
            "        <Value>61,9644</Value>\n" +
            "    </Valute>\n" +
            "    <!-- other Valute elements -->\n" +
            "</ValCurs>";

    @Test
    void findCurrencyIdByName_ValidCurrency_ReturnsCurrencyInfo() {
        String currencyCode = "AUD";

        CurrencyInfo expectedCurrencyInfo = CurrencyInfo.builder()
                .id("R01010")
                .numCode("036")
                .charCode("AUD")
                .nominal("1")
                .name("Австралийский доллар")
                .value(61.9644)
                .build();

        CurrencyInfo currencyInfo = Parser.findCurrencyIdByName(xml, currencyCode);

        Assertions.assertEquals(expectedCurrencyInfo, currencyInfo);
    }

    @Test
    void findCurrencyIdByName_InvalidCurrency_ReturnsNull() {
        String currencyCode = "EUR";

        CurrencyInfo currencyInfo = Parser.findCurrencyIdByName(xml, currencyCode);

        Assertions.assertNull(currencyInfo);
    }
}
