package com.example.task05.NaverMovie;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private final static String BASE_API_URL = "https://openapi.naver.com/v1/search/";
    private static Retrofit mRetrofit = null;
    private static Gson gson = new GsonBuilder().create();

    private static HttpLoggingInterceptor mHttpLoggingIntercepter = new HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder mOkHttpClientBuilder = new OkHttpClient.Builder()
            .addInterceptor(mHttpLoggingIntercepter);

    private static OkHttpClient mOkHttpClient = mOkHttpClientBuilder.build();

    public static <T> T createService(Class<T> serviceClass) {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .client(mOkHttpClient)
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return mRetrofit.create(serviceClass);
    }
}
