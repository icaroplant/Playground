package com.example.playground.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.CalendarContract
import android.provider.CalendarContract.Events
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.playground.R
import com.example.playground.common.CoreFragment
import com.example.playground.data.db.ResponseModel
import com.example.playground.data.db.entity.MusicEntity
import com.example.playground.databinding.MainFragmentBinding
import com.example.playground.extensions.*
import com.example.playground.ui.main.adapters.MusicaAdapter
import com.example.playground.ui.manage.ManageMusicModalBottomSheet
import com.example.playground.ui.manage.MusicModel
import com.example.playground.utils.getColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class MainFragment : CoreFragment<MainFragmentBinding>(MainFragmentBinding::inflate),
    SwipeRefreshLayout.OnRefreshListener {
    private val viewModel: MainViewModel by viewModel()

    private var titulos = ""
    private var spanCount = 3

    override fun setupViews() {

        binding.slParent.setOnRefreshListener(this)
        binding.slParent.setColorSchemeColors(
            getColor(R.color.refresh_progress_1),
            getColor(R.color.refresh_progress_2),
            getColor(R.color.refresh_progress_3)
        )
        binding.slParent.isRefreshing = true

        binding.btMudarLayout.setOnClickListener {
            binding.rvListaMusicas.adapter?.let { adapter ->
                binding.rvListaMusicas.layoutManager?.let { layoutManager ->
                    if (layoutManager is GridLayoutManager) {
                        spanCount = if (layoutManager.spanCount == 3) 1 else 3
                        binding.rvListaMusicas.layoutManager =
                            GridLayoutManager(activity, spanCount)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }

        binding.fbRegister.setOnClickListener {
            ManageMusicModalBottomSheet.newInstance(viewModel).showOnce(childFragmentManager)
        }

        binding.buttonHome.setOnClickListener {

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

        binding.buttonInstrument.setOnClickListener {
            findNavController().navigateWithAnimations(
                MainFragmentDirections.actionMainFragmentToInstrumentFragment()
            )
        }

        binding.buttonGlide.setOnClickListener {
            findNavController().navigateWithAnimations(
                MainFragmentDirections.actionMainFragmentToGlideFragment()
            )
        }

        binding.buttonNotification.setOnClickListener() {
            val deeplink = findNavController().createDeepLink()
                .setDestination(R.id.chatFragment)
                .createPendingIntent()

            val notificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
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

        binding.button.apply {
            text = "Marketplace"
            setOnClickListener {
                context.openDeepLink("bancointer://marketplace")
            }
        }

        binding.button2.apply {
            text = "Whatsapp"
            setOnClickListener {
                context.openDeepLink("bancointer://whatsappAuthentication")
            }
        }

        binding.button3.apply {
            text = "One Link"
            setOnClickListener {
                context.openDeepLink("https://bancointer.onelink.me/s9CL/31528354")
            }
        }

        binding.button4.apply {
            text = "Marketplace Produto"
            setOnClickListener {
                context.openDeepLink("bancointer://marketplace/produto?idProduto=23316&nomeProduto=%20&nomeLoja=Casas%20Bahia&idLoja=casasbahia&corTexto=%23ffffff&corFundo=%230e2e6a")
            }
        }

        binding.button5.apply {
            text = "Convidar Amigo"
            setOnClickListener {
                context.openDeepLink("bancointer://convidarAmigo")
            }
        }

        binding.button6.apply {
            text = "One Link Marketplace Produto"
            setOnClickListener {
                Intent(Intent.ACTION_VIEW).run {
                    this.data =
                        Uri.parse("https://bancointer.onelink.me/5zQP?pid=COMPARTILHAR&af_force_deeplink=true&af_web_dp=https%3A%2F%2Fcdnmarketplace.uatbi.com.br%2Fecommerce%2Fprodutos%2F2020-11-06%2FCasasBahia%2Fproduto-12158.html&af_dp=bancointer%3A%2F%2Fmarketplace%2Fproduto&idProduto=12158&nomeProduto=%20&nomeLoja=C&A&idLoja=1&corFundo=%23ffffff&corTexto=%23000000&deep_link_value=bancointer%3A%2F%2Fmarketplace%2Fproduto%3FidProduto%3D12158%26nomeProduto%3D%2520%26nomeLoja%3DC%2526A%26idLoja%3D1%26corFundo%3D%2523ffffff%26corTexto%3D%2523000000")
                    ContextCompat.startActivity(requireContext(), this, null)
                }
            }
        }

        binding.button7.apply {
            text = "Evento Calendario"
            setOnClickListener {
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

        binding.button8.apply {
            text = "Video Conference"
            setOnClickListener {
                context.openDeepLink("bancointer://videoConferenciaAgendamento")
            }
        }

        binding.button9.apply {
            text = "Gointer"
            setOnClickListener {
                context.openDeepLink("https://gointer-app.uatbi.com.br/convidarAmigo")
            }
        }

        binding.button10.apply {
            text = "Intercom"
            setOnClickListener {
                context.openDeepLink("bancointer://interchat")
            }
        }

        binding.button11.apply {
            text = "Precisa de ajuda"
            setOnClickListener {
                context.openDeepLink("bancointer://precisaDeAjuda")
            }
        }

        binding.buttonAnimations.setOnClickListener {
            findNavController().navigateWithAnimations(
                MainFragmentDirections.actionMainFragmentToAnimationsFragment()
            )
        }
    }

    override fun setupObservers() {
        observe(viewModel.allMusicsEvent) { musicas ->
            titulos = ""
            musicas.forEach { m ->
                val titulo = m.id.toString() + ": " + m.name + "\n"
                titulos += titulo
            }
            titulos = titulos.removeSuffix("\n")

            binding.rvListaMusicas.apply {
                layoutManager = GridLayoutManager(activity, spanCount)
                adapter = MusicaAdapter(musicas, ::onMusicaItemClick, ::onMusicaItemLongClick)
                alpha = 1.0f
            }
            binding.slParent.isRefreshing = false
        }

        observe(viewModel.repositoryReponse) { event ->

            event.contentIfNotHandled?.let { response ->
                if (response.success) {
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
                } else {
                    response.error?.let {
                        makeSnackBar("Erro: $it")
                    }
                }
            }
        }
    }

    private fun onMusicaItemClick(item: MusicEntity) {
        ManageMusicModalBottomSheet.newInstance(
            viewModel,
            MusicModel(item.id, item.name, item.artist)
        ).showOnce(childFragmentManager)
    }

    private fun onMusicaItemLongClick(musica: MusicEntity): Boolean {
        binding.slParent.isRefreshing = true
        binding.rvListaMusicas.alpha = 0.6f
        viewModel.deleteMusic(musica.id)
        return true
    }

    override fun onRefresh() {
        binding.rvListaMusicas.alpha = 0.6f
        viewModel.viewModelScope.launch {
            delay(2000)
            binding.rvListaMusicas.alpha = 1.0f
            binding.slParent.isRefreshing = false
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
