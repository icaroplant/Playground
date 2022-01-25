package com.example.playground.codetest

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun main() {
    val time = "2022-01-24T19:12:05Z".toLocalDateTimeFromIso()
    val time2 = "2021-06-24T19:12:05Z".toLocalDateTimeFromIso()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - EEEE - HH:mm:ss")

    println(time.format(formatter))
    println(time2.format(formatter))

    println("---------------------------")

    val startYearMonth = "2020-12-31".toLocalDate()!!.toYearMonth()
    val endYearMonth = "2022-01-01".toLocalDate()!!.toYearMonth()

    println("---------------------------")
    println(startYearMonth.until(endYearMonth, ChronoUnit.MONTHS))

    println("---------------------------")
    val nowUtc = LocalDateTime.now()
    val now = LocalDateTime.now(ZoneId.of("UTC"))
    println(nowUtc)
    println(now)
    println(time.toIsoUtcString())

}