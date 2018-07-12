package com.example.administrator.diadiemyeuthich.model;

import android.text.TextUtils;
import android.widget.Toast;

import java.io.Serializable;

public class Place implements Serializable{
    private String placeID;
    private String placeCategoryID;
    private String placeName;
    private String placeAddress;
    private String placeDescription;
    private byte[] placeImage;
    private double placeLat;
    private double placeLng;

    public Place() {
    }

    public Place(String placeID, String placeCategoryID, String placeName, String placeAddress, String placeDescription, byte[] placeImage, double placeLat, double placeLng) {
        this.placeID = placeID;
        this.placeCategoryID = placeCategoryID;
        this.placeName = placeName;
        this.placeAddress = placeAddress;
        this.placeDescription = placeDescription;
        this.placeImage = placeImage;
        this.placeLat = placeLat;
        this.placeLng = placeLng;
    }

    public String getPlaceID() {
        return placeID;
    }

    public void setPlaceID(String placeID) {
        this.placeID = placeID;
    }

    public String getPlaceCategoryID() {
        return placeCategoryID;
    }

    public void setPlaceCategoryID(String placeCategoryID) {
        this.placeCategoryID = placeCategoryID;
    }

    public byte[] getPlaceImage() {
        return placeImage;
    }

    public void setPlaceImage(byte[] placeImage) {
        this.placeImage = placeImage;
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public void setPlaceAddress(String placeAddress) {
        this.placeAddress = placeAddress;
    }

    public String getPlaceDescription() {
        return placeDescription;
    }

    public void setPlaceDescription(String placeDescription) {
        this.placeDescription = placeDescription;
    }

    public double getPlaceLat() {
        return placeLat;
    }

    public void setPlaceLat(double placeLat) {
        this.placeLat = placeLat;
    }

    public double getPlaceLng() {
        return placeLng;
    }

    public void setPlaceLng(double placeLng) {
        this.placeLng = placeLng;
    }

}