 package com.example.playground.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.playground.R
import com.example.playground.data.db.ResponseModel
import com.example.playground.data.db.entity.MusicEntity
import com.example.playground.extensions.*
import com.example.playground.utils.getColor
import com.example.playground.ui.main.adapters.MusicaAdapter
import com.example.playground.ui.manage.ManageMusicModalBottomSheet
import com.example.playground.ui.manage.MusicModel
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
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

        slParent.setOnRefreshListener(this)
        slParent.setColorSchemeColors(
            getColor(R.color.refresh_progress_1),
            getColor(R.color.refresh_progress_2),
            getColor(R.color.refresh_progress_3)
        )
        slParent.isRefreshing = true

        observe(viewModel.allMusicsEvent) { musicas ->
            titulos = ""
            musicas.forEach {m ->
                val titulo = m.id.toString() + ": " + m.name + "\n"
                titulos += titulo
            }
            titulos = titulos.removeSuffix("\n")

            rv_list_musicas.apply {
                layoutManager = GridLayoutManager(activity,spanCount)
                adapter = MusicaAdapter(musicas, ::onMusicaItemClick, ::onMusicaItemLongClick)
                alpha = 1.0f
            }
            slParent.isRefreshing = false
        }

        observe(viewModel.repositoryReponse){event ->

            event.contentIfNotHandled?.let {response ->
                if(response.success){
                    when (response) {
                        is ResponseModel.INSERT -> {
                            makeSnackBarWithAction("Salvo com sucesso!", "Desfazer") { view ->
                                response.model?.id?.let {id ->
                                    viewModel.deleteMusicFromInsert(id)
                                }
                            }
                        }
                        is ResponseModel.UPDATE -> {
                            makeSnackBarWithAction("Atualizado com sucesso!", "Desfazer") { view ->
                                response.model?.let { backup ->
                                    viewModel.restoreMusicFromUpdate(backup.id, backup.name, backup.artist)
                                }
                            }
                        }
                        is ResponseModel.DELETE -> {
                            makeSnackBarWithAction("Deletado com sucesso!", "Desfazer") { view ->
                                response.model?.let { backup ->
                                    viewModel.restoreMusicFromDelete(backup.id, backup.name, backup.artist)
                                }
                            }
                        }
                        is ResponseModel.RESTORE ->{
                            makeSnackBar("Ação desfeita com sucesso!")
                        }
                        else -> {
                        }
                    }
                } else{
                    response.error?.let {
                        makeSnackBar("Erro: $it")
                    }
                }
            }
        }

        btMudarLayout.setOnClickListener {
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

        fbRegister.setOnClickListener{
            ManageMusicModalBottomSheet.newInstance(viewModel).showOnce(childFragmentManager)
        }

        buttonHome.setOnClickListener{

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

        btDeepLinkInter.apply {
            text = "Marketplace"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("bancointer://marketplace")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }

        btDeepLinkInter2.apply {
            text = "Whatsapp Inter"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("bancointer://whatsappAuthentication")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }

        btDeepLinkInter3.apply {
            text = "One Link"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("https://bancointer.onelink.me/s9CL/31528354")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }

        btDeepLinkInter4.apply {
            text = "gointer Marketplace"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("https://gointer.app/marketplace")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }
    }

    private fun onMusicaItemClick(item: MusicEntity){
        ManageMusicModalBottomSheet.newInstance(
            viewModel,
            MusicModel(item.id, item.name, item.artist)
        ).showOnce(childFragmentManager)
    }

    private fun onMusicaItemLongClick(musica: MusicEntity) : Boolean{
        slParent.isRefreshing = true
        rv_list_musicas.alpha = 0.6f
        viewModel.deleteMusic(musica.id)
        return true
    }

    override fun onRefresh() {
        rv_list_musicas.alpha = 0.6f
        viewModel.viewModelScope.launch {
            delay(2000)
            rv_list_musicas.alpha = 1.0f
            slParent.isRefreshing = false
        }
    }

}