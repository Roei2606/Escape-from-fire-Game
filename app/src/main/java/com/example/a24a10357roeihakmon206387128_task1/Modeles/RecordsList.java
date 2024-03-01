package com.example.a24a10357roeihakmon206387128_task1.Modeles;

import com.example.a24a10357roeihakmon206387128_task1.Utilities.SharedPreferencesManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class RecordsList {
    private static ArrayList<Record> recordArrayList = new ArrayList<>();
    private final int MAX_RECORDS = 10;


    public RecordsList(){
        loadData();
    }

    public ArrayList<Record> jsonToRecordsList(String json){
        return new Gson().fromJson(json, new TypeToken<ArrayList<Record>>() {
        }.getType());
    }
    private void loadData() {
        ArrayList<Record> oldRecordList = jsonToRecordsList(SharedPreferencesManager.getInstance().getString("records",null));
        if(oldRecordList!=null){
            recordArrayList = oldRecordList;
        }
    }
    public void saveRecordsList(){
        String json = recordsListToJson();
        SharedPreferencesManager.getInstance().putString("records",json);
    }

    private String recordsListToJson() {
        return new Gson().toJson(recordArrayList);
    }

    public ArrayList<Record> getRecordArrayList(){
        return recordArrayList;
    }
    public RecordsList addRecord(Record record){
        if(recordArrayList.size()<MAX_RECORDS){
            recordArrayList.add(record);
            recordArrayList.sort(Comparator.comparingInt(Record::getScore).reversed());

        }else{
            if(record.getScore()>recordArrayList.get(recordArrayList.size()-1).getScore()){
                recordArrayList.remove(recordArrayList.size()-1);
                recordArrayList.add(record);
            }
        }
        setRank();
        saveRecordsList();
        return this;
    }

    @Override
    public String toString() {
        return "RecordsList{" +
                "recordArrayList=" + recordArrayList +
                '}';
    }
    private void setRank(){
        for(int i= 0;i<recordArrayList.size();i++){
            recordArrayList.get(i).setRank(i+1);
        }
    }
}
