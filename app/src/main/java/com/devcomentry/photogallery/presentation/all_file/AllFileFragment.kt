package com.devcomentry.photogallery.presentation.all_file

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAllFileBinding
import com.devcomentry.photogallery.domain.model.DataLocal
import com.devcomentry.photogallery.domain.model.DateSelect
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.presentation.all_file.adapter.FileAdapter
import com.devcomentry.photogallery.presentation.common.BaseFragment
import com.devcomentry.photogallery.presentation.utils.HideBottomNavEvent
import com.devcomentry.photogallery.presentation.utils.showToast
import org.greenrobot.eventbus.EventBus

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
            rvAllFile.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (dy > 0) {
                        EventBus.getDefault().post(HideBottomNavEvent(true))
                    } else {
                        EventBus.getDefault().post(HideBottomNavEvent(false))
                    }
                }
            })
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
            val layoutManager = GridLayoutManager(requireContext(), 3)
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int = when {
                    position == fileAdapter.currentList.size -> 3
                    fileAdapter.currentList[position] is FileModel -> 1
                    else -> 3
                }

            }
            rvAllFile.layoutManager = layoutManager
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