package com.devcomentry.photogallery.presention.photo_detail

import android.os.Bundle
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentFileDetailBinding
import com.devcomentry.photogallery.domain.model.FileModel
import com.devcomentry.photogallery.domain.model.Folder
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.utils.Constants
import com.devcomentry.photogallery.presention.utils.formatFileSize

class FileDetailFragment : BaseFragment<FragmentFileDetailBinding>(R.layout.fragment_file_detail) {

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)

        localDataViewModel.dataLocal.observe(viewLifecycleOwner, { dataLocal ->
            val idFolder = arguments?.getLong("idFolder") ?: -1
            val idFile = arguments?.getLong("idFile") ?: -1
            val file = dataLocal.file.find { idFile == it.id }
            val folder = dataLocal.folder.find { idFolder == it.id }

            if (file != null && folder != null) {
                setData(file, folder)
            }
        })
    }

    override fun initEvents() {
        super.initEvents()
        binding.toolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }
    }

    private fun setData(fileModel: FileModel, folder: Folder) {
        binding {
            txtFolderName.text = folder.name
            txtFolderDes.text = fileModel.path

            txtDateTitle.text = Constants.formatter.format(fileModel.timeCreated)
            txtDateDes.text = Constants.timeFormatter.format(fileModel.timeCreated)

            txtNameTitle.text = fileModel.name
            txtNameDes.text = ("${fileModel.width}x${fileModel.height} * ${
                formatFileSize(
                    fileModel.size,
                    requireContext()
                )
            }")

        }
    }


}