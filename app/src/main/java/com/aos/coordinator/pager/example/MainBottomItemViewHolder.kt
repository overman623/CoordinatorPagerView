package com.aos.coordinator.pager.example

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class MainBottomItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    abstract fun bind(position: Int)
}