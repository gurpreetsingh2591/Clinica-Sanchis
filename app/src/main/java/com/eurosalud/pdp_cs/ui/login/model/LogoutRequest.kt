package com.eurosalud.pdp_cs.ui.login.model

data class LogoutRequest(
    val dispositivo: String,
    val usuario_pdp: String,
    val contrasenya_pdp: String,
)