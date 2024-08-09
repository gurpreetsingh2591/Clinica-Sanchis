package com.eurosalud.pdp_cs.ui.login.model

data class LoginRequest(
    val dispositivo: String,
    val usuario_portal: String,
    val contrasenya_portal: String,
    val token: String
)