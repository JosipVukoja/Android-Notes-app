package com.practice.josip.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/*public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
    }
    private static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    //vars
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getLocationPermission();
        initMap();
    }
    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toast.makeText(this, "initializing map", Toast.LENGTH_SHORT).show();
    }
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;
        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }
}*/


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double latitude;
    Double longitude;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             if (extras.get("previous").equals("newNote")) {

                mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        latitude = latLng.latitude;
                        longitude = latLng.longitude;
                        Intent intent = new Intent(MapsActivity.this, NewNoteActivity.class);
                        intent.putExtra("latitude", latitude);
                        intent.putExtra("longitude", longitude);
                        startActivity(intent);
                    }
                });
            } else if(extras.get("previous").equals("updateNote")) {
                 id = extras.getInt("noteId");
                 Note openedNote = DataStorage.getNoteById(id);
                 //latitude = openedNote.getLatitude();
                 //longitude = openedNote.getLongitude();
                 latitude = 1.1;
                 longitude = 1.2;
                 LatLng pos = new LatLng(latitude, longitude);
                 mMap.addMarker(new MarkerOptions().position(pos).title("Location"));
                 mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,10));
                 mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                     @Override
                     public void onMapClick(LatLng latLng) {
                         latitude = latLng.latitude;
                         longitude = latLng.longitude;
                         Intent intent = new Intent(MapsActivity.this, UpdateNoteActivity.class);
                         intent.putExtra("latitude", latitude);
                         intent.putExtra("longitude", longitude);
                         intent.putExtra("noteId",id);
                         startActivity(intent);
                     }
                 });

             } else if(extras.get("previous").equals("openNote")) {
                 id = extras.getInt("noteId");
                 Note openedNote = DataStorage.getNoteById(id);
                 //latitude = openedNote.getLatitude();
                 //longitude = openedNote.getLongitude();
                 latitude = 1.1;
                 longitude = 1.1;
                 LatLng pos = new LatLng(latitude, longitude);
                 mMap.addMarker(new MarkerOptions().position(pos).title("Location"));
                 mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,10));
             } else {
                 Toast.makeText(getApplicationContext(), "Something wrong has happened!", Toast.LENGTH_LONG).show();
             }
        } else {
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
}