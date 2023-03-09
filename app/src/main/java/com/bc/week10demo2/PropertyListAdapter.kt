package com.bc.week10demo2

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bc.week10demo2.databinding.ItemViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.squareup.picasso.Picasso


class PropertyListAdapter :
    ListAdapter<MarsProperty, PropertyListAdapter.PropertyViewHolder>(RowItemDiffCallback()) {

    fun setData(data: List<MarsProperty>) {
        submitList(data)
    }

    class PropertyViewHolder private constructor(val binding: ItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): PropertyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ItemViewBinding = DataBindingUtil.inflate(
                    layoutInflater,
                    R.layout.item_view, parent, false
                )
                //val binding = ItemViewBinding.inflate(layoutInflater, parent, false)
                return PropertyViewHolder(binding)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        return PropertyViewHolder.from(parent)

    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        holder.binding.property = getItem(position) as MarsProperty
        holder.binding.executePendingBindings()
    }

}

class RowItemDiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
    override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
        Log.v("callback areItemsTheSame", Thread.currentThread().name)
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
        Log.v("callback areContentsTheSame", Thread.currentThread().name)
        return oldItem.id == newItem.id
    }
}


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
            .load(imgUrl.replace("http", "https"))
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(imgView)
    }
}

@BindingAdapter("imageUrl2")
fun bindImage2(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Picasso.get()
            .load(imgUri)
            .resize(200, 200)
            .placeholder(R.drawable.loading_animation)
            .error(R.drawable.ic_broken_image)
            .into(imgView)
    }
}


@BindingAdapter("price")
fun TextView.price(price: Double) {
    text = "Price: ${price.toString()}"
}