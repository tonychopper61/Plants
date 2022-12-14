package com.example.plants;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

public interface RetrofitAPIDelete {
    @DELETE("Plants/")
    Call<DataModel> deleteData(@Query("id")int ID);
}
