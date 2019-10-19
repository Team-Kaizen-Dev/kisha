package com.kaizen.team.kishaapp.core.ws;


import com.kaizen.team.kishaapp.BuildConfig;
import com.satellite.retrofit.retrofit.errors.NullOnEmptyConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jesli Albert Bautista on 10/19/2019.
 * jesli.bautista@safesat.com.ph
 * Satellite GPS (GPS Tracking and Asset Management System)
 */
public class RestHelper {

    private static RestHelper INSTANCE;

    private RestHelper() {
    }

    public static RestHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RestHelper();
        }
        return INSTANCE;
    }


    public <T> T createAuthApiInstance(Class<T> responseClass) {
        return new Retrofit.Builder()
//                .baseUrl(BuildConfig.AUTH_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .client(okHttpClient())
                .build().create(responseClass);
    }

    public <T> T createApiInstance(Class<T> responseClass) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.KISHA_API)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .client(okHttpClient())
                .build().create(responseClass);
    }

    private OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(
                        chain -> {
                            Request original = chain.request();
                            Request.Builder requestBuilder = original.newBuilder()
                                    .header("Accept", "application/json")
                                    .method(original.method(), original.body());

                            Request request = requestBuilder.build();
                            return chain.proceed(request);
                        })
                .build();
    }
}
