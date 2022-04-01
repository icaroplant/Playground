package com.example.playground.codetest

data class Schema(
    val records: List<CdpEvent>
)

data class CdpEvent(
    val masterLabel: String,
    val developerName: String,
    val category: String,
    val externalDataTranFields: List<CdpCustom>,
    var printed: Boolean? = false
)

data class CdpCustom(
    val masterLabel: String,
    val developerName: String,
    val dataType: String,
    val isDataRequired: Boolean,
    val primaryIndexOrder: Int? = null,
    var printed: Boolean? = false
)