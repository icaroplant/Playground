 package com.example.playground.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.playground.R
import com.example.playground.data.db.ResponseModel
import com.example.playground.data.db.entity.MusicEntity
import com.example.playground.extensions.*
import com.example.playground.ui.main.adapters.MusicaAdapter
import com.example.playground.ui.manage.ManageMusicModalBottomSheet
import com.example.playground.ui.manage.MusicModel
import com.example.playground.utils.getColor
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


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
            musicas.forEach { m ->
                val titulo = m.id.toString() + ": " + m.name + "\n"
                titulos += titulo
            }
            titulos = titulos.removeSuffix("\n")

            rv_list_musicas.apply {
                layoutManager = GridLayoutManager(activity, spanCount)
                adapter = MusicaAdapter(musicas, ::onMusicaItemClick, ::onMusicaItemLongClick)
                alpha = 1.0f
            }
            slParent.isRefreshing = false
        }

        observe(viewModel.repositoryReponse){ event ->

            event.contentIfNotHandled?.let { response ->
                if(response.success){
                    when (response) {
                        is ResponseModel.INSERT -> {
                            makeSnackBarWithAction("Salvo com sucesso!", "Desfazer") { view ->
                                response.model?.id?.let { id ->
                                    viewModel.deleteMusicFromInsert(id)
                                }
                            }
                        }
                        is ResponseModel.UPDATE -> {
                            makeSnackBarWithAction("Atualizado com sucesso!", "Desfazer") { view ->
                                response.model?.let { backup ->
                                    viewModel.restoreMusicFromUpdate(
                                        backup.id,
                                        backup.name,
                                        backup.artist
                                    )
                                }
                            }
                        }
                        is ResponseModel.DELETE -> {
                            makeSnackBarWithAction("Deletado com sucesso!", "Desfazer") { view ->
                                response.model?.let { backup ->
                                    viewModel.restoreMusicFromDelete(
                                        backup.id,
                                        backup.name,
                                        backup.artist
                                    )
                                }
                            }
                        }
                        is ResponseModel.RESTORE -> {
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
            rv_list_musicas.adapter?.let { adapter ->
                rv_list_musicas.layoutManager?.let { layoutManager ->
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
                if (titulos.isNotEmpty())
                    titulos.split("\n").size
                else 0,
                list = titulos
            )

            findNavController().navigateWithAnimations(action)

        }

        buttonInstrument.setOnClickListener {
            findNavController().navigateWithAnimations(
                MainFragmentDirections.actionMainFragmentToInstrumentFragment()
            )
        }

        buttonNotification.setOnClickListener(){
            val deeplink = findNavController().createDeepLink()
                .setDestination(R.id.chatFragment)
                .createPendingIntent()

            val notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notificationManager.createNotificationChannel(
                    NotificationChannel(
                        "channelId", "Deep Links", NotificationManager.IMPORTANCE_HIGH
                    )
                )
            }
            val builder = NotificationCompat.Builder(
                requireContext(), "channelId"
            )
                .setContentTitle("Navigation")
                .setContentText("Deep link to ChatFragment")
                .setSmallIcon(R.drawable.ic_notification_icon)
                .setColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                .setContentIntent(deeplink)
                .setAutoCancel(true)
            notificationManager.notify(0, builder.build())
        }

        button.apply {
            text = "Marketplace"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("bancointer://marketplace")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }

        button2.apply {
            text = "Whatsapp"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("bancointer://whatsappAuthentication")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }

        button3.apply {
            text = "One Link"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("https://bancointer.onelink.me/s9CL/31528354")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }

        button4.apply {
            text = "gointer Marketplace"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("https://gointer.app/marketplace")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }

        button5.apply {
            text = "Convidar Amigo"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("bancointer://convidarAmigo")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }

        button6.apply {
            text = "One Link Marketplace Produto"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("https://bancointer.onelink.me/5zQP?pid=COMPARTILHAR&af_force_deeplink=true&af_web_dp=https%3A%2F%2Fcdnmarketplace.uatbi.com.br%2Fecommerce%2Fprodutos%2F2020-11-06%2FCasasBahia%2Fproduto-12158.html&af_dp=bancointer%3A%2F%2Fmarketplace%2Fproduto&idProduto=12158&nomeProduto=%20&nomeLoja=C&A&idLoja=1&corFundo=%23ffffff&corTexto=%23000000&deep_link_value=bancointer%3A%2F%2Fmarketplace%2Fproduto%3FidProduto%3D12158%26nomeProduto%3D%2520%26nomeLoja%3DC%2526A%26idLoja%3D1%26corFundo%3D%2523ffffff%26corTexto%3D%2523000000")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }

        button7.apply {
            text = "Evento Calendario"
            setOnClickListener{
                val beginTime = Calendar.getInstance().apply { set(2021, 5, 19, 7, 30) }
                val endTime = Calendar.getInstance().apply { set(2021, 5, 19, 8, 30) }
                val intent: Intent = Intent(Intent.ACTION_INSERT)
                    .setData(Events.CONTENT_URI)
                    .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.timeInMillis)
                    .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.timeInMillis)
                    .putExtra(Events.TITLE, "Reunião Inter")
                    .putExtra(Events.DESCRIPTION, "Investimentos em Fundos Imobiliários")
                    //.putExtra(Events.EVENT_LOCATION, "App Inter")
                    .putExtra(Events.AVAILABILITY, Events.AVAILABILITY_BUSY)
                    .putExtra(Intent.EXTRA_EMAIL, "gerente@bancointer.com.br")
                startActivityForResult(intent, 111)
            }
        }

        button8.apply {
            text = "Video Conference"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("bancointer://videoConferenciaAgendamento")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }

        button9.apply {
            text = "Video Reunião"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("bancointer://videoConferencia?url=https%3A%2F%2Finter-video.loca.lt%2Froom%2FMobile")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }

        button10.apply {
            text = "Web Url"
            setOnClickListener{
                Intent(Intent.ACTION_VIEW).run {
                    this.data = Uri.parse("https://cloud.comunicacao.bancointer.com.br/plano-odono-clientes")
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

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)
         Log.d("INTENT_RESULT", "requestCode: $requestCode")
         Log.d("INTENT_RESULT", "resultCode: $resultCode")
         Log.d("INTENT_RESULT", "timezone: ${data?.getStringExtra("timezone")}")
         Log.d("INTENT_RESULT", "allday: ${data?.getBooleanExtra("allday", false)}")
         Log.d("INTENT_RESULT", "go_to_millis: ${data?.getLongExtra("go_to_millis", 0L)}")
         Log.d("INTENT_RESULT", "data: ${data.toString()}")
     }

}
