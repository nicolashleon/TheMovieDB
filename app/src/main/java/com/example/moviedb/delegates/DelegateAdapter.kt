package com.example.moviedb.delegates

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.moviedb.models.DelegateUIModel

/**
 * Code adapted from https://github.com/sockeqwe/AdapterDelegates
 * http://hannesdorfmann.com/android/adapter-delegates
 */
interface DelegateAdapter {
    fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, delegateUIModel: DelegateUIModel)
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
}