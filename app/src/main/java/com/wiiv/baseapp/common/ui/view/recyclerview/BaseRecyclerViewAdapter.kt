package com.wiiv.baseapp.common.ui.view.recyclerview

import android.view.ViewGroup

abstract class BaseRecyclerViewAdapter<T : Any> : androidx.recyclerview.widget.RecyclerView.Adapter<BaseViewHolder<T>>() {

    private var items = mutableListOf<T>()
    private var itemHeight = 0

    abstract fun createRecyclableViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T>

    override fun getItemCount() = items.size

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val viewHolder = createRecyclableViewHolder(parent, viewType)

        viewHolder.onCreated()
        return viewHolder
    }

    /**
    * Issue : APP-1684 holder.bindData 호출시 java.lang.IllegalArgumentException 발생
    * Resolve : item에 대한 null 체크 코드 추가 / 재현경로 불분명하여 수정적용 후 모니터링 필요
    * */
    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        this.items[position]?.let {
            holder.bindData(it)
            itemHeight = holder.itemView.layoutParams.height
        }
    }

    fun setItems(items: MutableList<T>) {
        this.items = items
    }

    fun getItems() = items

    fun getItem(position: Int) = items[position]

    open fun add(item: T) = items.add(item)

    open fun add(position: Int, item: T) = items.add(position, item)

    open fun addAll(items: Collection<T>) = this.items.addAll(items)

    open fun addAll(items: Array<T>) = this.items.addAll(items)

    open fun addAll(position: Int, items: Collection<T>) = this.items.addAll(position, items)

    open fun set(position: Int, item: T) = items.set(position, item)

    open fun removeAt(position: Int) = items.removeAt(position)

    open fun remove(item: T) = items.remove(item)

    open fun clear() = items.clear()

    open fun replaceAll(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
    }

    open fun getItemHeight() : Int {
        return itemHeight
    }
}