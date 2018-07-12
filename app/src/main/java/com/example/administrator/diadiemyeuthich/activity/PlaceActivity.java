package com.example.administrator.diadiemyeuthich.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.diadiemyeuthich.R;
import com.example.administrator.diadiemyeuthich.Utils.DBManager;
import com.example.administrator.diadiemyeuthich.Utils.DBRepo;
import com.example.administrator.diadiemyeuthich.adapter.PlaceAdapter;
import com.example.administrator.diadiemyeuthich.model.Place;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlaceActivity extends AppCompatActivity {

    Button btnShowMap;
    FloatingActionButton btnAddNewPlace;
    TextView txtThongBao;
    ListView lvPlace;
    public static PlaceAdapter placeAdapter;
    public static ArrayList<Place> arrPlace;

    public static String categoryID;
    DBRepo dbRepo;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        addControls();
        addEvents();
    }

    private void addEvents() {
        lvPlace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlaceActivity.this, DetailPlaceActivity.class);
                intent.putExtra("PlaceID", arrPlace.get(position).getPlaceID());
                intent.putExtra("CategoryID", arrPlace.get(position).getPlaceCategoryID());
                intent.putExtra("Position",position);
                intent.putExtra("Image",arrPlace.get(position).getPlaceImage());
                startActivity(intent);
            }
        });

        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceActivity.this, MapsActivity.class);
                intent.putExtra("CategoryID",categoryID);
                startActivity(intent);
            }
        });

        btnAddNewPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlaceActivity.this, AddNewPlaceActivity.class);
                intent.putExtra("CategoryID",categoryID);
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        categoryID = getIntent().getStringExtra("CategoryID");

        btnShowMap = findViewById(R.id.btnShowMap);
        btnAddNewPlace = findViewById(R.id.btnAddNewPlace);
        txtThongBao = findViewById(R.id.txtThongBao);
        lvPlace = findViewById(R.id.lvPlace);

        dbRepo = DBRepo.getInstance(this);

        arrPlace = new ArrayList<>();
        arrPlace = dbRepo.getPlaceByCategoryID(categoryID);
        placeAdapter = new PlaceAdapter(PlaceActivity.this, R.layout.place_item, arrPlace);
        lvPlace.setAdapter(placeAdapter);

        if (!arrPlace.isEmpty()) {
            txtThongBao.setVisibility(View.INVISIBLE);
        }

    }

    public static void deletePlace(int position) {
        arrPlace.remove(position);
        placeAdapter.notifyDataSetChanged();
    }

}
