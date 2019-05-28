package com.example.currencyconverter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class CurrencyController extends AppCompatActivity
{
   private final double USDTOBGN = 1.74;
   private final double EURTOBGN = 1.96;
   private final double GBPTOBGN = 2.26;
   private final double BGNTOBGN = 1.00;

   //final String SITE_URL = "http://api.openweathermap.org/data/2.5/forecast";
   final String SITE_URL = "https://api.exchangeratesapi.io/latest";
   final String APP_ID = "52770061439cb080d24efa42a288a469";

   private Spinner spinOne;
   private Spinner spinTwo;


   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      spinOne = findViewById(R.id.spinnerFirstSelect);
      spinTwo = findViewById(R.id.spinnerSecondSelect);

   }


   public void onClickConvert(View view)
   {

      EditText amount = findViewById(R.id.editText2);
      TextView sum = findViewById(R.id.textViewSum);
      String value = amount.getText().toString();

      String firstCurrencyName = String.valueOf(spinOne.getSelectedItem());
      String secondCurrencyName = String.valueOf(spinTwo.getSelectedItem());

      if(value.equals(""))
      {
         value = "1";
      }

      int amountSum = Integer.parseInt(value);


      if ((firstCurrencyName.equals("USD")) && (secondCurrencyName.equals("BGN")))
      {
         double currentSum = USDTOBGN * amountSum;
         sum.setText("" + currentSum);
      }
      else if((firstCurrencyName.equals("EUR")) && (secondCurrencyName.equals("BGN")))
      {
         double currentSum = EURTOBGN * amountSum;
         sum.setText("" + currentSum);
      }
      else if((firstCurrencyName.equals("GBP")) && (secondCurrencyName.equals("BGN")))
      {
         double currentSum = GBPTOBGN * amountSum;
         sum.setText("" + currentSum);
      }
      else if ((firstCurrencyName.equals("BGN")) && (secondCurrencyName.equals("BGN")))
      {
         double currentSum = BGNTOBGN * amountSum;
         sum.setText("" + currentSum);
      }
   }

   public void onClickAPI(View view)
   {
      String firstCurrencyName = String.valueOf(spinOne.getSelectedItem());
      String secondCurrencyName = String.valueOf(spinTwo.getSelectedItem());

      RequestParams params = new RequestParams();
      params.put("base",firstCurrencyName);
      //params.put("symbols",secondCurrencyName);

      getInfoFromAPI(params);
   }



   private void getInfoFromAPI(RequestParams params)
   {
      final String firstCurrencyName = String.valueOf(spinOne.getSelectedItem());
      final String secondCurrencyName = String.valueOf(spinTwo.getSelectedItem());

      AsyncHttpClient client = new AsyncHttpClient();

      client.get(SITE_URL,params, new JsonHttpResponseHandler()
      {
         @Override
         public void onSuccess(int StatusCode, Header[] headers, JSONObject response)
         {
            Log.d("Currency","Success with JSON" + response.toString());
            Toast.makeText(CurrencyController.this,"Success",Toast.LENGTH_LONG).show();

            try {
               CurrencyModel model = new CurrencyModel().getDataFromJson(response, firstCurrencyName, secondCurrencyName);
            }
            catch( JSONException e)
            {
               e.printStackTrace();
            }
         }

         @Override
         public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response)
         {
            Toast.makeText(CurrencyController.this, "Status code " + statusCode,Toast.LENGTH_LONG).show();
         }
      });


   }

   private void updateUI(CurrencyModel model)
   {
      
   }
}
