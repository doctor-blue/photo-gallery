package com.devcomentry.photogallery.presention.full_file

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