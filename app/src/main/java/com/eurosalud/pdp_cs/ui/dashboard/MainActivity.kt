package com.eurosalud.pdp_cs.ui.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.util.Log
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.eurosalud.pdp_cs.databinding.ActivityMainBinding
import com.eurosalud.pdp_cs.retrofit.WebResponse
import com.eurosalud.pdp_cs.retrofit.WebUrl
import com.eurosalud.pdp_cs.ui.login.view.LoginActivity
import com.eurosalud.pdp_cs.ui.login.view_model.LoginViewModel
import com.eurosalud.pdp_cs.utils.SharedPreferencesUtils
import okhttp3.ResponseBody


class MainActivity : AppCompatActivity() {
    lateinit var mainBinding: ActivityMainBinding
    private var webView: WebView? = null
    private var userName: String = ""
    private var password: String = ""
    private lateinit var loginViewModel: LoginViewModel

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


    companion object {

        fun startActivity(activity: Activity?, user: String, password: String) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra("user", user)
            intent.putExtra("password", password)
            activity?.startActivity(intent)
            activity?.finishAffinity()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view: View = mainBinding.root
        setContentView(view)
        initUI()
        initObserver()
        if (Build.VERSION.SDK_INT >= 33) {
            notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            hasNotificationPermissionGranted = true
        }
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun initUI() {
        userName = intent.getStringExtra("user").toString()
        password = intent.getStringExtra("password").toString()

        mainBinding.tvLogout.setOnClickListener {
            showAlertDialog()

        }
        mainBinding.tvToken.text = SharedPreferencesUtils.getFcmToken().toString()
        Log.e("token",SharedPreferencesUtils.getFcmToken().toString())
        mainBinding.tvToken.setOnClickListener {
            // Get the text from the TextView
            val textToCopy =  mainBinding.tvToken.text

            // Get a reference to the ClipboardManager
            val clipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

            // Create a ClipData object to store the text to be copied
            val clipData = ClipData.newPlainText("label", textToCopy)

            // Copy the text to the clipboard
            clipboardManager.setPrimaryClip(clipData)

            // Show a toast message to indicate that the text has been copied
            Toast.makeText(this, "Text copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        // Enable JavaScript (if needed)
        val settings: WebSettings = mainBinding.webview.settings
        settings.javaScriptEnabled = true
        mainBinding.webview.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY

        mainBinding.webview.settings.javaScriptEnabled = true
        mainBinding.webview.settings.setSupportMultipleWindows(true)
        mainBinding.webview.settings.builtInZoomControls = true
        mainBinding.webview.webChromeClient = WebChromeClient()

        mainBinding.webview.settings.allowFileAccess = true;

        mainBinding.webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                mainBinding.progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                mainBinding.progressBar.visibility = View.GONE
            }

            @Deprecated("Deprecated in Java")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return if (url.endsWith(".pdf")) {
                    // Handle PDF link differently, e.g., open in an external PDF viewer
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                    true
                } else if (url.endsWith("p=573708&p2=5")) {
                    // mainBinding.webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+url)
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                    true
                } else {
                    // Load other URLs in the WebView
                    view.loadUrl(url)
                    true
                }
            }
        }
        mainBinding.webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                mainBinding.progressBar.progress = newProgress
            }
        }


        // Load a URL

        // Load a URL
        mainBinding.webview.loadUrl(WebUrl.HOME_URL + "username=" + userName + "&" + "password=" + password)
    }


    private fun showAlertDialog() {

        val builder = AlertDialog.Builder(this)

            .setTitle("Cerrar sesión")
            .setPositiveButton("Cerrar sesión") { dialog, _ ->

                SharedPreferencesUtils.setUserLogin(false, "", "")
                startActivity(Intent(this, LoginActivity::class.java))
                finishAffinity()

//                loginViewModel.callGetLogoutApi(
//                    SharedPreferencesUtils.getUserEmail().toString(),
//                    SharedPreferencesUtils.getUserPassword().toString(),
//                    SharedPreferencesUtils.getFcmToken().toString(),
//                    "aaaaaa"
//                )


                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                // Handle negative button click
                dialog.dismiss()
            }

        val alertDialog = builder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).isAllCaps = false
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).isAllCaps = false
    }

    override fun onBackPressed() {
        if (mainBinding.webview.canGoBack()) {
            mainBinding.webview.goBack()
        } else {
            super.onBackPressed()
        }
    }

    /*    override fun onBackPressed() {


            if (Build.VERSION.SDK_INT >= 34) {
                onBackInvokedDispatcher.registerOnBackInvokedCallback(
                    OnBackInvokedDispatcher.PRIORITY_DEFAULT
                ) {
                    if (mainBinding.webview.canGoBack()) {
                        mainBinding.webview.goBack()
                    }else {
                        finish()
                    }
                }
            } else {
                onBackPressedDispatcher.addCallback(this *//* lifecycle owner *//*,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        if (mainBinding.webview.canGoBack()) {
                            mainBinding.webview.goBack()
                        } else {
                            finish()
                        }
                    }
                })
        }


    }*/

    private fun showSettingDialog() {
        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle("Notification Permission")
            .setMessage("Notification permission is required, Please allow notification permission from setting")
            .setPositiveButton("Ok") { _, _ ->
                val intent = Intent(ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.parse("package:$packageName")
                startActivity(intent)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showNotificationPermissionRationale() {

        MaterialAlertDialogBuilder(
            this,
            com.google.android.material.R.style.MaterialAlertDialog_Material3
        )
            .setTitle("Alert")
            .setMessage("Notification permission is required, to show notification")
            .setPositiveButton("Ok") { _, _ ->
                if (Build.VERSION.SDK_INT >= 33) {
                    notificationPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    //initialise Observer
    private fun initObserver() {
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loginViewModel.isLoading.observe(this) { aBoolean ->
            if (aBoolean) mainBinding.content.progressLoading.visibility = View.VISIBLE
            else mainBinding.content.progressLoading.visibility = View.GONE
        }

        loginViewModel.responseLogoutData.observe(
            this
        ) { responseLogin: WebResponse<ResponseBody> ->
            if (responseLogin.status == com.eurosalud.pdp_cs.annotation.Status.SUCCESS) {


                val responseBody = responseLogin.data?.string() // Get the response body as a string

                // Check if the response body contains "1" (indicating correct credentials)
                if (responseBody?.contains("</style>") == true || responseBody?.contains("</script>") == true) {
                    Log.e("response", "false")
                    Toast.makeText(this, "Error al cerrar sesión", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("response", "True")
                    //Handle positive button click
                    SharedPreferencesUtils.setUserLogin(false, "", "")
                    startActivity(Intent(this, LoginActivity::class.java))
                    finishAffinity()
                }

            }

            if (responseLogin.status == com.eurosalud.pdp_cs.annotation.Status.FAILURE) {
                Toast.makeText(this, responseLogin.errorMsg, Toast.LENGTH_SHORT).show()
            }

        }

    }


}

