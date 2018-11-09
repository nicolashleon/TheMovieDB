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

    companion object {
        @BindingAdapter("bind_imageUrl")
        @JvmStatic
        fun loadImage(view: ImageView, imageUrl: String?) {
            if (!imageUrl.isNullOrEmpty()) {
                Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.ic_movies_24px)
                        .error(R.drawable.ic_movies_24px)
                        .into(view)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, R.layout.item_movie, parent, false)
        return MovieViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, delegateUIModel: DelegateUIModel) {
        if (delegateUIModel is Movie && viewHolder is MovieViewHolder) {
            viewHolder.binding.setVariable(BR.movie, delegateUIModel)
            viewHolder.binding.executePendingBindings()
        }
    }


    inner class MovieViewHolder(val binding: ViewDataBinding) :
            RecyclerView.ViewHolder(binding.root)

}

