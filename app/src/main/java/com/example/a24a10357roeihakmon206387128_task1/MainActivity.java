package com.example.a24a10357roeihakmon206387128_task1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private ShapeableImageView main_IMG_background;
    private MaterialButton button_start;
    private MaterialButton button_start_with_sensors;
    private SwitchCompat switch_mode_fastSlow;
    private final int FAST = 500;
    private final int SLOW = 1000;
    private int speed = SLOW;
    private boolean isSensorOn = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findviews();

        main_IMG_background.setImageResource(R.drawable.main_background);
        modeProvider();
        switch_mode_fastSlow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    speed = FAST;
                }else{
                    speed = SLOW;
                }
            }
        });
    }


    private void modeProvider() {
        button_start.setOnClickListener(v -> startGame(speed,isSensorOn));
        button_start_with_sensors.setOnClickListener(v->startGame(speed,true));
    }

    private void startGame(int speed, boolean isSensorOn) {
        Intent intent = new Intent(MainActivity.this, gameActivity.class);
        intent.putExtra("speedValue", speed);
        intent.putExtra("isSensorOn", isSensorOn);
        startActivity(intent);
        finish();
    }

    private void findviews() {
        main_IMG_background = findViewById(R.id.main_IMG_background);
        button_start = findViewById(R.id.button_start);
        button_start_with_sensors = findViewById(R.id.button_start_with_sensors);
        switch_mode_fastSlow = (SwitchCompat) findViewById(R.id.switch_mode_fastSlow);

    }
}