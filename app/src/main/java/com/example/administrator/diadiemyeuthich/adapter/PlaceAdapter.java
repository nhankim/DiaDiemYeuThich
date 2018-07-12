package com.example.administrator.diadiemyeuthich.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.diadiemyeuthich.R;
import com.example.administrator.diadiemyeuthich.model.Place;

import java.util.List;

public class PlaceAdapter extends ArrayAdapter<Place> {

    @NonNull Activity context;
    int resource;
    @NonNull List<Place> objects;
    public PlaceAdapter(@NonNull Activity context, int resource, @NonNull List<Place> objects) {
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

        ImageView imgPlace = row.findViewById(R.id.imgPlace);
        TextView txtPlaceName = row.findViewById(R.id.txtPlaceName);

        Place place = this.objects.get(position);
        Bitmap bitmap = BitmapFactory.decodeByteArray(place.getPlaceImage(),0, place.getPlaceImage().length);
        imgPlace.setImageBitmap(bitmap);
        txtPlaceName.setText(place.getPlaceName());

        return row;
    }

}
