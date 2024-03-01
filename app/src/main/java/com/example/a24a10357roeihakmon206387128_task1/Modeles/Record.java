package com.example.a24a10357roeihakmon206387128_task1.Modeles;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.time.LocalDate;

public class Record  {
    private int score = 0;
    private int rank = 0;
    private double latitude = 0d;
    private double longitude = 0d;

    public Record(){

    }

    public int getRank() {
        return rank;
    }



    public Record setRank(int rank) {
        this.rank = rank;
        return this;
    }

    public int getScore() {
        return score;
    }

    public Record setScore(int score) {
        this.score = score;
        return this;
    }

    public Record setLatitude(double latitude) {
        this.latitude = latitude;
        return this;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }

    public Record setLongitude(double longitude) {
        this.longitude = longitude;
        return this;
    }

    @Override
    public String toString() {
        return "Record{" +
                "score=" + score +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
