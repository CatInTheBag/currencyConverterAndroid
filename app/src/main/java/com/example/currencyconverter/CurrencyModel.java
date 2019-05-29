package com.example.currencyconverter;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrencyModel
{
   private String firstCurrencyName;
   private String secondCurrencyName;
   private double secondCurrencyRate;
   private String date;

   static CurrencyModel getDataFromJson(JSONObject obj, String secondCurrency)
   {
      try {
         CurrencyModel data = new CurrencyModel();

         data.firstCurrencyName = obj.getString("base");
         data.secondCurrencyName = secondCurrency;
         data.secondCurrencyRate = Double.valueOf(obj.getJSONObject("rates").getDouble(secondCurrency));
         data.date = obj.getString("date");

         return data;
      } catch (JSONException e) {
         e.printStackTrace();
         return null;
      }

   }

   public double getSecondCurrencyRate()
   {
      return secondCurrencyRate;
   }

   public String getDate() {
      return date;
   }
}
