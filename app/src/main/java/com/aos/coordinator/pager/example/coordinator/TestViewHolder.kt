package com.aos.coordinator.pager.example.coordinator

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aos.coordinator.pager.example.MainBottomListAdapter
import com.aos.coordinator.pager.example.MainBottomListItem
import com.aos.coordinator.pager.example.RecyclerListener
import com.overman.coordinator.viewpager.holder.PagerViewHolder

class TestViewHolder(
    override val binding: View,
    private val recyclerListener: RecyclerListener
) : PagerViewHolder(binding) {

    fun bind(position: Int) {

    }

    override fun mainViewBind(mainContentLayout: View, position: Int) {

    }

    override fun createBottomLayout(recyclerView: RecyclerView, position: Int) {
        val bottomLayoutList = ArrayList<MainBottomListItem>().apply {
            add(MainBottomListItem(type = MainBottomListAdapter.Type.ITEM_TYPE_1))
            add(MainBottomListItem(type = MainBottomListAdapter.Type.ITEM_TYPE_1))
            add(MainBottomListItem(type = MainBottomListAdapter.Type.ITEM_TYPE_1))
            add(MainBottomListItem(type = MainBottomListAdapter.Type.ITEM_TYPE_1))
            add(MainBottomListItem(type = MainBottomListAdapter.Type.ITEM_TYPE_1))
            add(MainBottomListItem(type = MainBottomListAdapter.Type.ITEM_TYPE_1))
            add(MainBottomListItem(type = MainBottomListAdapter.Type.ITEM_TYPE_1))
            add(MainBottomListItem(type = MainBottomListAdapter.Type.ITEM_TYPE_1))
            add(MainBottomListItem(type = MainBottomListAdapter.Type.ITEM_TYPE_1))
            add(MainBottomListItem(type = MainBottomListAdapter.Type.ITEM_TYPE_1))
            add(MainBottomListItem(type = MainBottomListAdapter.Type.ITEM_TYPE_1))
        }

        recyclerView.isNestedScrollingEnabled = true
        recyclerView.layoutManager = LinearLayoutManager(binding.context)
        recyclerView.adapter = MainBottomListAdapter(
            list = bottomLayoutList
        )
        recyclerView.refreshDrawableState()
    }

}