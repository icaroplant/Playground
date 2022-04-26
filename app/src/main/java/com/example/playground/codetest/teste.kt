package com.example.playground.codetest

import android.os.Parcelable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.sqrt

val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

const val LOCAL_DATE_TIME_PARSE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
const val LOCAL_TIME_PARSE_FORMAT = "HH:mm"
private val UTC = TimeZone.getTimeZone("UTC")

fun main(){
    val msg = "A soma é ${soma(2,4)}"
    println(msg)
    println(Cor.LARANJA.rgb())
    println(Cor.LARANJA.hex())

    val cor = Cor.AMARELO
    println(when(cor){
        Cor.VERMELHO -> "Opa"
        Cor.LARANJA -> "Ai"
        Cor.VERDE, Cor.AMARELO -> "Sai"

        else -> "Erro"
    })

    println(avaliacao(Numero(77)))
    println(avaliacao(Soma(7,8)))
    println(avaliacao(Raiz(16)))

    println("----------------")
    var a = listOf<Int>(1,2,3,4,5,5,6,7,8,9,10)
    var b = a.random()
    println(b)
    a.indexOfFirst { it == b }.takeIf { it >= 5 }?.let{
        println("index: $it")
    } ?: run{
        println("não encontrei!")
    }

    println("----------------")
    var list = mapOf<Int,String>(
        1 to "10",
        2 to "20",
        3 to "30"
    )

    list.forEach {
        println("${it.key} - ${it.value}")
    }

    println("----------------")
    println(lazyValue)
    println(lazyValue)

    println("----------------")
    println(TipoSegmentacao.values().firstOrNull { it.name == "BLACK" }?.name?.toLowerCase()?.capitalize())
    println(TipoSegmentacao.valueOf("ONE"))
    println(enumValueOrNull<TipoSegmentacao>("BLACK"))


    println("----------------")
    var x = 1
    var y = 2
    x = y.also { y = x }
    println("x=$x")
    println("y=$y")

    println("----------------")
    var url = "https://inter.me/marketplace/produto"
    if( url.contains("https://inter.me/")){
        val path = url.removePrefix("https://inter.me/")
        val deeplink = "bancointer://$path"
        println(deeplink)
    }

    println("----------------")
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    var data = format.parse("2021-03-03T09:00:00")
    println("data: ${data.toScheduleString()}")


    val s = "2021-03-03T09:00:00Z"
    val d = LocalDateTime.parse(s, DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    println(d.toStringLocalFromUTC())

    println("----------------")
    val dates = mapOf(
        "2021-03-01" to "2021-03-01T12:00:00Z",
        "2021-03-02" to "2021-03-01T12:30:00Z",
        "2021-03-03" to "2021-03-01T15:00:00Z",
        "2021-03-04" to "2021-03-01T16:00:00Z",
        "2021-03-05" to "2021-03-01T17:30:00Z",
        "2021-03-06" to "2021-03-02T11:00:00Z",
        "2021-03-07" to "2021-03-02T12:00:00Z",
        "2021-03-08" to "2021-03-02T13:00:00Z",
        "2021-03-09" to "2021-03-02T18:00:00Z",
        "2021-04-01" to "2021-04-01T09:00:00Z",
        "2021-04-02" to "2021-04-01T10:00:00Z",
        "2021-04-03" to "2021-04-01T11:00:00Z",
        "2021-04-04" to "2021-04-02T18:00:00Z",
        "2021-04-05" to "2021-04-02T19:00:00Z",
        "2021-04-06" to "2021-04-02T20:00:00Z"
    )
    val newDates = dates.map { YearMonth.parse(it.key, DateTimeFormatter.ofPattern("yyyy-MM-dd")) }
    println(newDates)

    println("----------------")
    val calendar = buildResponse()
    val mapped = calendar.schedules.mapKeys { LocalDate.parse(it.key) }
    val yearMonth = YearMonth.parse("2021-04")
    val month = mapped.filter { it.key.toYearMonth() == yearMonth }
    month.forEach{
        println(it.key)
    }

    println("----------------")
    val l = mutableListOf(
        "1",
        "3",
        "4",
        "5",
        "7",
    )
    l.add(4, "6")
    println(l)

    println("----------------")
    val timezone = ZonedDateTime.now().withZoneSameInstant(ZoneId.of("Etc/Greenwich"))
    println("${timezone.zone}")
    println("${timezone.offset}")


    println("----------------")
    val input = "2021-07-02T12:00:00Z"
    val calendarUtc = calendarNewInstance(UTC)
    SimpleDateFormat(LOCAL_DATE_TIME_PARSE_FORMAT, defaultLocale).let {
        it.timeZone = UTC
        calendarUtc.time = it.parse(input) ?: throw ParseException("Erro parsing local date", 0)
    }
    val output = SimpleDateFormat(LOCAL_TIME_PARSE_FORMAT, defaultLocale).format(calendarUtc.time)
    println(output)

    println("----------------")
    Segmentation.update(ClientSegmentationModel.Type.DIGITAL)
    val p = PersonalHelpCenterModel(
        clientSegmentation = ClientSegmentationModel(
            type = Segmentation.type
        )
    )

    val copy = p.copy()
    println(copy)

    println("----------------")
    val json = "{}"
    val response = Gson().fromJson(json, PersonalHelpCenterResponse::class.java)
    println(response)

    val model = response.toPersonalHelpCenterModel()
    Segmentation.update(null)
    val copy2 = model.copy()
    println(copy2)

    println("----------------")
    var shouldRefresh = true
    val r = shouldRefresh.also {
        shouldRefresh = false
    }
    println(r)

    println("----------------")
    val formatedString = String.format("%02d", 9)
    println(formatedString)

    println("----------------")
    val map = mutableMapOf<String,Any>(
        "1" to "um",
        "2" to "dois",
        "3" to "tres",
        "4" to "quatro"
    )
    map.put("5", "cinco")

    println("----------------")
    val now = calendarNewInstance(UTC)
    val date1 = calendarNewInstance(UTC).apply { add(Calendar.HOUR, -6) }
    val diff = date1.time.hoursUntilNowUtc().toInt()
    println("Hours until now: $diff")

    println("----------------")
    val nowUtc = Calendar.getInstance(TimeZone.getTimeZone("UTC")).time
    println("nowUtc      : $nowUtc")
    println("offsetUtc   : ${nowUtc.timezoneOffset}")
    val nowLocale = Calendar.getInstance().time
    println("nowLocale   : $nowLocale")
    println("offsetLocale: ${nowLocale.timezoneOffset}")

    println("----------------")
    val dateStrings = listOf(
        "2022-01-18T12:45:47.095Z",
        "2022-01-18T13:45:47.007Z",
        "2022-01-18T17:45:47.888Z",
        "2022-01-16T13:45:47.095Z",
        "2022-01-01T23:59:47.000",
        "2022-01-29T00:45:47.095Z"
    )
    dateStrings.forEach {
        println("${it.toDateFromRestUtc()} | ${it.toDateOrNull()} - ${it.toDateFromRestUtc() == it.toDateOrNull()}")
    }

    println("----------------")
    val date = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC))
    println(date.toWeekDayTimeString())
}

fun PersonalHelpCenterResponse.toPersonalHelpCenterModel() = PersonalHelpCenterModel(
    clientSegmentation = ClientSegmentationModel(
        type = enumValueOrNull<ClientSegmentationModel.Type>(this.clientSegmentation)
            ?: Segmentation.type
    )
)


data class PersonalHelpCenterResponse (
    @SerializedName("clientSegmentation") val clientSegmentation: String
)

@Parcelize
data class PersonalHelpCenterModel(
    val clientSegmentation: ClientSegmentationModel? = null
) : Parcelable

@Parcelize
data class ClientSegmentationModel (
    val type: Type?
): Parcelable {
    enum class Type {
        DIGITAL,
        ONE,
        BLACK,
        WIN,
        BASIC;
    }
}

object Segmentation {
    private var segmentation: ClientSegmentationModel.Type? = null
        set(value) {
            field = value
        }

    val typeName: String
        get() = segmentation?.name ?: ""

    val type: ClientSegmentationModel.Type?
        get() = segmentation

    fun update(type: ClientSegmentationModel.Type?) {
        segmentation = type
    }
}

enum class TipoSegmentacao {
    ONE,
    BLACK,
    WIN;
}

fun soma(a: Int, b: Int) = a + b

enum class Cor(val vermelho: Int, val verde: Int, val azul: Int){
    VERMELHO(255, 0, 0),
    LARANJA(255,165,0),
    AMARELO(255,255,0),
    VERDE(0,255,0),
    AZUL(0,0,255);

    fun rgb() = ((vermelho + 256 + verde) * 256 + azul)

    fun hex() = "%02X".format(vermelho) + "%02X".format(verde) + "%02X".format(azul)


}

interface Expressao
class Numero (val valor: Int) : Expressao
class Soma(val a: Int, val b: Int) : Expressao
class Raiz(val value: Int) : Expressao

fun avaliacao(expressao: Expressao): Int {
    return when (expressao) {
        is Numero -> expressao.valor
        is Soma -> expressao.a + expressao.b
        is Raiz -> sqrt(expressao.value.toDouble()).toInt()
        else -> -1
    }
}

inline fun <reified T : Enum<T>> enumValueOrNull(type: String): T? =
    try {
        enumValueOf<T>(type)
    } catch (e: Throwable) {
        null
    }

private fun calendarNewInstance(timeZone: TimeZone? = null, withoutTime: Boolean = false): Calendar =
    Calendar.getInstance(timeZone ?: TimeZone.getDefault())

fun java.util.Date.hoursUntilNowUtc(): Long = TimeUnit.HOURS.convert(
    Calendar.getInstance(TimeZone.getTimeZone("UTC")).time.time - this.time,
    TimeUnit.MILLISECONDS
)

fun java.util.Date.toLocale(): java.util.Date = Calendar.getInstance().apply { time = this@toLocale }.time

