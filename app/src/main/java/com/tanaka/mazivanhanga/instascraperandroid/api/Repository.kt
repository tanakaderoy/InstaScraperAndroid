package com.tanaka.mazivanhanga.instascraperandroid.api

import androidx.lifecycle.MutableLiveData
import com.tanaka.mazivanhanga.instascraperandroid.models.Body
import com.tanaka.mazivanhanga.instascraperandroid.models.InstaPreview
import io.reactivex.Single


/**
 * Created by Tanaka Mazivanhanga on 07/31/2020
 */
object Repository {
    val TAG = Repository::class.simpleName

    fun getInstaPreviews(body: Body): Single<List<InstaPreview>> {
        val data = MutableLiveData<List<InstaPreview>>()
        return ApiHandler.instaService.getPreviews(body).map {
            return@map it.body()
        }

    }
}