package com.aos.coordinator.pager.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.aos.coordinator.pager.example.coordinator.TestCoordinatorRecyclerAdapter
import com.overman.coordinator.viewpager.CoordinatorViewPager

class MainActivity : AppCompatActivity(), RecyclerListener {

    private var dataList = arrayListOf<String>().apply {
        add("")
        add("")
        add("")
        add("")
    }

    private val mainAdapter: TestCoordinatorRecyclerAdapter by lazy {
        TestCoordinatorRecyclerAdapter(dataList, this)
    }

    private val onPageChangeCallback: ViewPager2.OnPageChangeCallback = object :
        ViewPager2.OnPageChangeCallback() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewPager = findViewById<CoordinatorViewPager>(R.id.coordinatorViewPager)
        viewPager.setBackgroundColor(ContextCompat.getColor(this, R.color.c_0277BD))
        viewPager.adapter = mainAdapter
        viewPager.registerOnPageChangeCallback(onPageChangeCallback)
    }

}