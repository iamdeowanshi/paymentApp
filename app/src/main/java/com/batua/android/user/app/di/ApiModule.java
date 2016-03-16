package com.batua.android.user.app.di;

import android.content.Context;

import com.batua.android.user.app.Config;
import com.batua.android.user.data.api.AppApi;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.OkHttpClient;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.Endpoint;
import retrofit.Endpoints;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.GsonConverter;

/**
 * Provides dependencies related to the ApiModule.
 *
 * @author Farhan Ali
 */
@Module(
        complete = false,
        library = true
)
public class ApiModule {

    @Provides
    @Singleton
    public AppApi provideApi(RestAdapter restAdapter) {
        return restAdapter.create(AppApi.class);
    }

    @Provides
    @Singleton
    public RestAdapter provideRestAdapter(Endpoint endpoint, OkHttpClient client,
                                          RequestInterceptor interceptor, Gson gson) {
        RestAdapter.Builder builder = new RestAdapter.Builder();

        if (Config.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }

        builder.setClient(new OkClient(client))
                .setEndpoint(endpoint)
                .setRequestInterceptor(interceptor)
                .setConverter(new GsonConverter(gson));

        return builder.build();
    }

    @Provides
    @Named("api_base_url")
    public String provideBaseUrl() {
        return Config.API_BASE_URL;
    }

    @Provides
    @Singleton
    public Endpoint provideEndpoint(@Named("api_base_url") String baseUrl) {
        return Endpoints.newFixedEndpoint(baseUrl);
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(Context context) {
        return createOkHttpClient(context);
    }

    @Provides
    @Singleton
    public RequestInterceptor provideRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                for (Map.Entry<String, String> header : Config.API_HEADERS.entrySet()) {
                    request.addHeader(header.getKey(), header.getValue());
                }
            }
        };
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .serializeNulls()
                .create();
    }

    /**
     * Creates a http cache enable OkHttpClient
     *
     * @param context Application Context
     * @return OkHttpClient
     */
    private static OkHttpClient createOkHttpClient(Context context) {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setReadTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(10, TimeUnit.SECONDS);

        // Install an HTTP cache in the application cache directory.
        File cacheDir = new File(context.getCacheDir(), "http");
        Cache cache = null;
        try {
            cache = new Cache(cacheDir, Config.HTTP_DISK_CACHE_SIZE);
            client.setCache(cache);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return client;
    }

}
