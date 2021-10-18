package com.devcomentry.photogallery.presention.all_file

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bumptech.glide.Glide
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.presention.utils.setPreventDoubleClick

class AdapterGridSectioned(
    private val items: List<SectionImage>,
    private val listener: OnImageClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_IMAGE = 0
        private const val ITEM_TITLE = 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_IMAGE) {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_grid_image_sectioned, parent, false)
            ImageViewHolder(v)
        } else {
            val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_grid_date_sectioned, parent, false)
            DateTitleViewHolder(v)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = items[position]

        val layoutParams = holder.itemView.layoutParams as StaggeredGridLayoutManager.LayoutParams
        layoutParams.isFullSpan = currentItem.isTitleSection

        return when (getItemViewType(position)) {
            ITEM_IMAGE -> {
                (holder as ImageViewHolder).bind(currentItem)
                holder.containerRipple.setPreventDoubleClick {
                    listener.onImageClick(position)
                }
            }
            else -> {
                (holder as DateTitleViewHolder).bind(currentItem)
            }
        }

    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return if (items[position].isTitleSection) {
            ITEM_TITLE
        } else {
            ITEM_IMAGE
        }
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image: ImageView = itemView.findViewById(R.id.img_grid)
        val containerRipple: View = itemView.findViewById(R.id.container_ripple)
        fun bind(item: SectionImage) {
            Glide.with(image.context)
                .load(item.image)
                .into(image)
        }
    }

    inner class DateTitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTitle: TextView = itemView.findViewById(R.id.tv_title_section)
        fun bind(item: SectionImage) {
            dateTitle.text = item.title
        }
    }
}