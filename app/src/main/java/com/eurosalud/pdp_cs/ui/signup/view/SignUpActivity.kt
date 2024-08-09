package com.eurosalud.pdp_cs.ui.signup.view


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eurosalud.pdp_cs.retrofit.WebResponse
import com.eurosalud.pdp_cs.databinding.ActivitySignupBinding
import com.eurosalud.pdp_cs.retrofit.WebUrl
import com.eurosalud.pdp_cs.ui.signup.model.SignUpModel
import com.eurosalud.pdp_cs.ui.signup.view_model.SignUpViewModel
import com.eurosalud.pdp_cs.utils.Static


class SignUpActivity : AppCompatActivity() {

    private lateinit var signUpViewModel: SignUpViewModel
    private val static = Static()
    private lateinit var binding : ActivitySignupBinding
    companion object {

        fun startActivity(activity: Activity?, screen: String) {
            val intent = Intent(activity, SignUpActivity::class.java)
            intent.putExtra("screen", screen)
            activity?.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)
        initUI()
        initObserver()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initUI() {
        binding.ivBack.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            isValidCredentials( binding.edtDni.text.toString(),
                binding.edtName.text.toString(),
                binding.edtSurname.text.toString(),
                binding.edtPhone.text.toString(),
                binding.edtEmail.text.toString(),
                binding.edtCp.text.toString(),)
        }

        binding.webview.settings.apply {
            // Enable JavaScript, if needed
            javaScriptEnabled = true

            // Enable zooming if required
            builtInZoomControls = true
            displayZoomControls = false

            // Enable both vertical and horizontal scrolling
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            useWideViewPort = true
            loadWithOverviewMode = true
        }

        binding.webview.settings.javaScriptEnabled = true

        binding.webview.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.progressBar.visibility = View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                binding.progressBar.progress = newProgress
            }
        }



        // Load a URL

        // Load a URL
        binding.webview.loadUrl(WebUrl.REGISTRATION_URL)





    }

    override fun onPause() {
        super.onPause()
        binding.edtEmail.error = null //removes error
        binding.edtEmail.clearFocus() //clear focus from edittext
        binding.edtCp.error = null //removes error
        binding.edtCp.clearFocus() //clear focus from edittext
    }

    //initialise Observer
    private fun initObserver() {
        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        signUpViewModel.isLoading.observe(this) { aBoolean ->
            if (aBoolean)
                binding.content.progressLoading.visibility = View.VISIBLE
            else
                binding.content.progressLoading.visibility = View.GONE
        }

        signUpViewModel.responseSignUpData.observe(this
        ) { Response: WebResponse<SignUpModel> ->
            if (Response.status == com.eurosalud.pdp_cs.annotation.Status.SUCCESS) {


            }

            if (Response.status == com.eurosalud.pdp_cs.annotation.Status.FAILURE) {
                Toast.makeText(this, Response.errorMsg, Toast.LENGTH_SHORT).show()
            }

        }

    }


    private fun isValidCredentials(dni: String,name: String,surname: String,phone: String,email: String, cp: String): Boolean {

        if (dni.isEmpty()) {
            binding.edtDni.error = com.eurosalud.pdp_cs.annotation.Constants.ENTER_EMAIL
            binding.edtDni.isFocusable = true
            binding.edtDni.requestFocus()
            return false
        } else if (name.isEmpty()) {
            binding.edtName.error = com.eurosalud.pdp_cs.annotation.Constants.ENTER_EMAIL
            binding.edtName.isFocusable = true
            binding.edtName.requestFocus()
            return false
        }  else if (surname.isEmpty()) {
            binding.edtSurname.error = com.eurosalud.pdp_cs.annotation.Constants.ENTER_EMAIL
            binding.edtSurname.isFocusable = true
            binding.edtSurname.requestFocus()
            return false
        }  else if (phone.isEmpty()) {
            binding.edtPhone.error = com.eurosalud.pdp_cs.annotation.Constants.ENTER_EMAIL
            binding.edtPhone.isFocusable = true
            binding.edtPhone.requestFocus()
            return false
        } else if (email.isEmpty()) {
            binding.edtEmail.error = com.eurosalud.pdp_cs.annotation.Constants.ENTER_EMAIL
            binding.edtEmail.isFocusable = true
            binding.edtEmail.requestFocus()
            return false
        } else if (cp.isEmpty()) {
            binding.edtCp.error = com.eurosalud.pdp_cs.annotation.Constants.ENTER_PASSWORD
            binding.edtCp.isFocusable = true
            binding.edtCp.requestFocus()
            return false
        } else if (!static.isValidEmail(email)) {
            binding.edtEmail.error = com.eurosalud.pdp_cs.annotation.Constants.ENTER_VALID_EMAIL
            binding.edtEmail.isFocusable = true
            binding.edtEmail.requestFocus()
            return false
        } else {

            // call signIn API
         /*   signUpViewModel.callGetRegistrationApi(
                binding.edtDni.text.toString(),
                binding.edtName.text.toString(),
                binding.edtSurname.text.toString(),
                binding.edtPhone.text.toString(),
                binding.edtEmail.text.toString(),
                binding.edtCp.text.toString(),
                SharedPreferencesUtils.getFcmToken().toString()
            )
*/
            return true
        }


    }

}


