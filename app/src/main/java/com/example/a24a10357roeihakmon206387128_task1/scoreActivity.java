package com.example.a24a10357roeihakmon206387128_task1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.a24a10357roeihakmon206387128_task1.Interfaces.RecordCallback;
import com.example.a24a10357roeihakmon206387128_task1.Modeles.Record;
import com.example.a24a10357roeihakmon206387128_task1.Views.ListFragment;
import com.example.a24a10357roeihakmon206387128_task1.Views.MapFragment;

public class scoreActivity extends AppCompatActivity {

    private MapFragment mapFragment;
    private ListFragment listFragment;
    private FrameLayout score_FRAME_map;
    private FrameLayout score_Frame_list;


    @SuppressLint("CommitTransaction")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        findViews();
        listFragment = new ListFragment();
        listFragment.setCallBackShowOnMaoClicked(new RecordCallback() {
            @Override
            public void showOnMapButtonClicked(Record record, int position) {
                mapFragment.showOnMapClicked(record.getLatitude(),record.getLongitude());
            }
        });
        mapFragment = new MapFragment();

        getSupportFragmentManager().beginTransaction().add(R.id.score_FRAME_list, listFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.score_FRAME_map, mapFragment).commit();
    }



    private void findViews() {
        score_FRAME_map = findViewById(R.id.score_FRAME_map);
        score_Frame_list = findViewById(R.id.score_FRAME_list);
    }
}