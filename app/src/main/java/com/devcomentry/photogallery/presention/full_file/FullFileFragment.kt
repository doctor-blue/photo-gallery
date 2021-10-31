package com.devcomentry.photogallery.presention.full_file

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentFullFileBinding
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.full_file.adapter.FullFileAdapter


class FullFileFragment : BaseFragment<FragmentFullFileBinding>(R.layout.fragment_full_file) {

    val fullFileAdapter: FullFileAdapter by lazy {
        FullFileAdapter()
    }

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)

        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        requireActivity().window.statusBarColor = Color.parseColor("#101010")
        initViewPager()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        requireActivity().window.statusBarColor = Color.parseColor("#03a9f4")
    }

}