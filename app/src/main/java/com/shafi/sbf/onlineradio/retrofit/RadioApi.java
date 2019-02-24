package com.shafi.sbf.onlineradio.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface RadioApi {
    @Headers({
            "Content-Type: application/json"
    })
    @GET("apk/items.json")
    Call<RadioAll> getAllData();
}
