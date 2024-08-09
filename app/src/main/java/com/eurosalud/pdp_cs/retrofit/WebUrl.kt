package com.eurosalud.pdp_cs.retrofit

interface WebUrl {
    companion object {


        //live url
        const val BASE_URL ="https://consulta.4hc.es/ws_pdp/API/"
        const val REGISTRATION_URL ="https://pdp.4hc.es/registro/registro_add.php?app=cs"
        const val HOME_URL ="https://pdp.4hc.es/login.php?"

        // APIs End Point
        const val API_LOGIN = "VincularPacienteDispositivo"
        //const val API_LOGIN = "ObtenerDatosPaciente"
        //const val API_LOGOUT = "DesvincularPacienteDispositivo"
        const val API_LOGOUT = "DesvincularPacienteDispositivo"

        const val API_GET_OFFICE_MEETING_LIST="meetwithoffice"
        const val API_GET_UPDATE_PARENT_PROFILE="update-parent-profile.php"


        //Parameters
        const val EMAIL="email"
        const val UsuarioPortal="usuario_portal"
        const val USER="user"
        const val PASSWORD="password"
        const val DeviceId="deviceid"
        const val ContrasenyaPortal="contrasenya_portal"
        const val STUDENT_ID="studentid"
        const val PARENT_ID="pid"
        const val MEET_ID="meet_id"
        const val P_ID="parentid"
        const val SUB="sub"
        const val MSG="msg"
        const val FCMToken="fcm_token"
        const val Dispositivo="dispositivo"
        const val Token="token"
        const val TITLE="title"
        const val SESSION_YEAR="sessionyear"
        const val DATE="date"
        const val MEETING_DATE="meetingdate"
        const val VCH_MEETING_TIME="vchmeetingtime"
        const val CUSTOMER_ID="customer_id"
        const val DELIVERY_WEEK="delivery_week"
        const val INTEVAL="interval"
        const val ID="id"
        const val FARM_ID="farmId"
        const val PRODUCTION_DATE="productionDate"
        const val SHIPMENT_DATE="shipmentDate"
        const val T2_DATE="t2Date"

    }

}