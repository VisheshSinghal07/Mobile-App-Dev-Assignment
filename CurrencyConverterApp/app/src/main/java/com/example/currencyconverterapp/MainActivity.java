package com.example.currencyconverterapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import android.content.Intent;

public class MainActivity extends AppCompatActivity {


    String[] currencies = {"INR", "USD", "EUR", "JPY"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Button settingsButton = findViewById(R.id.settingsButton);

        settingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        EditText amountInput = findViewById(R.id.amountInput);
        Spinner fromSpinner = findViewById(R.id.fromCurrency);
        Spinner toSpinner = findViewById(R.id.toCurrency);
        Button convertButton = findViewById(R.id.convertButton);
        TextView resultText = findViewById(R.id.resultText);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                currencies
        );

        fromSpinner.setAdapter(adapter);
        toSpinner.setAdapter(adapter);

        convertButton.setOnClickListener(v -> {

            String input = amountInput.getText().toString();

            if(input.isEmpty()){
                resultText.setText("Please enter an amount");
                return;
            }

            double amount = Double.parseDouble(input);

            String from = fromSpinner.getSelectedItem().toString();
            String to = toSpinner.getSelectedItem().toString();

            double inr;

            if(from.equals("USD")) inr = amount * 94.71;
            else if(from.equals("EUR")) inr = amount * 108.81;
            else if(from.equals("JPY")) inr = amount * 0.59;
            else inr = amount;

            double result;

            if(to.equals("USD")) result = inr * 0.011;
            else if(to.equals("EUR")) result = inr * 0.0092;
            else if(to.equals("JPY")) result = inr * 1.68;
            else result = inr;

            resultText.setText("Converted Amount: " + result);

        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}