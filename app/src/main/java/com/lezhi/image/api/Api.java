package com.lezhi.image.api;


import com.lezhi.image.api.model.Pins;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by lezhi on 2017/2/26.
 */

public interface Api {

    String IMAGE_BASE_URL = "http://img.hb.aicdn.com/";

    @GET("favorite/{type}")
    Observable<Pins> getPinsLimit(@Path("type") String type, @Query("limit") int limit);

    @GET("favorite/{type}")
    Observable<Pins> getPinsMaxLimit(@Path("type") String type, @Query("max") String max, @Query("limit") int limit);

}
