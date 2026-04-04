package com.example.sensorreaderapp;
import androidx.appcompat.app.AppCompatActivity;,
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;

    Sensor accelerometer;
    Sensor lightSensor;
    Sensor proximitySensor;

    TextView accValue, lightValue, proximityValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        accValue = findViewById(R.id.accValue);
        lightValue = findViewById(R.id.lightValue);
        proximityValue = findViewById(R.id.proximityValue);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
    }

    @Override
    protected void onResume() {
        super.onResume();

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            accValue.setText("X: " + x + " Y: " + y + " Z: " + z);
        }

        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

            float light = event.values[0];
            lightValue.setText("Light: " + light);
        }

        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {

            float proximity = event.values[0];
            proximityValue.setText("Proximity: " + proximity);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}