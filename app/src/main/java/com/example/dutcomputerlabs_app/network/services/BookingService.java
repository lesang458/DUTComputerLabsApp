package com.example.dutcomputerlabs_app.network.services;

import com.example.dutcomputerlabs_app.models.BookingForDetailed;
import com.example.dutcomputerlabs_app.models.BookingForInsert;
import com.example.dutcomputerlabs_app.models.ComputerLabForList;

import org.json.JSONObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BookingService {
    @GET("/api/computerlabs/search")
    Call<List<ComputerLabForList>> search(@Header("Authorization") String token, @Query("BookingDate") String BookingDate, @Query("StartAt") int StartAt, @Query("EndAt") int EndAt, @Query("EditMode") int EditMode);

    @POST("/api/bookings")
    Call<Void> addBooking(@Header("Authorization") String token, @Body BookingForInsert booking);

    @GET("/api/bookings/pages")
    Call<ResponseBody> getTotalPages(@Header("Authorization") String token);

    @GET("/api/bookings/booker")
    Call<List<BookingForDetailed>> getBookings(@Header("Authorization") String token,@Query("pageNumber") int page);
}
