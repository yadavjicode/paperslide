package com.example.paper_slide.network

import android.content.Context
import com.example.paper_slide.model.SignUpResponse
import com.example.paper_slide.model.UserResponse
import com.example.paper_slide.util.Constants
import com.example.paper_slide.util.SharedPref
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST

interface APIInterface {
@POST("register")
@FormUrlEncoded
fun getSignUp(
    @Field("name") name: String,
    @Field("email") email: String,
    @Field("phone") phone: String,
    @Field("password") password: String
): Call<SignUpResponse>















    class APIClient(context: Context) {
        private var sharedPref = SharedPref(context)
        private fun getOkHttpClientWithBearerToken(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val originalRequest: Request = chain.request()
                    val newRequest: Request = originalRequest.newBuilder()
                        .header("Authorization", "Bearer ${sharedPref.refreshToken}")
                        .build()
                    chain.proceed(newRequest)
                })
                .build()
        }

        val apiInstance: APIInterface

        init {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(getOkHttpClientWithBearerToken())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            apiInstance = retrofit.create(APIInterface::class.java)
        }
    }






}