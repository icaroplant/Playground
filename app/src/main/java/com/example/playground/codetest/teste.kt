package com.example.playground.codetest

import android.webkit.URLUtil
import java.sql.Date
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.sqrt

val lazyValue: String by lazy {
    println("computed!")
    "Hello"
}

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

