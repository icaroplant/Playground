package com.example.playground.codetest

import com.google.gson.GsonBuilder
import com.improve_future.case_changer.beginWithLowerCase
import com.improve_future.case_changer.toCamelCase
import java.io.BufferedReader
import java.io.File
import java.util.*

fun main() {
    val gson = GsonBuilder().setPrettyPrinting().create()
    val f = "app/src/main/java/com/example/playground/codetest/schema.json"

    //read
    val bufferedReader: BufferedReader = File(f).bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    val schema = gson.fromJson(inputString, CdpSchema::class.java)

    val newSchema = schema.copy(
        records = schema.records.map { event ->
            event.copy(
                externalDataTranFields = event.externalDataTranFields.map { field ->
                    field.copy(
                        masterLabel = field.developerName.toCdpCase(),
                        developerName = field.developerName.toCdpCase()
                    )
                }
            )
        }.sortedBy { it.developerName.lowercase() }
    )

    //write
    val newSchemaJson = gson.toJson(newSchema)
    val newF = "app/src/main/java/com/example/playground/codetest/newSchema.json"
    File(newF).printWriter().use { out ->
        out.println(newSchemaJson)
    }

}

fun String.isSystemField(): Boolean = this in listOf(
    "eventId",
    "category",
    "dateTime",
    "deviceId",
    "eventType",
    "sessionId",
    "contactKey"
)

fun String.toCdpCase(): String = if (this.isSystemField()) this
else this.toLowerCase(Locale.getDefault()).toCamelCase().beginWithLowerCase()