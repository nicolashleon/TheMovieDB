package com.example.moviedb

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MovieItemDecorator : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.top = view.resources?.getDimensionPixelOffset(R.dimen.half_margin) ?: 0
        outRect.bottom = view.resources?.getDimensionPixelOffset(R.dimen.half_margin) ?: 0
    }
}