package com.eurosalud.pdp_cs.ui.login.view


import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.messaging.FirebaseMessaging
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.eurosalud.pdp_cs.R
import com.eurosalud.pdp_cs.databinding.ActivityLoginBinding
import com.eurosalud.pdp_cs.retrofit.WebResponse
import com.eurosalud.pdp_cs.ui.dashboard.MainActivity
import com.eurosalud.pdp_cs.ui.login.view_model.LoginViewModel
import com.eurosalud.pdp_cs.ui.signup.view.SignUpActivity
import com.eurosalud.pdp_cs.utils.SharedPreferencesUtils
import com.eurosalud.pdp_cs.utils.Static
import okhttp3.ResponseBody
import java.util.Locale


class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel
    private val static = Static()
    private lateinit var binding: ActivityLoginBinding
    private var sharedPreferences = SharedPreferencesUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        // setContentView(R.layout.activity_login)
        initUI()
        onTokenRefresh()
        initObserver()
    }

    private var hasNotificationPermissionGranted = false
    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            hasNotificationPermissionGranted = isGranted
            if (!isGranted) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    if (Build.VERSION.SDK_INT >= 33) {
                        if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                            showNotificationPermissionRationale()
                        } else {
                            showSettingDialog()
                        }
                    }
                }
            } else {
                //    Toast.makeText(applicationContext, "notification permission granted", Toast.LENGTH_SHORT) .show()
            }
        }

    private fun initUI() {


        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ).withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<PermissionRequest?>?,
                    token: PermissionToken?
                ) { /* ... */
                }
            }).check()

        binding.btnLogin.setOnClickListener {
            isValidCredentials(
                binding.edtEmail.text.toString(), binding.edtPassword.text.toString()
            )
        }

        binding.btnRegister.setOnClickListener {
            SignUpActivity.startActivity(this, "login")
        }
        binding.btnCancel.setOnClickListener {

        }


        binding.showPassBtn.setOnClickListener {
            if (binding.edtPassword.transformationMethod.equals(
                    PasswordTransformationMethod.getInstance()
                )
            ) {
                binding.edtPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.showPassBtn.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.edtPassword.context,
                        R.drawable.eye_open
                    )
                )

            } else {
                binding.edtPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.showPassBtn.setImageDrawable(
                    ContextCompat.getDrawable(
                        binding.edtPassword.context,
                        R.drawable.eye_close
                    )
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        binding.edtEmail.error = null //removes error
        binding.edtEmail.clearFocus() //clear focus from edittext
        binding.edtPassword.error = null //removes error
        binding.edtPassword.clearFocus() //clear focus from edittext
    }

    //initialise Observer
    private fun initObserver() {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.isLoading.observe(this) { aBoolean ->
            if (aBoolean) binding.content.progressLoading.visibility = View.VISIBLE
            else binding.content.progressLoading.visibility = View.GONE
        }

        loginViewModel.responseLoginData.observe(
            this
        ) { responseLogin: WebResponse<ResponseBody> ->
            if (responseLogin.status == com.eurosalud.pdp_cs.annotation.Status.SUCCESS) {


             //   Log.e("response",responseLogin.data.toString())
               // Log.e("get shared data---", sharedPreferences.isUserLogin().toString())

                val responseBody = responseLogin.data?.string() // Get the response body as a string

                // Check if the response body contains "1" (indicating correct credentials)
                if (responseBody?.contains("</style>") == true||responseBody?.contains("</script>") == true) {
                    Log.e("response","false")
                    Toast.makeText(this,"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("response","True")
                   // if(binding.edtEmail.text.toString().lowercase(Locale.ROOT) =="jrlozano"&&binding.edtPassword.text.toString().lowercase(Locale.ROOT) =="text"){
                     SharedPreferencesUtils.setUserLogin( true, binding.edtEmail.text.toString(), binding.edtPassword.text.toString() )
                      MainActivity.startActivity(this, binding.edtEmail.text.toString(), binding.edtPassword.text.toString())
               // }else{
                      //  Log.e("response","false")
                       // Toast.makeText(this,"Usuario o contraseña incorrectos",Toast.LENGTH_SHORT).show()
                   // }
                }



            }

            if (responseLogin.status == com.eurosalud.pdp_cs.annotation.Status.FAILURE) {
                Toast.makeText(this, responseLogin.errorMsg, Toast.LENGTH_SHORT).show()
            }

        }

    }
    private fun showSettingDialog() {
        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle("Permiso de notificación")
            .setMessage("Se requiere permiso de notificación. Permita el permiso de notificación desde la configuración.")
/*            .setMessage("Notification permission is required, Please allow notification permission from setting")*/
            .setPositiveButton("De acuerdo") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun showNotificationPermissionRationale() {

        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle("Alerta")
            .setMessage("Se requiere permiso de notificación para mostrar la notificación.")
   /*         .setMessage("Notification permission is required, to show notification")*/
            .setPositiveButton("De acuerdo") { _, _ ->
                if (Build.VERSION.SDK_INT >= 33) {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }


    private fun isValidCredentials(email: String, password: String): Boolean {

        if (email.isEmpty()) {
            binding.edtEmail.error = com.eurosalud.pdp_cs.annotation.Constants.ENTER_EMAIL
            binding.edtEmail.isFocusable = true
            binding.edtEmail.requestFocus()
            return false
        } else if (password.isEmpty()) {
            binding.edtPassword.error = com.eurosalud.pdp_cs.annotation.Constants.ENTER_PASSWORD
            binding.edtPassword.isFocusable = true
            binding.edtPassword.requestFocus()
            return false
        } else {
            binding.content.progressLoading.visibility = View.VISIBLE

            Log.w("fcm token", SharedPreferencesUtils.getFcmToken().toString())
            // loginApi()
            // call signIn API
            loginViewModel.callGetLoginApi(
                binding.edtEmail.text.toString(),
                binding.edtPassword.text.toString(),
                SharedPreferencesUtils.getFcmToken().toString(),
                "aaaaaa",
            )/* loginViewModel.NotiApi(
                 binding.edtEmail.text.toString(),
                 binding.edtPassword.text.toString(),
                 SharedPreferencesUtils.getFcmToken().toString(),
                 "aaaaaa"
             )*/

            return true
        }


    }

    private fun onTokenRefresh() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(com.eurosalud.pdp_cs.annotation.Constants.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            //    val msg = getString("msg_token_fmt", token)
            Log.d(com.eurosalud.pdp_cs.annotation.Constants.TAG, token)
            SharedPreferencesUtils.setFcmToken(token)

            Log.w("fcm token", SharedPreferencesUtils.getFcmToken().toString())
            //  Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })
    }


}


