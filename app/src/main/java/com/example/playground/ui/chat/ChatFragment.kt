package com.example.playground.ui.chat

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.playground.R
import kotlinx.android.synthetic.main.chat_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

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

}