package com.tanaka.mazivanhanga.instascraperandroid

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.tanaka.mazivanhanga.instascraperandroid.databinding.ActivityMainBinding
import com.tanaka.mazivanhanga.instascraperandroid.models.Body
import com.tanaka.mazivanhanga.instascraperandroid.models.InstaPreview
import com.tanaka.mazivanhanga.instascraperandroid.views.InstaCardAdapter
import com.tanaka.mazivanhanga.instascraperandroid.views.InstaViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.simpleName
    lateinit var adapter: InstaCardAdapter
    var instaAccounts: List<InstaPreview> = ArrayList()
    val compositeDisposable = CompositeDisposable()
    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: InstaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = binding.root
        view.setOnClickListener{
            it.hideKeyboard()
        }
        setContentView(view)
        adapter = InstaCardAdapter(object : InstaCardAdapter.Interaction {
            override fun onItemSelected(position: Int, item: InstaPreview) {
                Log.i(TAG, item.toString())
            }
        }, getDisplayMetrics())
        adapter.setItemMargin(5)
        adapter.updateDisplayMetrics()
        binding.instaPreviewList.apply {
            this.adapter = this@MainActivity.adapter
            layoutManager =
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            this.setHasFixedSize(true)
            this.addItemDecoration(LinePagerIndicatorDecoration())
        }

        PagerSnapHelper().attachToRecyclerView(insta_preview_list)


        viewModel = ViewModelProviders.of(this).get(InstaViewModel::class.java)
        viewModel.instaListLiveData.observe(this, Observer {
            adapter.submitList(it)
            searching_text_view.text = "Searching for: ${viewModel.query}"

        })

        binding.searchButton.setOnClickListener {
            it.hideKeyboard()
            doSearch()
        }


    }

    private fun doSearch() {
        var body = Body(link_input.text.toString())
        viewModel.fetchData(body,link_input.text.toString())
        Log.i(TAG, body.toString())
    }

    private fun getDisplayMetrics(): DisplayMetrics {
        val display = windowManager.defaultDisplay
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)

        return metrics
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.clearDisposables()
    }
    fun View.hideKeyboard() {
        val inputMethodManager = context!!.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        inputMethodManager?.hideSoftInputFromWindow(this.windowToken, 0)
    }
}
