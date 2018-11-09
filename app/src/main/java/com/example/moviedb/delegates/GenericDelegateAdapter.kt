package com.example.moviedb.delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.BR
import com.example.moviedb.models.DelegateUIModel


class GenericDelegateAdapter(@LayoutRes private val layout: Int) : DelegateAdapter {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layout,
                parent, false)
        return GenericViewHolder(viewDataBinding)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, delegateUIModel: DelegateUIModel) {
        if (viewHolder is GenericViewHolder) {
            viewHolder.binding.setVariable(BR.movie, delegateUIModel)
            viewHolder.binding.executePendingBindings()
        }
    }

    inner class GenericViewHolder(val binding: ViewDataBinding) :
            RecyclerView.ViewHolder(binding.root)

}

