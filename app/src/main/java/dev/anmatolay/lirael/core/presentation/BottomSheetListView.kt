package dev.anmatolay.lirael.core.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.AbsListView
import android.widget.ListView
import timber.log.Timber

// Thanks to THEPATEL
// https://stackoverflow.com/a/40570986/21638763
class BottomSheetListView(context: Context?, attrs: AttributeSet?) : ListView(context, attrs) {
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    // No need to override performClick
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (canScrollVertically(this)) {
            parent.requestDisallowInterceptTouchEvent(true)
        }
        return super.onTouchEvent(ev)
    }

    private fun canScrollVertically(view: AbsListView?): Boolean {
        var canScroll = false
        if (view != null && view.childCount > 0) {
            val isOnTop = view.firstVisiblePosition != 0 || view.getChildAt(0).top != 0
            val isAllItemsVisible = isOnTop && view.lastVisiblePosition == view.childCount
            // isAllItemsVisible can be true too (logged and checked)
            @Suppress("KotlinConstantConditions")
            if (isOnTop || isAllItemsVisible) {
                canScroll = true
            }
        }
        return canScroll
    }
}
