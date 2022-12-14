package com.example.plants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("Plants")
    Call<DataModel> createPost(@Body DataModel dataModal);

}
