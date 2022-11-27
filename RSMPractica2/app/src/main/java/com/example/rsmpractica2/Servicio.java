package com.example.rsmpractica2;

import androidx.annotation.Nullable;
import android.app.Service;
import android.os.IBinder;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.content.Intent;
import android.util.Log;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Servicio extends Service implements SensorEventListener {

    private static final String TAG = "Servicio";
    private static final String IPServer="192.168.56.1";
    SensorManager sensorManager;
    Sensor acelerometro;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "Servicio iniciado");
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this,acelerometro,SensorManager.SENSOR_DELAY_GAME);
        Log.e(TAG, "Sensor leyendo");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(this);
        Log.e(TAG, "Desvincunlando sensor");
        Log.e(TAG, "Finalizando servicio");
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float x = sensorEvent.values[0];
        float y = sensorEvent.values[1];
        float z = sensorEvent.values[2];
        clienteUDP(x, y, z);
    }

    public void clienteUDP(float x, float y, float z) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String mensaje = "Valor eje X: " + x + " | Valor eje Y: " + y + " | Valor eje Z: " + z;
                    DatagramSocket datagramSocket = new DatagramSocket();
                    InetAddress serverAddress = InetAddress.getByName(IPServer);
                    DatagramPacket datagramPacket = new DatagramPacket(mensaje.getBytes(), mensaje.length(), serverAddress, 10000);
                    datagramSocket.send(datagramPacket);
                    Log.e(TAG, "Enviando mensaje");
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e(TAG, "Información transmitida al servidor");
            }
        }).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {return null;}

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}
}