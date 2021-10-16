package com.devcomentry.photogallery.presention.all_file

import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAllFileBinding
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.utils.PermissionUtils

class AllFileFragment : BaseFragment<FragmentAllFileBinding>(R.layout.fragment_all_file) {

    override fun initEvents() {
        super.initEvents()

       PermissionUtils.checkPermission(requireContext(),{},{})
    }
}