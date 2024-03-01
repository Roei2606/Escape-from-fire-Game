package com.example.a24a10357roeihakmon206387128_task1.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a24a10357roeihakmon206387128_task1.Interfaces.RecordCallback;
import com.example.a24a10357roeihakmon206387128_task1.MainActivity;
import com.example.a24a10357roeihakmon206387128_task1.Modeles.Record;
import com.example.a24a10357roeihakmon206387128_task1.R;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {
    private Context context;
    private ArrayList<Record> records;
    private RecordCallback recordCallback;
    private Location location;


    public RecordAdapter(Context context, ArrayList<Record> records) {
        this.context = context;
        this.records = records;
    }

    public RecordAdapter setRecordCallback(RecordCallback recordCallback) {
        this.recordCallback = recordCallback;
        return this;
    }

    @NonNull
    @Override
    public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_record_item,parent,false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordViewHolder holder, int position) {
        Record record = getItem(position);
        holder.record_LBL_rank.setText(record.getRank()+"");
        holder.record_LBL.setText(record.getScore()+"");
        holder.record_IMGBTTN_map.setImageResource(R.drawable.google_maps);

    }

    @Override
    public int getItemCount() {
        return records == null ? 0 : records.size();
    }
    private Record getItem(int position){
        return records.get(position);
    }



    public class RecordViewHolder extends RecyclerView.ViewHolder{
        private MaterialTextView record_LBL_rank;
        private MaterialTextView record_LBL;
        private ImageButton record_IMGBTTN_map;
        public RecordViewHolder(@NonNull View itemView){
            super(itemView);
            record_LBL_rank = itemView.findViewById(R.id.record_LBL_rank);
            record_LBL = itemView.findViewById(R.id.record_LBL);
            record_IMGBTTN_map = itemView.findViewById(R.id.record_IMGBTTN_map);
            record_IMGBTTN_map.setOnClickListener(v->{
                if(recordCallback !=null){
                    recordCallback.showOnMapButtonClicked(getItem(getAdapterPosition()),getAdapterPosition());
                }
            });
        }
    }
}
