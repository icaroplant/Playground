package com.example.playground.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playground.R
import com.example.playground.data.db.entity.MusicEntity
import com.example.playground.extensions.navigateWithAnimations
import com.example.playground.extensions.observe
import com.example.playground.ui.main.adapters.MusicaAdapter
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()

    private var titulos  = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.mostrar) { mostrar ->
            when(mostrar){
                true -> {
                    text1.visibility = View.VISIBLE
                    text2.visibility = View.VISIBLE
                    text3.visibility = View.VISIBLE
                }
                false -> {
                    text1.visibility = View.GONE
                    text2.visibility = View.GONE
                    text3.visibility = View.GONE
                }
            }
        }

        observe(viewModel.musicasLiveData) { musicas ->
            titulos = ""
            musicas.forEach {m ->
                val titulo = m.id.toString() + ": " + m.name + "\n"
                titulos += titulo
            }
            titulos = titulos.removeSuffix("\n")

            button1.isEnabled = true

            rv_list_musicas.layoutManager = GridLayoutManager(activity,3)
            rv_list_musicas.adapter = MusicaAdapter(musicas, ::onMusicaItemClick)
            progressBar.visibility = View.INVISIBLE
        }

        buttonMostrar.setOnClickListener {
            viewModel.onClickButtonMostrar()
        }

        button1.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            button1.isEnabled = false
            viewModel.onClickButton1()

        }

        button2.setOnClickListener {
            viewModel.onClickButton2()
            rv_list_musicas.adapter?.let {adapter ->
                rv_list_musicas.layoutManager?.let {layoutManager ->
                    if(layoutManager is GridLayoutManager){
                        if(layoutManager.spanCount == 3){
                            rv_list_musicas.layoutManager = GridLayoutManager(activity, 1)
                        }
                        else {
                            rv_list_musicas.layoutManager = GridLayoutManager(activity, 3)
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }

        button3.setOnClickListener {
            val current = findNavController().currentDestination
            viewModel.onClickButton3()
        }

        buttonHome.setOnClickListener(){
//            val bundle = bundleOf(
//                "title" to "Lista de Músicas",
//                "number" to 10,
//                "list" to titulos
//            )

            val action = MainFragmentDirections.actionMainFragmentToHomeFragment(
                title = "Lista de Músicas",
                number =
                if(titulos.isNotEmpty())
                    titulos.split("\n").size
                else 0,
                list = titulos
            )

            findNavController().navigateWithAnimations(action)

        }

        buttonNotification.setOnClickListener(){
            val deeplink = findNavController().createDeepLink()
                .setDestination(R.id.chatFragment)
                .createPendingIntent()

            val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(NotificationChannel(
                    "channelId", "Deep Links", NotificationManager.IMPORTANCE_HIGH)
                )
            }
            val builder = NotificationCompat.Builder(
                requireContext(), "channelId")
                .setContentTitle("Navigation")
                .setContentText("Deep link to ChatFragment")
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                .setContentIntent(deeplink)
                .setAutoCancel(true)
            notificationManager.notify(0, builder.build())
        }

        buttonCadastro.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToSaveMusicFragment()
            findNavController().navigateWithAnimations(action)
        }
    }

    private fun onMusicaItemClick(musica: MusicEntity){
        val action = MainFragmentDirections.actionMainFragmentToSaveMusicFragment(
            musicId = musica.id,
            musicName = musica.name,
            musicArtist = musica.artist
        )
        findNavController().navigateWithAnimations(action)
    }

}