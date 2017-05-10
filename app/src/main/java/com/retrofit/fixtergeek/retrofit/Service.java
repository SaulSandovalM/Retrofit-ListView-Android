package com.retrofit.fixtergeek.retrofit;

import com.retrofit.fixtergeek.retrofit.modelos.Catalogo;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by fixtergeek on 09/05/17.
 */

public interface Service {

    public static final String BASE_URL = "https://www.udacity.com/public-api/v0/";

    @GET("courses")
    Call<Catalogo> listaCatalogo();
}
