package com.tecsol.batua.user.injection.module;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tecsol.batua.user.Config;
import com.tecsol.batua.user.data.api.ApiErrorParser;
import com.tecsol.batua.user.data.api.BatuaUserService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Provides dependencies related to the ApiModule.
 *
 * @author Farhan Ali
 */
@Module
public class ApiModule {

    @Provides
    @Singleton
    public BatuaUserService provideApi(
            OkHttpClient client,
            Converter.Factory converterFactory,
            CallAdapter.Factory callAdapterFactory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Config.API_BASE_URL)
                .client(client)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .build();

        return retrofit.create(BatuaUserService.class);
    }

    @Provides
    public ApiErrorParser provideErrorParser() {
        return new ApiErrorParser();
    }

    @Provides
    @Singleton
    public Converter.Factory provideConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    public Gson provideGson() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                .serializeNulls()
                .create();
    }

    @Provides
    @Singleton
    public CallAdapter.Factory provideCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    @Singleton
    public OkHttpClient provideOkHttpClient(List<Interceptor> interceptors) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        for (Interceptor interceptor : interceptors) {
            builder.addInterceptor(interceptor);
        }

        return builder.build();
    }

    @Provides
    @Singleton
    public List<Interceptor> provideInterceptors() {
        List<Interceptor> interceptors = new ArrayList<>();
        // add header interceptor
        interceptors.add(getHeaderInterceptor());

        // add logging interceptor
        if (Config.DEBUG) {
            interceptors.add(getLoggingInterceptor());
        }

        return interceptors;
    }

    private Interceptor getHeaderInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();

                Map<String, String> headers =  getHeadersAfterAnnotatedSkip(chain.request().headers());
                for (Map.Entry<String, String> header : headers.entrySet()) {
                    builder.addHeader(header.getKey(), header.getValue());
                }

                return chain.proceed(builder.build());
            }
        };
    }

    private Map<String, String> getHeadersAfterAnnotatedSkip(Headers annotatedHeaders) {
        Map<String, String> configHeaders =  new HashMap<>(Config.API_HEADERS);

        for (String headerName : annotatedHeaders.names()) {
            configHeaders.remove(headerName);
        }

        return configHeaders;
    }

    private Interceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return  interceptor;
    }

}
