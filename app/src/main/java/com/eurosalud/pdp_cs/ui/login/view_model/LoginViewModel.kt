package com.eurosalud.pdp_cs.ui.login.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eurosalud.pdp_cs.retrofit.WebResponse
import com.eurosalud.pdp_cs.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.eurosalud.pdp_cs.ui.login.model.LoginRequest
import okhttp3.ResponseBody


class LoginViewModel : ViewModel() {
    var responseLoginData: MutableLiveData<WebResponse<ResponseBody>> = MutableLiveData()
    var responseLogoutData: MutableLiveData<WebResponse<ResponseBody>> = MutableLiveData()

    var isLoading = MutableLiveData<Boolean>()
    var isViewEnable = MutableLiveData<Boolean>()

    var errorHandler: com.eurosalud.pdp_cs.exception.ErrorHandler? =
        com.eurosalud.pdp_cs.exception.ErrorHandler()


    /*
     * method to call login api
     * */
    fun callGetLoginApi(email: String, password: String, fcmToken: String, token: String) {
        isLoading.postValue(true)
        isViewEnable.postValue(true)

        val loginRequest = LoginRequest(
            dispositivo = fcmToken,
            usuario_portal = email,
            contrasenya_portal = password,
            token = token
        )
        val call = ApiClient().getClient().getLogin(loginRequest)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>, response: Response<ResponseBody?>
            ) {
                isLoading.postValue(false)
                isViewEnable.postValue(false)
                if (response.isSuccessful && response.body() != null) {
                    val result: ResponseBody? = response.body()

                    responseLoginData.value = (WebResponse(com.eurosalud.pdp_cs.annotation.Status.SUCCESS, result, null))

                } else {
                    // val errorMsg = errorHandler!!.reportError(response.code())
                    responseLoginData.value = (WebResponse(
                        com.eurosalud.pdp_cs.annotation.Status.FAILURE, null, response.message()
                    ))
                }
            }

            override fun onFailure(
                call: Call<ResponseBody?>, t: Throwable
            ) {
                isLoading.postValue(false)
                isViewEnable.postValue(false)
                val errorMsg = errorHandler!!.reportError(t)
                responseLoginData.value = WebResponse(com.eurosalud.pdp_cs.annotation.Status.FAILURE, null, errorMsg)
            }
        })
    }


    /*
    * method to call logout api
    * */
    fun callGetLogoutApi(email: String, password: String, fcmToken: String,token: String) {
        isLoading.postValue(true)
        isViewEnable.postValue(true)

        val loginRequest = LoginRequest(
            dispositivo = fcmToken,
            usuario_portal = email,
            contrasenya_portal = password,
            token = token
        )
        val call = ApiClient().getClient().getLogout(loginRequest)
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>, response: Response<ResponseBody?>
            ) {
                isLoading.postValue(false)
                isViewEnable.postValue(false)
                if (response.isSuccessful && response.body() != null) {
                    val result: ResponseBody? = response.body()

                    responseLogoutData.value = (WebResponse(com.eurosalud.pdp_cs.annotation.Status.SUCCESS, result, null))

                } else {
                    // val errorMsg = errorHandler!!.reportError(response.code())
                    responseLogoutData.value = (WebResponse(
                        com.eurosalud.pdp_cs.annotation.Status.FAILURE, null, response.message()
                    ))
                }
            }

            override fun onFailure(
                call: Call<ResponseBody?>, t: Throwable
            ) {
                isLoading.postValue(false)
                isViewEnable.postValue(false)
                val errorMsg = errorHandler!!.reportError(t)
                responseLogoutData.value = WebResponse(com.eurosalud.pdp_cs.annotation.Status.FAILURE, null, errorMsg)
            }
        })
    }

}