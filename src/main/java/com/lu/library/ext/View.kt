package com.lu.commons.ext

import androidx.recyclerview.widget.*
import com.blankj.utilcode.util.LogUtils
import java.lang.RuntimeException


//滑动闪烁问题
fun RecyclerView.noChangeAnimations() {

    (this.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations =
        false
    (this.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations =
        false
}

/**
 * 瀑布流顶部空白
 */
fun RecyclerView.invalidateSpanAssignments() {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            //防止第一行到顶部有空白区域
            if (layoutManager is StaggeredGridLayoutManager) {
                (layoutManager as StaggeredGridLayoutManager).invalidateSpanAssignments()
            }
        }
    })
}

/**
 * RecyclerView
 * 加载更多
 */
fun RecyclerView.loadMore(loadMore: () -> Unit) {
    this.addOnScrollListener(
        object : RecyclerView.OnScrollListener() {
            var lastVisibleItemPosition = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState);

//                val layoutManager = layoutManager
                val visibleItemCount = layoutManager?.childCount ?: 0
                val totalItemCount = layoutManager?.itemCount ?: 0
                if (visibleItemCount > 0 && newState === RecyclerView.SCROLL_STATE_IDLE
                    && lastVisibleItemPosition >= totalItemCount - 1
                ) {
                    LogUtils.d("滑动到底部")
                    loadMore()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                var layoutManagerType = -1
                if (layoutManagerType == -1) {
                    if (layoutManager is LinearLayoutManager) {
                        layoutManagerType = 0
                    } else if (layoutManager is GridLayoutManager) {
                        layoutManagerType = 1
                    } else if (layoutManager is StaggeredGridLayoutManager) {
                        layoutManagerType = 2
                    } else {
                        throw RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager")
                    }
                }

                when (layoutManagerType) {
                    0 -> lastVisibleItemPosition =
                        (layoutManager as LinearLayoutManager?)?.findLastVisibleItemPosition()
                            ?: 0
                    1 -> lastVisibleItemPosition =
                        (layoutManager as GridLayoutManager?)?.findLastVisibleItemPosition()
                            ?: 0
                    2 -> {
                        val staggeredGridLayoutManager =
                            layoutManager as StaggeredGridLayoutManager?
                        val lastPositions = IntArray(staggeredGridLayoutManager!!.spanCount)
                        staggeredGridLayoutManager.findLastVisibleItemPositions(
                            lastPositions
                        )
                        lastVisibleItemPosition = findMax(lastPositions)
                    }
                }
            }

            private fun findMax(lastPositions: IntArray): Int {
                var max = lastPositions[0]
                for (value in lastPositions) {
                    if (value > max) {
                        max = value
                    }
                }
                return max
            }
        });
}