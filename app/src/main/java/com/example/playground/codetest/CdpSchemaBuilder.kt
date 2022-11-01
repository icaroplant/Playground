package com.example.playground.codetest

import com.example.playground.codetest.CdpSchemaBuilderConfig.isUpdateSchema
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File

object CdpSchemaBuilderConfig {
    const val isUpdateSchema = false
}

fun main() {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val f = "app/src/main/java/com/example/playground/codetest/Events.txt"

    //read
    val bufferedReader: BufferedReader = File(f).bufferedReader()
    val records = mutableListOf<CdpEvent>()

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

    records.apply {
        sortBy { it.developerName }
        if (!isUpdateSchema) {
            add(0,
                CdpEvent(
                    masterLabel = "partyIdentification",
                    developerName = "partyIdentification",
                    category = "Profile",
                    externalDataTranFields = getBaseFields(true).apply {
                        add(CdpField("IDName", "IDName", "Text", true))
                        add(CdpField("IDType", "IDType", "Text", true))
                        add(CdpField("userId", "userId", "Text", true))
                    }
                )
            )
        }
    }

    val schema: Any = if (isUpdateSchema) records else {
        CdpSchema(
            records = records
        )
    }

    //write
    var newSchemaJson = gson.toJson(schema)
    if (isUpdateSchema) {
        newSchemaJson = newSchemaJson
            .trim('[', ']')
            .removePrefix("  ")
            .lines().reduce { acc, s ->
                acc + "\n" + s.removePrefix("  ")
            }.trim()
    }
    val newF = "app/src/main/java/com/example/playground/codetest/buildSchema.json"
    File(newF).printWriter().use { out ->
        out.println(newSchemaJson)
    }

}

fun getBaseFields(isProfileEvent: Boolean = false) = mutableListOf<CdpField>().apply {
    if (isProfileEvent) {
        add(CdpField("eventId", "eventId", "Text", true))
    } else {
        add(CdpField("eventId", "eventId", "Text", true, 1))
    }
    add(CdpField("category", "category", "Text", true))
    add(CdpField("dateTime", "dateTime", "Date", true))
    if (isProfileEvent) {
        add(CdpField("deviceId", "deviceId", "Text", true, 1))
    } else {
        add(CdpField("deviceId", "deviceId", "Text", true))
    }
    add(CdpField("eventType", "eventType", "Text", true))
    add(CdpField("sessionId", "sessionId", "Text", true))
}

fun buildCdpCustomField(name: String): CdpField = CdpField(
    masterLabel = name,
    developerName = name,
    dataType = "Text",
    isDataRequired = false
)