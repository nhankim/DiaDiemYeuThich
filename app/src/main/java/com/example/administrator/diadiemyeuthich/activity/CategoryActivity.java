package com.example.administrator.diadiemyeuthich.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.diadiemyeuthich.R;
import com.example.administrator.diadiemyeuthich.Utils.DBManager;
import com.example.administrator.diadiemyeuthich.adapter.CategoryAdapter;
import com.example.administrator.diadiemyeuthich.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    public static ArrayList<Integer> imgCategory;

    ListView lvCategory;
    ArrayList<Category> category;
    CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        addControls();
        addEvents();
    }

    private void addEvents() {
        lvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CategoryActivity.this, PlaceActivity.class);
                intent.putExtra("CategoryID", category.get(position).getCategoryID());
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        lvCategory = findViewById(R.id.lvCategory);

        imgCategory = new ArrayList<>();
        imgCategory.add(R.drawable.restaurant_icon);
        imgCategory.add(R.drawable.cinema);
        imgCategory.add(R.drawable.gas_icon);
        imgCategory.add(R.drawable.atm_icon);
        imgCategory.add(R.drawable.park_icon);
        imgCategory.add(R.drawable.fashion_icon);

        category = new ArrayList<>();
        category.add(new Category("1", "Restaurant"));
        category.add(new Category("2", "Cinema"));
        category.add(new Category("3", "Gas Station"));
        category.add(new Category("4", "ATM"));
        category.add(new Category("5", "Park"));
        category.add(new Category("6","Fashion"));
        categoryAdapter = new CategoryAdapter(CategoryActivity.this, R.layout.category_item, category);
        lvCategory.setAdapter(categoryAdapter);

    }
}
