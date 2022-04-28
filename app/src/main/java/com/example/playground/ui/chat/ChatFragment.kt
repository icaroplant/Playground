package com.example.playground.ui.chat

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.playground.R
import com.example.playground.common.CoreFragment
import com.example.playground.databinding.ChatFragmentBinding
import com.example.playground.extensions.toHtml
import org.koin.androidx.viewmodel.ext.android.viewModel
import permissions.dispatcher.*

@RuntimePermissions
class ChatFragment : CoreFragment<ChatFragmentBinding>(ChatFragmentBinding::inflate) {

    private val viewModel: ChatViewModel by viewModel()
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        try {
            binding.tvSelectedContent.text = uri.toString()
            val contentResolver = requireActivity().contentResolver
            val inputStream = contentResolver.openInputStream(uri)
            binding.tvInputStream.text = contentResolver.getType(uri)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun setupViews() {

        binding.btWhatsapp.setOnClickListener{
            openWhatsApp(requireContext(), "https://wa.me/553130034070")
        }

        binding.btBrowser.setOnClickListener {
            openBrowserWithPermissionCheck()
        }

        binding.tvSpannedText.text = String.format(
            getString(R.string.video_conference_confirmation_description_success),
            "Quarta, 4 de março",
            "11:30"
        ).toHtml

        val count = 1
        binding.tvPlural.text = String.format(
            resources.getQuantityString(R.plurals.video_conference_duration_time_full, count, count),
        )
    }

    private fun openWhatsApp(context: Context, toNumber: String, msg: String? = null) {
        context.run {
            val isAppInstalled = appInstalledOrNot(context, "com.whatsapp")
            if (isAppInstalled) {
                //val msgEncoded = URLEncoder.encode(msg, "UTF-8")
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(toNumber))
                startActivity(intent)
            } else {
                Toast.makeText(context, "Whatsapp não instalado!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun appInstalledOrNot(context: Context, uri: String): Boolean {
        return context.run {
            try {
                packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES)
                true
            } catch (e: PackageManager.NameNotFoundException) {
                false
            }
        } ?: false
    }

    @NeedsPermission(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun openBrowser() {
        getContent.launch("image/*")
    }

    @OnShowRationale(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun showRationale(request: PermissionRequest) {
    }

    @OnPermissionDenied(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun onPermissionsDenied() {
        Toast.makeText(requireContext(), "PERMISSIONS DENIED", Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )
    fun onPermissionsNeverAskAgain() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

}