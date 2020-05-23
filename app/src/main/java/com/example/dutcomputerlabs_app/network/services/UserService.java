package com.example.dutcomputerlabs_app.network.services;

import com.example.dutcomputerlabs_app.models.Faculty;
import com.example.dutcomputerlabs_app.models.PasswordToUpdate;
import com.example.dutcomputerlabs_app.models.UserForDetailed;
import com.example.dutcomputerlabs_app.models.UserForInsert;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @GET("/api/users/{id}")
    Call<UserForDetailed> getUser(@Header("Authorization") String token, @Path("id") int id);

    @POST("/api/users/{id}/password")
    Call<Void> changePassword(@Header("Authorization") String token, @Path("id") int id, @Body PasswordToUpdate passwordToUpdate);

    @GET("/api/users/faculties")
    Call<List<Faculty>> getFaculties(@Header("Authorization") String token);

    @PUT("/api/users/{id}/info")
    Call<UserForDetailed> updateUserInfo(@Header("Authorization") String token,@Path("id") int id, @Body UserForInsert userForInsert);
}
