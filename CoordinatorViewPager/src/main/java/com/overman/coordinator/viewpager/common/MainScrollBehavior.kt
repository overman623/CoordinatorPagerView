package com.overman.coordinator.viewpager.common

import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs


internal class MainScrollBehavior(private val onDetectUpDownScrollListener: (isUpDownScroll: Boolean, offset: Float) -> Unit) : AppBarLayout.ScrollingViewBehavior() {

    private var startX = 0f
    private var startY = 0f
    private var isScrollX = false
    private var isScrollY = false

    private var isCanceled = false

    override fun onInterceptTouchEvent(parent: CoordinatorLayout, child: View, ev: MotionEvent): Boolean {

        if (isCanceled) {
            startX = ev.x
            startY = ev.y
            isCanceled = false
            return super.onInterceptTouchEvent(parent, child, ev)
        }

        when (ev.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x
                startY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                val dx = abs(ev.x - startX) // 가로 스크롤 간격
                val dy = abs(ev.y - startY) // 세로 스크롤 간격
                if (dy < dx) { // dx > dy 가로 스크롤
                    isScrollX = true
                    isScrollY = false
                    // 뷰 페이져 인식 O, 리스트 스크롤 인식 X
                } else if (dy > dx) { // dx < dy 세로 스크롤
                    isScrollX = false
                    isScrollY = true
                    // 뷰 페이져 인식 X, 리스트 스크롤 인식 O
                } else { // 간격이 같을 때는 해당 하지 않음. (우선 세로 스크롤 인식.)
                    isScrollX = false
                    isScrollY = true
//                     Log.e("SCROLL_TEST", "간격 같음 -> 세로 스크롤")
                }

                if (isScrollY) {
                    onDetectUpDownScrollListener(true, ev.y - startY) // 뷰 페이져 인식 X, 리스트 스크롤 인식 O
                }

                if (isScrollX) {
                    onDetectUpDownScrollListener(false, ev.x - startX) // 뷰 페이져 인식 O, 리스트 스크롤 인식 X
                }
            }
            else -> {
                if (ev.actionMasked == MotionEvent.ACTION_CANCEL) {
                    isCanceled = true
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(parent, child, ev)
    }
}