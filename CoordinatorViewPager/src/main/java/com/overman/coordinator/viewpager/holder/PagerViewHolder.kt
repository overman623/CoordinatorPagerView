package com.overman.coordinator.viewpager.holder

import android.annotation.SuppressLint
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.overman.coordinator.viewpager.ALog
import com.overman.coordinator.viewpager.R
import com.overman.coordinator.viewpager.common.MainCoordinatorListener
import com.overman.coordinator.viewpager.common.MainScrollBehavior
import kotlin.math.abs

abstract class PagerViewHolder(
    open val binding: View,
) : RecyclerView.ViewHolder(binding) {

    private val NAME = PagerViewHolder::class.java.simpleName

    internal var isExpanded = true

    private val stateExpand = 0
    private val stateCollapse = 1
    private val stateScroll = 2

    private var isFirstVisibleItemPosition = true

    private var isScrollCenter = false
    private var currentOffset = 0
    private var firstVisibleItemPosition = 0
    private var issueListBoardScrollPositionY = 0

    internal var onExpand: ((isExpand: Boolean) -> Unit)? = null

    abstract fun createBottomLayout(recyclerView: RecyclerView, position: Int)

    abstract fun mainViewBind(mainContentLayout: View, position: Int)

    internal var mainCoordinatorListener: MainCoordinatorListener? = null

    internal fun setCallback(callback: MainCoordinatorListener?) {
        mainCoordinatorListener = callback
    }

    internal fun setExpandListener(test: ((isExpand: Boolean) -> Unit)) {
        onExpand = test
    }

    @SuppressLint("ClickableViewAccessibility")
    internal fun innerBind(position: Int) {
        ALog.d { "$NAME -> bind -> position::$position" }
        val flMainContent = binding.findViewById<FrameLayout>(R.id.flMainContent)
        val rvBottomMenuRecycler = binding.findViewById<RecyclerView>(R.id.rvBottomMenuRecycler)
        val alAppBarLayout = binding.findViewById<AppBarLayout>(R.id.alAppBarLayout)
        rvBottomMenuRecycler?.let {
            createBottomLayout(it, position)
        }

        alAppBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) { // AppBarLayout Expand -> MainLayout visible
                ALog.d { "$NAME -> addOnOffsetChangedListener -> (verticalOffset == 0)" }
                isExpanded = true
                onExpand?.invoke(true)
                isScrollCenter = false
                mainCoordinatorListener?.onAppBarActive(true)
                mainCoordinatorListener?.onAppBarVerticalScrollState(stateExpand)
            } else if (abs(verticalOffset) >= appBarLayout.totalScrollRange) { // AppBarLayout Collapse -> BottomLayout visible
                ALog.d { "$NAME -> addOnOffsetChangedListener -> ((abs(verticalOffset) >= appBarLayout.totalScrollRange)) -> verticalOffset::$verticalOffset" }
                isExpanded = false
                onExpand?.invoke(false)
                isScrollCenter = false
                mainCoordinatorListener?.onAppBarActive(false)
                mainCoordinatorListener?.onAppBarVerticalScrollState(stateCollapse)
            } else { // AppBarLayout scrolling on touch
                ALog.d { "$NAME -> addOnOffsetChangedListener -> else -> verticalOffset::$verticalOffset" }
                isScrollCenter = true
                mainCoordinatorListener?.onAppBarActive(false)
                mainCoordinatorListener?.onAppBarVerticalScrollState(stateScroll)
            }
        }

        flMainContent.setOnTouchListener { _, _ -> true }
        rvBottomMenuRecycler.layoutParams = CoordinatorLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        ).apply {
            behavior = MainScrollBehavior { isUpDownScroll, _ ->
                ALog.d { "$NAME -> MainScrollBehavior isUpDownScroll::$isUpDownScroll" }
                mainCoordinatorListener?.onViewPagerActive(isUpDownScroll)
            }
        }

        rvBottomMenuRecycler.addOnScrollListener(object: RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) { // 스크롤이 멈춘 상태
                    ALog.d { "$NAME -> onScrollStateChanged -> position::$position" }
                    mainCoordinatorListener?.onAppBarVerticalScrollPosition(firstVisibleItemPosition, currentOffset)
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                ALog.d { "$NAME -> onScrolled -> dx::$dx, dy::$dy" }
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager

                firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                currentOffset = layoutManager.findViewByPosition(firstVisibleItemPosition)?.top ?: 0
                isFirstVisibleItemPosition = firstVisibleItemPosition == 0 // 첫번째 리스트 아이템이 보여지는 경우

                if (isFirstVisibleItemPosition && (layoutManager.findViewByPosition(0)?.top ?: 0) > 0 && isScrollCenter) {
                    ALog.d { "$NAME -> onScrolled -> isFirstVisibleItemPosition::true, isScrollCenter::true, findViewByPosition::0" }
                    rvBottomMenuRecycler.stopScroll() // 스크롤 강제 멈춤
                    alAppBarLayout.setExpanded(false, false)
                    isScrollCenter = false
                }
            }

        })

        if (!isExpanded) {
            ALog.d { "$NAME -> bind -> isExpanded::false" }
            syncBottomScrollPosition()
        }


    }

    internal fun setMainView() {
        ALog.d { "$NAME -> setMainView -> isExpanded::$isExpanded" }
        if (!isExpanded) {
            isExpanded = true
            onExpand?.invoke(true)
            mainCoordinatorListener?.onAppBarActive(true)
            binding.findViewById<AppBarLayout>(R.id.alAppBarLayout).setExpanded(true, false)
        }
    }

    internal fun setBottomView() {
        ALog.d { "$NAME -> setBottomView -> isExpanded::$isExpanded" }
        if (isExpanded) {
            isExpanded = false
            onExpand?.invoke(false)
            mainCoordinatorListener?.onAppBarActive(false)
            binding.findViewById<AppBarLayout>(R.id.alAppBarLayout).setExpanded(false, false)
        }
    }


    internal fun syncSelectLayout() {
        ALog.d { "$NAME -> syncSelectLayout -> isExpanded::$isExpanded" }
        if (!isExpanded) {
            ALog.d { "$NAME -> syncSelectLayout -> return" }
            return
        }

        isExpanded = true
        onExpand?.invoke(true)
        isScrollCenter = false
        binding.findViewById<AppBarLayout>(R.id.alAppBarLayout).setExpanded(true, false)
        val rvBottomMenuRecycler = binding.findViewById<RecyclerView>(R.id.rvBottomMenuRecycler)
        val layoutManager = rvBottomMenuRecycler.layoutManager as LinearLayoutManager
        layoutManager.scrollToPositionWithOffset(0, 0)
        rvBottomMenuRecycler.visibility = View.GONE
        rvBottomMenuRecycler.visibility = View.VISIBLE
    }

    internal fun setScrollPosition(position: Int, offset: Int) {
        ALog.d { "$NAME -> setScrollPosition -> position::$position, offset::$offset" }
        firstVisibleItemPosition = position
        currentOffset = offset
    }

    internal fun syncBottomScrollPosition() {
        ALog.d { "$NAME -> syncBottomScrollPosition" }
        val rvBottomMenuRecycler = binding.findViewById<RecyclerView>(R.id.rvBottomMenuRecycler)
        val layoutManager = rvBottomMenuRecycler.layoutManager as LinearLayoutManager
        layoutManager.scrollToPositionWithOffset(firstVisibleItemPosition, currentOffset)
    }

    internal fun syncIssueListBoardScroll(scrollY: Int) {
        ALog.d { "$NAME -> syncIssueListBoardScroll scrollY::$scrollY" }
        issueListBoardScrollPositionY = scrollY
    }

    internal fun getMainContentContainer(): ViewGroup {
        return binding.findViewById(R.id.flMainContent)
    }

    internal fun getMainContentLayout(): View? {
        return binding.findViewById<ViewGroup>(R.id.flMainContent)?.getChildAt(0)
    }

    internal fun setTitleBar(parent: ViewGroup) {
        binding.findViewById<FrameLayout>(R.id.llTitleLayout).apply {
            visibility = View.VISIBLE
            addView(parent)
        }
    }

}