package com.overman.coordinator.viewpager.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.overman.coordinator.viewpager.ALog
import com.overman.coordinator.viewpager.common.MainCoordinatorListener
import com.overman.coordinator.viewpager.holder.PagerViewHolder
import com.overman.coordinator.viewpager.R

abstract class CoordinatorRecyclerAdapter<T: PagerViewHolder> :
    RecyclerView.Adapter<T>() {

    private val NAME = CoordinatorRecyclerAdapter::class.java.simpleName

    internal var isExpand = false
    internal var isExpandBindingViewHolders = true
    internal var currentBottomScrollPosition = 0
    internal var currentBottomScrollOffset = 0

    private var mainCoordinatorListener: MainCoordinatorListener? = null
    private val viewHolderList: MutableList<T?> = ArrayList()

    abstract fun createContentView(context: Context, viewType: Int): View
    abstract fun onBindingContentView(holder: T, position: Int)
    abstract fun onPreCreateViewHolder(view: View, viewType: Int): T

    internal fun setCallback(mainCoordinatorListener: MainCoordinatorListener) {
        this.mainCoordinatorListener = mainCoordinatorListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        ALog.d { "$NAME -> onCreateViewHolder -> viewType::$viewType" }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_list_item_main, parent, false)
        val createdViewHolder: T = onPreCreateViewHolder(view, viewType)

        createdViewHolder.getMainContentContainer()
            .addView(createContentView(createdViewHolder.binding.context, viewType))

        return createdViewHolder.apply {
            setCallback(mainCoordinatorListener)
            setExpandListener { isExpand ->
                this@CoordinatorRecyclerAdapter.isExpand = isExpand
            }
        }
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        ALog.d { "$NAME -> onBindViewHolder -> position::$position, isExpandBindingViewHolders::$isExpandBindingViewHolders" }
        viewHolderList.add(holder)
        holder.setScrollPosition(currentBottomScrollPosition, currentBottomScrollOffset)
        if (isExpandBindingViewHolders) {
            holder.setMainView()
        } else {
            holder.setBottomView()
        }
        holder.innerBind(position = position)
        holder.getMainContentLayout()?.let {
            holder.mainViewBind(it, position)
        }
        onBindingContentView(holder, position)
    }

    internal fun setMainView() {
        ALog.i { "$NAME -> setMainView" }
        for (i in 0 until viewHolderList.count()) {
            viewHolderList[i]?.setMainView()
        }
    }

    internal fun setBottomView() {
        ALog.i { "$NAME -> setBottomView" }
        for (i in 0 until viewHolderList.count()) {
            viewHolderList[i]?.setBottomView()
        }
    }

    internal fun syncSelectLayout() {
        ALog.i { "$NAME -> syncSelectLayout" }
        for (i in 0 until viewHolderList.count()) {
            viewHolderList[i]?.syncSelectLayout()
        }
    }

    internal fun setHolderScrollPosition() {
        ALog.i { "$NAME -> setHolderScrollPosition" }
        for (i in 0 until viewHolderList.count()) {
            viewHolderList[i]?.setScrollPosition(currentBottomScrollPosition, currentBottomScrollOffset)
        }
    }

    internal fun syncBottomScrollPosition() {
        ALog.i { "$NAME -> syncBottomScrollPosition" }
        for (i in 0 until viewHolderList.count()) {
            viewHolderList[i]?.syncBottomScrollPosition()
        }
    }

}