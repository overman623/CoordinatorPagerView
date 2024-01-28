package com.overman.coordinator.viewpager.common

internal interface MainCoordinatorListener {
    fun onViewPagerActive(isEnabled: Boolean)
    fun onAppBarActive(isEnabled: Boolean)
    fun onAppBarVerticalScrollPosition(position: Int, offset: Int)
    fun onAppBarVerticalScrollState(state: Int)
}