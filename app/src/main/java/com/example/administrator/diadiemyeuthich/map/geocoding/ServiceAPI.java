package com.example.administrator.diadiemyeuthich.map.geocoding;

import com.example.administrator.diadiemyeuthich.map.direction.DirectionRoot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceAPI {

    @GET("geocode/json?&key=AIzaSyBGlqGDVDxU0qrdQiUXcJPOnADOlZNoHIc")
    Call<GeocodingRoot> getLocation(@Query("address") String address);

    @GET("directions/json?&key=AIzaSyBGlqGDVDxU0qrdQiUXcJPOnADOlZNoHIc")
    Call<DirectionRoot> getDirection(@Query("origin") String origin, @Query("destination") String destination);
}
