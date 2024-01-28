package com.aos.coordinator.pager.example.coordinator

import android.content.Context
import android.view.View
import com.aos.coordinator.pager.example.RecyclerListener
import com.overman.coordinator.viewpager.adapter.CoordinatorRecyclerAdapter

class TestCoordinatorRecyclerAdapter(
    private val dataList: List<String>,
    private val recyclerListener: RecyclerListener
) : CoordinatorRecyclerAdapter<TestViewHolder>() {

    override fun onPreCreateViewHolder(view: View, viewType: Int): TestViewHolder {
        return TestViewHolder(view, recyclerListener)
    }

    override fun createContentView(context: Context, viewType: Int): View {
        // 여기서 만드는... 뷰는 메인 컨텐츠 뷰가 된다.
        return View(context)
    }

    override fun onBindingContentView(holder: TestViewHolder, position: Int) {
        // 여기서 새로운 자신만의 뷰를 바인드 시켜야 한다.
        holder.bind(position)
    }

    override fun getItemCount(): Int = dataList.size


}