package com.eurosalud.pdp_cs.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.eurosalud.pdp_cs.annotation.Constants.TAG
import com.eurosalud.pdp_cs.databinding.ActivitySplashBinding
import com.eurosalud.pdp_cs.ui.dashboard.MainActivity
import com.eurosalud.pdp_cs.ui.login.view.LoginActivity
import com.eurosalud.pdp_cs.utils.SharedPreferencesUtils


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    private val WAIT_TIME = 1 * 1500L
    private var startedAt: Long = 0
    private var isLogin: Boolean = false
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        //setContentView(R.layout.activity_splash)
        startedAt = System.currentTimeMillis()

        onTokenRefresh()
        proceedToNext()

    }

    private fun proceedToNext() {

        val elapsedTime = System.currentTimeMillis() - startedAt
        val remainingTime = WAIT_TIME - elapsedTime
        if (remainingTime > 0) {
            Handler().postDelayed({

                afterSplash()
            }, remainingTime)
        } else {
            afterSplash()
        }
    }

    private fun afterSplash() {

        try {
            isLogin = SharedPreferencesUtils.isUserLogin()!!

            Log.e("isLogin--",isLogin.toString())
        } catch (e: NullPointerException) {
            Log.e("isLogin--",isLogin.toString())
        }
        if (!isLogin) {
            Log.e("isLogin--",isLogin.toString())
           // proceedMain()
            proceedLogin()
        } else {
            Log.e("isLogin--",isLogin.toString())
            proceedMain()
        }

    }

    private fun proceedLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun proceedMain() {
        val userName = SharedPreferencesUtils.getUserEmail().toString()
        val password = SharedPreferencesUtils.getUserPassword().toString()

        MainActivity.startActivity(this, userName,password)
        finishAffinity()
    }

    /* private fun proceedSignUp() {
         startActivity(Intent(this, SignUpNameActivity::class.java))
         //   finish()
     }*/

    private fun onTokenRefresh() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
        //    val msg = getString("msg_token_fmt", token)
            Log.d(TAG, token)
            SharedPreferencesUtils.setFcmToken(token)
          //  Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }
}