package org.example.Tests;

import org.example.API.Parser;
import org.example.API.cbrCalls;
import org.example.DTO.CurrencyInfo;
import org.example.Exceptions.CurrencyNotFound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class cbrCallsTest {

    @Test
    void getInfo_Correct() {
        LocalDate futureDate = LocalDate.of(2023, 7, 20);
        var info = cbrCalls.getInfo("USD", futureDate);
        assertNotNull(info.getValue());
        assertEquals(91.2046, info.getValue(), 0.001);
    }

    @Test
    void getInfo_FutureDate_ThrowsRuntimeException() {
        LocalDate futureDate = LocalDate.now().plusDays(1);
        Assertions.assertThrows(RuntimeException.class, () -> {
            cbrCalls.getInfo("USD", futureDate);
        });
    }

    @Test
    void getInfo_CurrencyNotFound_ThrowsCurrencyNotFoundException() {
        LocalDate pastDate = LocalDate.of(2020, 1, 1);
        Assertions.assertThrows(CurrencyNotFound.class, () -> {
            cbrCalls.getInfo("XYZ", pastDate);
        });
    }


}
