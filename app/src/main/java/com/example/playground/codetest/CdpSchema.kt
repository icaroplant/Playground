package com.example.playground.codetest

data class CdpSchema(
    val records: List<CdpEvent>
)

data class CdpEvent(
    val masterLabel: String,
    val developerName: String,
    val category: String,
    val externalDataTranFields: List<CdpField>,
    var printed: Boolean? = null
)

data class CdpField(
    val masterLabel: String,
    val developerName: String,
    val dataType: String,
    val isDataRequired: Boolean,
    val primaryIndexOrder: Int? = null,
    var printed: Boolean? = null
)