package com.example.playground.codetest

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


    println("----------------")
    var x = 1
    var y = 2
    x = y.also { y = x }
    println(x)
    println(y)

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