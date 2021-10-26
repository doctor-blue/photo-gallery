package com.devcomentry.photogallery.presention.all_file

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAllFileBinding
import com.devcomentry.photogallery.domain.model.DataLocal
import com.devcomentry.photogallery.domain.model.DateSelect
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presention.all_file.adapter.FileAdapter
import com.devcomentry.photogallery.presention.common.BaseFragment

class AllFileFragment :
    BaseFragment<FragmentAllFileBinding>(R.layout.fragment_all_file) {

    val fileAdapter: FileAdapter by lazy {
        FileAdapter(onItemSelected, onItemUnselected, onItemClick)
    }

    var numFileSelected = 0

    var dataLocal: DataLocal? = null

    override fun initEvents() {
        super.initEvents()

        binding {
            toolbarSelected.setNavigationOnClickListener {
                unselectedAll()
            }

            toolbar.setOnMenuItemClickListener {
                onToolbarItemClick(it)
                true
            }
            toolbarSelected.setOnMenuItemClickListener {
                onSelectedToolbarItemClick(it)
                true
            }
        }
    }


    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)

        localDataViewModel.dataLocal.observe(viewLifecycleOwner, { dataLocal ->
            this.dataLocal = dataLocal
            val list: MutableList<Any> = mutableListOf()
            list.addAll(dataLocal.file)
            list.addAll(0, dataLocal.listMonth)

            list.sortByDescending { if (it is FileModel) it.timeCreated else (it as DateSelect).time }
            fileAdapter.submitList(list)

            showEmptyLisLayout(list.isEmpty())
            Log.d("AllFile", "initControls: ")
        })

        binding {
            rvAllFile.layoutManager =
                StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            rvAllFile.adapter = fileAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        unselectedAll()
    }
}