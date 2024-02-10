package com.example.a24a10357roeihakmon206387128_task1;




import android.content.Context;
import android.os.Bundle;
import android.os.Handler;


import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.example.a24a10357roeihakmon206387128_task1.Logic.gameManager;




public class gameActivity extends AppCompatActivity {
    private ShapeableImageView main_IMG_background;
    private gameManager gameManager;
    private MaterialButton main_BTN_left;
    private MaterialButton main_BTN_right;
    private int moveCounterRight = 0;
    private int moveCounterLeft = 0;
    private ShapeableImageView main_IMG_firemen1;
    private ShapeableImageView main_IMG_firemen2;
    private ShapeableImageView main_IMG_firemen3;
    private ShapeableImageView[] main_IMG_hearts;
    public static int LIVES = 3;
    private ShapeableImageView[][] firesObstacles ;
    private final int ROW = 4;
    private final  int COL = 3;

    private final Handler handler = new Handler();
    public Vibrator vibrator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        findviews();
        setFirstViewGame();
        gameManager = new gameManager().setLife(LIVES).setObstaclesLogicMatrix(ROW, COL).setFiremanLogicArray(COL);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        main_BTN_left.setOnClickListener(view -> moveLeft());
        main_BTN_right.setOnClickListener(view -> moveRight());
        refreshUI();


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshUI();
                gameManager.startMovingObstacles();
                refreshUI();
                handler.postDelayed(this, 1000);
            }
        }, 500);
    }

    private void refreshUI() {
        for(int i = 0; i < ROW; i++){
            for(int j = 0; j < COL ; j++){
                if(gameManager.getObstaclesLogicMatrix()[i][j]==1)
                    firesObstacles[i][j].setVisibility(View.VISIBLE);
                else
                    firesObstacles[i][j].setVisibility(View.INVISIBLE);
            }
            if(gameManager.getCollisionDetector2()){
                collisionReact();
                gameManager.setCollisionDetector2(false);
            }
        }
    }

    private void setFirstViewGame() {

        for (int i = 0; i < firesObstacles.length; i++) {
            for (int j = 0; j < firesObstacles[i].length; j++) {
                firesObstacles[i][j].setVisibility(View.INVISIBLE);
            }
        }
        main_IMG_background.setImageResource(R.drawable.main_background);
        main_IMG_firemen1.setVisibility(View.INVISIBLE);
        main_IMG_firemen2.setVisibility(View.VISIBLE);
        main_IMG_firemen3.setVisibility(View.INVISIBLE);
    }

    private void moveRight() {
        if ((moveCounterRight == 0) && (moveCounterLeft == 0)) {
            moveCounterRight++;
            main_IMG_firemen2.setVisibility(View.INVISIBLE);
            main_IMG_firemen3.setVisibility(View.VISIBLE);
            gameManager.setFiremanLocation("middleToRight");

        }
        if ((moveCounterLeft == 1) && (moveCounterRight == 0)) {
            moveCounterLeft--;
            main_IMG_firemen1.setVisibility(View.INVISIBLE);
            main_IMG_firemen2.setVisibility(View.VISIBLE);
            gameManager.setFiremanLocation("leftToRight");
        }
        refreshUI();
    }

    private void moveLeft() {
        if ((moveCounterRight == 0) && (moveCounterLeft == 0)) {
            moveCounterLeft++;
            main_IMG_firemen2.setVisibility(View.INVISIBLE);
            main_IMG_firemen1.setVisibility(View.VISIBLE);
            gameManager.setFiremanLocation("middleToLeft");
        }
        if ((moveCounterLeft == 0) && (moveCounterRight == 1)) {
            moveCounterRight--;
            main_IMG_firemen3.setVisibility(View.INVISIBLE);
            main_IMG_firemen2.setVisibility(View.VISIBLE);
            gameManager.setFiremanLocation("rightToLeft");
        }
        refreshUI();
    }

    public void collisionReact() {
        if( (gameManager.getCountCollision()<3) && (gameManager.getCountCollision()>0) ) {
            main_IMG_hearts[main_IMG_hearts.length - gameManager.getCountCollision()].setVisibility(View.INVISIBLE);
        }
        vibrator.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
        Toast.makeText(this, "Your fireman got burned!", Toast.LENGTH_SHORT).show();
        if(gameManager.getCountCollision()==3){
            for (ShapeableImageView mainImgHeart : main_IMG_hearts) {
                mainImgHeart.setVisibility(View.VISIBLE);
            }
            gameManager.setCountCollision(0);
        }
        gameManager.setCollisionDetector(false);

    }

    private void findviews() {

        main_IMG_background = findViewById(R.id.street_background);
        main_BTN_left = findViewById(R.id.main_BTN_left);
        main_BTN_right = findViewById(R.id.main_BTN_right);
        main_IMG_firemen1 = findViewById(R.id.main_IMG_firemen1);
        main_IMG_firemen2 = findViewById(R.id.main_IMG_firemen2);
        main_IMG_firemen3 = findViewById(R.id.main_IMG_firemen3);

        firesObstacles = new ShapeableImageView[][]{
                {findViewById(R.id.main_IMG_fire1), findViewById(R.id.main_IMG_fire2), findViewById(R.id.main_IMG_fire3)},
                {findViewById(R.id.main_IMG_fire4), findViewById(R.id.main_IMG_fire5), findViewById(R.id.main_IMG_fire6)},
                {findViewById(R.id.main_IMG_fire7), findViewById(R.id.main_IMG_fire8), findViewById(R.id.main_IMG_fire9)},
                {findViewById(R.id.main_IMG_fire10), findViewById(R.id.main_IMG_fire11), findViewById(R.id.main_IMG_fire12)}

        };

        main_IMG_hearts = new ShapeableImageView[]{
                findViewById(R.id.main_IMG_heart1),
                findViewById(R.id.main_IMG_heart2),
                findViewById(R.id.main_IMG_heart3)
        };
    }
}