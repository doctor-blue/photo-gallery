package com.devcomentry.photogallery.presention.all_file

import android.content.Context
import android.content.res.TypedArray
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAllFileBinding
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.utils.PermissionUtils
import java.util.*

class AllFileFragment :
    BaseFragment<FragmentAllFileBinding>(R.layout.fragment_all_file), OnImageClickListener {

    private lateinit var adapter: AdapterGridSectioned

    override fun initEvents() {
        super.initEvents()
        PermissionUtils.checkPermission(requireContext(), {}, {})
        initComponent()
    }

    private fun initComponent() {
        binding.rvAllFile.apply {
            layoutManager =
                StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }

        val listOfImageData = generateData()
        adapter = AdapterGridSectioned(listOfImageData, this)
        binding.rvAllFile.adapter = adapter
    }

    private fun generateData(): List<SectionImage> {
        val images = getNatureImages(requireContext())
        images.addAll(getNatureImages(requireContext()))
        images.addAll(getNatureImages(requireContext()))
        images.addAll(getNatureImages(requireContext()))
        images.addAll(getNatureImages(requireContext()))
        images.addAll(getNatureImages(requireContext()))
        images.addAll(getNatureImages(requireContext()))
        images.addAll(getNatureImages(requireContext()))
        images.addAll(getNatureImages(requireContext()))
        images.addAll(getNatureImages(requireContext()))
        images.addAll(getNatureImages(requireContext()))
        images.addAll(getNatureImages(requireContext()))
        images.addAll(getNatureImages(requireContext()))

        val listOfSectionImages = mutableListOf<SectionImage>()

        images.forEach { image ->
            listOfSectionImages.add(SectionImage(image, "IMG_$image.jpg", false))
        }

        var sectCount = 0
        val size = listOfSectionImages.size / 16
        val months = getStringsMonth(requireContext())
        for ((sectIdx, i) in (0 until listOfSectionImages.size / 16).withIndex()) {
            listOfSectionImages.add(sectCount, SectionImage(-1, months[sectIdx], true))
            sectCount += 16
        }

        return listOfSectionImages
    }

    private fun getStringsMonth(ctx: Context): List<String> {
        val items: MutableList<String> = mutableListOf()
        val arr = ctx.resources.getStringArray(R.array.month)
        for (s in arr) items.add(s)
        return items
    }

    private fun getNatureImages(ctx: Context): MutableList<Int> {
        val items: MutableList<Int> = ArrayList()
        val img = ctx.resources.obtainTypedArray(R.array.sample_images)
        for (i in 0 until img.length()) {
            items.add(img.getResourceId(i, -1))
        }
        items.shuffle()
        return items
    }

    override fun onImageClick(position: Int) {
        Toast.makeText(requireContext(), "Image $position", Toast.LENGTH_SHORT).show()
    }

}