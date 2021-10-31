package com.devcomentry.photogallery.presention.all_file

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAllFileBinding
import com.devcomentry.photogallery.domain.model.DataLocal
import com.devcomentry.photogallery.domain.model.DateSelect
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presention.all_file.adapter.FileAdapter
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.utils.showToast

class AllFileFragment :
    BaseFragment<FragmentAllFileBinding>(R.layout.fragment_all_file) {

    val fileAdapter: FileAdapter by lazy {
        FileAdapter(onItemSelected, onItemUnselected, onItemClick)
    }

    var numFileSelected = 0

    var dataLocal: DataLocal? = null

    lateinit var intentSenderLauncher: ActivityResultLauncher<IntentSenderRequest>

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

        intentSenderLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
                if (it.resultCode == RESULT_OK) {
                    requireContext().showToast(R.string.file_deleted_mess)
                    unselectedAll()
                    localDataViewModel.refreshData()
                } else {
                    requireContext().showToast(R.string.could_not_deleted_file_mess)
                }
            }
    }

    override fun onResume() {
        super.onResume()
        unselectedAll()
    }
}