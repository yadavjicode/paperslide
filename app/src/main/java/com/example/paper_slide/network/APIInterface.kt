package com.example.paper_slide.network

import android.content.Context
import com.example.paper_slide.model.ForgotPasswordResponse
import com.example.paper_slide.model.LogoutResponse
import com.example.paper_slide.model.OTPResponse
import com.example.paper_slide.model.ResetPasswordResponse
import com.example.paper_slide.model.SignInResponse
import com.example.paper_slide.model.SignUpResponse
import com.example.paper_slide.model.SummaryUpdateResponse
import com.example.paper_slide.model.SummeryResponse
import com.example.paper_slide.model.TranslateResponse
import com.example.paper_slide.util.Constants
import com.example.paper_slide.util.SharedPref
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface APIInterface {
@POST("register")
@FormUrlEncoded
fun getSignUp(
    @Field("name") name: String,
    @Field("email") email: String,
    @Field("phone") phone: String,
    @Field("password") password: String
    ): Call<SignUpResponse>

    @POST("account/api/login")
    @FormUrlEncoded
    fun getSignIn(
        @Field("email") username: String,
        @Field("password") password: String
    ): Call<SignInResponse>

    @POST("forgotpass")
    @FormUrlEncoded
    fun getForgotPassword(
        @Field("email") email: String,
    ): Call<ForgotPasswordResponse>

    @POST("otpverify")
    @FormUrlEncoded
    fun getOTP(
        @Field("email") email: String,
        @Field("otp") otp: Int,
    ): Call<OTPResponse>

    @POST("resetpass")
    @FormUrlEncoded
    fun getResetPassword(
        @Field("password") password: String
    ): Call<ResetPasswordResponse>

    @POST("logout")
    fun getLogout(
    ): Call<LogoutResponse>
    @POST("summarization/api/summary")
    @FormUrlEncoded
    fun getSummary(
        @Field("original_text") originalText :String,
        @Field("summary_length") summaryLength:Int
    ): Call<SummeryResponse>

    @POST("translation/api/translate")
    @FormUrlEncoded
    fun getTranslation(
        @Field("original_text")  originalText : String,
        @Field("target_lang") targetLang : String
    ) : Call <TranslateResponse>

    @PATCH("summarization/api/summary/{id}")
    fun updateSummary(
        @Path("id") id: Int,
        @Field("summarized_text") summarizedText :String,
    ): Call<SummaryUpdateResponse>

    class APIClient(context: Context) {
        private var sharedPref = SharedPref(context)
        private fun getOkHttpClientWithBearerToken(): OkHttpClient {
            return OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS) // Set the connection timeout
                .readTimeout(60, TimeUnit.SECONDS)    // Set the read timeout
                .writeTimeout(60, TimeUnit.SECONDS)   // Set the write timeout
                .addInterceptor(Interceptor { chain ->
                    val originalRequest: Request = chain.request()
                    val newRequest: Request = originalRequest.newBuilder()
                        .header("Authorization", "Bearer ${sharedPref.accessToken}")
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