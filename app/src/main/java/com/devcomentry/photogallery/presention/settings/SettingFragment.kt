package com.devcomentry.photogallery.presention.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.devcomentry.photogallery.R
import com.devcomentry.photogallery.databinding.FragmentSettingBinding
import com.devcomentry.photogallery.presention.common.BaseFragment

class SettingFragment : BaseFragment<FragmentSettingBinding>(R.layout.fragment_setting) {

    override fun initControls(savedInstanceState: Bundle?) {
        super.initControls(savedInstanceState)

        binding {

            toolbar.setNavigationOnClickListener {
                navController.popBackStack()
            }

            llInviteFriends.setOnClickListener {

            }

            llFeedback.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO)
                intent.data = Uri.parse("mailto:")
                intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("devcomentry@gmail.com"))
                intent.putExtra(Intent.EXTRA_SUBJECT, "Moony Feedback")
                startActivity(Intent.createChooser(intent, "Send Email"))
            }

            llRate.setOnClickListener {

            }

            llAboutUs.setOnClickListener {

            }

        }

    }

    override fun initEvents() {
        super.initEvents()
    }

    private fun openLink(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}