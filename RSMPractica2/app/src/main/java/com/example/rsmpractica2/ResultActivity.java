package com.example.rsmpractica2;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.os.Bundle;
import android.widget.TextView;
import android.view.View;
import android.content.Intent;
import android.util.Log;

public class ResultActivity extends AppCompatActivity implements SensorEventListener{

    private static final String TAG = "Actividad Secundaria";
    SensorManager sensorManager;
    Sensor acelerometro;
    TextView xID,yID,zID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fin_lectura);

        xID = (TextView) findViewById(R.id.xID);
        yID = (TextView) findViewById(R.id.yID);
        zID = (TextView) findViewById(R.id.zID);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (acelerometro == null) {
            finish();
        }
        Log.e(TAG, "Sensor leyendo");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            xID.setText("");
            yID.setText("");
            zID.setText("");

            xID.append("El valor del acelerometro en el eje X es: "+x);
            yID.append("El valor del acelerometro en el eje Y es: "+y);
            zID.append("El valor del acelerometro en el eje Z es: "+z);
    }

    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,acelerometro,SensorManager.SENSOR_DELAY_NORMAL);
    }

    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    public void finLectura(View view) {
        onPause();
        Log.e(TAG, "Desvinculando sensor");

        Intent intentServicio = new Intent(this, Servicio.class);
        stopService(intentServicio);
        Log.e(TAG, "Finalizando servicio");

        Intent intentActividad = new Intent(this, MainActivity.class);
        startActivity(intentActividad);
        Log.e(TAG, "Volviendo a la actividad principal");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}