package com.tanaka.mazivanhanga.instascraperandroid.views

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tanaka.mazivanhanga.instascraperandroid.api.Repository
import com.tanaka.mazivanhanga.instascraperandroid.models.Body
import com.tanaka.mazivanhanga.instascraperandroid.models.InstaPreview
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


/**
 * Created by Tanaka Mazivanhanga on 07/31/2020
 */
class InstaViewModel: ViewModel() {
//    private var _instalistObservable: Single<List<InstaPreview>>? = null
//    val instaListObservable: Single<List<InstaPreview>>
//        get() = _instalistObservable!!
    private var _instaListLiveData: MutableLiveData<List<InstaPreview>> = MutableLiveData()
    val instaListLiveData get() = _instaListLiveData
    private val compositeDisposable = CompositeDisposable()
var query = ""
    fun fetchData(body: Body, query: String){
        this.query = query
        compositeDisposable.add(Repository.getInstaPreviews(body).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                _instaListLiveData.value = it

            }) {
                val msg = it.message ?: "ERROR"
                println(it)
//                Log.e(TAG, msg)
            })
    }


    fun clearDisposables(){
        compositeDisposable.clear()
    }

//
//    class ViewModelFactory(private var body: Body): ViewModelProvider.Factory {
//
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            return InstaViewModel(body) as T
//        }
//
//    }

}