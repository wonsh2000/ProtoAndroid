package com.wiiv.baseapp.ui.main.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.wiiv.baseapp.R
import com.wiiv.baseapp.common.ui.view.recyclerview.BaseDataBindingViewHolder
import com.wiiv.baseapp.common.ui.view.recyclerview.BaseRecyclerViewAdapter
import com.wiiv.baseapp.common.ui.view.recyclerview.BaseViewHolder
import com.wiiv.baseapp.databinding.SearchListItemBinding
import com.wiiv.baseapp.data.model.Document


interface EndlessScrollListener {
    fun onLoadMore()
}

class SearchBookAdaptor(
    private val onClickItem: ((Document) -> Unit)
) : BaseRecyclerViewAdapter<Document>() {
    companion object {
        const val VISIBLE_THRESHOLD = 8
    }

    lateinit var endlessScrollListener: EndlessScrollListener

    override fun getItemId(position: Int): Long {
        return getItems()[position].isbn.hashCode().toLong()
    }

    override fun createRecyclableViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Document> = SearchBookItemViewHodler(parent)

    override fun replaceAll(items: List<Document>) {
        val preItem = getItems()
        val diifResult = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                preItem[oldItemPosition].isbn == items[newItemPosition].isbn

            override fun getOldListSize(): Int = preItem.size

            override fun getNewListSize(): Int = items.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return preItem[oldItemPosition].title == items[newItemPosition].title
            }
        })
        super.replaceAll(items)
        diifResult.dispatchUpdatesTo(this)
    }

    inner class SearchBookItemViewHodler(parent: ViewGroup) :
        BaseDataBindingViewHolder<Document, SearchListItemBinding>(
            R.layout.search_list_item, parent
        ) {

        override fun SearchListItemBinding.onDataBind(data: Document) {
            document = data

            CLMain.setOnClickListener {
                onClickItem.invoke(getData())
            }
        }
    }
}