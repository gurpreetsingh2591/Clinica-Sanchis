package com.eurosalud.pdp_cs.exception

import java.io.IOException

/**
 * This is No internet exception class which directly implemented in service generator
 */

class NoInternetException : IOException() {

    override val message: String
        get() = com.eurosalud.pdp_cs.annotation.Constants.NO_INTERNET


}