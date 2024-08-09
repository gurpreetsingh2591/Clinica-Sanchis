package com.eurosalud.pdp_cs.retrofit




import com.eurosalud.pdp_cs.ui.login.model.LoginRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {


    @Headers("Content-Type: text/plain", "Cookie: 8zaqGuiwTZNYZLJtuz4Y=39ecb20cb14b7065dc20d958bc3850d3")
    @POST("https://consulta.4hc.es/ws_pdp/API/VincularPacienteDispositivo")
    fun login(@Body requestBody: String): Call<ResponseBody>



    // login api
    @Headers("Content-Type: text/plain", "Cookie: 8zaqGuiwTZNYZLJtuz4Y=39ecb20cb14b7065dc20d958bc3850d3")
    @POST(WebUrl.API_LOGIN)
    fun getLogin(
        @Body loginRequest: LoginRequest
    ): Call<ResponseBody>

    // logout api

    @Headers("Content-Type: text/plain", "Cookie: 8zaqGuiwTZNYZLJtuz4Y=39ecb20cb14b7065dc20d958bc3850d3")
    @POST(WebUrl.API_LOGOUT)
    fun getLogout(
        @Body logoutRequest: LoginRequest
    ): Call<ResponseBody>






}