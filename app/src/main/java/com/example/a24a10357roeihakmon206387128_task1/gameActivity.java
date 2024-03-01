package com.example.a24a10357roeihakmon206387128_task1;




import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;


import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.a24a10357roeihakmon206387128_task1.Interfaces.SensorCallBack;
import com.example.a24a10357roeihakmon206387128_task1.Modeles.Record;
import com.example.a24a10357roeihakmon206387128_task1.Modeles.RecordsList;
import com.example.a24a10357roeihakmon206387128_task1.Utilities.BackGroundSound;
import com.example.a24a10357roeihakmon206387128_task1.Utilities.SensorDetector;
import com.example.a24a10357roeihakmon206387128_task1.Utilities.SharedPreferencesManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.example.a24a10357roeihakmon206387128_task1.Logic.gameManager;
import com.google.android.material.textview.MaterialTextView;
import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;


public class gameActivity extends AppCompatActivity implements LocationListener {
    private ShapeableImageView game_IMG_background;
    private gameManager gameManager;
    private MaterialButton main_BTN_left;
    private MaterialButton main_BTN_right;
    private MaterialTextView main_LBL_score;
    private int moveCounterRight = 0;
    private int moveCounterLeft = 0;
    private ShapeableImageView main_IMG_firemen1;
    private ShapeableImageView main_IMG_firemen2;
    private ShapeableImageView main_IMG_firemen3;
    private ShapeableImageView main_IMG_firemen4;
    private ShapeableImageView main_IMG_firemen5;
    private ShapeableImageView[] main_IMG_hearts;
    public static int LIFE = 3;
    private ShapeableImageView[][] obstacles ;
    private final int ROW = 4;
    private final  int COL = 5;
    private final Handler handler = new Handler();
    public Vibrator vibrator;
    private BackGroundSound fireman_got_burned;
    private BackGroundSound fireman_got_wet;
    private int speed;
    private boolean isSensorOn;
    private boolean isRunning = true;
    private SensorDetector sensorDetector;
    private LocationManager locationManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findviews();
        gameManager = new gameManager()
                .setLife(LIFE)
                .setObstaclesLogicMatrix(ROW, COL)
                .setFiremanLogicArray(COL);
        getLocationPermission();
        getLocation();
        speed = getIntent().getIntExtra("speedValue",speed);
        isSensorOn = getIntent().getBooleanExtra("isSensorOn",isSensorOn);
        setFirstViewGame(isSensorOn);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        startPlay();
    }
    @SuppressLint("MissingPermission")
    private void getLocation() {
        try{
            locationManager = (android.location.LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5,gameActivity.this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void getLocationPermission() {
        if(ContextCompat.checkSelfPermission(gameActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(gameActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(gameActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        gameManager.setLatitudeAndSetLongitude(location.getLatitude(),location.getLongitude());
        try {
            Geocoder geocoder = new Geocoder(gameActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);
            System.out.println("Current address: "+ address);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }

    private void startPlay() {
        if(!isSensorOn){
            main_BTN_left.setOnClickListener(view -> moveLeft());
            main_BTN_right.setOnClickListener(view -> moveRight());
        }else{
            sensorDetector = new SensorDetector(this, new SensorCallBack() {
                @Override
                public void moveX(String moveTo) {
                    if(moveTo.equals("RIGHT")){
                        moveRight();
                    }
                    if(moveTo.equals("LEFT")){
                        moveLeft();
                    }
                }
                @Override
                public void moveY(String acceleration) {
                    if(acceleration.equals("FAST")){
                        speed = 1000;
                    }
                    if(acceleration.equals("SLOW")){
                        speed = 2000;
                    }

                }
            });
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        isRunning = false;
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (isSensorOn)
            sensorDetector.stop();
        isRunning = false;
    }
    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
        if (isSensorOn)
            sensorDetector.start();
        handlerStartGame();
    }

    private void handlerStartGame() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(gameManager.isGameLost()) {
                    isRunning = false;
                    changeActivity(gameManager);
                    return;
                }
                gameManager.startMovingObstacles();
                refreshUI();
                handler.postDelayed(this, speed);
            }
        }, speed);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void refreshUI() {
                 for(int i = 0; i < ROW; i++){
                     for(int j = 0; j < COL ; j++){
                         if(gameManager.getObstaclesLogicMatrix()[i][j]==1){
                             obstacles[i][j].setImageResource(R.drawable.fire);
                             obstacles[i][j].setVisibility(View.VISIBLE);
                         } else if (gameManager.getObstaclesLogicMatrix()[i][j]==2) {
                             obstacles[i][j].setImageResource(R.drawable.drop);
                             obstacles[i][j].setVisibility(View.VISIBLE);
                         } else
                             obstacles[i][j].setVisibility(View.INVISIBLE);
                     }
                     if(gameManager.getCollisionDetector2()){
                         collisionReact();
                         gameManager.setCollisionDetector2(false);
                     }
                     if(gameManager.isBurnedDetector()){
                         fireman_got_burned.playSound();
                         gameManager.setBurnedDetector(false);
                     }
                     if(gameManager.isSplashDetector()){
                         fireman_got_wet.playSound();
                         gameManager.setSplashDetector(false);
                     }
                 }
                 main_LBL_score.setText(gameManager.getScore() + "");

    }
    private void changeActivity(gameManager gameManager) {
        Intent scoreIntent =  new Intent(this, scoreActivity.class);
        startActivity(scoreIntent);
        finishAffinity();
    }
    private void setFirstViewGame(boolean isSensorOn) {
        game_IMG_background.setImageResource(R.drawable.main_background);
        for (int i = 0; i < obstacles.length; i++) {
            for (int j = 0; j < obstacles[i].length; j++) {
                obstacles[i][j].setVisibility(View.INVISIBLE);
            }
        }
        main_IMG_firemen1.setVisibility(View.INVISIBLE);
        main_IMG_firemen2.setVisibility(View.INVISIBLE);
        main_IMG_firemen3.setVisibility(View.VISIBLE);
        main_IMG_firemen4.setVisibility(View.INVISIBLE);
        main_IMG_firemen5.setVisibility(View.INVISIBLE);
        if(isSensorOn){
            main_BTN_left.setVisibility(View.GONE);
            main_BTN_right.setVisibility(View.GONE);
        }
    }

    private void moveRight() {
        if((moveCounterRight==1)&&(moveCounterLeft==0))
        {
            moveCounterRight++;
            main_IMG_firemen4.setVisibility(View.INVISIBLE);
            main_IMG_firemen5.setVisibility(View.VISIBLE);
            gameManager.setFiremanLocation("fourToFive");
        }
        if ((moveCounterRight == 0) && (moveCounterLeft == 0)) {
            moveCounterRight++;
            main_IMG_firemen3.setVisibility(View.INVISIBLE);
            main_IMG_firemen4.setVisibility(View.VISIBLE);
            gameManager.setFiremanLocation("threeToFour");

        }

        if ((moveCounterLeft == 1) && (moveCounterRight == 0)) {
            moveCounterLeft--;
            main_IMG_firemen2.setVisibility(View.INVISIBLE);
            main_IMG_firemen3.setVisibility(View.VISIBLE);
            gameManager.setFiremanLocation("twoToThree");
        }
        if ((moveCounterLeft == 2) && (moveCounterRight == 0)) {
            moveCounterLeft--;
            main_IMG_firemen1.setVisibility(View.INVISIBLE);
            main_IMG_firemen2.setVisibility(View.VISIBLE);
            gameManager.setFiremanLocation("oneToTwo");
        }
        refreshUI();
    }

    private void moveLeft() {
        if ((moveCounterRight == 0) && (moveCounterLeft == 1)) {
            moveCounterLeft++;
            main_IMG_firemen2.setVisibility(View.INVISIBLE);
            main_IMG_firemen1.setVisibility(View.VISIBLE);
            gameManager.setFiremanLocation("twoToOne");
        }
        if ((moveCounterRight == 0) && (moveCounterLeft == 0)) {
            moveCounterLeft++;
            main_IMG_firemen3.setVisibility(View.INVISIBLE);
            main_IMG_firemen2.setVisibility(View.VISIBLE);
            gameManager.setFiremanLocation("threeToTwo");
        }
        if ((moveCounterLeft == 0) && (moveCounterRight == 1)) {
            moveCounterRight--;
            main_IMG_firemen4.setVisibility(View.INVISIBLE);
            main_IMG_firemen3.setVisibility(View.VISIBLE);
            gameManager.setFiremanLocation("fourToThree");
        }
        if ((moveCounterLeft == 0) && (moveCounterRight == 2)) {
            moveCounterRight--;
            main_IMG_firemen5.setVisibility(View.INVISIBLE);
            main_IMG_firemen4.setVisibility(View.VISIBLE);
            gameManager.setFiremanLocation("fiveToFour");
        }
        refreshUI();
    }

    public void collisionReact() {
        if( (gameManager.getCountCollision()<3) && (gameManager.getCountCollision()>0) ) {
            main_IMG_hearts[main_IMG_hearts.length - gameManager.getCountCollision()].setVisibility(View.INVISIBLE);
        }
        vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
        Toast.makeText(this, "Your fireman got burned!", Toast.LENGTH_SHORT).show();
        gameManager.setCollisionDetector(false);

    }

    private void findviews() {

        game_IMG_background = findViewById(R.id.game_IMG_background);
        main_LBL_score = findViewById(R.id.main_LBL_score);
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);

        fireman_got_wet = new BackGroundSound(this, R.raw.water_splash);
        fireman_got_burned = new BackGroundSound(this,R.raw.man_got_burned);

        main_IMG_firemen1 = findViewById(R.id.main_IMG_firemen1);
        main_IMG_firemen2 = findViewById(R.id.main_IMG_firemen2);
        main_IMG_firemen3 = findViewById(R.id.main_IMG_firemen3);
        main_IMG_firemen4 = findViewById(R.id.main_IMG_firemen4);
        main_IMG_firemen5 = findViewById(R.id.main_IMG_firemen5);


        obstacles = new ShapeableImageView[][]{
                {findViewById(R.id.main_IMG_fire1), findViewById(R.id.main_IMG_fire2), findViewById(R.id.main_IMG_fire3), findViewById(R.id.main_IMG_fire4), findViewById(R.id.main_IMG_fire5)},
                {findViewById(R.id.main_IMG_fire6), findViewById(R.id.main_IMG_fire7), findViewById(R.id.main_IMG_fire8), findViewById(R.id.main_IMG_fire9), findViewById(R.id.main_IMG_fire10)},
                {findViewById(R.id.main_IMG_fire11), findViewById(R.id.main_IMG_fire12), findViewById(R.id.main_IMG_fire13), findViewById(R.id.main_IMG_fire14), findViewById(R.id.main_IMG_fire15)},
                {findViewById(R.id.main_IMG_fire16), findViewById(R.id.main_IMG_fire17), findViewById(R.id.main_IMG_fire18), findViewById(R.id.main_IMG_fire19), findViewById(R.id.main_IMG_fire20)}

        };

        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };

    }

}