package com.example.administrator.diadiemyeuthich.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.diadiemyeuthich.R;
import com.example.administrator.diadiemyeuthich.Utils.DBRepo;
import com.example.administrator.diadiemyeuthich.map.geocoding.GeocodingRoot;
import com.example.administrator.diadiemyeuthich.map.geocoding.Location;
import com.example.administrator.diadiemyeuthich.map.geocoding.ServiceAPI;
import com.example.administrator.diadiemyeuthich.model.Place;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNewPlaceActivity extends AppCompatActivity {

    ImageView imgEditPlace;
    EditText edtPlaceName, edtPlaceAddress, edtPlaceDescription;
    Button btnSave;

    private String categoryID;
    private String placeID;
    private byte[] placeImage;

    boolean imageCapture = false;

    private static final int IMAGE_CAPTURE_REQUEST_CODE = 1;
    private String BASE_URL = "https://maps.googleapis.com/maps/api/";

    private Retrofit retrofit;
    Location location;
    private DBRepo dbRepo;
    Place place;



    private String placeName, placeAddress, placeDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_place);
        addControls();
        addEvents();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data == null) {
                imageCapture = false;
            } else {
                Bitmap placeImage = (Bitmap) data.getExtras().get("data");
                imgEditPlace.setImageBitmap(placeImage);
                imageCapture = true;
            }
        }
    }



    private void addEvents() {
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //savePlace();
                getLatLng();
            }
        });

        imgEditPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImageCamera();
            }
        });

    }

    private void addControls() {
        imgEditPlace = findViewById(R.id.imgEditPlace);
        edtPlaceName = findViewById(R.id.edtPlaceName);
        edtPlaceAddress = findViewById(R.id.edtPlaceAddress);
        edtPlaceDescription = findViewById(R.id.edtPlaceDescription);
        btnSave = findViewById(R.id.btnSave);

        placeID = getIntent().getStringExtra("PlaceID"); //intentDetails
        categoryID = getIntent().getStringExtra("CategoryID"); //intentPlace
        placeImage = getIntent().getByteArrayExtra("Image"); //intentDetails


        dbRepo = DBRepo.getInstance(this);
        location = new Location();
        place = new Place();


        if (placeID != null) {
            imageCapture = true;
            edtPlaceName.setText(getIntent().getStringExtra("PlaceName"));
            edtPlaceAddress.setText(getIntent().getStringExtra("PlaceAddress"));
            edtPlaceDescription.setText(getIntent().getStringExtra("PlaceDescription"));
            Bitmap bitmap = BitmapFactory.decodeByteArray(placeImage, 0, placeImage.length);
            imgEditPlace.setImageBitmap(bitmap);
        }

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }

    /*private void getLatLng(String address) {
        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<GeocodingRoot> call = serviceAPI.getLocation(address);
        call.enqueue(new Callback<GeocodingRoot>() {
            @Override
            public void onResponse(Call<GeocodingRoot> call, Response<GeocodingRoot> response) {
                GeocodingRoot geocodingRoot = response.body();
                double lat = geocodingRoot.getResults().get(0).getGeometry().getLocation().getLat();
                double lng = geocodingRoot.getResults().get(0).getGeometry().getLocation().getLng();
                location.setLat(lat);
                location.setLng(lng);
                Log.d("Location", location.getLat() + " && " + location.getLng());

            }

            @Override
            public void onFailure(Call<GeocodingRoot> call, Throwable t) {

            }
        });
    }*/

    /*private void savePlace() {
        placeName = edtPlaceName.getText().toString();
        placeAddress = edtPlaceAddress.getText().toString();
        placeDescription = edtPlaceDescription.getText().toString();

        if (placeName.length() > 0 && placeAddress.length() > 0 && placeDescription.length() > 0 && categoryID.length() > 0) {

            getLatLng(placeAddress);

            if (placeID == null && imageCapture) {

                place.setPlaceID(UUID.randomUUID().toString());
                place.setPlaceCategoryID(categoryID);
                place.setPlaceName(placeName);
                place.setPlaceAddress(placeAddress);
                place.setPlaceDescription(placeDescription);
                place.setPlaceImage(convertImageViewToByteArray(imgEditPlace));
                place.setPlaceLat(location.getLat());
                place.setPlaceLng(location.getLng());
                Log.d("Place1",place.getPlaceLat() + " - " + place.getPlaceLng());
                dbRepo.insertPlace(place);
                redirectPlace();

            }
            else if (placeID != null) {
                place = new Place();
                place.setPlaceID(placeID);
                place.setPlaceCategoryID(categoryID);
                place.setPlaceName(placeName);
                place.setPlaceAddress(placeAddress);
                place.setPlaceDescription(placeDescription);
                place.setPlaceImage(convertImageViewToByteArray(imgEditPlace));
                *//*if (imageCapture) {
                    place.setPlaceImage(convertImageViewToByteArray(imgEditPlace));
                } else {
                    place.setPlaceImage(placeImage);
                }*//*
                place.setPlaceLat(location.getLat());
                place.setPlaceLng(location.getLng());

                dbRepo.updatePlace(place);
                redirectPlace();
                Log.d("Place",place.getPlaceLat() + " - " + place.getPlaceLng());
            }
            else {
                Toast.makeText(AddNewPlaceActivity.this, "Please fill in place's information", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(AddNewPlaceActivity.this, "Please fill in place's information", Toast.LENGTH_SHORT).show();
        }
    }*/

    private void getImageCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_REQUEST_CODE);
    }

    private byte[] convertImageViewToByteArray(ImageView imageView) {
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        return out.toByteArray();
    }

    private void redirectPlace() {
        Intent intent = new Intent(AddNewPlaceActivity.this, PlaceActivity.class);
        intent.putExtra("CategoryID", categoryID);
        startActivity(intent);
        finish();
    }

    private void getLatLng() {
        placeName = edtPlaceName.getText().toString();
        placeAddress = edtPlaceAddress.getText().toString();
        placeDescription = edtPlaceDescription.getText().toString();

        ServiceAPI serviceAPI = retrofit.create(ServiceAPI.class);
        Call<GeocodingRoot> call = serviceAPI.getLocation(placeAddress);
        call.enqueue(new Callback<GeocodingRoot>() {
            @Override
            public void onResponse(Call<GeocodingRoot> call, Response<GeocodingRoot> response) {
                GeocodingRoot geocodingRoot = response.body();
                double lat = geocodingRoot.getResults().get(0).getGeometry().getLocation().getLat();
                double lng = geocodingRoot.getResults().get(0).getGeometry().getLocation().getLng();
                location.setLat(lat);
                location.setLng(lng);
                Log.d("Location", location.getLat() + " && " + location.getLng());

                if (placeName.length() > 0 && placeAddress.length() > 0 && placeDescription.length() > 0 && categoryID.length() > 0) {

                    if (placeID == null && imageCapture) {

                        place.setPlaceID(UUID.randomUUID().toString());
                        place.setPlaceCategoryID(categoryID);
                        place.setPlaceName(placeName);
                        place.setPlaceAddress(placeAddress);
                        place.setPlaceDescription(placeDescription);
                        place.setPlaceImage(convertImageViewToByteArray(imgEditPlace));
                        place.setPlaceLat(location.getLat());
                        place.setPlaceLng(location.getLng());
                        Log.d("Place1", place.getPlaceLat() + " - " + place.getPlaceLng());
                        dbRepo.insertPlace(place);
                        redirectPlace();

                    } else if (placeID != null) {
                        place = new Place();
                        place.setPlaceID(placeID);
                        place.setPlaceCategoryID(categoryID);
                        place.setPlaceName(placeName);
                        place.setPlaceAddress(placeAddress);
                        place.setPlaceDescription(placeDescription);
                        place.setPlaceImage(convertImageViewToByteArray(imgEditPlace));
                        place.setPlaceLat(location.getLat());
                        place.setPlaceLng(location.getLng());

                        dbRepo.updatePlace(place);
                        redirectPlace();
                        Log.d("Place", place.getPlaceLat() + " - " + place.getPlaceLng());
                    } else {
                        Toast.makeText(AddNewPlaceActivity.this, "Please fill in place's information", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(AddNewPlaceActivity.this, "Please fill in place's information", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeocodingRoot> call, Throwable t) {

            }
        });

}
    }


