package com.example.playground.codetest

fun main() {
    print("digite o primeiro valor: ")
    val a = readLine()?.toInt()
    print("digite o segundo valor: ")
    val b = readLine()?.toInt()
    val soma = somaMaria(a!!,b!!)

    println("digite a operação: ")
    println("1 - Soma\n2 - Multiplicação\n3 - Divisão")
    val resultado = when (readLine()) {
        "1" -> somaMaria(a,b)
        "2" -> multiplicacaoMaria(a,b)
        "3" -> divisaoMaria(a,b)
        else -> "Operação Inválida"
    }

    println("resultado: $resultado")
}

private fun somaMaria(a: Int, b: Int): Int {
    return a + b
}

private fun multiplicacaoMaria(a: Int, b: Int): Int {
    return a * b
}

private fun divisaoMaria(a: Int, b: Int): Double {
    return a / b.toDouble()
}