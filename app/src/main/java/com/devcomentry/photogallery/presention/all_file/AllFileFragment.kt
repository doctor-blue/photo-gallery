package com.devcomentry.photogallery.presention.all_file

import android.content.ContentUris
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAllFileBinding
import com.devcomentry.photogallery.domain.model.DateSelect
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presention.all_file.adapter.FileAdapter
import com.devcomentry.photogallery.presention.all_file.adapter.Payload
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.utils.PermissionUtils
import com.devcomentry.photogallery.presention.utils.gone
import com.devcomentry.photogallery.presention.utils.show

class AllFileFragment :
    BaseFragment<FragmentAllFileBinding>(R.layout.fragment_all_file) {

    val fileAdapter: FileAdapter by lazy {
        FileAdapter(onItemSelected, onItemUnselected, onItemClick)
    }

    var numFileSelected = 0

    override fun initEvents() {
        super.initEvents()
        PermissionUtils.checkPermission(requireContext(), {
            localDataViewModel.refreshData()
        }, {})

        binding {
            toolbarSelected.setNavigationOnClickListener {
                unselectedAll()
            }

            toolbar.setOnMenuItemClickListener {
                onToolbarItemClick(it)
                true
            }
        }
    }


    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)

        localDataViewModel.dataLocal.observe(viewLifecycleOwner, { dataLocal ->
            val list: MutableList<Any> = mutableListOf()
            list.addAll(dataLocal.file)
            list.addAll(0, dataLocal.listMonth)

            list.sortByDescending { if (it is FileModel) it.timeCreated else (it as DateSelect).time }
            fileAdapter.submitList(list)

            showEmptyLisLayout(list.isEmpty())
        })

        binding {
            rvAllFile.layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
//            rvAllFile.addItemDecoration(
//                GridSpacingItemDecoration(
//                    3,
//                    2,
//                    false
//                )
//            )
            rvAllFile.adapter = fileAdapter

        }
//        queryImageStorage()
    }

    override fun onResume() {
        super.onResume()
        unselectedAll()
    }

    private fun queryImageStorage() {
        val imageProjection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            arrayOf(
                MediaStore.Images.Media.RELATIVE_PATH,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
                MediaStore.Images.Media.BUCKET_ID,
            )
        else arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.TITLE,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID,
        )

        val imageSortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        val cursor = requireContext().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            imageProjection,
            null,
            null,
            imageSortOrder
        )
        cursor.use {
            it?.let {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                val nameColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
                val sizeColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                val dateColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID)
                while (it.moveToNext()) {
                    val id = it.getLong(idColumn)
                    val name = it.getString(nameColumn)
                    val size = it.getString(sizeColumn)
                    val date = it.getString(dateColumn)
                    val contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id
                    )
                    // add the URI to the list
                    // generate the thumbnail
//                    val thumbnail = requireContext().contentResolver.loadThumbnail(contentUri, Size(480, 480), null)
                    Log.d("AllFileFragment", "queryImageStorage: $id $name")
                }
            } ?: kotlin.run {
                Log.e("TAG", "Cursor is null!")
            }
        }
    }
//    private fun initComponent() {
//        binding.rvAllFile.apply {
//            layoutManager =
//                StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
//            setHasFixedSize(true)
//        }
//
//        val listOfImageData = generateData()
//        adapter = AdapterGridSectioned(listOfImageData, this)
//        binding.rvAllFile.adapter = adapter
//    }
//
//    private fun generateData(): List<SectionImage> {
//        val images = getNatureImages(requireContext())
//        images.addAll(getNatureImages(requireContext()))
//        images.addAll(getNatureImages(requireContext()))
//        images.addAll(getNatureImages(requireContext()))
//        images.addAll(getNatureImages(requireContext()))
//        images.addAll(getNatureImages(requireContext()))
//        images.addAll(getNatureImages(requireContext()))
//        images.addAll(getNatureImages(requireContext()))
//        images.addAll(getNatureImages(requireContext()))
//        images.addAll(getNatureImages(requireContext()))
//        images.addAll(getNatureImages(requireContext()))
//        images.addAll(getNatureImages(requireContext()))
//        images.addAll(getNatureImages(requireContext()))
//
//        val listOfSectionImages = mutableListOf<SectionImage>()
//
//        images.forEach { image ->
//            listOfSectionImages.add(SectionImage(image, "IMG_$image.jpg", false))
//        }
//
//        var sectCount = 0
//        val size = listOfSectionImages.size / 16
//        val months = getStringsMonth(requireContext())
//        for ((sectIdx, i) in (0 until listOfSectionImages.size / 16).withIndex()) {
//            listOfSectionImages.add(sectCount, SectionImage(-1, months[sectIdx], true))
//            sectCount += 16
//        }
//
//        return listOfSectionImages
//    }
//
//    private fun getStringsMonth(ctx: Context): List<String> {
//        val items: MutableList<String> = mutableListOf()
//        val arr = ctx.resources.getStringArray(R.array.month)
//        for (s in arr) items.add(s)
//        return items
//    }
//
//    private fun getNatureImages(ctx: Context): MutableList<Int> {
//        val items: MutableList<Int> = ArrayList()
//        val img = ctx.resources.obtainTypedArray(R.array.sample_images)
//        for (i in 0 until img.length()) {
//            items.add(img.getResourceId(i, -1))
//        }
//        items.shuffle()
//        return items
//    }
//
//    override fun onImageClick(position: Int) {
//        Toast.makeText(requireContext(), "Image $position", Toast.LENGTH_SHORT).show()
//    }

}