package com.aos.coordinator.pager.example

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aos.coordinator.pager.example.databinding.LayoutListItemEmptyBottomBinding
import com.aos.coordinator.pager.example.databinding.LayoutListItemFooterBottomBinding
import com.aos.coordinator.pager.example.databinding.LayoutListItemType1BottomBinding
import com.aos.coordinator.pager.example.databinding.LayoutListItemType2BottomBinding
import com.aos.coordinator.pager.example.databinding.LayoutListItemType3BottomBinding
import com.aos.coordinator.pager.example.databinding.LayoutListItemType4BottomBinding
import com.aos.coordinator.pager.example.databinding.LayoutListItemType5BottomBinding
import com.aos.coordinator.pager.example.databinding.LayoutListItemType6BottomBinding
import com.aos.coordinator.pager.example.databinding.LayoutListItemType7BottomBinding
import com.aos.coordinator.pager.example.databinding.LayoutListItemType8BottomBinding
import com.aos.coordinator.pager.example.databinding.LayoutListItemType9BottomBinding
import com.aos.coordinator.pager.example.viewholder.ItemType3ViewHolder
import com.aos.coordinator.pager.example.viewholder.ItemType9ViewHolder
import com.aos.coordinator.pager.example.viewholder.ItemType7ViewHolder
import com.aos.coordinator.pager.example.viewholder.ItemType5ViewHolder
import com.aos.coordinator.pager.example.viewholder.EmptyBottomItemViewHolder
import com.aos.coordinator.pager.example.viewholder.FooterBottomItemViewHolder
import com.aos.coordinator.pager.example.viewholder.ItemType2ViewHolder
import com.aos.coordinator.pager.example.viewholder.ItemType6ViewHolder
import com.aos.coordinator.pager.example.viewholder.ItemType1ViewHolder
import com.aos.coordinator.pager.example.viewholder.ItemType4ViewHolder
import com.aos.coordinator.pager.example.viewholder.ItemType8ViewHolder

class MainBottomListAdapter(
    private val list: ArrayList<MainBottomListItem>
) : RecyclerView.Adapter<MainBottomItemViewHolder>() {

    enum class Type {
        ITEM_TYPE_1,
        ITEM_TYPE_2,
        ITEM_TYPE_3,
        ITEM_TYPE_4,
        ITEM_TYPE_5,
        ITEM_TYPE_6,
        ITEM_TYPE_7,
        ITEM_TYPE_8,
        ITEM_TYPE_9,
        FOOTER,
        EMPTY
    }

    private val NAME = MainBottomListAdapter::class.java.simpleName

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainBottomItemViewHolder {
        when(viewType) {
            Type.ITEM_TYPE_1.ordinal -> {
                val binding = ViewBindingCreator<LayoutListItemType1BottomBinding>(R.layout.layout_list_item_type_1_bottom)
                    .createViewHolder(parent)
                return ItemType1ViewHolder(binding)
            }
            Type.ITEM_TYPE_2.ordinal -> {
                val binding = ViewBindingCreator<LayoutListItemType2BottomBinding>(R.layout.layout_list_item_type_2_bottom)
                    .createViewHolder(parent)
                return ItemType2ViewHolder(binding)
            }
            Type.ITEM_TYPE_3.ordinal -> {
                val binding = ViewBindingCreator<LayoutListItemType3BottomBinding>(R.layout.layout_list_item_type_3_bottom)
                    .createViewHolder(parent)
                return ItemType3ViewHolder(binding)
            }
            Type.ITEM_TYPE_4.ordinal -> {
                val binding = ViewBindingCreator<LayoutListItemType4BottomBinding>(R.layout.layout_list_item_type_4_bottom)
                    .createViewHolder(parent)
                return ItemType4ViewHolder(binding)
            }
            Type.ITEM_TYPE_5.ordinal -> {
                val binding = ViewBindingCreator<LayoutListItemType5BottomBinding>(R.layout.layout_list_item_type_5_bottom)
                    .createViewHolder(parent)
                return ItemType5ViewHolder(binding)
            }
            Type.ITEM_TYPE_6.ordinal -> {
                val binding = ViewBindingCreator<LayoutListItemType6BottomBinding>(R.layout.layout_list_item_type_6_bottom)
                    .createViewHolder(parent)
                return ItemType6ViewHolder(binding)
            }
            Type.ITEM_TYPE_7.ordinal -> {
                val binding = ViewBindingCreator<LayoutListItemType7BottomBinding>(R.layout.layout_list_item_type_7_bottom)
                    .createViewHolder(parent)
                return ItemType7ViewHolder(binding)
            }
            Type.ITEM_TYPE_8.ordinal -> {
                val binding = ViewBindingCreator<LayoutListItemType8BottomBinding>(R.layout.layout_list_item_type_8_bottom)
                    .createViewHolder(parent)
                return ItemType8ViewHolder(binding)
            }
            Type.ITEM_TYPE_9.ordinal -> {
                val binding = ViewBindingCreator<LayoutListItemType9BottomBinding>(R.layout.layout_list_item_type_9_bottom)
                    .createViewHolder(parent)
                return ItemType9ViewHolder(binding)
            }
            Type.FOOTER.ordinal -> {
                val binding = ViewBindingCreator<LayoutListItemFooterBottomBinding>(R.layout.layout_list_item_footer_bottom)
                    .createViewHolder(parent)
                return FooterBottomItemViewHolder(binding)
            }
            else -> {
                val binding = ViewBindingCreator<LayoutListItemEmptyBottomBinding>(R.layout.layout_list_item_empty_bottom)
                    .createViewHolder(parent)
                return EmptyBottomItemViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: MainBottomItemViewHolder, position: Int) {
        when(indexToTypeValue(position)) {
            Type.ITEM_TYPE_1 -> (holder as ItemType1ViewHolder).bind(position = position)
            Type.ITEM_TYPE_2 -> (holder as ItemType2ViewHolder).bind(position = position)
            Type.ITEM_TYPE_3 -> (holder as ItemType3ViewHolder).bind(position = position)
            Type.ITEM_TYPE_4 -> (holder as ItemType4ViewHolder).bind(position = position)
            Type.ITEM_TYPE_5 -> (holder as ItemType5ViewHolder).bind(position = position)
            Type.ITEM_TYPE_6 -> (holder as ItemType6ViewHolder).bind(position = position)
            Type.ITEM_TYPE_7 -> (holder as ItemType7ViewHolder).bind(position = position)
            Type.ITEM_TYPE_8 -> (holder as ItemType8ViewHolder).bind(position = position)
            Type.ITEM_TYPE_9 -> (holder as ItemType9ViewHolder).bind(position = position)
            Type.FOOTER -> (holder as FooterBottomItemViewHolder).bind(position = position)
            Type.EMPTY -> (holder as EmptyBottomItemViewHolder).bind(position = position)
        }
    }

    override fun getItemCount(): Int = list.size

    private fun indexToTypeValue(index: Int): Type {
        return list[index].type
    }

    override fun getItemViewType(position: Int): Int {
        return when(list[position].type) {
            Type.ITEM_TYPE_1 -> Type.ITEM_TYPE_1.ordinal
            Type.ITEM_TYPE_2 -> Type.ITEM_TYPE_2.ordinal
            Type.ITEM_TYPE_3 -> Type.ITEM_TYPE_3.ordinal
            Type.ITEM_TYPE_4 -> Type.ITEM_TYPE_4.ordinal
            Type.ITEM_TYPE_5 -> Type.ITEM_TYPE_5.ordinal
            Type.ITEM_TYPE_6 -> Type.ITEM_TYPE_6.ordinal
            Type.ITEM_TYPE_7 -> Type.ITEM_TYPE_7.ordinal
            Type.ITEM_TYPE_8 -> Type.ITEM_TYPE_8.ordinal
            Type.ITEM_TYPE_9 -> Type.ITEM_TYPE_9.ordinal
            Type.FOOTER -> Type.FOOTER.ordinal
            Type.EMPTY -> Type.EMPTY.ordinal
        }
    }

}
