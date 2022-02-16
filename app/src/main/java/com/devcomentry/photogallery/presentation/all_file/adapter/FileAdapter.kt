package com.devcomentry.photogallery.presentation.all_file.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FileItemBinding
import com.devcomentry.photogallery.databinding.ItemFooterBinding
import com.devcomentry.photogallery.databinding.ItemGridDateSectionedBinding
import com.devcomentry.photogallery.domain.model.DateSelect
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presentation.common.FooterViewHolder
import com.devcomentry.photogallery.presentation.utils.getDisplayWidth

class FileAdapter(
    val onItemSelected: (FileModel) -> Unit,
    val onItemUnselected: (FileModel) -> Unit,
    val onItemClick: (FileModel) -> Unit
) : ListAdapter<Any, RecyclerView.ViewHolder>(FileDiff()) {

    var isShowSelector = false
        set(value) {
            if (value != field) {
                field = value
                showSelector()
            }
        }

    companion object {
        const val ITEM_IMAGE = 0
        const val ITEM_TITLE = 1
        const val FOOTER = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        when (viewType) {
            ITEM_IMAGE -> {
                val binding =
                    FileItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                val desiredWidth = parent.context.getDisplayWidth() / 3
                return FileViewHolder(binding, desiredWidth = desiredWidth.toInt())
            }
            ITEM_TITLE -> {
                val binding =
                    ItemGridDateSectionedBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return DateTitleViewHolder(binding)
            }
            else -> {
                val binding =
                    ItemFooterBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                return FooterViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == currentList.size -> FOOTER
            currentList[position] is DateSelect -> ITEM_TITLE
            else -> ITEM_IMAGE
        }
    }

//    override fun getItemCount(): Int {
//        if (currentList.size == 0) {
//            //Return 1 here to show nothing
//            return 1
//        }
//
//        // Add extra view to show the footer view
//        return super.getItemCount() + 1;
//    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FileViewHolder)
            holder.onBind(currentList[position] as FileModel)
        else if (holder is DateTitleViewHolder)
            holder.onBind(currentList[position] as DateSelect)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            for (item in payloads) {
                if (item is Payload) {
                    if (holder is FileViewHolder)
                        holder.onBind(currentList[position] as FileModel)
                }
            }
        }
    }

    private fun showSelector() {
        for (i in currentList.indices) {
            if (currentList[i] is FileModel)
                notifyItemChanged(i, Payload())
        }
    }

    fun unselectedAll() {
        for (i in currentList.indices) {
            val current = currentList[i]
            if (current is FileModel) {
                current.isSelected = false
                notifyItemChanged(i, Payload())
            }
        }
        isShowSelector = false
    }

    fun selectAll() {
        for (i in currentList.indices) {
            val current = currentList[i]
            if (current is FileModel) {
                current.isSelected = true
                notifyItemChanged(i, Payload())
            }
        }
        isShowSelector = true
    }

    inner class FileViewHolder(
        val binding: FileItemBinding,
        val desiredWidth: Int
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            initEvents()
        }

        var fileModel: FileModel? = null

        fun onBind(item: FileModel) {
            this.fileModel = item
            setImage(item)
            showSelector(item, isShowSelector)
        }

        private fun setImage(item: FileModel) {
            Glide.with(binding.root.context).load(item.uri)
                .error(getError()).override(desiredWidth, desiredWidth)
                .into(binding.imgFile)
        }

        private fun getError(): Int {
            return R.drawable.ic_image_not_found
        }

        fun showAllSelector() {
            isShowSelector = true
        }

        fun onItemClick(isSelected: Boolean) {
            if (isShowSelector) {
                onItemSelected(isSelected)
            } else {
                onItemClick(fileModel!!)
            }
            showSelector(fileModel!!, isShowSelector)
        }

        private fun onItemSelected(isSelected: Boolean) {
            if (isSelected) {
                fileModel!!.isSelected = true
                onItemSelected(fileModel!!)
            } else {
                fileModel!!.isSelected = false
                onItemUnselected(fileModel!!)
            }
        }
    }

    inner class DateTitleViewHolder(
        val binding: ItemGridDateSectionedBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(item: DateSelect) {
            binding.tvTitleSection.text = item.month
        }
    }

}