package com.example.playground.ui.chat

import android.Manifest
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import com.example.playground.R
import com.example.playground.extensions.toHtml
import kotlinx.android.synthetic.main.chat_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import permissions.dispatcher.*
import java.lang.Exception

@RuntimePermissions
class ChatFragment : Fragment() {

    private val viewModel: ChatViewModel by viewModel()
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        tvSelectedContent.text = uri.toString()
        try {
            val contentResolver = requireActivity().contentResolver
            val inputStream = contentResolver.openInputStream(uri)
            tvInputStream.text = contentResolver.getType(uri)
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btWhatsapp.setOnClickListener{
            openWhatsApp(requireContext(), "https://wa.me/553130034070")
        }

        btBrowser.setOnClickListener {
            openBrowserWithPermissionCheck()
        }

        tvSpannedText.text = String.format(
            getString(R.string.video_conference_confirmation_description_success),
            "Quarta, 4 de março",
            "11:30"
        ).toHtml

        val count = 1
        tvPlural.text = String.format(
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