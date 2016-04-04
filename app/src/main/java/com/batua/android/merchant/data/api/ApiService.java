package com.batua.android.merchant.data.api;

import com.batua.android.merchant.data.model.Task;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * @author Farhan Ali
 */
public interface ApiService {

    @GET("tasks")
    Observable<Response<List<Task>>> getTasks();

}
