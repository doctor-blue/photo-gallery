package com.devcomentry.photogallery.presention.full_file

import com.devcomentry.photogallery.presention.utils.gone
import com.devcomentry.photogallery.presention.utils.show

fun FullFileFragment.initViewPager() {
    binding {
        vpFullFile.adapter = fullFileAdapter
    }
}

fun FullFileFragment.initData() {
    localDataViewModel.dataLocal.observe(viewLifecycleOwner, { dataLocal ->
        val idFolder = arguments?.getLong("idFolder") ?: -1
        val idFile = arguments?.getLong("idFile") ?: -1
        val listFile =
            if (idFolder != -1L) dataLocal.file.filter { it.idFolder == idFolder } else dataLocal.file
        val file = listFile.find { idFile == it.id }
        val position = listFile.indexOf(file)

        fullFileAdapter.submitList(listFile)
        binding.vpFullFile.setCurrentItem(position, false)

    })
}
fun FullFileFragment.setFunctionButtonVisible(isVisible: Boolean) {
    binding {
        if (isVisible) {
            imvBack.gone()
            txtName.gone()
            txtDate.gone()
            imvDelete.gone()
            imvShare.gone()
            imvDetail.gone()
            imvLock.gone()
        } else {
            imvBack.show()
            txtName.show()
            txtDate.show()
            imvDelete.show()
            imvShare.show()
            imvDetail.show()
            imvLock.show()
        }
    }
}