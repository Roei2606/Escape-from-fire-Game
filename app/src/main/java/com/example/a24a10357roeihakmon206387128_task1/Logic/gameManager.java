package com.example.a24a10357roeihakmon206387128_task1.Logic;

import java.util.Random;
import com.example.a24a10357roeihakmon206387128_task1.gameActivity;
public class gameManager {

    private int life;
    private int [][] obstaclesLogicMatrix;
    boolean collisionDetector;
    boolean collisionDetector2;
    int countCollision = 0;
    private int[] firemanLogicArray;

public gameManager(){

}
    public gameManager setLife (int life){
        this.life=life;
        return this;
    }
    public int getLife(){
        return this.life;
    }

    public gameManager setObstaclesLogicMatrix(int row, int col){
        this.obstaclesLogicMatrix=new int[row][col];
        for(int i = 0;i<this.obstaclesLogicMatrix.length;i++) {
            for (int j = 0; j < obstaclesLogicMatrix[0].length; j++) {
                obstaclesLogicMatrix[i][j] = 0;
            }
        }
        return this;
    }
    public int[][] getObstaclesLogicMatrix() {
        return obstaclesLogicMatrix;
    }

    public gameManager setFiremanLogicArray (int col){
    this.firemanLogicArray=new int[col];
        for(int i = 0; i < col;i++){
            firemanLogicArray[i]=0;
        }
        firemanLogicArray[1]=1;
        return this;
    }


    public void setFiremanLocation (String from){
        if(from.equals("middleToRight")){
            this.firemanLogicArray[0]=0;
            this.firemanLogicArray[1]=0;
            this.firemanLogicArray[2]=2;
        }
        if(from.equals("middleToLeft")){
            this.firemanLogicArray[0]=2;
            this.firemanLogicArray[1]=0;
            this.firemanLogicArray[2]=0;

        }
        if(from.equals("rightToLeft")){
            this.firemanLogicArray[0]=0;
            this.firemanLogicArray[1]=2;
            this.firemanLogicArray[2]=0;
        }
        if(from.equals("leftToRight")){
            this.firemanLogicArray[0]=0;
            this.firemanLogicArray[1]=2;
            this.firemanLogicArray[2]=0;
        }
    }
    private int getCurrentFiremanLocation(){
        for(int i = 0;i<this.firemanLogicArray.length;i++){
            if(this.firemanLogicArray[i]==2){
                return i;
            }
        }
        return 0;
    }

    public void randomlyPlaceObject(){
        Random rand = new Random();
        int randomCol = rand.nextInt(this.obstaclesLogicMatrix[0].length);
        this.obstaclesLogicMatrix[0][randomCol]=1;
    }

    public void startMovingObstacles(){

        for(int i = this.obstaclesLogicMatrix.length-1; i >= 0; i--){
            for(int j = 0; j < this.obstaclesLogicMatrix[0].length; j++){
                if(this.obstaclesLogicMatrix[i][j]==1){
                    if(!checkCollision(i,j)){
                        if(i<3) {
                            this.obstaclesLogicMatrix[i + 1][j] = this.obstaclesLogicMatrix[i][j];
                            this.obstaclesLogicMatrix[i][j]=0;
                        }
                    }
                    this.obstaclesLogicMatrix[i][j]=0;
                }
            }
        }
        randomlyPlaceObject();
    }

    private boolean checkCollision(int row, int col) {
        if((getCurrentFiremanLocation()==col)&&(this.obstaclesLogicMatrix.length-1==row)){
            setCollisionDetector(true);
            countCollision++;
            collisionDetector2=true;
            return this.collisionDetector;
        }
        setCollisionDetector(false);
        return this.collisionDetector;
    }
    public boolean getCollisionDetector() {

        return this.collisionDetector;
    }
    public void setCollisionDetector(boolean hitOrMiss) {

        this.collisionDetector = hitOrMiss;
    }
    public int getCountCollision(){

         return this.countCollision;
    }
    public void setCountCollision(int collision){

        this.countCollision= collision;
    }
    public boolean getCollisionDetector2() {
        return collisionDetector2;
    }
    public void setCollisionDetector2(boolean validation) {

        this.collisionDetector2 = validation;
    }
}


