package com.example.a24a10357roeihakmon206387128_task1.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.a24a10357roeihakmon206387128_task1.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapFragment extends Fragment  {

    //private MapView mapFragment;
    private GoogleMap myMap;
    private LatLng currentLocation;

    public MapFragment(){
    }
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_map_fragment, container, false);
        SupportMapFragment supportMapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_view));
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                myMap = googleMap;
            }
        });
        return view;
    }

    public void showOnMapClicked(double latitude, double longitude){
        currentLocation = new LatLng(latitude, longitude);
        System.out.println(currentLocation.latitude + " ' " + currentLocation.longitude);
        this.myMap.addMarker(new MarkerOptions().position(currentLocation).title("Current location"));
        this.myMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation));
    }


}