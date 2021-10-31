package com.devcomentry.photogallery.presention.file_list

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentFileListBinding
import com.devcomentry.photogallery.domain.model.DataLocal
import com.devcomentry.photogallery.domain.model.DateSelect
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presention.all_file.adapter.FileAdapter
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.utils.Constants
import kotlin.math.log

class FileListFragment : BaseFragment<FragmentFileListBinding>(R.layout.fragment_file_list) {
    val fileAdapter: FileAdapter by lazy {
        FileAdapter(onItemSelected, onItemUnselected, onItemClick)
    }

    var numFileSelected = 0

    var dataLocal: DataLocal? = null
    var idFolder: Long = -1

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
            toolbar.setNavigationOnClickListener {
                navController.popBackStack()
            }

            toolbarSelected.setOnMenuItemClickListener {
                onSelectedToolbarItemClick(it)
                true
            }
        }
    }


    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)
        binding.toolbar.title = arguments?.getString("folderName")
        idFolder = arguments?.getLong("idFolder") ?: -1

        localDataViewModel.dataLocal.observe(viewLifecycleOwner, { dataLocal ->
            this.dataLocal = dataLocal
            val list: MutableList<Any> = mutableListOf()
            list.addAll(if (idFolder != -1L) dataLocal.file.filter { it.idFolder == idFolder } else dataLocal.file)

            val setMonth = list.map {
                val file = it as FileModel
                Constants.monthFormatter.format(file.timeCreated)
            }.toSet()

            list.addAll(
                0,
                dataLocal.listMonth.filter { month ->
                    !setMonth.find { it == month.month }.isNullOrEmpty()
                })

            list.sortByDescending { if (it is FileModel) it.timeCreated else (it as DateSelect).time }
            fileAdapter.submitList(list)

            showEmptyLisLayout(list.isEmpty())
            Log.d("AllFile", "initControls: $setMonth ")
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