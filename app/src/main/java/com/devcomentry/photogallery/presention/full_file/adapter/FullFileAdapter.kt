package com.devcomentry.photogallery.presention.full_file.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FullImageItemBinding
import com.devcomentry.photogallery.domain.model.FileModel

class FullFileAdapter : ListAdapter<FileModel, FullFileAdapter.FullFileViewHolder>(FileDiff()) {
    var isDetailVisible = false
        set(value) {
            field = value
            showDetailView()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FullFileViewHolder {
        val binding =
            FullImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return FullFileViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FullFileViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    override fun onBindViewHolder(
        holder: FullFileViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            for (item in payloads) {
                if (item is Payload) {
                    holder.onBind(currentList[position] as FileModel)
                }
            }
        }
    }

    private fun showDetailView() {
        for (i in currentList.indices) {
            if (currentList[i] is FileModel)
                notifyItemChanged(i, Payload())
        }
    }

    inner class FullFileViewHolder(
        val binding: FullImageItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        var fileModel: FileModel? = null

        init {
            initEvents()
        }

        fun onBind(fileModel: FileModel) {
            this.fileModel = fileModel
            setImage(fileModel)
            setData(fileModel)
            setFunctionButtonVisible(isDetailVisible)
        }

        fun setDetailVisible() {
            isDetailVisible = !isDetailVisible
        }

        private fun setImage(item: FileModel) {
            Glide.with(binding.root.context).load(item.uri).error(getError())
                .into(binding.imgFull)
        }

        private fun getError(): Int {
            return R.drawable.ic_image_not_found
        }

    }
}