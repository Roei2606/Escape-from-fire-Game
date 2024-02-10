package com.example.a24a10357roeihakmon206387128_task1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {

    private ShapeableImageView main_IMG_background;
    private MaterialButton button_start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findviews();
        main_IMG_background.setImageResource(R.drawable.main_background);
        button_start.setOnClickListener(v->startGame());
    }

    private void startGame() {
        Intent intent = new Intent(MainActivity.this, gameActivity.class);
        startActivity(intent);
    }

    private void findviews() {

       // main_IMG_background=findViewById(R.id.main_IMG_background);
        main_IMG_background=findViewById(R.id.main_IMG_background);
        button_start=findViewById(R.id.button_start);

    }
}