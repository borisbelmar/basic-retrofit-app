package com.example.basicretrofitapp.services;

import com.example.basicretrofitapp.dto.AccessTokenDTO;
import com.example.basicretrofitapp.dto.LoginDTO;
import com.example.basicretrofitapp.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("/user/login")
    Call<AccessTokenDTO> login(@Body LoginDTO login);

    @POST("/user/register")
    Call<User> register(@Body User user);
}
