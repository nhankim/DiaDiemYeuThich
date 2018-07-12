package com.example.administrator.diadiemyeuthich.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.diadiemyeuthich.R;
import com.example.administrator.diadiemyeuthich.Utils.DBRepo;
import com.example.administrator.diadiemyeuthich.model.Place;

public class DetailPlaceActivity extends AppCompatActivity {

    ImageView imgDetailPlace;
    TextView txtPlaceDetailName, txtPlaceAddress, txtPlaceDescription;
    ImageButton btnDeletePlace, btnEditPlace;

    String placeID;
    String categoryID;
    int position;
    DBRepo dbRepo;

    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_place);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnDeletePlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePlace();
            }
        });

        btnEditPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDetail = new Intent(DetailPlaceActivity.this, AddNewPlaceActivity.class);
                intentDetail.putExtra("PlaceID",place.getPlaceID());
                intentDetail.putExtra("CategoryID",place.getPlaceCategoryID());
                intentDetail.putExtra("PlaceName",place.getPlaceName());
                intentDetail.putExtra("PlaceAddress",place.getPlaceAddress());
                intentDetail.putExtra("PlaceDescription",place.getPlaceDescription());
                intentDetail.putExtra("Image",place.getPlaceImage());
                startActivity(intentDetail);
            }
        });

    }

    private void addControls() {
        imgDetailPlace = findViewById(R.id.imgDetailPlace);
        txtPlaceDetailName = findViewById(R.id.txtPlaceDetailName);
        txtPlaceAddress = findViewById(R.id.txtPlaceAddress);
        txtPlaceDescription = findViewById(R.id.txtPlaceDescription);
        btnDeletePlace = findViewById(R.id.btnDeletePlace);
        btnEditPlace = findViewById(R.id.btnEditPlace);

        placeID = getIntent().getStringExtra("PlaceID");
        categoryID = getIntent().getStringExtra("CategoryID");
        position = getIntent().getIntExtra("Position", -1);

        dbRepo = DBRepo.getInstance(this);

        place = PlaceActivity.arrPlace.get(position);
        setPlace();
    }

    private void setPlace() {
        Bitmap bitmap = BitmapFactory.decodeByteArray(place.getPlaceImage(),0,place.getPlaceImage().length);
        imgDetailPlace.setImageBitmap(bitmap);
        txtPlaceDetailName.setText("Tên: " + place.getPlaceName());
        txtPlaceAddress.setText("Địa chỉ: " + place.getPlaceAddress());
        txtPlaceDescription.setText("Mô tả: " + place.getPlaceDescription());
    }

    private void deletePlace() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailPlaceActivity.this);
        builder.setTitle("Warning");
        builder.setMessage("Do you want to delete " + "'" + place.getPlaceName() + "'?");
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                PlaceActivity.deletePlace(position);
                dbRepo.deletePlace(place.getPlaceID());
                finish();
            }
        });
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        builder.show();
    }
}
