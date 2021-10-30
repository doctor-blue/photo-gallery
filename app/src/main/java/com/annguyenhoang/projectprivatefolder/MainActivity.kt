package com.annguyenhoang.projectprivatefolder

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.security.crypto.EncryptedFile
import androidx.security.crypto.MasterKey
import com.annguyenhoang.projectprivatefolder.databinding.ActivityMainBinding
import com.bumptech.glide.Glide
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var readPermissionGranted = false
    private var writePermissionGranted = false
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
                readPermissionGranted =
                    permissions[Manifest.permission.READ_EXTERNAL_STORAGE] ?: readPermissionGranted
                writePermissionGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE]
                    ?: writePermissionGranted

            }

        updateOrRequestPermissions()

        val w = 100
        val h = 100

        val conf = Bitmap.Config.ARGB_8888

        val bmp = Bitmap.createBitmap(w, h, conf)

        binding.btnCreate.setOnClickListener {
            encrypt(
                context = this,
                bitmap = bmp
            )
        }

        binding.btnDecrypt.setOnClickListener {
            val bitmap = decrypt(
                this, File(
                    "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/.test",
                    "abc.jpg"
                )
            )
            Glide.with(this)
                .load(bitmap)
                .into(binding.testImg)
        }
    }


    private fun createHiddenFolder(dirName: String): File {
        val file =
            File("${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)}/.${dirName}")
        if (!file.exists()) {
            file.mkdirs()
        }
        return file
    }

    private fun updateOrRequestPermissions() {
        val hasReadPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val hasWritePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED

        val minSdk29 = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q

        readPermissionGranted = hasReadPermission
        writePermissionGranted = hasWritePermission || minSdk29

        val permissionToRequest = mutableListOf<String>()
        if (!writePermissionGranted) {
            permissionToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (!readPermissionGranted) {
            permissionToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (permissionToRequest.isNotEmpty()) {
            permissionLauncher.launch(permissionToRequest.toTypedArray())
        }

    }

    private fun encrypt(context: Context, bitmap: Bitmap) {
        val mainKey: MasterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val folder = createHiddenFolder("test")

        val encryptedFile = EncryptedFile.Builder(
            context,
            File(
                folder,
                "abc.jpg"
            ),
            mainKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val stream = encryptedFile.openFileOutput()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        stream.flush()
        stream.close()
    }

    private fun decrypt(context: Context, target: File): Bitmap {
        val mainKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        val imageFile = EncryptedFile.Builder(
            context,
            target,
            mainKey,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val stream = imageFile.openFileInput()
        return BitmapFactory.decodeStream(stream)
    }

}