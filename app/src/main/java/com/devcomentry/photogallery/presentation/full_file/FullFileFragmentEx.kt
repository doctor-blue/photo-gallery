package com.devcomentry.photogallery.presentation.full_file

import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.devcomentry.photogallery.presentation.utils.gone
import com.devcomentry.photogallery.presentation.utils.show

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
            blurViewBottom.gone()
            blurViewTop.gone()
        } else {
            imvBack.show()
            txtName.show()
            txtDate.show()
            imvDelete.show()
            imvShare.show()
            imvDetail.show()
            imvLock.show()
            blurViewBottom.show()
            blurViewTop.show()
        }
    }
}
 fun FullFileFragment.hideSystemUI(window: Window = requireActivity().window) {

    WindowCompat.setDecorFitsSystemWindows(window, false)
    WindowInsetsControllerCompat(window, binding.root).let { controller ->
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}

 fun FullFileFragment.showSystemUI(window: Window = requireActivity().window) {
    WindowCompat.setDecorFitsSystemWindows(window, true)
    WindowInsetsControllerCompat(
        window,
        binding.root
    ).show(WindowInsetsCompat.Type.systemBars())
}