package com.tanaka.mazivanhanga.instascraperandroid.api

import com.tanaka.mazivanhanga.instascraperandroid.models.InstaPreview
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST


/**
 * Created by Tanaka Mazivanhanga on 07/31/2020
 */
interface Service {
    @Headers("Content-Type:application/json; charset=UTF-8")
    @POST("scraper")
    fun getPreviews(@Body body: com.tanaka.mazivanhanga.instascraperandroid.models.Body): Single<Response<List<InstaPreview>>>
}