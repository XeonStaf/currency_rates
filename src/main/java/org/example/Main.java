package org.example;

import org.example.API.cbrCalls;
import org.example.DTO.CurrencyInfo;
import org.example.Exceptions.CurrencyNotFound;
import org.example.Exceptions.FutureDayException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    private static final String errorArguments = "Формат команды для утилиты: currency_rates --code=USD --date=2022-10-08";

    public static void main(String[] args) {
        System.setProperty("console.encoding", "UTF-8");

        if (args.length != 2) {
            System.out.println(errorArguments);
            return;
        }

        String currencyCode = null;
        LocalDate targetDate = null;

        for (String arg : args) {
            if (arg.startsWith("--code=")) {
                currencyCode = arg.substring(7);
            } else if (arg.startsWith("--date=")) {
                String dateString = arg.substring(7);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                targetDate = LocalDate.parse(dateString, formatter);
            }
        }

        if (currencyCode == null || targetDate == null) {
            System.out.println("Неверные аргументы. \n" + errorArguments);
            return;
        }


        try {
            var data = cbrCalls.getInfo(currencyCode, targetDate);
            printResult(data);
        } catch (CurrencyNotFound cnf) {
            System.out.println("Такая валюта не найдена");
        } catch (FutureDayException fde) {
            System.out.println("Дата не должна быть в будущем");
        } catch (RuntimeException e) {
            e.printStackTrace();
        }


    }

    private static void printResult(CurrencyInfo info) {
        System.out.println(info.getCharCode() + " (" + info.getName() + "): " + info.getValue());
    }
}