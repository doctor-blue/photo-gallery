package com.devcomentry.photogallery.presention.albums

import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentAllFileBinding
import com.devcomentry.photogallery.presention.common.BaseFragment

class AllFileFragment : BaseFragment<FragmentAllFileBinding>(R.layout.fragment_all_file) {

    override fun initEvents() {
        super.initEvents()

        binding.txtTest.setOnClickListener {
            safeNav(R.id.allFileFragment,R.id.splashFragment)
        }
    }
}