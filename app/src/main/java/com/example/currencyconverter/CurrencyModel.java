package com.example.currencyconverter;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyModel
{
   private String firstCurrencyName;
   private String secondCurrencyName;
   private double firstCurrencyRate;
   private double secondCurrencyRate;

   public static CurrencyModel getDataFromJson(JSONObject obj, String firstCurrencyName, String secondCurrencyName) throws JSONException
   {
      CurrencyModel data = new CurrencyModel();
      data.firstCurrencyName = firstCurrencyName;
      data.secondCurrencyName = secondCurrencyName;
      data.secondCurrencyRate = Double.valueOf(obj.getJSONObject("rates").getDouble(secondCurrencyName));
      return data;
   }

   public String getFirstCurrencyName()
   {
      return firstCurrencyName;
   }

   public String getSecondCurrencyName()
   {
      return secondCurrencyName;
   }

   public double getSecondCurrencyRate()
   {
      return secondCurrencyRate;
   }
}
