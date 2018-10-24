package com.example.diegoalvis.watchersexplorer.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager


abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener() {

    private var mPreviousTotal = 0
    private var mLoading = true

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val totalItemCount = recyclerView.layoutManager?.itemCount

        if (mLoading) {
            if (totalItemCount != null) {
                if (totalItemCount > mPreviousTotal) {
                    mLoading = false
                    mPreviousTotal = totalItemCount
                }
            }
        }

        val visibleThreshold = 5
        if (totalItemCount != null) {
            val itemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
            if (!mLoading && itemPosition + visibleThreshold > totalItemCount) {
                mLoading = true
                onLoadMore()
            }
        }
    }

    abstract fun onLoadMore()
}