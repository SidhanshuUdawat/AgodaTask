package com.agodatask.main.news.adapter

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Sid on 27/06/2018.
 *
 * Adds an extra spacing between the list elements of RecyclerView
 */

class RecyclerViewDecorator(private val upMargin: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val lastPosition = parent.adapter.itemCount

        if (position != lastPosition && position != 0) {
            outRect.top = upMargin
        }
    }
}
