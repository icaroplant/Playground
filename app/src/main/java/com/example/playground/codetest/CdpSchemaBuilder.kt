package com.example.playground.codetest

import com.google.gson.GsonBuilder
import com.improve_future.case_changer.beginWithLowerCase
import com.improve_future.case_changer.toCamelCase
import java.io.BufferedReader
import java.io.File
import java.util.*

fun main() {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val f = "app/src/main/java/com/example/playground/codetest/Events.txt"

    //read
    val bufferedReader: BufferedReader = File(f).bufferedReader()
    val records = mutableListOf<CdpEvent>().apply {
        // Profile Event
        add(
            CdpEvent(
                masterLabel = "partyIdentification",
                developerName = "partyIdentification",
                category = "Profile",
                externalDataTranFields = getBaseFields().apply {
                    add(CdpField("IDName", "IDName", "Text", true))
                    add(CdpField("IDType", "IDType", "Text", true))
                    add(CdpField("userId", "userId", "Text", true))
                }
            )
        )
    }
    bufferedReader.forEachLine { line ->
        if (line.isNotBlank()) {
            val itens = line
                .split("\t", ";", " ")
                .map { it.trim() }
                .filter { it.isNotBlank() }
            val eventName = itens[0]
            val fields = getBaseFields().apply {
                for (i in 1 until itens.size) {
                    val fieldName = itens[i]
                    add(buildCdpCustomField(fieldName.toCdpCase()))
                }
            }
            records.add(
                CdpEvent(
                    masterLabel = eventName,
                    developerName = eventName,
                    category = "Engagement",
                    externalDataTranFields = fields
                )
            )
        }
    }

    val schema = CdpSchema(
        records = records
    )

    //write
    val newSchemaJson = gson.toJson(schema)
    val newF = "app/src/main/java/com/example/playground/codetest/buildSchema.json"
    File(newF).printWriter().use { out ->
        out.println(newSchemaJson)
    }

}

fun getBaseFields() = mutableListOf<CdpField>().apply {
    add(CdpField("eventId", "eventId", "Text", true, 1))
    add(CdpField("category", "category", "Text", true))
    add(CdpField("dateTime", "dateTime", "Date", true))
    add(CdpField("deviceId", "deviceId", "Text", true))
    add(CdpField("eventType", "eventType", "Text", true))
    add(CdpField("sessionId", "sessionId", "Text", true))
}

fun buildCdpCustomField(name: String): CdpField = CdpField(
    masterLabel = name,
    developerName = name,
    dataType = "Text",
    isDataRequired = false
)