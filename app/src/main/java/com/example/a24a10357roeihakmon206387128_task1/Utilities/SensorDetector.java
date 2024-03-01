package com.example.a24a10357roeihakmon206387128_task1.Utilities;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.a24a10357roeihakmon206387128_task1.Interfaces.SensorCallBack;

public class SensorDetector {
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener sensorEventListener;
    private SensorCallBack sensorCallBack;
    private long timeStamp = 0l;

    
    public SensorDetector(Context context, SensorCallBack sensorCallBack){
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        this.sensorCallBack = sensorCallBack;
        initEventListener();
    }

    private void initEventListener() {
        sensorEventListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float x = event.values[0];
                float y = event.values[1];
                calculateMove(x,y);
            }
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    private void calculateMove(float x, float y) {
        String moveTo;
        String acceleration;
        if(x>0){moveTo = "RIGHT";}else{moveTo = "LEFT";}
        if(y>0){acceleration = "FAST";}else{acceleration = "SLOW";}
        if(System.currentTimeMillis()-timeStamp>500){
            timeStamp = System.currentTimeMillis();
            if(x>2.0||x<-2.0){
                if(sensorCallBack!=null){
                    sensorCallBack.moveX(moveTo);
                }
            }
            if(y>2.0||y<-2.0){
                if(sensorCallBack!=null){
                    sensorCallBack.moveY(acceleration);
                }
            }
        }
    }
    public void start() {sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);}

    public void stop() {
        sensorManager.unregisterListener(sensorEventListener, sensor);
    }
}
