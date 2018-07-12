package com.example.administrator.diadiemyeuthich.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.diadiemyeuthich.R;
import com.example.administrator.diadiemyeuthich.activity.CategoryActivity;
import com.example.administrator.diadiemyeuthich.model.Category;

import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {

    @NonNull Activity context;
    int resource;
    @NonNull List<Category> objects;

    public CategoryAdapter(@NonNull Activity context, int resource, @NonNull List<Category> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);

        TextView txtCategoryLeft = row.findViewById(R.id.txtCategoryLeft);
        TextView txtCategoryRight = row.findViewById(R.id.txtCategoryRight);
        ImageView imgCategoryLeft = row.findViewById(R.id.imgCategoryLeft);
        ImageView imgCategoryRight = row.findViewById(R.id.imgCategoryRight);

        Category category = this.objects.get(position);
        if (position%2 != 0)
        {
            txtCategoryRight.setVisibility(View.INVISIBLE);
            imgCategoryLeft.setVisibility(View.INVISIBLE);
            txtCategoryLeft.setText(category.getCategoryName());
            imgCategoryRight.setImageResource(CategoryActivity.imgCategory.get(position));
        }else {
            txtCategoryLeft.setVisibility(View.INVISIBLE);
            imgCategoryRight.setVisibility(View.INVISIBLE);
            txtCategoryRight.setText(category.getCategoryName());
            imgCategoryLeft.setImageResource(CategoryActivity.imgCategory.get(position));
        }
        return row;
    }
}
