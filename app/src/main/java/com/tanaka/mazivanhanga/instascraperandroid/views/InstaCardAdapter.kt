package com.tanaka.mazivanhanga.instascraperandroid.views

import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tanaka.mazivanhanga.instascraperandroid.R
import com.tanaka.mazivanhanga.instascraperandroid.models.InstaPreview
import kotlinx.android.synthetic.main.insta_preview_cards.view.*


/**
 * Created by Tanaka Mazivanhanga on 07/31/2020
 */
class InstaCardAdapter(private val interaction: Interaction? = null ,private val metrics: DisplayMetrics) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemMargin: Int = 0
    private var itemWidth: Int = 0

    internal fun setItemMargin(itemMargin: Int) {
        this.itemMargin = itemMargin
    }

    internal fun updateDisplayMetrics() {
        itemWidth = metrics.widthPixels - itemMargin * 2
    }


    val DIFF_CALLBACK = object : DiffUtil.ItemCallback<InstaPreview>() {

        override fun areItemsTheSame(oldItem: InstaPreview, newItem: InstaPreview): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: InstaPreview, newItem: InstaPreview): Boolean {
            return oldItem == newItem
        }

    }
    private val differ = AsyncListDiffer(this, DIFF_CALLBACK)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return InstaCardViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.insta_preview_cards,
                parent,
                false
            ),
            interaction
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is InstaCardViewHolder -> {
                var currentItemWidth = itemWidth
                if (position == 0) {
                    currentItemWidth += itemMargin
                    holder.itemView.setPadding(itemMargin, 0, 0, 0)
                } else if (position == itemCount - 1) {
                    currentItemWidth += itemMargin
                    holder.itemView.setPadding(0, 0, itemMargin, 0)
                }

                val height = holder.itemView.layoutParams.height
                holder.itemView.layoutParams = ViewGroup.LayoutParams(currentItemWidth, height)
                holder.bind(differ.currentList[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    fun submitList(list: List<InstaPreview>) {
        differ.submitList(list)
    }

    class InstaCardViewHolder
    constructor(
        itemView: View,
        private val interaction: Interaction?
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: InstaPreview) = with(itemView) {
            itemView.setOnClickListener {
                interaction?.onItemSelected(adapterPosition, item)
            }
            Glide.with(itemView).load(item.image).into(preview_image)
            name_text_view.text = item.title
            detail_text_view.text = item.description
        }
    }

    interface Interaction {
        fun onItemSelected(position: Int, item: InstaPreview)
    }
}
