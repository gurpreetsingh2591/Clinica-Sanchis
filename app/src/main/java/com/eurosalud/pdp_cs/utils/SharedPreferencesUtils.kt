package com.eurosalud.pdp_cs.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtils {

    private const val SHARED_PREFERENCES = com.eurosalud.pdp_cs.annotation.Constants.PREFERENCE_NAME
    private var sPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null
    private const val IS_USER_LOGIN = com.eurosalud.pdp_cs.annotation.Constants.IS_USER_LOGIN
    private const val USER_ID = com.eurosalud.pdp_cs.annotation.Constants.USER_ID
    private const val TOKEN = com.eurosalud.pdp_cs.annotation.Constants.TOKEN

    private const val USER_NAME = com.eurosalud.pdp_cs.annotation.Constants.USER_NAME
    private const val USER_EMAIL = com.eurosalud.pdp_cs.annotation.Constants.USER_EMAIL
    private const val USER_PHONE = com.eurosalud.pdp_cs.annotation.Constants.USER_PHONE
    private const val USER_PASSWORD = "PASSWORD"
    private const val Address = com.eurosalud.pdp_cs.annotation.Constants.Address
    private const val MOM = com.eurosalud.pdp_cs.annotation.Constants.MOM
    private const val STUDENT_COUNT = com.eurosalud.pdp_cs.annotation.Constants.STUDENT_COUNT
    private const val Country = com.eurosalud.pdp_cs.annotation.Constants.Country
    private const val State = com.eurosalud.pdp_cs.annotation.Constants.State
    private const val City = com.eurosalud.pdp_cs.annotation.Constants.City
    private const val Pin = com.eurosalud.pdp_cs.annotation.Constants.Pin
    private const val Gender = com.eurosalud.pdp_cs.annotation.Constants.Gender
    private const val PARENT_ID = com.eurosalud.pdp_cs.annotation.Constants.PARENT_ID
    private const val IMAGE = com.eurosalud.pdp_cs.annotation.Constants.IMAGE
    private const val FcmToken = com.eurosalud.pdp_cs.annotation.Constants.FcmToken
    private const val DeviceId = com.eurosalud.pdp_cs.annotation.Constants.DeviceId
    private const val transport_id = com.eurosalud.pdp_cs.annotation.Constants.TRANSPORT_ID
    private const val Current_lat = com.eurosalud.pdp_cs.annotation.Constants.LAT
    private const val Current_long = com.eurosalud.pdp_cs.annotation.Constants.LONG
    private const val NUMBER_OF_STUDENT = com.eurosalud.pdp_cs.annotation.Constants.NUMBER_OF_STUDENT

    @SuppressLint("CommitPrefEdits")
    fun init(context: Context?) {
        sPreferences = context?.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        editor = sPreferences!!.edit()
    }

    //get is user login?
    fun isUserLogin(): Boolean? {
        return sPreferences?.getBoolean(IS_USER_LOGIN, false)
    }

    //get user detail
    fun getUserId(): String? {
        return sPreferences?.getString(USER_ID, "")
    }

    fun getUserName(): String? {
        return sPreferences?.getString(USER_NAME, "")
    }

    fun getUserPassword(): String? {
        return sPreferences?.getString(USER_PASSWORD, "")
    }

    fun getUserEmail(): String? {
        return sPreferences?.getString(USER_EMAIL, "")
    }

    fun getUserNumber(): String? {
        return sPreferences?.getString(USER_PHONE, "")
    }


    fun getDeviceId(): String? {
        return sPreferences?.getString(DeviceId, "")
    }

    fun getFcmToken(): String? {
        return sPreferences?.getString(FcmToken, "")
    }


    // set user and company detail

    fun setFcmToken(token: String) {
        sPreferences?.edit()
            ?.putString(FcmToken, token)
            ?.apply()
    }


    fun setUserLogin(status: Boolean, email: String, password: String) {
        sPreferences?.edit()
            ?.putBoolean(IS_USER_LOGIN, status)
            ?.putString(USER_EMAIL, email)
            ?.putString(USER_PASSWORD, password)
            ?.apply()
    }


    fun clearUser() {
        sPreferences?.edit()
            ?.clear()
            ?.apply()
    }


}