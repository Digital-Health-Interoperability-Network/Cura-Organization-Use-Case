package com.nameksolutions.regchain.curaorganization.requests

data class ContactRequest(
    val name: Name,
    val purpose: String,
    val telecom: List<TelecomX>
)