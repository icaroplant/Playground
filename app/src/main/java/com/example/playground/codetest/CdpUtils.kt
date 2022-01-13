package com.example.playground.codetest

import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.File

data class Schema(
    val records: List<CdpEvent>
)

data class CdpEvent(
    val masterLabel: String,
    val developerName: String,
    val category: String,
    val externalDataTranFields: List<CdpCustomField>
)

data class CdpCustomField(
    val masterLabel: String,
    val developerName: String,
    val dataType: String,
    val isDataRequired: Boolean,
    val primaryIndexOrder: Int? = null,
    var printed: Boolean = false
)

fun main() {
    val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
    val f = "app/src/main/java/com/example/playground/codetest/schema.json"

    //read
    val bufferedReader: BufferedReader = File(f).bufferedReader()
    val inputString = bufferedReader.use { it.readText() }
    val schema = gson.fromJson(inputString, Schema::class.java)

    printDuplicatedFields(schema)
}

fun printDuplicatedFields(schema: Schema) {
    println("searching duplicated fields...\n")
    var count = 0
    schema.records.forEach { event ->
        val size = event.externalDataTranFields.size
        for (i in 0 until size) {
            val field = event.externalDataTranFields[i]
            if (field.printed) continue
            val fieldName = field.masterLabel
            for (j in i+1 until size) {
                val otherField = event.externalDataTranFields[j]
                if(fieldName == otherField.masterLabel) {
                    count++
                    println("Event: ${event.masterLabel}   Field: $fieldName")
                    otherField.printed = true
                }
            }
        }
    }
    println("\nfinished! $count fields duplicated")
}