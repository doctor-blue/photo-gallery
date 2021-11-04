package com.devcomentry.photogallery.presention.full_file

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentFullFileBinding
import com.devcomentry.photogallery.presention.common.BaseFragment
import com.devcomentry.photogallery.presention.full_file.adapter.FullFileAdapter
import com.devcomentry.photogallery.presention.utils.*


class FullFileFragment : BaseFragment<FragmentFullFileBinding>(R.layout.fragment_full_file) {

    val fullFileAdapter: FullFileAdapter by lazy {
        FullFileAdapter {
            isFunctionButtonVisible = !isFunctionButtonVisible
        }
    }

    private var isFunctionButtonVisible = false
        set(value) {
            field = value
            setFunctionButtonVisible(value)
            if (value) {
                hideSystemUI()
            } else {
                showSystemUI()
            }
        }
    lateinit var intentSenderLauncher: ActivityResultLauncher<IntentSenderRequest>

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)
        intentSenderLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    requireContext().showToast(R.string.file_deleted_mess)
                    localDataViewModel.refreshData()
                } else {
                    requireContext().showToast(R.string.could_not_deleted_file_mess)
                }
            }

        initViewPager()
        initData()
    }

    override fun initEvents() {
        super.initEvents()
        binding {
            imvDelete.setPreventDoubleClick {
            }
            imvBack.setPreventDoubleClick {
                navController.popBackStack()
            }
            vpFullFile.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        val currentItem = fullFileAdapter.currentList[position]
                        binding {
                            txtName.text = currentItem.name
                            txtDate.text = Constants.formatter.format(currentItem.timeCreated)
                        }
                    }
                })

            imvDelete.setPreventDoubleClick {
                val currentItem = fullFileAdapter.currentList[vpFullFile.currentItem]

                performDeleteImage(
                    listOf(currentItem),
                    requireContext(),
                    intentSenderLauncher,
                    lifecycle = lifecycle,
                ) {
                    localDataViewModel.refreshData()
                }
            }
            imvShare.setPreventDoubleClick {
                val currentItem = Uri.parse(fullFileAdapter.currentList[vpFullFile.currentItem].uri)
                shareImageTo(arrayListOf(currentItem), requireContext())
            }
            imvDetail.setPreventDoubleClick {
                val currentItem = fullFileAdapter.currentList[vpFullFile.currentItem]
                requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

                safeNav(
                    R.id.fullFileFragment,
                    FullFileFragmentDirections.actionFullFileFragmentToFileDetailFragment(
                        idFile = currentItem.id,
                        idFolder = currentItem.idFolder
                    )
                )
            }
            imvLock.setPreventDoubleClick {
                val currentItem = fullFileAdapter.currentList[vpFullFile.currentItem]
                performDeleteImage(
                    listOf(currentItem),
                    requireContext(),
                    intentSenderLauncher,
                    lifecycle = lifecycle,
                    isHide = true,
                    dialogMesRes = R.string.hide_file_complete_mess
                ) {
                    localDataViewModel.refreshData()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showSystemUI()
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }


}