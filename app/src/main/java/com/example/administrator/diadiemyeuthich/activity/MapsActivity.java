package com.example.administrator.diadiemyeuthich.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.diadiemyeuthich.R;
import com.example.administrator.diadiemyeuthich.Utils.DBRepo;
import com.example.administrator.diadiemyeuthich.map.direction.DirectionRoot;
import com.example.administrator.diadiemyeuthich.map.geocoding.GeocodingRoot;
import com.example.administrator.diadiemyeuthich.map.geocoding.Location;
import com.example.administrator.diadiemyeuthich.map.geocoding.ServiceAPI;
import com.example.administrator.diadiemyeuthich.model.Place;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    GoogleMap googleMap;
    private GoogleApiClient apiClient;
    private LocationRequest locationRequest;
    private android.location.Location currentLocation;

    private DBRepo dbRepo;
    private ArrayList<Place> places = new ArrayList<>();
    private String categoryID;
    private Retrofit retrofit;

    private static final int GPS_PERMISSION_REQUEST_CODE = 1;
    private String BASE_URL = "https://maps.googleapis.com/maps/api/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        categoryID = getIntent().getStringExtra("CategoryID");
        dbRepo = DBRepo.getInstance(this);
        places = dbRepo.getPlaceByCategoryID(categoryID);

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    private void setApiClient() {
        apiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        apiClient.connect();

    }

    private void displayPlaceOnMap() {
        for (Place place : places) {
            LatLng marker = new LatLng(place.getPlaceLat(), place.getPlaceLng());
            googleMap.addMarker(new MarkerOptions().position(marker)
                    .title(place.getPlaceName()));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker,12));
        }
    }

    private void getDirection(LatLng origin, LatLng destination) {
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        String originAddress = String.valueOf(origin.latitude) + "," + String.valueOf(origin.longitude);
        String destinationAddress = String.valueOf(destination.latitude) + "," + String.valueOf(destination.longitude);
        Call<DirectionRoot> call = serviceAPI.getDirection(originAddress, destinationAddress);
        call.enqueue(new Callback<DirectionRoot>() {
            @Override
            public void onResponse(Call<DirectionRoot> call, Response<DirectionRoot> response) {
                DirectionRoot directionRoot = response.body();
                String polylines = directionRoot.getRoutes().get(0).getOverview_polyline().getPoints();

                List<LatLng> decodePath = PolyUtil.decode(polylines);
                googleMap.addPolyline(new PolylineOptions().addAll(decodePath));
            }

            @Override
            public void onFailure(Call<DirectionRoot> call, Throwable t) {

            }
        });
    }


    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(apiClient, locationRequest, new LocationListener() {
                @Override
                public void onLocationChanged(android.location.Location location) {
                    currentLocation = location;

                    if (apiClient != null) {
                        LocationServices.FusedLocationApi.removeLocationUpdates(apiClient, this);
                    }
                }
            });
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GPS_PERMISSION_REQUEST_CODE:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (apiClient == null) {
                            setApiClient();
                        }
                        googleMap.setMyLocationEnabled(true);
                    }else {
                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        this.googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng currentLatLng = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
                getDirection(currentLatLng,marker.getPosition());
                return false;
            }
        });
        displayPlaceOnMap();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setApiClient();
            this.googleMap.setMyLocationEnabled(true);
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},GPS_PERMISSION_REQUEST_CODE);
            }
        }


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        getLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

}
