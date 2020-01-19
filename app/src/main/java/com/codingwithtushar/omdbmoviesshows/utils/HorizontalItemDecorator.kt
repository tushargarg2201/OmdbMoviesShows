package com.codingwithtushar.omdbmoviesshows.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class HorizontalItemDecorator (verticalSpaceHeight: Int) : RecyclerView.ItemDecoration() {
    private var verticalSpaceHeight = 0
    init {
        this.verticalSpaceHeight = verticalSpaceHeight
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = verticalSpaceHeight
    }
}