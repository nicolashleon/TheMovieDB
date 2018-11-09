package com.example.moviedb.adapters

import android.util.SparseArray
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.moviedb.delegates.DelegateAdapter
import com.example.moviedb.models.DelegateUIModel


/**
 * Code adapted from https://github.com/sockeqwe/AdapterDelegates
 * http://hannesdorfmann.com/android/adapter-delegates
 */
abstract class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    protected var delegateAdapters = SparseArray<DelegateAdapter>()
    protected var delegateUIModels: MutableList<DelegateUIModel> = ArrayList()

    val isEmpty: Boolean
        get() = delegateUIModels.isEmpty()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegateAdapter = delegateAdapters.get(viewType)
        return delegateAdapter?.onCreateViewHolder(parent, viewType)!!
    }

    override fun getItemViewType(position: Int): Int {
        return delegateUIModels[position].viewType
    }

    override fun getItemCount(): Int {
        return delegateUIModels.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val delegateAdapter = delegateAdapters.get(getItemViewType(position))
        delegateAdapter?.onBindViewHolder(holder, delegateUIModels[position])
    }

    fun <T : DelegateAdapter> addDelegate(delegateAdapter: T, viewType: Int) {
        delegateAdapters.put(viewType, delegateAdapter)
    }

    fun <T : DelegateUIModel> getItem(pos: Int): T {
        return delegateUIModels[pos] as T
    }

    fun removeItemsAndNotify(uiModel: DelegateUIModel) {
        val posToRemove = delegateUIModels.indexOf(uiModel)
        if (posToRemove != NO_POSITION) {
            delegateUIModels.removeAt(posToRemove)
            notifyItemRemoved(posToRemove)
        }
    }

    fun clearItems() {
        delegateUIModels.clear()
    }

    fun addAllItemsAndNotify(uiModels: List<DelegateUIModel>) {
        val size = delegateUIModels.size
        delegateUIModels.addAll(uiModels)
        notifyItemRangeInserted(size, uiModels.size)
    }

    fun addItemAndNotify(uiModel: DelegateUIModel) {
        val size = delegateUIModels.size
        delegateUIModels.add(uiModel)
        notifyItemInserted(size)
    }

    fun clearItemsAndNotify() {
        delegateUIModels.clear()
        notifyDataSetChanged()
    }

}
