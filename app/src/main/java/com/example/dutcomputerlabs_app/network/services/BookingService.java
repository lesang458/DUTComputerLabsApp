package com.example.dutcomputerlabs_app.network.services;

import com.example.dutcomputerlabs_app.models.BookingForInsert;
import com.example.dutcomputerlabs_app.models.ComputerLab;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BookingService {
    @GET("/api/computerlabs/search")
    Call<List<ComputerLab>> search(@Header("Authorization") String token,@Query("BookingDate") String BookingDate, @Query("StartAt") int StartAt, @Query("EndAt") int EndAt, @Query("EditMode") int EditMode);

    @POST("/api/bookings")
    Call<Void> addBooking(@Header("Authorization") String token, @Body BookingForInsert booking);
}
