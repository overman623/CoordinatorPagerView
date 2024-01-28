package com.aos.coordinator.pager.example

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

class ViewBindingCreator<B: ViewDataBinding>(@LayoutRes val resId: Int) {
    fun createViewHolder(parent: ViewGroup): B {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            resId,
            parent,
            false
        )
    }
}
