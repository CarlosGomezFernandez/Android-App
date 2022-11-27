package com.example.rsmpractica2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Actividad Principal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void inicioLectura(View view) {
        Log.e(TAG, "Acceso a la actividad con la lectura del sensor");
        Intent intentActividad = new Intent(this, ResultActivity.class);
        startActivity(intentActividad);

        Log.e(TAG, "Iniciando servicio");
        Intent intentServicio = new Intent(this, Servicio.class);
        startService(intentServicio);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "Finalizando servicio");
        Intent intent = new Intent(this, Servicio.class);
        stopService(intent);
    }
}