package com.overman.coordinator.viewpager

import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

internal inline fun <reified T : RecyclerView.ViewHolder> ViewPager2.findViewHolderForAdapter(): ArrayList<T?> {
    val childCount = this.childCount
    val arrayList: ArrayList<T?> = arrayListOf()
    for (i in 0 until childCount) {
        arrayList.add((getChildAt(0) as RecyclerView).findViewHolderForAdapterPosition(i) as? T)
    }
    return arrayList
}

internal inline fun <reified T : RecyclerView.ViewHolder> ViewPager2.findViewHolderForAdapterPosition(position: Int): T? {
    val recyclerView = getChildAt(0) as? RecyclerView
    return recyclerView?.findViewHolderForAdapterPosition(position) as? T
}
