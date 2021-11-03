package com.devcomentry.photogallery.presention.full_file

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
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
        requireActivity().window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
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
                    lifecycle
                ) {
                    localDataViewModel.refreshData()
                }
            }
            imvShare.setPreventDoubleClick {
                val currentItem = Uri.parse(fullFileAdapter.currentList[vpFullFile.currentItem].uri)
                shareImageTo(arrayListOf(currentItem), requireContext())
            }
        }
    }

    override fun onResume() {
        super.onResume()
        showSystemUI()
    }

    override fun onDetach() {
        super.onDetach()
        requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun hideSystemUI(window: Window = requireActivity().window) {

        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window, binding.root).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior =
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    private fun showSystemUI(window: Window = requireActivity().window) {
        WindowCompat.setDecorFitsSystemWindows(window, true)
        WindowInsetsControllerCompat(
            window,
            binding.root
        ).show(WindowInsetsCompat.Type.systemBars())
    }
}