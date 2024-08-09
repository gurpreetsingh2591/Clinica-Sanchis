package com.eurosalud.pdp_cs.utils

import com.eurosalud.pdp_cs.annotation.Constants
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import kotlin.jvm.Throws

class AddHeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //Basic Auth
        val builder = chain.request().newBuilder()
        builder.addHeader(Constants.AUTH, Constants.AUTH_TOKEN)
        return chain.proceed(builder.build())
    }
}