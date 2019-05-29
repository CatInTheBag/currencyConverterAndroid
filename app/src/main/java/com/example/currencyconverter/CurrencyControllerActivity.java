package com.example.currencyconverter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.currencyconverter.databinding.ActivityMainBinding;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

import cz.msebera.android.httpclient.Header;

public class CurrencyControllerActivity extends AppCompatActivity {
    final String SITE_URL = "https://api.exchangeratesapi.io/latest";

    private Spinner spinOne;
    private Spinner spinTwo;

    private EditText amountEditText;
    private TextView sumTextView;

    private ImageView convertImage;
    private ActivityMainBinding binding;
    private Toolbar toolbar;
    String[] currencySpinner = new String[]{"BGN", "USD", "EUR", "GBP"};

    private String secondCurrency;
    private String firstCurrency;
    private CurrencyModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initViews();
        initSpinner();
        initOnClick();
    }

    private void updateUI() {
        double amount = Double.valueOf(amountEditText.getText().toString());
        double sum = amount * model.getSecondCurrencyRate();
        double sumDecimal = BigDecimal.valueOf(sum)
                .setScale(3, RoundingMode.HALF_UP)
                .doubleValue();
        sumTextView.setText(String.valueOf(sumDecimal));
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, currencySpinner);
        binding.spinnerFirst.setAdapter(adapter);
        binding.spinnerSecond.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    private void initOnClick() {
        convertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInfoFromAPI();
            }
        });

    }

    private void initViews() {
        spinOne = binding.spinnerFirst;
        spinTwo = binding.spinnerSecond;
        amountEditText = binding.editTextQuery;
        sumTextView = binding.textViewQuery;
        convertImage = binding.imageCurrencyConvert;

        initToolbar();
    }

    private void initToolbar() {
        toolbar = binding.appBar;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
    }


    private void getInfoFromAPI() {
        firstCurrency = spinOne.getSelectedItem().toString();
        secondCurrency = spinTwo.getSelectedItem().toString();

        RequestParams params = new RequestParams();
        params.put("base", firstCurrency);
        //params.put("symbols", firstCurrency +"," +secondCurrency);
        //params.put("symbols", secondCurrency);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get(SITE_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int StatusCode, Header[] headers, JSONObject response) {
                /*Log.d("Currency", "Success with JSON" + response.toString());
                ;*/

                model = CurrencyModel.getDataFromJson(response, secondCurrency);
                //Toast.makeText(CurrencyControllerActivity.this, model.getDate(), Toast.LENGTH_SHORT).show();
                updateUI();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, JSONObject response) {
                Toast.makeText(CurrencyControllerActivity.this, "Status code " + statusCode, Toast.LENGTH_LONG).show();
            }
        });
    }
}
