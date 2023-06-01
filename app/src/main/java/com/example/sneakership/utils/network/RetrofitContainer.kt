package com.example.sneakership.utils.network

import com.example.sneakership.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitContainer {

    /*Retrofit Builder*/
    fun getRetrofitBuilder(apiEndPoint: String): Retrofit.Builder {
        val retrofitBuilder = Retrofit.Builder()
        retrofitBuilder.baseUrl(apiEndPoint)

        val okhttpClientBuilder = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okhttpClientBuilder.addInterceptor(loggingInterceptor)


        okhttpClientBuilder.addInterceptor { chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader(Constants.X_ACCESS_KEY, BuildConfig.X_ACCESS_KEY)
                .addHeader(Constants.X_MASTER_KEY, BuildConfig.X_MASTER_KEY)
                .build()
            chain.proceed(request)
        }

        /* read/connect timeout set up */
        okhttpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        okhttpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)

        val client = okhttpClientBuilder.build()
        retrofitBuilder.client(client)

        /*GSON serialization factory*/
        retrofitBuilder.addConverterFactory(GsonConverterFactory.create())

        return retrofitBuilder
    }


}