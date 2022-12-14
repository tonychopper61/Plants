package com.example.plants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPIUpdate {
    @PUT("Plants/")
    Call<DataModel> updateData(@Query("id")int id, @Body DataModel dataModal);

}
