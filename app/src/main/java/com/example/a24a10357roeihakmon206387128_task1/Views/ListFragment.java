package com.example.a24a10357roeihakmon206387128_task1.Views;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a24a10357roeihakmon206387128_task1.Adapters.RecordAdapter;
import com.example.a24a10357roeihakmon206387128_task1.Interfaces.RecordCallback;
import com.example.a24a10357roeihakmon206387128_task1.Modeles.Record;
import com.example.a24a10357roeihakmon206387128_task1.Modeles.RecordsList;
import com.example.a24a10357roeihakmon206387128_task1.R;

public class ListFragment extends Fragment {
    private RecordCallback callBackShowOnMaoClicked;
    private RecyclerView main_LST_record;
    private RecordsList recordsList;
    private RecordAdapter recordAdapter;
    public ListFragment(){
        recordsList = new RecordsList();
        recordAdapter = new RecordAdapter(getContext(),recordsList.getRecordArrayList());
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_list_fragment, container, false);
        findViews(view);
        initViews();
        return view;

    }
    private void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        main_LST_record.setLayoutManager(linearLayoutManager);
        main_LST_record.setAdapter(recordAdapter);

        recordAdapter.setRecordCallback(new RecordCallback() {
            @Override
            public void showOnMapButtonClicked(Record record, int position) {
                itemClicked(record,position);
            }
        });
    }

    private void itemClicked(Record record ,int position) {
        if (callBackShowOnMaoClicked != null)
            callBackShowOnMaoClicked.showOnMapButtonClicked(record,position);
    }

    private void findViews(View view) {
        main_LST_record = view.findViewById(R.id.main_LST_records);

    }

    public void setCallBackShowOnMaoClicked(RecordCallback callBackShowOnMaoClicked) {
        this.callBackShowOnMaoClicked = callBackShowOnMaoClicked;
    }

}