package com.overman.coordinator.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.viewpager2.widget.ViewPager2
import com.overman.coordinator.viewpager.adapter.CoordinatorRecyclerAdapter
import com.overman.coordinator.viewpager.common.MainCoordinatorListener
import com.overman.coordinator.viewpager.holder.PagerViewHolder

class CoordinatorViewPager: FrameLayout {

    constructor(context: Context) : super(context) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        initView()
    }

    private val viewPager by lazy {
        ViewPager2(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        }
    }

    private val mainCoordinatorListener = object: MainCoordinatorListener {

        override fun onViewPagerActive(isEnabled: Boolean) {
            viewPager.isUserInputEnabled = !isEnabled
        }

        override fun onAppBarActive(isEnabled: Boolean) {
            if (isEnabled && viewPager.scrollState == ViewPager2.SCROLL_STATE_DRAGGING) {
                return
            }
        }

        override fun onAppBarVerticalScrollPosition(position: Int, offset: Int) {
            innerAdapter?.let {
                it.currentBottomScrollPosition = position
                it.currentBottomScrollOffset = offset
                it.setHolderScrollPosition()
            }
        }

        override fun onAppBarVerticalScrollState(state: Int) {
        }

    }

    private var onPageCallback2: ViewPager2.OnPageChangeCallback? = null

    private val onPageChangeCallback = object: ViewPager2.OnPageChangeCallback() {

        override fun onPageSelected(position: Int) {
            innerAdapter?.syncSelectLayout()
            onPageCallback2?.onPageSelected(position)
        }

        override fun onPageScrollStateChanged(state: Int) {
            if (state != ViewPager2.SCROLL_STATE_IDLE) {
                innerAdapter?.let {
                    if (it.isExpand) {
                        it.isExpandBindingViewHolders = true
                        it.setMainView()
                    } else {
                        it.isExpandBindingViewHolders = false
                        it.setHolderScrollPosition()
                        it.setBottomView()
                    }
                }
            }

            onPageCallback2?.onPageScrollStateChanged(state)
        }

        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
//            if (weatherDrawerList.isEmpty()) {
//                return
//            }
            innerAdapter?.let {
                if (!it.isExpandBindingViewHolders) {
                    it.syncBottomScrollPosition()
                }
            }
            onPageCallback2?.onPageScrolled(position, positionOffset, positionOffsetPixels)
        }
    }

    private var innerAdapter: CoordinatorRecyclerAdapter<*>? = null

    var adapter: Adapter<*>? = null
        get() = innerAdapter
        set(value) {

            viewPager.adapter = value
            field = value
            (field as? CoordinatorRecyclerAdapter<*>)?.also {
                innerAdapter = it
            }
            innerAdapter?.setCallback(mainCoordinatorListener)
        }

    val scrollState: Int
        get() = viewPager.scrollState

    var isUserInputEnabled: Boolean
        get() = viewPager.isUserInputEnabled
        set(value) {
            viewPager.isUserInputEnabled = value
        }

    fun setDebug(isDebug: Boolean) {
        ALog.available = isDebug
    }

    private fun initView() {
        addView(viewPager)
    }

    fun registerOnPageChangeCallback(callback: ViewPager2.OnPageChangeCallback) {
        this.onPageCallback2 = callback
        viewPager.registerOnPageChangeCallback(onPageChangeCallback)
    }

    fun unRegisterOnPageChangeCallback(callback: ViewPager2.OnPageChangeCallback) {
        this.onPageCallback2 = null
        viewPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    fun setTitleBar(parent: ViewGroup) {
        viewPager.findViewHolderForAdapter<PagerViewHolder>()
            .filterNotNull()
            .forEach {
                it.setTitleBar(parent)
            }
    }

    override fun setBackgroundColor(@ColorInt color: Int) {
        super.setBackgroundColor(color)
        viewPager.setBackgroundColor(color)
    }

}

