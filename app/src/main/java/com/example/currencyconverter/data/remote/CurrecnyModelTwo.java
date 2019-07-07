package com.example.currencyconverter.data.remote;

public class CurrecnyModelTwo {
    String base;
    String date;
    Rates currencyRates;


    class Rates {
        float USD;
        float BGN;
        float TRY;
        float EUR;
        float GBP;
    }
    @Override
    public String toString() {
        return "CurrencyModel :) ";
    }

}
