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

        buttonGlide.setOnClickListener {
            findNavController().navigateWithAnimations(
                MainFragmentDirections.actionMainFragmentToGlideFragment()
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
                context.openDeepLink("bancointer://marketplace")
            }
        }

        button2.apply {
            text = "Whatsapp"
            setOnClickListener{
                context.openDeepLink("bancointer://whatsappAuthentication")
            }
        }

        button3.apply {
            text = "One Link"
            setOnClickListener{
                context.openDeepLink("https://bancointer.onelink.me/s9CL/31528354")
            }
        }

        button4.apply {
            text = "Marketplace Produto"
            setOnClickListener{
                context.openDeepLink("bancointer://marketplace/produto?idProduto=23316&nomeProduto=%20&nomeLoja=Casas%20Bahia&idLoja=casasbahia&corTexto=%23ffffff&corFundo=%230e2e6a")
            }
        }

        button5.apply {
            text = "Convidar Amigo"
            setOnClickListener{
                context.openDeepLink("bancointer://convidarAmigo")
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
                context.openDeepLink("bancointer://videoConferenciaAgendamento")
            }
        }

        button9.apply {
            text = "Comprovante"
            setOnClickListener{
                context.openDeepLink("bancointer://detalhesComprovantes?receipt=%7BmMap%3D%7Breceipt%3D%7BreceiptButtons%3D%5B%5D%2CreceiptScheduling%3Dfalse%2CreceiptSections%3D%5B%7BreceiptRows%3D%5B%7BreceiptKey%3DFormadePagamento%2CreceiptValue%3D%7D%2C%7BreceiptKey%3DPagoVia%2CreceiptValue%3D%7D%2C%7BreceiptKey%3DInstitui%C3%A7%C3%A3oIniciadora%2CreceiptValue%3D%7D%2C%7BreceiptKey%3DDatadopagamento%2CreceiptValue%3DTer%C3%A7a%2C15%2F02%2F2022%7D%2C%7BreceiptKey%3DHor%C3%A1rio%2CreceiptValue%3D13h40%7D%2C%7BreceiptKey%3DTipo%2CreceiptValue%3D%7D%2C%7BreceiptKey%3DValordacompra%2CreceiptValue%3D%7D%2C%7BreceiptKey%3DValordotroco%2CreceiptValue%3D%7D%2C%7BreceiptKey%3DIDdatransa%C3%A7%C3%A3o%2CreceiptValue%3DE00416968202202151940tOTOH0A0v6z%7D%2C%7BreceiptKey%3DDescri%C3%A7%C3%A3o%2CreceiptValue%3D%7D%5D%2CreceiptTitle%3DSobreatransa%C3%A7%C3%A3o%7D%2C%7BreceiptRows%3D%5B%7BreceiptKey%3DNome%2CreceiptValue%3DNomecompleto90403518000169%7D%2C%7BreceiptKey%3DCPF%2FCNPJ%2CreceiptValue%3D90.403.518%2F0001-69%7D%2C%7BreceiptKey%3DInstitui%C3%A7%C3%A3o%2CreceiptValue%3DBancoInter%7D%2C%7BreceiptKey%3DAg%C3%AAncia%2CreceiptValue%3D0001%7D%2C%7BreceiptKey%3DConta%2CreceiptValue%3D857937-7%7D%5D%2CreceiptTitle%3DQuemrecebeu%7D%2C%7BreceiptRows%3D%5B%7BreceiptKey%3DNome%2CreceiptValue%3DNOMECOMPLETO90225503115%7D%2C%7BreceiptKey%3DCPF%2FCNPJ%2CreceiptValue%3D%2A%2A%2A.632.922-%2A%2A%7D%2C%7BreceiptKey%3DInstitui%C3%A7%C3%A3o%2CreceiptValue%3DBancoInterS.A.%7D%5D%2CreceiptTitle%3DQuempagou%7D%5D%2CreceiptShare%3Dfalse%2CreceiptShowBackButton%3Dtrue%2CreceiptShowNps%3Dfalse%2CreceiptTitle%3DPixenviado%2CreceiptValue%3DR%240%2C12%7D%7D%7D")
            }
        }

        button10.apply {
            text = "Intercom"
            setOnClickListener{
                context.openDeepLink("bancointer://interchat")
            }
        }

        button11.apply {
            text = "Precisa de ajuda"
            setOnClickListener{
                context.openDeepLink("bancointer://precisaDeAjuda")
            }
        }

        buttonAnimations.setOnClickListener {
            findNavController().navigateWithAnimations(
                MainFragmentDirections.actionMainFragmentToAnimationsFragment()
            )
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
