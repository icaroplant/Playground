package com.example.playground.codetest

import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

data class SchedulesResponse(
    val maxMonthlySchedules: Int,
    val selectedDate: String,
    val schedules: Map<String, DayResponse>,
)

data class DayResponse(
    val date: String,
    val times: List<String>,
    val status: String,
    val warning: WarningResponse? = null,
    val meeting: MeetingResponse? = null,
)

data class MeetingResponse(
    val id: String,
    val subject: String,
    val time: String,
    val duration: Int,
    val msg: String? = null,
    val link: String? = null,
)

data class WarningResponse(
    val title: String,
    val description: String
)

fun main(){
    val schedules = buildResponse()
    val gson = GsonBuilder().setPrettyPrinting().create()
    val json = gson.toJson(schedules)

    val f = "json_response_mock.txt"

    //write
    File(f).printWriter().use { out ->
        out.println(json)
    }

    //read
    val bufferedReader: BufferedReader = File(f).bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    val post = gson.fromJson(inputString, SchedulesResponse::class.java)
    println(post)
}

fun buildResponse(): SchedulesResponse{
    val now = getNow()
    val startDate = now.minusMonths(1).withDayOfMonth(1)
    val endMonth = now.plusMonths(1)
    val endDate = endMonth.withDayOfMonth(endMonth.toLocalDate().lengthOfMonth())

    val start = startDate.dayOfYear
    val end = endDate.dayOfYear

    println("start: ${startDate.toStringTime()}")
    println("end: ${endDate.toStringTime()}")

    val h = now.dayOfYear
    val days = mutableMapOf<String, DayResponse>()
    for (i in start..end){
        val datetime = now.withDayOfYear(i).withHour(0).withMinute(0).withSecond(0).withNano(0)

        val day = when(i){
            h-20 -> buildDay(datetime, VideoConferenceStatus.CANCELED)
            h-15 -> buildDay(datetime, VideoConferenceStatus.COMPLETED)
            h-10 -> buildDay(datetime, VideoConferenceStatus.CANCELED)
            h -> buildDay(datetime, VideoConferenceStatus.STARTED)
            h+15 -> buildDay(datetime)
            h+20 -> buildDay(datetime, VideoConferenceStatus.SCHEDULED)
            in start..h -> buildDay(datetime, VideoConferenceStatus.UNAVAILABLE)
            else -> buildDay(datetime, VideoConferenceStatus.AVAILABLE)
        }

        days[datetime.toStringYearMonthDay()] = day
    }

    val schedules = SchedulesResponse(
        maxMonthlySchedules = 2,
        selectedDate = now.toStringYearMonthDay(),
        schedules = days
    )

    return schedules
}

fun getNow() = LocalDateTime.now(ZoneOffset.UTC)

fun getRandomSubject() = listOf(
    "Investimentos",
    "Previdência",
    "Crédito Imobiliário",
    "Cartão",
    "Conta Digital",
    "Outros"
).random()

fun getRandomTime(datetime: LocalDateTime) = listOf(
    datetime.withHour(12).withMinute(0).toStringISO(),
    datetime.withHour(12).withMinute(30).toStringISO(),
    datetime.withHour(13).withMinute(0).toStringISO(),
    datetime.withHour(14).withMinute(0).toStringISO(),
    datetime.withHour(17).withMinute(0).toStringISO(),
    datetime.withHour(17).withMinute(30).toStringISO(),
    datetime.withHour(18).withMinute(0).toStringISO(),
    datetime.withHour(20).withMinute(30).toStringISO(),
    datetime.withHour(21).withMinute(0).toStringISO(),
).random()

fun buildDay(datetime: LocalDateTime, status: VideoConferenceStatus = VideoConferenceStatus.AVAILABLE): DayResponse {
    val id = UUID.randomUUID().toString()
    return when(status){
        VideoConferenceStatus.AVAILABLE -> DayResponse(
            date = datetime.toStringYearMonthDay(),
            times = listOf(
                datetime.withHour(12).withMinute(0).toStringISO(),
                datetime.withHour(12).withMinute(30).toStringISO(),
                datetime.withHour(14).withMinute(0).toStringISO(),
                datetime.withHour(17).withMinute(0).toStringISO(),
                datetime.withHour(17).withMinute(30).toStringISO(),
                datetime.withHour(18).withMinute(0).toStringISO(),
                datetime.withHour(20).withMinute(30).toStringISO(),
                datetime.withHour(21).withMinute(0).toStringISO(),
            ),
            status = VideoConferenceStatus.AVAILABLE.name
        )
        VideoConferenceStatus.UNAVAILABLE -> DayResponse(
            date = datetime.toStringYearMonthDay(),
            times = listOf(),
            status = VideoConferenceStatus.UNAVAILABLE.name,
            warning = WarningResponse(
                title = "Marque para o próximo mês",
                description = "Você já realizou suas 2 reuniões disponíveis nesse mês. Selecione uma data a partir de ${datetime.plusMonths(1).toMonth().toLowerCase()}"
            )
        )
        VideoConferenceStatus.SCHEDULED -> DayResponse(
            date = datetime.toStringYearMonthDay(),
            times = listOf(),
            status = VideoConferenceStatus.SCHEDULED.name,
            meeting = MeetingResponse(
                id = id,
                subject = getRandomSubject(),
                time = getRandomTime(datetime),
                duration = 30
            )
        )
        VideoConferenceStatus.STARTING -> DayResponse(
            date = getNow().toStringYearMonthDay(),
            times = listOf(),
            status = VideoConferenceStatus.STARTING.name,
            meeting = MeetingResponse(
                id = id,
                subject = getRandomSubject(),
                time = getNow().plusMinutes(5).toStringISO(),
                duration = 30,
                link = "https://videocall.bancointer.com.br/$id"
            )
        )
        VideoConferenceStatus.STARTED -> DayResponse(
            date = getNow().toStringYearMonthDay(),
            times = listOf(),
            status = VideoConferenceStatus.STARTED.name,
            meeting = MeetingResponse(
                id = id,
                subject = getRandomSubject(),
                time = getNow().minusMinutes(12).toStringISO(),
                duration = 30,
                link = "https://videocall.bancointer.com.br/$id"
            )
        )
        VideoConferenceStatus.COMPLETED -> DayResponse(
            date = datetime.toStringYearMonthDay(),
            times = listOf(),
            status = VideoConferenceStatus.COMPLETED.name,
            meeting = MeetingResponse(
                id = id,
                subject = getRandomSubject(),
                time = getRandomTime(datetime),
                duration = 30
            )
        )
        VideoConferenceStatus.CANCELED -> DayResponse(
            date = datetime.toStringYearMonthDay(),
            times = listOf(),
            status = VideoConferenceStatus.CANCELED.name,
            meeting = MeetingResponse(
                id = id,
                subject = getRandomSubject(),
                time = getRandomTime(datetime),
                duration = 30,
                msg = "Seu gerente teve um imprevisto e cancelou a reunião. Você pode remarcá-la quando quiser."
            )
        )
    }
}

