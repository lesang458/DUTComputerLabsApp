package com.example.dutcomputerlabs_app.network.services;

import com.example.dutcomputerlabs_app.models.FeedbackForDetailed;
import com.example.dutcomputerlabs_app.models.FeedbackForInsert;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface FeedbackService {
    @POST("/api/feedbacks")
    Call<FeedbackForDetailed> addFeedback(@Header("Authorization") String token, @Body FeedbackForInsert feedback);

    @GET("/api/feedbacks/lab/{id}/pages")
    Call<ResponseBody> getTotalPages(@Header("Authorization") String token, @Path("id") int id);

    @GET("/api/feedbacks/lab/{id}")
    Call<List<FeedbackForDetailed>> getFeedbacks(@Header("Authorization") String token, @Path("id") int id, @Query("pageNumber") int page);

}
