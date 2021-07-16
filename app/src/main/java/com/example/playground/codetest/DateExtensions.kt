package com.example.playground.codetest

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.math.abs

val defaultLocale = Locale("pt", "BR")

fun Date.toBrazilianStringDate(): String {
    return SimpleDateFormat("dd/MM/yyyy", defaultLocale).format(this)
}

fun Date.toFormattedString(format: String): String {
    return SimpleDateFormat(format, defaultLocale).format(this)
}

fun Date.toFormattedString(): String? {
    return try {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        formatter.format(this)
    } catch (e: Exception) {
        null
    }
}

fun Date.toStringTime(): String {
    return SimpleDateFormat("HH:mm", defaultLocale).format(this)
}

fun Date.toSoapFormat(): String {
    return SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000", defaultLocale).format(this)
}

fun Date.toStringCompleteFormatted(): String {
    return SimpleDateFormat("dd/MM/yyyy - HH:mm:ss", defaultLocale).format(this)
}

fun Date.toSimpleDateFormatted(): String {
    return SimpleDateFormat("dd/MM - HH:mm", defaultLocale).format(this)
}

fun Date.isBetween(startDate: Date, endDate: Date): Boolean {
    return startDate.time >= time && endDate.time <= time
}

fun Date.addDays(days: Int): Date {
    val calendar: Calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.DATE, days)

    return calendar.time
}

fun Date.beforeNow(): Boolean {
    return before(Calendar.getInstance().time)
}

fun Date.afterNow(): Boolean {
    return after(Calendar.getInstance().time)
}

fun Date.toDateStringFormat(): String {

    val current = Date()

    val diffInMillies = abs(current.time - time)
    val diffHours = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS)
    val diffDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS)
    val diffWeeks = diffDays / 7
    val diffMonths = diffWeeks / 4

    return when {
        diffHours.toInt() == 0 -> "Atualizado neste momento"
        diffHours.toInt() == 1 -> "Atualizado há $diffHours Hora"
        diffHours in 2..23 -> "Atualizado há $diffHours Horas"
        diffDays.toInt() == 1 -> "Atualizado há $diffDays Dia"
        diffDays in 2..6 -> "Atualizado há $diffDays Dias"
        diffWeeks.toInt() == 1 -> "Atualizado há $diffWeeks Semana"
        diffWeeks in 1..3 -> "Atualizado há $diffWeeks Semanas"
        diffMonths.toInt() == 1 -> "Atualizado há $diffMonths Mês"
        else -> "Atualizado há $diffMonths Mêses"
    }
}

fun Date.toMonthString(): String {
    return SimpleDateFormat("MMM", Locale.getDefault()).format(this)
}

fun Date.toWeekDay(): String {
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(this)
}

fun Date.toWeekDayPrefix(): String {
    return toWeekDay().substring(0, 3)
}

fun Date.toDayOfMonth(): String {
    return SimpleDateFormat("dd", Locale.getDefault()).format(this)
}

fun Date.toMonthPrefix(): String {
    return toMonthString().substring(0, 3)
}

fun Date.isTheSameDay(date: Date): Boolean {
    return this.toBrazilianStringDate() == date.toBrazilianStringDate()
}

fun Date.secondsUntilNow(): Long = TimeUnit.SECONDS.convert(Date().time - this.time, TimeUnit.MILLISECONDS)

fun java.util.Date.toScheduleString(): String{
    return "${this.toWeekDay().removeSuffix("-feira").toCapitalizeWords()}, ${this.toDayOfMonth()} de ${this.toMonthPrefix()} ${this.toYear()}" +
            "\n${this.toStringTime()}"
}

fun java.util.Date.toCalendarString(): String{
    return "${this.toWeekDay().removeSuffix("-feira").toCapitalizeWords()}, ${this.toDayOfMonth()} de ${this.toMonth()}"
}

fun java.util.Date.toYear(): String {
    return SimpleDateFormat("yyyy", Locale.getDefault()).format(this)
}

fun java.util.Date.toMonth(): String {
    return SimpleDateFormat("MMMM", Locale.getDefault()).format(this)
}

fun String.toCapitalizeWords(): String {
    val words = this.toLowerCase(defaultLocale).split(" ").toMutableList()
    var output = ""

    for (word in words) {
        output += word.capitalize(defaultLocale) + " "
    }

    return output.trim()
}

fun LocalDate.toCalendarString(): String{
    return "${this.toWeekDay().removeSuffix("-feira").toCapitalizeWords()}, ${this.toDayOfMonth()} de ${this.toMonth()}"
}

fun LocalDate.toWeekDay(): String {
    return SimpleDateFormat("EEEE", Locale.getDefault()).format(this)
}

fun LocalDate.toDayOfMonth(): String {
    return SimpleDateFormat("dd", Locale.getDefault()).format(this)
}

fun LocalDate.toMonth(): String {
    return SimpleDateFormat("MMMM", Locale.getDefault()).format(this)
}

fun LocalDateTime.toStringTime(): String {
    return format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale.getDefault()))
}

fun LocalDateTime.toLocalFromUTC(): LocalDateTime {
    return atZone(ZoneOffset.UTC)
        .withZoneSameInstant(ZoneId.systemDefault())
        .toLocalDateTime()
}

fun LocalDateTime.toStringLocalFromUTC(): String {
    return atZone(ZoneOffset.UTC)
        .withZoneSameInstant(ZoneId.systemDefault())
        .toLocalDateTime().format(DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm", Locale.getDefault()))
}

fun LocalDateTime.toStringISO(): String {
    return format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'", Locale.getDefault()))
}

fun LocalDateTime.toStringYearMonthDay(): String {
    return format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.getDefault()))
}

fun LocalDateTime.toMonth(): String {
    return format(DateTimeFormatter.ofPattern("MMMM", Locale.getDefault()))
}

fun LocalDate.toYearMonth(): YearMonth? {
    return try{
        YearMonth.parse(this.toString(), DateTimeFormatter.ISO_DATE)
    } catch (e: ParseException){
        null
    }
}