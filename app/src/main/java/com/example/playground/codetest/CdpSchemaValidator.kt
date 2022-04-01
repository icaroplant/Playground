package com.example.playground.codetest

import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File

fun main() {
    val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
    val f = "app/src/main/java/com/example/playground/codetest/schema_lite.json"

    //read
    val bufferedReader: BufferedReader = File(f).bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    val schema = gson.fromJson(inputString, Schema::class.java)

    printDuplicatedEvents(schema)
    printDuplicatedFields(schema)
    printInvalidLabels(schema)

}

fun printDuplicatedEvents(schema: Schema) {
    println("searching duplicated events...\n")
    var count = 0
    val size = schema.records.size
    for (i in 0 until size) {
        val event = schema.records[i]
        if (event.printed == true) continue
        val eventName = event.masterLabel
        for (j in i + 1 until size) {
            val otherEvent = schema.records[j]
            if (eventName == otherEvent.masterLabel) {
                count++
                println("Event: ${event.masterLabel}")
                otherEvent.printed = true
            }
        }
    }
    println("\nfinished! $count events duplicated")
    println("-------------------------------------")
    println()
}

fun printDuplicatedFields(schema: Schema) {
    println("searching duplicated fields...\n")
    var count = 0
    schema.records.forEach { event ->
        val size = event.externalDataTranFields.size
        for (i in 0 until size) {
            val field = event.externalDataTranFields[i]
            if (field.printed == true) continue
            val fieldName = field.masterLabel
            for (j in i + 1 until size) {
                val otherField = event.externalDataTranFields[j]
                if (fieldName == otherField.masterLabel) {
                    count++
                    println("Event: ${event.masterLabel}   Field: $fieldName")
                    otherField.printed = true
                }
            }
        }
    }
    println("\nfinished! $count fields duplicated")
    println("-------------------------------------")
    println()
}

fun printInvalidLabels(schema: Schema) {
    println("searching invalid labels...\n")
    var count = 0
    schema.records.forEach { event ->
        if (!event.developerName.isValidField()) {
            count++
            println("Event: ${event.developerName}")
        }
        event.externalDataTranFields.forEach { field ->
            if (!field.developerName.isValidField()) {
                count++
                println("Event: ${event.developerName}   Field: ${field.developerName}")
            }
        }
    }
    println("\nfinished! $count invalid labels")
    println("-------------------------------------")
    println()
}

internal fun String.isValidField(): Boolean {
    return !this.contains(" ") &&
            !this.contains("[À-ú]".toRegex()) &&
            this.count() <= 80
}
