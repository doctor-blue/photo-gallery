package com.devcomentry.photogallery.presention.all_file

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAllFileBinding
import com.devcomentry.photogallery.presention.all_file.adapter.FileAdapter
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.utils.PermissionUtils
import com.devcomentry.photogallery.presention.utils.convertDpToPixel

class AllFileFragment : BaseFragment<FragmentAllFileBinding>(R.layout.fragment_all_file) {

    private val fileAdapter: FileAdapter by lazy {
        FileAdapter()
    }

    override fun initEvents() {
        super.initEvents()

        PermissionUtils.checkPermission(requireContext(), {}, {})

    }

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)

        localDataViewModel.dataLocal.observe(viewLifecycleOwner, {
            fileAdapter.submitList(it.file)
        })

        binding {
            rvAllFile.layoutManager = GridLayoutManager(requireContext(), 3)
            rvAllFile.addItemDecoration(
                GridSpacingItemDecoration(
                    3,
                    2,
                    false
                )
            )
            rvAllFile.adapter = fileAdapter

        }

    }
}