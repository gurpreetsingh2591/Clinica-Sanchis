package com.eurosalud.pdp_cs.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Paint
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


class Static {

    lateinit var dialogClickListener: DialogInterface

    var count: Int = 1
    var cart_count: Int = 0


    fun statusBarColor(activity: Activity, color: Int) {

        val window: Window = activity.window
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//        window.statusBarColor = ContextCompat.getColor(activity, color)


        val decor = window.decorView
        decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        window.statusBarColor = color;
    }

    fun toggleFullscreen(fullscreen: Boolean, activity: Activity) {
        val attrs = activity.window.attributes
        if (fullscreen) {
            attrs.flags = attrs.flags or WindowManager.LayoutParams.FLAG_FULLSCREEN
        } else {
            attrs.flags = attrs.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN.inv()
        }
        activity.window.attributes = attrs
    }


    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    fun showDialog(activity: Activity, title: String, msg: String) {
        AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(msg)
            .setCancelable(false)
            .setPositiveButton(
                com.eurosalud.pdp_cs.annotation.Constants.OK
            ) { dialog, whichButton ->
                // do something...
                dialog.dismiss()
            }
            .setNegativeButton(
                com.eurosalud.pdp_cs.annotation.Constants.CANCEL
            ) { dialog, whichButton -> dialog.dismiss() }
            .show()
    }

    fun TextView.showStrikeThrough(show: Boolean) {
        paintFlags =
            if (show) paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            else paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
    }


    private var progressDialog: Dialog? = null


    fun cancelLoading() {
        if (progressDialog != null && progressDialog!!.isShowing) progressDialog!!.cancel()
    }


    fun changeNumberFormat(value: String): String {
        val nf: NumberFormat = NumberFormat.getInstance(Locale("eu", "dk"))

        return nf.format(value.toDouble()).toString()
    }





    fun getCurrentDate(): String {
        val locale: Locale = Locale.getDefault()
        val sdf = SimpleDateFormat(com.eurosalud.pdp_cs.annotation.Constants.DATE_FORMAT_DD_MM, locale)
        return sdf.format(Date())
    }
    fun getCurrentYear(): String {
        val locale: Locale = Locale.getDefault()
        val sdf = SimpleDateFormat(com.eurosalud.pdp_cs.annotation.Constants.DATE_FORMAT_YYYY, locale)
        return sdf.format(Date())
    }
    @SuppressLint("SimpleDateFormat")
    fun getDateFormat(date: String): String {

        val formatIn = SimpleDateFormat(com.eurosalud.pdp_cs.annotation.Constants.DATE_FORMAT)
        val formatOut =
            SimpleDateFormat(
                com.eurosalud.pdp_cs.annotation.Constants.FORMAT_EEE_dd_MM, Locale(
                    com.eurosalud.pdp_cs.annotation.Constants.LOCALE_DANISH))
        val calendar = Calendar.getInstance()
        calendar.time = formatIn.parse(date)

        return formatOut.format(calendar.time)

    }

    fun getCDate(date: String): String {
        val locale: Locale = Locale.getDefault()
        val sdf = SimpleDateFormat(
            com.eurosalud.pdp_cs.annotation.Constants.FORMAT_EEE_dd_MM, Locale(
                com.eurosalud.pdp_cs.annotation.Constants.LOCALE_DANISH))
        return sdf.format(date)
    }

    fun getText(data: Any): String {
        var str = ""
        if (data is EditText) {
            str = data.text.toString()
        } else if (data is String) {
            str = data
        }
        return str
    }
}

