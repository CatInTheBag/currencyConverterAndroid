package com.example.currencyconverter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
   final String SITE_URL = "https://api.exchangeratesapi.io/latest";
   //final String APP_ID = "52770061439cb080d24efa42a288a469";


   private Spinner spinOne;
   private Spinner spinTwo;
   private EditText amount;
   private TextView sum;
   private String value;
   private ImageView convertImage;

   @Override
   protected void onCreate(Bundle savedInstanceState)
   {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      initViews();
      //setSupportActionBar(toolbar);

      //initOnClick();
   }

   private void initOnClick() {
      convertImage.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {

         }
      });

   }

   private void initViews() {
      spinOne = findViewById(R.id.spinner_first);
      spinTwo = findViewById(R.id.spinner_second);
      amount = findViewById(R.id.edit_text_query);
      sum = findViewById(R.id.text_view_query);
      convertImage = findViewById(R.id.image_currency_convert);
      Toolbar toolbar = findViewById(R.id.app_bar);
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
}
