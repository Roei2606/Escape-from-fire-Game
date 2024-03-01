package com.example.a24a10357roeihakmon206387128_task1.Logic;

import java.time.LocalDate;
import java.util.Random;


import com.example.a24a10357roeihakmon206387128_task1.Modeles.Record;
import com.example.a24a10357roeihakmon206387128_task1.Modeles.RecordsList;
import com.google.gson.Gson;


public class gameManager {

    private int life;
    private int score=0;
    private static final int DROP_COLLECT = 10;
    private int [][] obstaclesLogicMatrix;
    boolean collisionDetector;
    boolean collisionDetector2;
    int countCollision = 0;
    private int [] firemanLogicArray;

    private Record record = new Record();
    private RecordsList recordsList = new RecordsList();
    public static final String RECORDS_LIST = "RECORDS_LIST";
    Gson gson = new Gson();
    private boolean burnedDetector = false;
    private boolean splashDetector = false;

    public boolean isBurnedDetector() {
        return burnedDetector;
    }

    public void setBurnedDetector(boolean burnedDetector) {
        this.burnedDetector = burnedDetector;
    }

    public boolean isSplashDetector() {
        return splashDetector;
    }

    public void setSplashDetector(boolean splashDetector) {
        this.splashDetector = splashDetector;
    }

    public gameManager(){


}
    public gameManager setLife (int life){
        this.life=life;
        return this;
    }
    public int getLife(){
        return this.life;
    }
    public int getScore() {
        return score;
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
        firemanLogicArray[2]=2;
        return this;
    }


    public void setFiremanLocation (String from){
        if(from.equals("threeToFour")){
            this.firemanLogicArray[0]=0;
            this.firemanLogicArray[1]=0;
            this.firemanLogicArray[2]=0;
            this.firemanLogicArray[3]=2;
            this.firemanLogicArray[4]=0;
        }
        if(from.equals("fourToFive")){
            this.firemanLogicArray[0]=0;
            this.firemanLogicArray[1]=0;
            this.firemanLogicArray[2]=0;
            this.firemanLogicArray[3]=0;
            this.firemanLogicArray[4]=2;

        }
        if(from.equals("fiveToFour")){
            this.firemanLogicArray[0]=0;
            this.firemanLogicArray[1]=0;
            this.firemanLogicArray[2]=0;
            this.firemanLogicArray[3]=2;
            this.firemanLogicArray[4]=0;
        }
        if(from.equals("fourToThree")){
            this.firemanLogicArray[0]=0;
            this.firemanLogicArray[1]=0;
            this.firemanLogicArray[2]=2;
            this.firemanLogicArray[3]=0;
            this.firemanLogicArray[4]=0;
        }
        if(from.equals("threeToTwo")){
            this.firemanLogicArray[0]=0;
            this.firemanLogicArray[1]=2;
            this.firemanLogicArray[2]=0;
            this.firemanLogicArray[3]=0;
            this.firemanLogicArray[4]=0;
        }
        if(from.equals("twoToOne")){
            this.firemanLogicArray[0]=2;
            this.firemanLogicArray[1]=0;
            this.firemanLogicArray[2]=0;
            this.firemanLogicArray[3]=0;
            this.firemanLogicArray[4]=0;
        }
        if(from.equals("oneToTwo")){
            this.firemanLogicArray[0]=0;
            this.firemanLogicArray[1]=2;
            this.firemanLogicArray[2]=0;
            this.firemanLogicArray[3]=0;
            this.firemanLogicArray[4]=0;
        }
        if(from.equals("twoToThree")){
            this.firemanLogicArray[0]=0;
            this.firemanLogicArray[1]=0;
            this.firemanLogicArray[2]=2;
            this.firemanLogicArray[3]=0;
            this.firemanLogicArray[4]=0;
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

    public void randomlyPlaceObject()
    {
        Random rand = new Random();
        int randomCol = rand.nextInt(this.obstaclesLogicMatrix[0].length);
        this.obstaclesLogicMatrix[0][randomCol]=rand.nextInt(2)+1;
    }

    public void startMovingObstacles()
    {
        for(int i = this.obstaclesLogicMatrix.length-1; i >= 0; i--){
            for(int j = 0; j < this.obstaclesLogicMatrix[0].length; j++){
                if((this.obstaclesLogicMatrix[i][j]==1)||(this.obstaclesLogicMatrix[i][j]==2)){
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
    public boolean isGameLost()
    {
        if(countCollision==life){
            //record.setRecordDate(LocalDate.now());
            record.setScore(score);
            recordsList.addRecord(record);
            return true;
        }
        return false;

    }

    private boolean checkCollision(int row, int col) {
        if((getCurrentFiremanLocation()==col)&&(this.obstaclesLogicMatrix.length-1==row)&&(this.obstaclesLogicMatrix[row][col]==1)){
            setCollisionDetector(true);
            countCollision++;
            collisionDetector2=true;
            burnedDetector = true;
            return this.collisionDetector;
        }
        if((getCurrentFiremanLocation()==col)&&(this.obstaclesLogicMatrix.length-1==row)&&(this.obstaclesLogicMatrix[row][col]==2)){
            splashDetector = true;
            score+=DROP_COLLECT;
        }
        setCollisionDetector(false);
        return getCollisionDetector();
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

    public boolean getCollisionDetector2() {
        return collisionDetector2;
    }
    public void setCollisionDetector2(boolean validation) {

        this.collisionDetector2 = validation;
    }

    public void setRecordsList(RecordsList recordsList) {
        this.recordsList = recordsList;
    }

    public void setLatitudeAndSetLongitude(double latitude, double longitude) {
        record.setLatitude(latitude);
        record.setLongitude(longitude);
    }
}


