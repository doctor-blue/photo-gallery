package com.devcomentry.photogallery.presention.photo_detail

import android.os.Bundle
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentFullFileBinding
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.photo_detail.adapter.FullFileAdapter

class FullFileFragment:BaseFragment<FragmentFullFileBinding>(R.layout.fragment_full_file) {

    val fullFileAdapter:FullFileAdapter by lazy {
        FullFileAdapter()
    }

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)
        initViewPager()
        initData()
    }
}