package com.eurosalud.pdp_cs.ui.login.model

import com.eurosalud.pdp_cs.ui.signup.model.Result


data class LoginModel(
    val message: String,
    val result: Result,
    val status: Int
)