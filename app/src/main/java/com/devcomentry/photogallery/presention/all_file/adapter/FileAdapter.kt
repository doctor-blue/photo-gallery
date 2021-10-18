package com.devcomentry.photogallery.presention.all_file.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FileItemBinding
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presention.utils.convertDpToPixel
import com.devcomentry.photogallery.presention.utils.getDisplayWidth
import com.devcomentry.photogallery.presention.utils.gone
import com.devcomentry.photogallery.presention.utils.show

class FileAdapter : ListAdapter<FileModel, FileAdapter.FileViewHolder>(FileDiff()) {
    private val isShowSelector = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val binding =
            FileItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        val desiredWidth = parent.context.getDisplayWidth() / 3
        return FileViewHolder(binding, desiredWidth = desiredWidth.toInt())
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    fun showSelector() {

    }

    inner class FileViewHolder(
        val binding: FileItemBinding,
        val desiredWidth: Int
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            initEvents()
        }

        fun onBind(fileModel: FileModel) {

            setImage(fileModel)
            binding.cbFile.isChecked = fileModel.isSelected
            if (isShowSelector) {
                binding.viewBlur.show()
                binding.cbFile.show()
            } else {
                binding.viewBlur.gone()
                binding.cbFile.gone()
            }

            if (fileModel.isSelected) {
                binding.root.setBackgroundResource(R.color.blue_200_alpha)
                binding.root.setPadding(binding.root.context.convertDpToPixel(8f).toInt())
            } else {
                binding.root.background = null
                binding.root.setPadding(0)
            }
        }

        private fun setImage(item: FileModel) {
            Glide.with(binding.root.context).load(item.uri)
                .error(getError()).override(desiredWidth, desiredWidth)
                .into(binding.imgFile)
        }

        private fun getError(): Int {
            return R.drawable.ic_image_not_found
        }
    }

}