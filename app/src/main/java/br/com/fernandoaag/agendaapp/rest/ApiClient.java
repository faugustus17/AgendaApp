package br.com.fernandoaag.agendaapp.rest;

import android.support.annotation.NonNull;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public enum ApiClient{
    INSTANCE;

    private static final String BASE_URL = "http://10.0.2.2:8080/WebServiceAgenda/rest/service/";
    private final ApiInterface apiInterface;

    ApiClient(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client =  new OkHttpClient.Builder().addInterceptor(interceptor).build();

        final Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(BASE_URL)
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public @NonNull ApiInterface apiInterface() {
        return apiInterface;
    }
}

