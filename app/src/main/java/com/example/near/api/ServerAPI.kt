package com.example.near.api

import android.content.Context
import com.example.near.utils.ContextUtil
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class ServerAPI {
    companion object {
        private val baseUrl = "https://api.gudoc.in"
        private var retrofit : Retrofit? = null

        fun getRetrofit(context: Context) : Retrofit {
            if(retrofit == null){
                val interceptor = Interceptor{
                    with(it){
                        val newRequest = request().newBuilder().addHeader("X-Http-Token", ContextUtil.getLoginToken(context)).build()
                        proceed(newRequest)
                    }
                }
                //val gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").registerTypeAdapter(Date::class.java, DateDeserializer()).create()
                val myClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

                retrofit = Retrofit.Builder().baseUrl(baseUrl).client(myClient).addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit!!
        }
    }
}