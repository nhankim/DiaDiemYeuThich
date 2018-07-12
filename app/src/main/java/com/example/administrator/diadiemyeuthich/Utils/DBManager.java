package com.example.administrator.diadiemyeuthich.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.administrator.diadiemyeuthich.model.Category;
import com.example.administrator.diadiemyeuthich.model.Place;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private static final String DB_NAME = "PLACE";
    private static final int DB_VERSION = 3;

    private static final String CREATE_PLACE_TABLE =
            "CREATE TABLE " + DBUtils.PLACE_TABLE_NAME + "("
                    + DBUtils.COLLUMN_PLACE_ID + " " + DBUtils.TEXT_DATA_TYPE + " " + DBUtils.PRIMARY_KEY + ", "
                    + DBUtils.COLLUMN_PLACE_CATEGORY_ID + " " + DBUtils.TEXT_DATA_TYPE + " " + DBUtils.NOT_NULL + ", "
                    + DBUtils.COLLUMN_PLACE_NAME + " " + DBUtils.TEXT_DATA_TYPE + " " + DBUtils.NOT_NULL + ", "
                    + DBUtils.COLLUMN_PLACE_ADDRESS + " " + DBUtils.TEXT_DATA_TYPE + " " + DBUtils.NOT_NULL + ", "
                    + DBUtils.COLLUMN_PLACE_DESCRIPTIOM + " " + DBUtils.TEXT_DATA_TYPE + " " + DBUtils.NOT_NULL + ", "
                    + DBUtils.COLLUMN_PLACE_IMAGE + " " + DBUtils.BLOB_DATA_TYPE + " " + DBUtils.NOT_NULL + ", "
                    + DBUtils.COLLUMN_PLACE_LAT + " " + DBUtils.REAL_DATA_TYPE + " " + DBUtils.NOT_NULL + ", "
                    + DBUtils.COLLUMN_PLACE_LNG + " " + DBUtils.REAL_DATA_TYPE + " " + DBUtils.NOT_NULL
                    + ")";


    public DBManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_PLACE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

   /* public void insertPlace(Place place) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.COLLUMN_PLACE_ID, place.getPlaceID());
        contentValues.put(DBUtils.COLLUMN_PLACE_CATEGORY_ID, place.getPlaceCategoryID());
        contentValues.put(DBUtils.COLLUMN_PLACE_NAME, place.getPlaceName());
        contentValues.put(DBUtils.COLLUMN_PLACE_ADDRESS, place.getPlaceAddress());
        contentValues.put(DBUtils.COLLUMN_PLACE_DESCRIPTIOM, place.getPlaceDescription());
        contentValues.put(DBUtils.COLLUMN_PLACE_IMAGE, place.getPlaceImage());
        contentValues.put(DBUtils.COLLUMN_PLACE_LAT, place.getPlaceLat());
        contentValues.put(DBUtils.COLLUMN_PLACE_LNG, place.getPlaceLng());

        database.insert(DBUtils.PLACE_TABLE_NAME, null, contentValues);
        database.close();
    }

    public void updatePlace(Place place) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DBUtils.COLLUMN_PLACE_ID, place.getPlaceID());
        contentValues.put(DBUtils.COLLUMN_PLACE_CATEGORY_ID, place.getPlaceCategoryID());
        contentValues.put(DBUtils.COLLUMN_PLACE_IMAGE, place.getPlaceImage());
        contentValues.put(DBUtils.COLLUMN_PLACE_NAME, place.getPlaceName());
        contentValues.put(DBUtils.COLLUMN_PLACE_ADDRESS, place.getPlaceAddress());
        contentValues.put(DBUtils.COLLUMN_PLACE_DESCRIPTIOM, place.getPlaceDescription());
        contentValues.put(DBUtils.COLLUMN_PLACE_LAT, place.getPlaceLat());
        contentValues.put(DBUtils.COLLUMN_PLACE_LNG, place.getPlaceLng());

        String selection = DBUtils.COLLUMN_PLACE_ID + " = ?";
        String[] selectionArgs = {place.getPlaceID()};

        database.update(DBUtils.PLACE_TABLE_NAME, contentValues, selection, selectionArgs);
        database.close();
    }

    public void deletePlace(String placeID) {
        SQLiteDatabase database = this.getWritableDatabase();

        String selection = DBUtils.COLLUMN_PLACE_ID + " = ?";
        String[] selectionArgs = {placeID};

        database.delete(DBUtils.PLACE_TABLE_NAME, selection, selectionArgs);
        database.close();
    }


    public ArrayList<Place> getPlace() {
        ArrayList<Place> listPlace = new ArrayList<Place>();

        String selectQuery = "SELECT * FROM " + DBUtils.PLACE_TABLE_NAME;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Place place = new Place();
                place.setPlaceID(cursor.getString(0));
                place.setPlaceCategoryID(cursor.getString(1));
                place.setPlaceName(cursor.getString(2));
                place.setPlaceAddress(cursor.getString(3));
                place.setPlaceDescription(cursor.getString(4));
                place.setPlaceImage(cursor.getBlob(5));
                place.setPlaceLat(cursor.getDouble(6));
                place.setPlaceLng(cursor.getDouble(7));

                listPlace.add(place);
            }
        }
        cursor.close();
        database.close();
        return listPlace;
    }


    public List<Place> getPlaceByCategoryID(String categoryID) {

        List<Place> listPlace = new ArrayList<Place>();
        String collumn[] = {
                DBUtils.COLLUMN_PLACE_ID,
                DBUtils.COLLUMN_PLACE_CATEGORY_ID,
                DBUtils.COLLUMN_PLACE_IMAGE,
                DBUtils.COLLUMN_PLACE_NAME,
                DBUtils.COLLUMN_PLACE_ADDRESS,
                DBUtils.COLLUMN_PLACE_DESCRIPTIOM,
                DBUtils.COLLUMN_PLACE_LAT,
                DBUtils.COLLUMN_PLACE_LNG
        };

        String selection = DBUtils.COLLUMN_PLACE_CATEGORY_ID + " = ?";
        String[] selectionArgs = {categoryID};

        SQLiteDatabase data = this.getReadableDatabase();
        Cursor cursor = data.query(DBUtils.PLACE_TABLE_NAME, collumn, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                Place place = new Place();
                place.setPlaceID(cursor.getString(0));
                place.setPlaceCategoryID(cursor.getString(1));
                place.setPlaceName(cursor.getString(2));
                place.setPlaceAddress(cursor.getString(3));
                place.setPlaceDescription(cursor.getString(4));
                place.setPlaceImage(cursor.getBlob(5));
                place.setPlaceLat(cursor.getDouble(6));
                place.setPlaceLng(cursor.getDouble(7));

                listPlace.add(place);
            }
        }
            cursor.close();
            data.close();
            return listPlace;
    }*/
}
