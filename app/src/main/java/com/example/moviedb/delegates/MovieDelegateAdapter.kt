package com.example.moviedb.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.BR
import com.example.moviedb.R
import com.example.moviedb.models.DelegateUIModel
import com.example.moviedb.models.Movie
import com.squareup.picasso.Picasso


class MovieDelegateAdapter : DelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(
                layoutInflater, R.layout.item_movie, parent, false)
        return MovieViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, delegateUIModel: DelegateUIModel) {
        if (delegateUIModel is Movie && viewHolder is MovieViewHolder) {
            viewHolder.binding.setVariable(BR.movie, delegateUIModel)
            viewHolder.binding.executePendingBindings()
        }
    }

    @BindingAdapter("bind_imageUrl")
    fun loadImage(view: ImageView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty()) {
            Picasso.Builder(view.context)
                    .build()
                    .load(imageUrl)
                    .placeholder(R.drawable.ic_movies_24px)
                    .error(R.drawable.ic_movies_24px)
                    .into(view)
        }
    }

    inner class MovieViewHolder(val binding: ViewDataBinding) :
            RecyclerView.ViewHolder(binding.root)

}

