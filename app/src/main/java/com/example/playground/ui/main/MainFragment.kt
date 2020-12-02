package com.example.playground.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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
    private var spanCount = 3

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe(viewModel.musicasLiveData) { musicas ->
            titulos = ""
            musicas.forEach {m ->
                val titulo = m.id.toString() + ": " + m.name + "\n"
                titulos += titulo
            }
            titulos = titulos.removeSuffix("\n")

            button1.isEnabled = true

            rv_list_musicas.apply {
                layoutManager = GridLayoutManager(activity,spanCount)
                adapter = MusicaAdapter(musicas, ::onMusicaItemClick, ::onMusicaItemLongClick)
                alpha = 1.0f
            }
            progressBar.visibility = View.INVISIBLE

        }

        observe(viewModel.musicDeleted){
            rv_list_musicas?.adapter?.run {
                loadMusics()
            }
        }

        button1.setOnClickListener {
            rv_list_musicas?.run {
                alpha = 0.6f
            }
            loadMusics()
        }

        button2.setOnClickListener {
            rv_list_musicas.adapter?.let {adapter ->
                rv_list_musicas.layoutManager?.let {layoutManager ->
                    if(layoutManager is GridLayoutManager){
                        spanCount = if(layoutManager.spanCount == 3) 1 else 3
                        rv_list_musicas.layoutManager = GridLayoutManager(activity, spanCount)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }

        button3.setOnClickListener{
            val action = MainFragmentDirections.actionMainFragmentToSaveMusicFragment()
            findNavController().navigateWithAnimations(action)
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
    }

    private fun loadMusics(){
        progressBar.visibility = View.VISIBLE
        button1.isEnabled = false
        viewModel.loadMusics()
    }

    private fun onMusicaItemClick(musica: MusicEntity){
        val action = MainFragmentDirections.actionMainFragmentToSaveMusicFragment(
            musicId = musica.id,
            musicName = musica.name,
            musicArtist = musica.artist
        )
        findNavController().navigateWithAnimations(action)
    }

    private fun onMusicaItemLongClick(musica: MusicEntity) : Boolean{
        viewModel.deleteMusic(musica)
        return true
    }
}