package com.example.playground.ui.chat

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.playground.R
import kotlinx.android.synthetic.main.chat_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import permissions.dispatcher.*

@RuntimePermissions
class ChatFragment : Fragment() {

    private val viewModel: ChatViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.chat_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button1.setOnClickListener{
            openWhatsApp(requireContext(), "https://wa.me/553130034070")
        }

        getPermissionsWithPermissionCheck()

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
        Manifest.permission.CAMERA,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.RECORD_AUDIO
    )
    fun getPermissions() {
        Toast.makeText(requireContext(), "PERMISSIONS GRANTED", Toast.LENGTH_SHORT).show()
    }

    @OnShowRationale(
        Manifest.permission.CAMERA,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.RECORD_AUDIO
    )
    fun showRationale(request: PermissionRequest) {
    }

    @OnPermissionDenied(
        Manifest.permission.CAMERA,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.RECORD_AUDIO
    )
    fun onPermissionsDenied() {
        Toast.makeText(requireContext(), "PERMISSIONS DENIED", Toast.LENGTH_SHORT).show()
    }

    @OnNeverAskAgain(
        Manifest.permission.CAMERA,
        Manifest.permission.MODIFY_AUDIO_SETTINGS,
        Manifest.permission.RECORD_AUDIO
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